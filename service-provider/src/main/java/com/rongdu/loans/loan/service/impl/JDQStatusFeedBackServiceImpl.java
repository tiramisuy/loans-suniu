package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.jdq.JdqUtil;
import com.rongdu.loans.common.rong360.HttpClientUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.jdq.JDQBankCardInfo;
import com.rongdu.loans.loan.option.jdq.JDQOrderStatusFeedBackVO;
import com.rongdu.loans.loan.option.jdq.JDQRepaymentPlan;
import com.rongdu.loans.loan.option.jdq.JDQResp;
import com.rongdu.loans.loan.service.JDQService;
import com.rongdu.loans.loan.service.JDQStatusFeedBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**  
* @Title: JDQStatusFeedBackServiceImpl.java  
* @Package com.rongdu.loans.loan.service.impl  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月12日  
* @version V1.0  
*/
@Slf4j
@Service("jdqStatusFeedBackService")
public class JDQStatusFeedBackServiceImpl implements JDQStatusFeedBackService{
	
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private BorrowInfoManager borrowInfoManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private JDQService jdqService;
	@Autowired
	private CustUserManager custUserManager;

	@Override
	public boolean orderStatusFeedBack(String applyId) {
		JDQResp resp = null;//订单状态推送 响应结果
		String orderId = jdqService.getOrderNo(applyId);
		
		boolean flag = true;
		try {
			log.debug("----------开始异步执行【借点钱-订单状态推送】applyId={},jdqOrderId={}----------",applyId,orderId);
			//订单数据组合
			JDQOrderStatusFeedBackVO vo = pullOrderStatus(orderId);
			if ("0".equals(vo.getStatus())) {
				log.debug("----------撤销异步执行【借点钱-订单状态推送】applyId={},jdqOrderId={}----------",applyId,orderId);
				return flag;
			}
			String url = Global.getConfig("jdq_gateWay");
			String paramJson = JdqUtil.partnerEncode(JsonMapper.toJsonString(vo), "feedbackOrderStatus", vo.getChannelCode());
//			JSONObject jsonObject = JSONObject.parseObject(paramJson);
//			Map<String, String> params = new HashMap<>();
//			params.put("data", jsonObject.getString("data"));
//			params.put("sign", jsonObject.getString("sign"));
//			params.put("channel_code", JdqUtil.channelCode);
//			params.put("call", "feedbackOrderStatus");
			String result = HttpClientUtils.postForJson(url, null, paramJson);
			log.debug("orderId={}-url={}反馈响应结果：{}",orderId,url,result);
			resp = JSONObject.parseObject(result, JDQResp.class);
			
			if (resp == null || !"0".equals(resp.getCode())) {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			log.error("----------【借点钱-订单状态推送】异常applyId={},jdqOrderId={},响应结果={}----------",applyId,orderId,JSONObject.toJSONString(resp),e);
		}
        log.debug("----------结束异步执行【借点钱-订单状态推送】applyId={},jdqOrderId={},响应结果={}----------",applyId,orderId,JSONObject.toJSONString(resp));
		return flag;
	}

	@Override
	public JDQOrderStatusFeedBackVO pullOrderStatus(String orderId) {
		String loanApplyId = jdqService.getApplyId(orderId);
		if (StringUtils.isBlank(loanApplyId)) {
			log.warn("【借点钱】订单尚未进件！jdqOrderId={}",orderId);
		}
		log.debug("----------开始拉取【借点钱-订单状态】applyId={},jdqOrderId={}----------",loanApplyId,orderId);
		LoanApply loanApply = loanApplyManager.getLoanApplyById(loanApplyId);
		JDQOrderStatusFeedBackVO vo = new JDQOrderStatusFeedBackVO();
		//订单状态
		vo.setJdqOrderId(orderId);
		vo.setChannelOrderId(loanApplyId);
		vo.setChangeCardFlag(1);  //是否可换卡，1-是，0-否
		if (loanApply == null) {
			vo.setStatus("22");//22:待绑卡 (前置绑卡流程)
			return vo;
		}
		vo.setChannelCode(loanApply.getChannelId());
		Integer status = loanApply.getStatus();
		if (ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(status) 
				|| ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.MANUAL_RECHECK.getValue().equals(status)) {
			//待审批
			vo.setStatus("0");//0:待审核
		}else if (ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
			//取消审批
			vo.setStatus("1");//1:已取消
		}else if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status)) {
			//审批通过
			CustUser custUser = custUserManager.getById(loanApply.getUserId());
			vo.setStatus("4");//3:审核成功
			vo.setApprovalAmount(loanApply.getApproveAmt());//审批金额，单位元
			vo.setApprovaPperiods(loanApply.getTerm());//审批期数
			vo.setApprovalPeriodDays(loanApply.getRepayUnit().intValue());//审批每期天数
			vo.setApprovaldays(loanApply.getApproveTerm());//审批总天数
			vo.setChangeCardFlag(1);  //是否可换卡，1-是，0-否
			vo.setWithdrawFlag(0); //0-不需要存管账户提款，1-需要去存管账户提款，不需要再次去提时需再传0过来。放款后必传
			vo.setAutopayFlag(1); //1-支持还款账户于还款日进行自动划扣（如果机构支持主动还款，用户也可进行主动还款）。 2-不支持还款账户于还款日进行自动划扣(机构必须支持用户主动还款)。 放款后必传
			//组装银行卡信息
			if (StringUtils.isNotBlank(custUser.getBankCode()) && StringUtils.isNotBlank(custUser.getCardNo())){
				JDQBankCardInfo bankCardInfo = new JDQBankCardInfo();
				bankCardInfo.setBankCode(custUser.getBankCode());
				bankCardInfo.setBankName(BankCodeEnum.getName(custUser.getBankCode()));
				bankCardInfo.setCardNo(custUser.getCardNo());
				bankCardInfo.setCardType("1");
				List<JDQBankCardInfo> list = new ArrayList<>();
				list.add(bankCardInfo);
				vo.setJdqBankCardInfo(list);
			}
			//待放款
			Criteria criteria = new Criteria();
			//criteria.add(Criterion.ne("push_status", ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue()));
			criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue()));
			criteria.and(Criterion.eq("apply_id", loanApplyId));
			BorrowInfo borrowInfo = borrowInfoManager.getByCriteria(criteria);
			if (borrowInfo != null) {
				vo.setStatus("6");//6:待放款
			}
		}else if (ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue().equals(status)||ApplyStatusLifeCycleEnum.WAITING_LENDING.getValue().equals(status)) {
			vo.setStatus("6");//6:待放款
			vo.setApprovalAmount(loanApply.getApproveAmt());//审批金额，单位元
			vo.setApprovaPperiods(loanApply.getTerm());//审批期数
			vo.setApprovalPeriodDays(loanApply.getRepayUnit().intValue());//审批每期天数
			vo.setApprovaldays(loanApply.getApproveTerm());//审批总天数
			vo.setChangeCardFlag(0);  //是否可换卡，1-是，0-否
		}else if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)) {
			//审核不通过
			vo.setStatus("2");//2:审核失败
			vo.setApprovalAmount(loanApply.getApproveAmt());//审批金额，单位元
			vo.setApprovaPperiods(loanApply.getTerm());//审批期数
			vo.setApprovalPeriodDays(loanApply.getRepayUnit().intValue());//审批每期天数
			vo.setApprovaldays(loanApply.getApproveTerm());//审批总天数
		}else if (ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.WAITING_REPAY.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY.getValue().equals(status)) {
			// 放款成功
			vo.setApprovalAmount(loanApply.getApproveAmt());//审批金额，单位元
			vo.setApprovaPperiods(loanApply.getTerm());//审批期数
			vo.setApprovalPeriodDays(loanApply.getRepayUnit().intValue());//审批每期天数
			vo.setApprovaldays(loanApply.getApproveTerm());//审批总天数
			vo.setLoanAmount(loanApply.getApproveAmt());  //确认借款/合同金额，单位元，确认借款后必传
			vo.setLoanPeriods(loanApply.getTerm()); //确认借款/合同期数，确认借款后必传
			vo.setLoanPeriodDays(loanApply.getRepayUnit().intValue()); //确认借款/合同每期天数，确认借款后必传（月按30计算）
			vo.setCardAmount(loanApply.getApproveAmt().multiply(new BigDecimal(0.75)));  //实际到手金额，确认借款后必传
			vo.setChangeCardFlag(1);  //是否可换卡，1-是，0-否
			vo.setWithdrawFlag(0); //0-不需要存管账户提款，1-需要去存管账户提款，不需要再次去提时需再传0过来。放款后必传
            vo.setAutopayFlag(1); //1-支持还款账户于还款日进行自动划扣（如果机构支持主动还款，用户也可进行主动还款）。 2-不支持还款账户于还款日进行自动划扣(机构必须支持用户主动还款)。 放款后必传
			vo.setStatus("7");//7:已放款
			//组装还款计划
			vo = setJQDRepaymentPlan(loanApplyId, vo);
		}else if (ApplyStatusLifeCycleEnum.REPAY.getValue().equals(status)
				||ApplyStatusLifeCycleEnum.OVERDUE_REPAY.getValue().equals(status)) {
			//贷款结清
			vo.setApprovalAmount(loanApply.getApproveAmt());//审批金额，单位元
			vo.setApprovaPperiods(loanApply.getTerm());//审批期数
			vo.setApprovalPeriodDays(loanApply.getRepayUnit().intValue());//审批每期天数
			vo.setApprovaldays(loanApply.getApproveTerm());//审批总天数
			vo.setLoanAmount(loanApply.getApproveAmt());  //确认借款/合同金额，单位元，确认借款后必传
			vo.setLoanPeriods(loanApply.getTerm()); //确认借款/合同期数，确认借款后必传
			vo.setLoanPeriodDays(loanApply.getRepayUnit().intValue()); //确认借款/合同每期天数，确认借款后必传（月按30计算）
			vo.setCardAmount(loanApply.getApproveAmt().multiply(new BigDecimal(0.75)));  //实际到手金额，确认借款后必传
			vo.setChangeCardFlag(1);  //是否可换卡，1-是，0-否
			vo.setStatus("8");//8:已还清
			//组装还款计划
			vo = setJQDRepaymentPlan(loanApplyId, vo);
		}else {
			vo.setStatus("13");//12:已坏账
		}
		log.debug("----------结束拉取【借点钱-订单状态】applyId={},jdqOrderId={},结果={}----------",loanApplyId,orderId,vo);
		return vo;
	}
	
	private JDQOrderStatusFeedBackVO setJQDRepaymentPlan(String applyId,JDQOrderStatusFeedBackVO vo) {
		//组装还款计划
		List<JDQRepaymentPlan> list = new ArrayList<>();
		//LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser custUser = custUserManager.getById(loanApply.getUserId());

		List<RepayPlanItem> planItems = repayPlanItemManager.getByApplyIdForApp(applyId);
        for (RepayPlanItem planItem : planItems) {
        	JDQRepaymentPlan jdqRepaymentPlan = new JDQRepaymentPlan();
        	jdqRepaymentPlan.setAmount(planItem.getPrincipal());//本期还款本金，单位元
			jdqRepaymentPlan.setOverdue(0);// 是否逾期，0未逾期，1逾期
			Integer overdue = null;
			if (planItem.getStatus().equals(1)) {
				overdue = DateUtils.daysBetween(planItem.getRepayDate(), planItem.getActualRepayTime());
				overdue = overdue > 0 ? overdue : 0;
			} else {
				overdue = DateUtils.daysBetween(planItem.getRepayDate(), new Date());
				overdue = overdue > 0 ? overdue : 0;
			}
			if (overdue > 0){
				jdqRepaymentPlan.setOverdue(1);// 是否逾期，0未逾期，1逾期
			}

			jdqRepaymentPlan.setOverdueFee(planItem.getPenalty().add(planItem.getOverdueFee()));// 逾期罚款，单位元
			jdqRepaymentPlan.setOverdueDay(overdue);// 逾期天数
        	jdqRepaymentPlan.setPeriod(planItem.getThisTerm());//期数
        	jdqRepaymentPlan.setPeriodFee(planItem.getInterest());//本期手续（利息）费，单位元
        	jdqRepaymentPlan.setStatus(planItem.getStatus(), planItem.getRepayDate(), planItem.getActualRepayTime());
        	jdqRepaymentPlan.setPlanRepaymentTime(DateUtils.formatDate(planItem.getRepayDate()));
        	jdqRepaymentPlan.setTrueRepaymentTime(planItem.getActualRepayDate());
        	list.add(jdqRepaymentPlan);
		}
        vo.setJdqRepaymentPlan(list);
        
        //组装银行卡信息
        JDQBankCardInfo bankCardInfo = new JDQBankCardInfo();
        bankCardInfo.setBankCode(custUser.getBankCode());
        bankCardInfo.setBankName(BankCodeEnum.getName(custUser.getBankCode()));
        bankCardInfo.setCardNo(custUser.getCardNo());
        bankCardInfo.setCardType("1");
        //bankCardInfo.setIdNumber(custUser.getIdNo());
       // bankCardInfo.setName(custUser.getRealName());
        //bankCardInfo.setPhone(custUser.getMobile());
        List<JDQBankCardInfo> list2 = new ArrayList<>();
        list2.add(bankCardInfo);
        vo.setJdqBankCardInfo(list2);
		return vo;
	}

}
