package com.rongdu.loans.koudai.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.HttpUtils;
import com.rongdu.common.utils.MD5Util;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.SexEnum;
import com.rongdu.loans.koudai.api.service.KDCreateApiService;
import com.rongdu.loans.koudai.common.KDBankEnum;
import com.rongdu.loans.koudai.op.create.KDOrderBaseOP;
import com.rongdu.loans.koudai.op.create.KDOrderOP;
import com.rongdu.loans.koudai.op.create.KDPeriodBaseOP;
import com.rongdu.loans.koudai.op.create.KDReceiveCardOP;
import com.rongdu.loans.koudai.op.create.KDRepaymentBaseOP;
import com.rongdu.loans.koudai.op.create.KDUserBaseOP;
import com.rongdu.loans.koudai.vo.create.KDCreateResultVO;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanRepayPlanManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
@Service("kDCreateApiService")
public class KDCreateApiServiceImpl implements KDCreateApiService {
	
	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;	
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;

	@Override
	public KDCreateResultVO createOrder(String applyId) {
		
		if(StringUtils.isBlank(applyId)){
			logger.error("申请id不能为空");
			throw new IllegalArgumentException("申请id不能为空");
		}
		
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser custUser = custUserManager.getById(loanApply.getUserId());
		
		
		String submitUrl = Global.getConfig("kd.create.submitUrl");
		String account = Global.getConfig("kd.create.projectName");
		String password = Global.getConfig("kd.create.password");
		
		HttpUtils httpUtils = new HttpUtils();
		
		KDOrderOP kdOrderOP = new KDOrderOP();
		
		kdOrderOP.setTimestamp(Long.valueOf(System.currentTimeMillis()/1000).intValue());
		kdOrderOP.setAccount(account);
		kdOrderOP.setId_number(custUser.getIdNo());
		//sign=md5(account+md5(pwd)+md5(id_number)+timestamp)
		String sign = MD5Util.string2MD5(kdOrderOP.getAccount()+MD5Util.string2MD5(password)+MD5Util.string2MD5(kdOrderOP.getId_number())+kdOrderOP.getTimestamp());
		
		kdOrderOP.setSign(sign);
		
		//用户信息
		KDUserBaseOP kdUserBaseOP = new KDUserBaseOP();
		kdUserBaseOP.setId_number(kdOrderOP.getId_number());
		kdUserBaseOP.setName(custUser.getRealName());
		kdUserBaseOP.setPhone(Long.parseLong(custUser.getMobile()));
		kdUserBaseOP.setProperty(SexEnum.getDesc(custUser.getSex()));
		//借款人类型(1:企业，2:个人)
		kdUserBaseOP.setType(StringUtils.isBlank(custUser.getType())?2:custUser.getType().equals("2")?1:2);
		
		kdOrderOP.setUser_base(kdUserBaseOP);
		
		//借款订单信息
		KDOrderBaseOP kdOrderBaseOP = new KDOrderBaseOP();
		kdOrderBaseOP.setOut_trade_no(loanApply.getId());
		//贷款金额，单位：分
		kdOrderBaseOP.setMoney_amount(loanApply.getApplyAmt().multiply(BigDecimal.valueOf(100)).intValue());
		//贷款方式(0:按天,1:按月,2:按年)   M-月、Q-季、Y-年、D-天
		kdOrderBaseOP.setLoan_method(loanApply.getRepayFreq().equals("D")?0:loanApply.getRepayFreq().equals("M")?1:loanApply.getRepayFreq().equals("Q")?1:loanApply.getRepayFreq().equals("Y")?2:-1);
		kdOrderBaseOP.setLoan_term(loanApply.getRepayFreq().equals("Q")?(3*loanApply.getTerm()):loanApply.getTerm());
		kdOrderBaseOP.setLoan_interests(loanApply.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
		kdOrderBaseOP.setApr(loanApply.getActualRate().multiply(BigDecimal.valueOf(100)).floatValue());
		kdOrderBaseOP.setOrder_time(kdOrderOP.getTimestamp());
		kdOrderBaseOP.setCounter_fee(loanApply.getServFee().multiply(BigDecimal.valueOf(100)).intValue());
				
		kdOrderOP.setOrder_base(kdOrderBaseOP);
		
		//打款银行卡
		KDReceiveCardOP kdReceiveCardOP = new KDReceiveCardOP();
		
		String bankId = KDBankEnum.getId(custUser.getBankCode());
		if(bankId != null){
			kdReceiveCardOP.setBank_id(Integer.parseInt(bankId));
		}else {
			logger.error("打款银行不能为空");
			throw new IllegalArgumentException("打款银行不能为空");
		}
		kdReceiveCardOP.setCard_no(custUser.getCardNo());
		kdReceiveCardOP.setName(custUser.getRealName());
		
		
		
		kdOrderOP.setReceive_card(kdReceiveCardOP);
		
		//扣款银行卡，不代扣可不传
//		KDDebitCardOP kdDebitCardOP = new KDDebitCardOP();
//		kdOrderOP.setDebit_card(kdDebitCardOP);
		
		
		
		//总还款信息		
		KDRepaymentBaseOP kdRepaymentBaseOP = new KDRepaymentBaseOP();
		kdRepaymentBaseOP.setRepayment_type(2);
		
		LoanRepayPlan loanRepayPlan = loanRepayPlanManager.getByApplyId(applyId);
		kdRepaymentBaseOP.setRepayment_amount(loanRepayPlan.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
		kdRepaymentBaseOP.setRepayment_time(Long.valueOf(loanRepayPlan.getLoanEndDate().getTime()/1000).intValue());
		kdRepaymentBaseOP.setPeriod(kdOrderBaseOP.getLoan_term());
		kdRepaymentBaseOP.setRepayment_principal(loanRepayPlan.getPrincipal().multiply(BigDecimal.valueOf(100)).intValue());
		kdRepaymentBaseOP.setRepayment_interest(loanRepayPlan.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
		
		
		kdOrderOP.setRepayment_base(kdRepaymentBaseOP);
		
		//还款计划信息
		List<KDPeriodBaseOP> kdPeriodBaseOPList = new ArrayList<>();
		
		/** 获取所有明细 */
		List<RepayPlanItem> repayPlanItemList = repayPlanItemManager.getByApplyId(applyId);
		KDPeriodBaseOP preBaseOP = null;
		for (RepayPlanItem repayPlanItem : repayPlanItemList) {
			preBaseOP = new KDPeriodBaseOP();
			preBaseOP.setPeriod(repayPlanItem.getThisTerm());
			preBaseOP.setPlan_repayment_money(repayPlanItem.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
			preBaseOP.setPlan_repayment_time(Long.valueOf(repayPlanItem.getRepayDate().getTime()/1000).intValue());
			preBaseOP.setPlan_repayment_principal(repayPlanItem.getPrincipal().multiply(BigDecimal.valueOf(100)).intValue());
			preBaseOP.setPlan_repayment_interest(repayPlanItem.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
			preBaseOP.setApr(kdOrderBaseOP.getApr());
			
			kdPeriodBaseOPList.add(preBaseOP);
		}
						
		kdOrderOP.setPeriod_base(kdPeriodBaseOPList);
		
		logger.info("{}-{}-请求地址：{}", "口袋", "创建订单", submitUrl);
		logger.info("{}-{}-请求报文：{}", "口袋", "创建订单", JsonMapper.toJsonString(kdOrderOP));
		String responseString =  httpUtils.postForJson(submitUrl,JsonMapper.toJsonString(kdOrderOP));
		logger.info("{}-{}-应答结果：{}", "口袋", "创建订单", responseString);
		KDCreateResultVO resultVO = (KDCreateResultVO) JsonMapper.fromJsonString(responseString,KDCreateResultVO.class);
		
		return resultVO;
	}

}
