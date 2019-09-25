package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StandardDesUtil;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.common.RequestDefaultHandler;
import com.rongdu.loans.common.ThirdApiDTO;
import com.rongdu.loans.common.dwd.DwdUtil;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.DWDServiceEnums;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.dwd.BankVerifyOP;
import com.rongdu.loans.loan.option.dwd.DWDResp;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.loan.vo.dwd.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
* @Title: DWDStatusFeedBackServiceImpl.java
* @Package com.rongdu.loans.loan.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author: yuanxianchu
* @date 2018年10月29日
* @version V1.0
*/
@Slf4j
@Service("dwdStatusFeedBackService")
public class DWDStatusFeedBackServiceImpl extends RequestDefaultHandler implements DWDStatusFeedBackService {

	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private DWDService dwdService;
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private BorrowInfoManager borrowInfoManager;
	@Autowired
	private CustUserManager custUserManager;

	@Override
	public OrderStatusVO pullOrderStatus(String orderNo) {
		log.debug("----------开始拉取【大王贷-订单状态】orderNo={}----------", orderNo);
		String applyId = dwdService.getApplyId(orderNo);
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		OrderStatusVO orderStatusVO = new OrderStatusVO();

		if (loanApply == null) {
			// 还未生成订单
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setUpdateTime(String.valueOf(new Date().getTime()));
			orderStatusVO.setOrderStatus(1);
			return orderStatusVO;
		}

		orderStatusVO.setChannelCode(loanApply.getChannelId());// 用于推送时区分不同APP来源

		Integer status = loanApply.getStatus();
		if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status)) {
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(100);// 审核通过
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
			//待放款
			Criteria criteria = new Criteria();
			criteria.add(Criterion.ne("push_status", ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue()));
			//criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_ONLINE.getValue()));
			criteria.and(Criterion.eq("apply_id", applyId));
			BorrowInfo borrowInfo = borrowInfoManager.getByCriteria(criteria);
			if (borrowInfo != null) {
				orderStatusVO.setOrderStatus(171);//171放款处理中
			}
		} else if (ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue().equals(status)) {
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(171);// 171放款处理中
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
		} else if (ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.MANUAL_RECHECK.getValue().equals(status)) {
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(120);// 审批处理中
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
		} else if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)) {
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(110);// 审核不通过
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
		} else if (ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue().equals(status)) {
			// 放款成功
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(170);// 放款成功
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
		} else if (ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
			// 贷款取消
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(169);// 贷款取消 大王贷贷款取消161状态，暂时改为放款失败169状态
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
		} else if (ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY.getValue().equals(status)) {
			// 贷款逾期
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(180);// 贷款逾期
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
		} else if (ApplyStatusLifeCycleEnum.REPAY.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.OVERDUE_REPAY.getValue().equals(status)) {
			// 贷款结清
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(200);// 贷款结清
			orderStatusVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime()));
		} else {
			orderStatusVO.setOrderNo(orderNo);
			orderStatusVO.setOrderStatus(1);
			orderStatusVO.setUpdateTime(String.valueOf(new Date().getTime()));
		}
		log.debug("----------结束拉取【大王贷-订单状态】orderNo={}----------{}", orderNo, orderStatusVO);
		return orderStatusVO;
	}

	@Override
	public AuditResultVO pullAudiResult(String orderNo) {
		log.debug("----------开始拉取【大王贷-审批状态】orderNo={}----------", orderNo);
		String applyId = dwdService.getApplyId(orderNo);
		AuditResultVO auditResultVO = new AuditResultVO();
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		if (loanApply == null) {
			// 还未生成订单
			auditResultVO.setOrderNo(orderNo);
			auditResultVO.setConclusion(30);// 审批处理中
			return auditResultVO;
		}

		auditResultVO.setChannelCode(loanApply.getChannelId());// 用于推送时区分不同APP来源

		Integer status = loanApply.getStatus();
		if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status)
				|| (status.intValue() > ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue() && status.intValue() !=  ApplyStatusLifeCycleEnum.CANCAL.getValue())) {
			auditResultVO.setOrderNo(orderNo);
			auditResultVO.setConclusion(10);// 审批通过
			auditResultVO.setAmountType(0);// 0=固定金额
			auditResultVO.setTermType(0);// 0=固定期限
			auditResultVO.setApprovalTime(String.valueOf(loanApply.getApproveTime().getTime()));
			auditResultVO.setApprovalAmount(loanApply.getApproveAmt().intValue());// 审批金额
			auditResultVO.setTermUnit(loanApply.getRepayFreq());// 期限单位（1=天，2=月，3=周，4=年）
			auditResultVO.setApprovalTerm(loanApply.getApproveTerm());// 审批期限
			if (loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28){
				auditResultVO.setTermUnit("W");// 期限单位（1=天，2=月，3=周，4=年）
				auditResultVO.setApprovalTerm(loanApply.getTerm());// 审批期限
			}
			auditResultVO.setProType(loanApply.getTerm() != null && loanApply.getTerm() > 1 ? 2:1);//1:单期 2:多期
			auditResultVO.setCreditDeadline(DateUtils.formatDate(DateUtils.addDay(loanApply.getApproveTime(), 7)));
			//审批结果有效期+7天

		} else if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)
		|| ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
			auditResultVO.setOrderNo(orderNo);
			auditResultVO.setConclusion(40);// 审批不通过
			auditResultVO.setReapply("0");//是否可再申请1-是，0-不可以
			//auditResultVO.setReapplytime(DateUtils.formatDate(DateUtils.addDay(loanApply.getApplyTime(), 60)));//可再申请的时间，yyyy-MM-dd
			Date refuseTime = loanApply.getApproveTime() == null ? loanApply.getUpdateTime() : loanApply.getApproveTime();
			auditResultVO.setRefuseTime(String.valueOf(refuseTime.getTime()));
			auditResultVO.setRemark(loanApply.getRemark() == null ? "" : loanApply.getRemark());
		} else if (ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.MANUAL_RECHECK.getValue().equals(status)) {
			auditResultVO.setOrderNo(orderNo);
			auditResultVO.setConclusion(30);// 审批处理中
		}
		log.debug("----------结束拉取【大王贷-审批状态】orderNo={}----------{}", orderNo, JSONObject.toJSONString(auditResultVO));
		return auditResultVO;
	}

	@Override
	public RepaymentPlanVO pullRepaymentPlan(String orderNo) {
		log.debug("----------开始拉取【大王贷-还款计划】orderNo={}----------",orderNo);
        String applyId = dwdService.getApplyId(orderNo);
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser custUser = custUserManager.getById(loanApply.getUserId());
		//LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
        //LoanRepayPlanVO repayPlan = loanRepayPlanService.getByApplyId(applyId);

        // 还款计划推送数据组合
        // 第一步：基础数据组合
        RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();

		repaymentPlanVO.setChannelCode(loanApply.getChannelId());// 用于推送时区分不同APP来源

        repaymentPlanVO.setOrderNo(orderNo);// 三方订单号
        repaymentPlanVO.setBankCard(custUser.getCardNo());// 银行卡号
        repaymentPlanVO.setOpenBank(custUser.getBankCode());// 银行名称

        //RepayDetailListOP op = new RepayDetailListOP();
        //op.setContNo(repayPlan.getContNo());
        //op.setIsDelaySettlement(2);
        //List<RepayDetailListVO> rpayDetailListVOList = repayPlanItemManager.repayDetailList(null, op);
		List<RepayPlanItem> planItems = repayPlanItemManager.getByApplyIdForApp(applyId);
		// 第二步：还款计划数据组合
        List<RepaymentPlanDetail> list = new ArrayList<>();
        for (RepayPlanItem planItem : planItems) {
        	RepaymentPlanDetail detail = new RepaymentPlanDetail();
        	detail.setPeriodNo(planItem.getThisTerm());//期数
        	detail.setBillStatus(planItem.getStatus(), planItem.getRepayDate());// 账单状态 1未到期；2已还款；3逾期
        	detail.setDueTime(String.valueOf(planItem.getRepayDate().getTime()));// 到期时间
        	detail.setCanRepayTime(String.valueOf(planItem.getCreateTime().getTime()));// 可以还款时间
//        	detail.setPayType("");
			detail.setPaidAmount(planItem.getActualRepayAmt()==null?BigDecimal.ZERO:planItem.getActualRepayAmt());// 已还金额
			detail.setAmount(planItem.getTotalAmount().subtract(detail.getPaidAmount()));// 剩余待还金额
			detail.setOverdueFee(planItem.getOverdueFee()==null?BigDecimal.ZERO:planItem.getOverdueFee());//逾期费用
			detail.setSuccessTime(planItem.getActualRepayTime() == null ? ""
					: String.valueOf(planItem.getActualRepayTime().getTime()));
        	detail.setRemark("");
        	if (detail.getBillStatus() == 2) {
        		detail.setRemark("含本金" + planItem.getPrincipal() + "元，利息&手续费" + planItem.getInterest() + "元，逾期" + planItem.getOverdueFee()
                    + "元");
			}
        	Map<String, BigDecimal> map = new HashMap<>();
        	map.put("1", planItem.getPrincipal());//本金
        	map.put("2", planItem.getInterest());//利息
        	map.put("3", planItem.getOverdueFee());//罚息
        	List<Billitem> billitems = new ArrayList<>();
        	for (Entry<String, BigDecimal> entry : map.entrySet()) {
        		Billitem billitem = new Billitem();
        		billitem.setFeetype(entry.getKey());
        		billitem.setDueamount(entry.getValue());
        		billitems.add(billitem);
			}
        	detail.setBillitem(billitems);
        	list.add(detail);
        }
        repaymentPlanVO.setRepaymentPlan(list);
        log.debug("----------结束拉取【大王贷-还款计划】oredrNo={}----------{}", orderNo,JSONObject.toJSONString(repaymentPlanVO));
		return repaymentPlanVO;
	}

	@Override
	public PaymentStatusVO pullPaymentStatus(String orderNo, String repayPlanItemId) {
		log.debug("----------开始拉取【大王贷-还款状态】orderNo={},repayPlanItemId={}----------",orderNo,repayPlanItemId);
		PaymentStatusVO vo = new PaymentStatusVO();
		RepayPlanItem repayPlanItem = repayPlanItemManager.getById(repayPlanItemId);
		if (repayPlanItem == null) {
			log.error("【大王贷-查询还款状态接口】异常-用户还款计划不存在repayPlanItemId={},orderNo={}",repayPlanItemId,orderNo);
			return null;
		}
		RepayLogVO repayLogVO = repayLogService.findByRepayPlanItemId(repayPlanItemId);
		if (repayLogVO == null) {
			log.warn("【大王贷-查询还款状态接口】异常-用户尚无还款记录repayPlanItemId={},orderNo={}",repayPlanItemId,orderNo);
			return null;
		}

		vo.setOrderNo(orderNo);
		vo.setPeriodNos(String.valueOf(repayPlanItem.getThisTerm()));
		vo.setRepayAmount(repayPlanItem.getTotalAmount());
		if ("SUCCESS".equals(repayLogVO.getStatus()) || "S".equals(repayLogVO.getStatus())) {
			vo.setRepayStatus(1);//1=还款成功
		}else if (isUnsolved(repayLogVO.getStatus())) {
			vo.setRepayStatus(0);//0=还款中
		}else {
			vo.setRepayStatus(2);//2=还款失败
		}
		if ("AUTH_PAY".equals(repayLogVO.getTxType())) {
			vo.setRepayPlace(1);//1=主动还款
		}else {
			vo.setRepayPlace(2);//2=自动代扣
		}
		vo.setSuccess_time(String.valueOf(repayLogVO.getTxTime()==null?"":repayLogVO.getTxTime().getTime()));
		log.debug("----------结束拉取【大王贷-还款状态】orderNo={},repayPlanItemId={}----------{}",orderNo,repayPlanItemId,JSONObject.toJSONString(vo));
		return vo;
	}

	@Override
	public DWDResp bankVerifyFeedBack(BankVerifyOP bankVerifyOP, DWDResp dwdResp) throws Exception {
		DWDResp resp = new DWDResp();
		BankResultVO vo = new BankResultVO();
		String applyId = dwdService.getApplyId(bankVerifyOP.getOrderNo());
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		log.debug("----------开始异步执行【大王贷-绑卡结果推送】applyId={},orderNo={}----------",applyId,bankVerifyOP.getOrderNo());
		vo.setOrderNo(bankVerifyOP.getOrderNo());
		vo.setBankCard(bankVerifyOP.getBankCard());
		BankVerifyVO bankVerifyVO = (BankVerifyVO) dwdResp.getData();
		String dealResult = "2";
		if (bankVerifyVO != null) {
			dealResult = bankVerifyVO.getDealResult();
		}
		if ("1".equals(dealResult)) {
			vo.setBindStatus(1);
		} else {
			vo.setBindStatus(2);
		}
		ThirdApiDTO thirdApiDTO = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(vo),
				DWDServiceEnums.DWD_BINDCARDFEEDBACK.getMethod());
		thirdApiDTO.setChannelCode(loanApply.getChannelId());
		resp = this.requestHandler(thirdApiDTO, DWDResp.class);
		log.debug("----------结束异步执行【大王贷-绑卡结果推送】applyId={},orderNo={},响应结果={}----------",applyId,bankVerifyOP.getOrderNo(),JSONObject.toJSONString(resp));
		return resp;
	}

	@Override
	public boolean approveFeedBack(String applyId){
		DWDResp approveResp = null;//推送审批结论 响应结果
		DWDResp orderResp = null;//推送订单状态 响应结果
		String orderNo = dwdService.getOrderNo(applyId);

		boolean flag = true;
		try {
			log.debug("----------开始异步执行【大王贷-审批结论，订单状态推送】applyId={},orderNo={}----------", applyId, orderNo);
			AuditResultVO auditResultVO = pullAudiResult(orderNo);
			if (30 == auditResultVO.getConclusion()) {
				log.debug("----------撤销异步执行【大王贷-审批结论，订单状态推送】applyId={},orderNo={}----------", applyId, orderNo);
				return flag;
			}
			// 推送审批结论
			ThirdApiDTO thirdApiDTO = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(auditResultVO),
					DWDServiceEnums.DWD_APPROVEFEEDBACK.getMethod());
			thirdApiDTO.setChannelCode(auditResultVO.getChannelCode());
			approveResp = this.requestHandler(thirdApiDTO, DWDResp.class);
			if (approveResp == null || !"200".equals(approveResp.getCode().toString())) {
				flag = false;
			}
			// 推送订单状态
			OrderStatusVO orderStatusVO = pullOrderStatus(orderNo);
			ThirdApiDTO thirdApiDTO2 = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(orderStatusVO),
					DWDServiceEnums.DWD_ORDERFEEDBACK.getMethod());
			thirdApiDTO2.setChannelCode(orderStatusVO.getChannelCode());
			orderResp = this.requestHandler(thirdApiDTO2, DWDResp.class);
			if (orderResp == null || !"200".equals(orderResp.getCode().toString())) {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			log.error("----------【大王贷-审批结论，订单状态推送】异常applyId={},orderNo={},推送审批结论响应结果={},推送订单状态响应结果={}----------", applyId, orderNo,
					JSONObject.toJSONString(approveResp),JSONObject.toJSONString(orderResp),e);
		}
		log.debug("----------结束异步执行【大王贷-审批结论，订单状态推送】applyId={},orderNo={},推送审批结论响应结果={},推送订单状态响应结果={}----------", applyId, orderNo,
				JSONObject.toJSONString(approveResp),JSONObject.toJSONString(orderResp));
		return flag;
	}

	@Override
	public boolean lendFeedBack(String applyId){
		DWDResp orderResp = null;// 推送订单状态 响应结果
		DWDResp planResp = null;// 推送还款计划 响应结果
		String orderNo = dwdService.getOrderNo(applyId);

		boolean flag = true;
		try {
			log.debug("----------开始异步执行【大王贷-订单状态，还款计划推送】applyId={},orderNo={}----------", applyId, orderNo);
			// 推送订单状态
			OrderStatusVO orderStatusVO = pullOrderStatus(orderNo);
			ThirdApiDTO thirdApiDTO = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(orderStatusVO),
					DWDServiceEnums.DWD_ORDERFEEDBACK.getMethod());
			thirdApiDTO.setChannelCode(orderStatusVO.getChannelCode());
			orderResp = this.requestHandler(thirdApiDTO, DWDResp.class);
			if (orderResp == null || !"200".equals(orderResp.getCode().toString())) {
				flag = false;
			}
			// 推送还款计划
			RepaymentPlanVO repaymentPlanVO = pullRepaymentPlan(orderNo);
			ThirdApiDTO thirdApiDTO2 = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(repaymentPlanVO),
					DWDServiceEnums.DWD_PUSHREPAYMENT.getMethod());
			thirdApiDTO2.setChannelCode(repaymentPlanVO.getChannelCode());
			planResp = this.requestHandler(thirdApiDTO2, DWDResp.class);
			if (planResp == null || !"200".equals(planResp.getCode().toString())) {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			log.error("----------【大王贷-订单状态，还款计划推送】异常applyId={},orderNo={},推送订单状态响应结果={},推送还款计划响应结果={}----------",
					applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(planResp),e);
		}
		log.debug("----------结束异步执行【大王贷-订单状态，还款计划推送】applyId={},orderNo={},推送订单状态响应结果={},推送还款计划响应结果={}----------",
				applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(planResp));
		return flag;
	}

	@Override
	public boolean overdueFeedBack(String applyId){
		return lendFeedBack(applyId);
	}

	@Override
	public boolean settlementFeedBack(String applyId, String repayPlanItemId){
		DWDResp orderResp = null;// 推送订单状态 响应结果
		DWDResp payStatusResp = null;// 推送还款状态 响应结果
		DWDResp planResp = null;// 推送还款计划 响应结果
		String orderNo = dwdService.getOrderNo(applyId);

		boolean flag = true;
		try {
			log.debug("----------开始异步执行【大王贷-订单状态，还款状态，还款计划推送】applyId={},orderNo={}----------", applyId, orderNo);
			// 推送订单状态
			OrderStatusVO orderStatusVO = pullOrderStatus(orderNo);
			ThirdApiDTO thirdApiDTO = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(orderStatusVO),
					DWDServiceEnums.DWD_ORDERFEEDBACK.getMethod());
			thirdApiDTO.setChannelCode(orderStatusVO.getChannelCode());
			orderResp = this.requestHandler(thirdApiDTO, DWDResp.class);
			if (orderResp == null || !"200".equals(orderResp.getCode().toString())) {
				flag = false;
			}
			// 推送还款状态
			PaymentStatusVO paymentStatusVO = pullPaymentStatus(orderNo, repayPlanItemId);
			ThirdApiDTO thirdApiDTO2 = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(paymentStatusVO),
					DWDServiceEnums.DWD_REPAYFEEDBACK.getMethod());
			thirdApiDTO2.setChannelCode(orderStatusVO.getChannelCode());
			payStatusResp = this.requestHandler(thirdApiDTO2, DWDResp.class);
			if (payStatusResp == null || !"200".equals(payStatusResp.getCode().toString())) {
				flag = false;
			}
			// 推送还款计划
			RepaymentPlanVO repaymentPlanVO = pullRepaymentPlan(orderNo);
			ThirdApiDTO thirdApiDTO3 = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(repaymentPlanVO),
					DWDServiceEnums.DWD_PUSHREPAYMENT.getMethod());
			thirdApiDTO3.setChannelCode(repaymentPlanVO.getChannelCode());
			planResp = this.requestHandler(thirdApiDTO3, DWDResp.class);
			if (planResp == null || !"200".equals(planResp.getCode().toString())) {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			log.error(
					"----------【大王贷-订单状态，还款状态，还款计划推送】异常applyId={},orderNo={},推送订单状态响应结果={},推送还款状态响应结果={},推送还款计划响应结果={}----------",
					applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(payStatusResp),
					JSONObject.toJSONString(planResp), e);
		}
		log.debug(
				"----------结束异步执行【大王贷-订单状态，还款状态，还款计划推送】applyId={},orderNo={},推送订单状态响应结果={},推送还款状态响应结果={},推送还款计划响应结果={}----------",
				applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(payStatusResp),
				JSONObject.toJSONString(planResp));
		return flag;
	}

	@Override
	public boolean orderStatusFeedback(String orderNo, boolean flag) throws Exception{
		// 推送订单状态
		OrderStatusVO orderStatusVO = pullOrderStatus(orderNo);
		ThirdApiDTO thirdApiDTO = new ThirdApiDTO(DwdUtil.gateWay, JsonMapper.toJsonString(orderStatusVO),
				DWDServiceEnums.DWD_ORDERFEEDBACK.getMethod());
		thirdApiDTO.setChannelCode(orderStatusVO.getChannelCode());
		DWDResp orderResp = this.requestHandler(thirdApiDTO, DWDResp.class);
		if (orderResp == null || !"200".equals(orderResp.getCode().toString())) {
			flag = false;
		}
		return flag;
	}

	@Override
	public Map<String, String> createPostParam(ThirdApiDTO thirdApiDTO) throws Exception {
		Map<String, String> params = new HashMap<>();

		String privateKey = DwdUtil.partnerPrivateKey;
		DWDServiceEnums dwdServiceEnums = DWDServiceEnums.get(thirdApiDTO.getServiceName());

		// 拼装网关参数
		params.put("biz_data", thirdApiDTO.getData());
		params.put("method", thirdApiDTO.getServiceName());
		params.put("sign_type", dwdServiceEnums.getSignType());
		params.put("version", dwdServiceEnums.getVersion());
		params.put("format", "json");
		params.put("app_id", DwdUtil.getAppId(DwdUtil.ChannelParse.lookupByChannelCode(thirdApiDTO.getChannelCode())));
		params.put("timestamp", String.valueOf(new Date().getTime()));

		String paramStr = StandardDesUtil.getSignParamStr(JSONObject.toJSONString(params));
		String sign = StandardDesUtil.sign(paramStr, privateKey);
		params.put("sign", sign);

		return params;
	}

	private static boolean isUnsolved(String status) {
		if (StringUtils.isBlank(status)) {
			return false;
		}
		String[] statusArr = { "BF00100", "BF00112", "BF00113", "BF00115", "BF00144", "BF00202", "I" };
		for (String str : statusArr) {
			if (status.equals(str)) {
				return true;
			}
		}
		return false;
	}

}
