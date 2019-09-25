package com.rongdu.loans.tongrong.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JRandomUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.entity.CustUserInfo;
import com.rongdu.loans.cust.manager.CustUserInfoManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.KDBankEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.tongrong.api.service.TRPayApiService;
import com.rongdu.loans.tongrong.common.TRPurposeEnum;
import com.rongdu.loans.tongrong.common.TRRepayMethodEnum;
import com.rongdu.loans.tongrong.entity.TongrongPayLog;
import com.rongdu.loans.tongrong.manager.TongrongPayLogManager;
import com.rongdu.loans.tongrong.op.TRLoanOrder;
import com.rongdu.loans.tongrong.op.TRPayOP;
import com.rongdu.loans.tongrong.op.TRUserBasicInfo;
import com.rongdu.loans.tongrong.op.TongrongPayLogOP;
import com.rongdu.loans.tongrong.service.TRPayService;
import com.rongdu.loans.tongrong.vo.TRPayVO;
import com.rongdu.loans.tongrong.vo.TongrongPayLogVO;

@Service("tRPayService")
public class TRPayServiceImpl implements TRPayService {
	
	public static final Logger logger = LoggerFactory.getLogger(TRPayServiceImpl.class);
	
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private CustUserInfoManager custUserInfoManager;
	@Autowired
	private TongrongPayLogManager tongrongPayLogManager;
	@Autowired
	private TRPayApiService tRPayApiService;
	
	@Override
	public TRPayVO pay(String applyId) {
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser user = custUserManager.getById(loanApply.getUserId());
		CustUserInfo userInfo = custUserInfoManager.getById(loanApply.getUserId());
		if (StringUtils.isBlank(user.getBankCode())) {
			throw new RuntimeException("通融放款失败，未绑定银行卡," + applyId);
		}
		if (tongrongPayLogManager.countByApplyId(applyId) > 0) {
			throw new RuntimeException("已存在通融放款记录," + applyId);
		}
		TRPayOP op = new TRPayOP();
		op.setRequestNo(applyId + JRandomUtils.getRandomNumStr(30 - applyId.length()));
		op.setOrderId(applyId);
		TRUserBasicInfo userBasicInfo = new TRUserBasicInfo();
		userBasicInfo.setBankCardName(user.getBankCode());
		userBasicInfo.setBankCardNo(user.getCardNo());
		userBasicInfo.setIdAddress(userInfo.getRegAddr());
		userBasicInfo.setIdNo(user.getIdNo());
		userBasicInfo.setMobileNo(user.getMobile());
		userBasicInfo.setName(user.getRealName());
		op.setUserBasicInfo(userBasicInfo);
		TRLoanOrder loanOrder = new TRLoanOrder();
		loanOrder.setAccount(loanApply.getApproveAmt().subtract(loanApply.getServFee()));
		loanOrder.setApplyDate(loanApply.getApplyDate().toString());
		loanOrder.setLoanDays(loanApply.getApproveTerm());
		loanOrder.setLoanMonths(0);
		loanOrder.setPurpose(TRPurposeEnum.CODE_2.getCode());
		loanOrder.setRate(loanApply.getActualRate());
		loanOrder.setRepayment(TRRepayMethodEnum.CODE_1.getCode());
		op.setLoanOrder(loanOrder);
		TRPayVO vo = tRPayApiService.pay(op);
		
		savePayLog(loanApply, user, op, vo);
		return vo;
	}

	private int savePayLog(LoanApply loanApply, CustUser user, TRPayOP op, TRPayVO vo) {
		TongrongPayLog entity = new TongrongPayLog();
		entity.setApplyId(loanApply.getId());
		entity.setUserId(user.getId());
		entity.setUserName(user.getRealName());
		entity.setMobile(user.getMobile());
		entity.setIdNo(user.getIdNo());
		entity.setBankCode(user.getBankCode());
		entity.setBankName(KDBankEnum.getName(user.getBankCode()));
		entity.setCardNo(user.getCardNo());
		entity.setPayAmt(op.getLoanOrder().getAccount());
		entity.setPayTime(new Date());
		entity.setRequestNo(op.getRequestNo());
		if (null != vo) {
			entity.setPayFailCount("200".equals(vo.getCode()) ? 0 : 1);// 放款失败次数
			entity.setPayStatus("200".equals(vo.getCode()) ? 0 : 1);// 0=成功,1=失败
			entity.setContractMsg(vo.getMessage());
			if (null != vo.getData()) {
				entity.setPaySuccTime(new Date());
				Map<String,String> result = (Map) JsonMapper.fromJsonString(JsonMapper.toJsonString(vo.getData()), Map.class);
				entity.setContractId(result.get("contractId"));
				entity.setContractUrl(result.get("pdfUrl"));
				entity.setContractMsg(result.get("code"));
			}
		} else {
			entity.setPayFailCount(1);// 放款失败次数
			entity.setPayStatus(1);// 0=成功,1=失败
			entity.setContractMsg("系统异常");
		}
		return tongrongPayLogManager.insert(entity);
	}

	
	
	@SuppressWarnings({ "rawtypes" })
	public Page findList(Page page, TongrongPayLogOP op) {
		List<TongrongPayLog> list = tongrongPayLogManager.findList(page, op);
		if (CollectionUtils.isEmpty(list)) {
			page.setList(Collections.emptyList());
			return page;
		}
		List<TongrongPayLogVO> list2 = BeanMapper.mapList(list, TongrongPayLogVO.class);
		page.setList(list2);
		return page;
	}

	
	
	@Override
	public AdminWebResult adminPay(String payLogId) {
		TongrongPayLog log = tongrongPayLogManager.get(payLogId);
		if (log.getPayStatus().intValue() != 1) {
			return new AdminWebResult("99", "放款订单状态异常");
		}
		int failCount = log.getPayFailCount();
		if (failCount >= 2) {
			return new AdminWebResult("99", "失败次数超限，请次日对账看放款结果");
		}
		long pastMinute = DateUtils.pastMinutes(log.getPayTime());
		if (pastMinute < 30) {
			return new AdminWebResult("99", "半个小时后重新发起放款操作");
		}
		LoanApply loanApply = loanApplyManager.getLoanApplyById(log.getApplyId());
		if (loanApply != null && loanApply.getStatus() != XjdLifeCycle.LC_RAISE_1) {
			return new AdminWebResult("99", "申请单状态异常");
		}

		CustUser user = custUserManager.getById(loanApply.getUserId());
		CustUserInfo userInfo = custUserInfoManager.getById(loanApply.getUserId());
		TRPayOP op = new TRPayOP();
			op.setRequestNo(log.getRequestNo());
			op.setOrderId(loanApply.getId());
				TRUserBasicInfo userBasicInfo = new TRUserBasicInfo();
				userBasicInfo.setBankCardName(user.getBankCode());
				userBasicInfo.setBankCardNo(user.getCardNo());
				userBasicInfo.setIdAddress(userInfo.getRegAddr());
				userBasicInfo.setIdNo(user.getIdNo());
				userBasicInfo.setMobileNo(user.getMobile());
				userBasicInfo.setName(user.getRealName());
			op.setUserBasicInfo(userBasicInfo);
				TRLoanOrder loanOrder = new TRLoanOrder();
				loanOrder.setAccount(loanApply.getApproveAmt().subtract(loanApply.getServFee()));
				loanOrder.setApplyDate(loanApply.getApplyDate().toString());
				loanOrder.setLoanDays(loanApply.getApproveTerm());
				loanOrder.setLoanMonths(0);
				loanOrder.setPurpose(TRPurposeEnum.CODE_2.getCode());
				loanOrder.setRate(loanApply.getActualRate());
				loanOrder.setRepayment(TRRepayMethodEnum.CODE_1.getCode());
			op.setLoanOrder(loanOrder);
		TRPayVO vo = tRPayApiService.pay(op);

		log.setPayTime(new Date());
		log.setPayFailCount("200".equals(vo.getCode()) ? 0 : 1);// 放款失败次数
		log.setPayStatus("200".equals(vo.getCode()) ? 0 : 1);// 0=成功,1=失败
		log.setPayMsg(vo.getMessage());

		int rz = tongrongPayLogManager.update(log);
		return new AdminWebResult(String.valueOf(rz), rz == 1 ? "提交成功" : "提交失败");
	}


	@Override
	public AdminWebResult adminCancel(String payLogId) {
		TongrongPayLog log = tongrongPayLogManager.get(payLogId);

		LoanApply loanApply = loanApplyManager.getLoanApplyById(log.getApplyId());

		logger.info("取消订单: {},{},{}", log.getApplyId(), loanApply.getStatus(),
				ApplyStatusLifeCycleEnum.CANCAL.getValue());
		loanApply.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
		loanApply.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
		loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
		loanApplyManager.updateLoanApplyInfo(loanApply);

		log.setPayStatus(3);
		int rz = tongrongPayLogManager.update(log);
		return new AdminWebResult(String.valueOf(rz), rz == 1 ? "提交成功" : "提交失败");
	}
	
	
	
	
}
