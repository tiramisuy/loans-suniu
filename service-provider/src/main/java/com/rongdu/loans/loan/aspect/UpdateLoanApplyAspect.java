package com.rongdu.loans.loan.aspect;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.option.rong360Model.ApproveOP;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.ApplyListVO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.option.LoanCheckOP;
import com.rongdu.loans.loan.option.rong360Model.BindCardOP;
import com.rongdu.loans.loan.option.rong360Model.Rong360Resp;
import com.rongdu.loans.loan.service.ApplyTripartiteRong360Service;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.RongStatusFeedBackService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: UpdateLoanApplyAspect.java
 * @Package com.rongdu.loans.loan.aspect
 * @Description: 融360订单审批，绑卡，放款，异步反馈结果切面 （针对融360订单，订单状态变化的结果反馈）
 * @author: yuanxianchu
 * @date 2018年7月3日
 * @version V1.0
 */
@Slf4j
@Aspect
@Component
public class UpdateLoanApplyAspect {

	@Autowired
	private RongStatusFeedBackService rongStatusFeedBackService;
	@Autowired
	private ApplyTripartiteRong360Service applyTripartiteRong360Service;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private BorrowInfoManager borrowInfoManager;
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongService.confirmBindCard(..))")
	void bindCardStatusFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "bindCardStatusFeedBackPointcut()&&args(bindCardOP)", returning = "returnValue")
	public void bindCardStatusFeedBack(BindCardOP bindCardOP, Rong360Resp returnValue) throws Exception {
		String orderNo = bindCardOP.getOrderNo();
		String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
		ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
		if (applyAllotVO == null
				|| WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(applyAllotVO.getPayChannel())) {
			// 放款渠道是口袋存管，不反馈绑卡结果，后续开户成功后反馈
			return ;
		}
		log.debug("----------进入【融360-绑卡】后置通知：异步执行结果反馈applyid={},orderNo={}----------", applyId, orderNo);
		log.debug("入参bindCardOP:{}", bindCardOP);
		rongStatusFeedBackService.rongBindCardStatusFeedBack(bindCardOP, returnValue);
		log.debug("----------离开【融360-绑卡】后置通知applyId={},orderNo={}----------", applyId, orderNo);
	}
	
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.creatAccount(..))")
	void createAccountStatusFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "createAccountStatusFeedBackPointcut()&&args(applyId,msg,result)")
	public void createAccountStatusFeedBack(String applyId, String msg, boolean result) throws Exception {
		if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
			log.debug("【融360】三方订单表不存在此订单-{}", applyId);
			return;
		}
		log.debug("----------进入【融360-开户】后置通知：异步执行结果反馈applyId={}----------", applyId);
		String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);
		BindCardOP bindCardOP = new BindCardOP();
		bindCardOP.setOrderNo(orderNo);
		bindCardOP.setBindCardSrc(0);
		Rong360Resp resp = new Rong360Resp();
		resp.setCode("300");
		if (result) {
			resp.setCode("200");
		}
		resp.setMsg(msg);
		rongStatusFeedBackService.rongBindCardStatusFeedBack(bindCardOP, resp);
		log.debug("----------离开【融360-开户】后置通知applyId={}----------", applyId);
	}

	/**
	 * 
	 * @Title: approveCancelFeedBackPointcut
	 * @Description: 取消审批-订单状态反馈切点
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.LoanApplyService.updateCancel(..)) " +
			"|| execution(* com.rongdu.loans.loan.service.RongPointCutService.cancelApply(..))")
	void approveCancelFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "approveCancelFeedBackPointcut()&&args(applyId,operatorName)", returning = "returnValue")
	public void approveCancelFeedBack(String applyId, String operatorName, int returnValue) throws Exception {
		if (returnValue == 0){
			return;
		}
		if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
			log.debug("【融360】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.debug("----------进入【融360-取消审批】后置通知：异步执行结果反馈applyId={}----------",applyId);
		boolean flag = rongStatusFeedBackService.rongCancelStatusFeedBack(applyId);
		if (!flag) {
			this.toRedis(applyId, Global.RONG_ORDERSTATUS_FEEDBACK);
		}
		log.debug("----------离开【融360-取消审批】后置通知applyId={}----------",applyId);
	}

	/**
	 * 
	 * @Title: approveFeedBackPointcut
	 * @Description: 审批结论反馈切点
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.LoanApplyService.approve(..))")
	void approveFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "approveFeedBackPointcut()&&args(loanCheckOP)", returning = "returnValue")
	public void approveFeedBack(LoanCheckOP loanCheckOP, boolean returnValue) throws Exception {
		String applyId = loanCheckOP.getApplyId();
		if (returnValue == false) {
			return;
		}
		if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
			log.debug("【融360】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.debug("----------进入【融360-审批】后置通知：异步执行结果反馈applyId={}----------",applyId);
		boolean flag = rongStatusFeedBackService.rongApproveStatusFeedBack(applyId);
		if (!flag) {
			this.toRedis(applyId, Global.RONG_APPROVE_FEEDBACK);
		}
		log.debug("----------离开【融360-审批】后置通知applyId={}----------",applyId);
	}

	/**
	 *
	 * @Title: confirmLoanPointcut
	 * @Description: 确认借款反馈切点（口袋存管放款渠道 需要推送待开户状态）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongService.confirmLoan(..))")
	void confirmLoanPointcut() {
	}

	@Async
	@AfterReturning(value = "confirmLoanPointcut()&&args(approveOP)", returning = "returnValue")
	public void confirmLoanFeedback(ApproveOP approveOP, Rong360Resp returnValue) throws Exception {
		String applyId = approveOP.getApplyId();
		ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
		if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().equals(loanApply.getPayChannel())
				&& Rong360Resp.SUCCESS.equals(returnValue.getCode())
				&& ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue().equals(loanApply.getStatus())) {
			//口袋存管账渠道 且 放款回调成功 推送“待提现”状态
			if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
				log.debug("【融360】三方订单表不存在此订单-{}", applyId);
				return;
			}
			Thread.sleep(2000);
			log.debug("----------进入【融360-确认借款】后置通知：异步执行结果反馈applyId={}----------",applyId);
			boolean result = rongStatusFeedBackService.rongLendStatusFeedBack(applyId);
			if (!result){
				this.toRedis(applyId, Global.RONG_PAY_FEEDBACK);
			}
			log.debug("----------离开【融360-确认借款】后置通知applyId={}----------",applyId);
		}
	}

	/**
	 * 
	 * @Title: lendFeedBackPointcut
	 * @Description: 放款-订单状态反馈切点（口袋放款）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.ContractService.processKoudaiLendPay(..))")
	void lendFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "lendFeedBackPointcut()&&args(applyId,payTime)")
	public void lendFeedBackPointcut(String applyId, Date payTime) throws Exception {
		lendingFeedback(applyId, WithdrawalSourceEnum.WITHDRAWAL_KOUDAI.getDesc());
	}
	
	/**
	 * 
	 * @Title: lendFeedBackPointcut2
	 * @Description: 放款-订单状态反馈切点（p2p平台放款）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.ContractService.process(..))")
	void lendFeedBackPointcut2() {
	}
	@Async
	@AfterReturning(value = "lendFeedBackPointcut2()&&args(outsideSerialNo,payTime,notifyType)",returning = "returnValue")
	public void lendFeedBackPointcut2(String outsideSerialNo, Date payTime, String notifyType, boolean returnValue) throws Exception {
		if (!"3".equals(notifyType) || !returnValue) {
			return;
		}
		String applyId = borrowInfoManager.getApplyIdByOutSideNum(outsideSerialNo);
		lendingFeedback(applyId, WithdrawalSourceEnum.WITHDRAWAL_ONLINE.getDesc());
	}

	/**
	 * @Title: lendFeedBackPointcut
	 * @Description: 放款-订单状态反馈切点（乐视放款）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.ContractService.processLeshiLendPay(..))")
	void lendFeedBackPointcut3() {
	}
	@Async
	@AfterReturning(value = "lendFeedBackPointcut3()&&args(applyId,payTime)")
	public void lendFeedBackLeShi(String applyId, Date payTime) throws Exception {
		lendingFeedback(applyId, WithdrawalSourceEnum.WITHDRAWAL_LESHI.getDesc());
	}

	/**
	 *
	 * @Title: lendFeedBackPointcut
	 * @Description: 放款-订单状态反馈切点（口袋存管开户放款）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.lendPoint(..))")
	void lendFeedBackPointcut4() {
	}

	@Async
	@AfterReturning(value = "lendFeedBackPointcut4()&&args(applyId,flag)")
	public void lendFeedBackPointcut4(String applyId, int flag) throws Exception {
		/*if (flag == 2){
			// 口袋存管提现回调，放入队列中延迟推送
			amqpTemplate.convertAndSend("exchange_delay","delayDoneQuene",applyId);
			return;
		}*/
		if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
			log.debug("【融360】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		if (!JedisUtils.exists(Global.RONG_CREATE_ACCOUNT + applyId)){
			// 用户尚未审批确认，不推送“待提现”状态
			return;
		}
		log.debug("----------进入【融360-口袋存管放款-阶段{}】后置通知：异步执行结果反馈applyId={}----------", flag, applyId);
		boolean result = rongStatusFeedBackService.rongLendStatusFeedBack(applyId);
		if (!result){
			this.toRedis(applyId, Global.RONG_PAY_FEEDBACK);
		}
		log.debug("----------离开【融360-口袋存管放款-阶段{}】后置通知applyId={}----------", flag, applyId);
	}

	private void lendingFeedback(String applyId, String payChannel) throws Exception {
		if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
			log.debug("【奇虎360】-三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【融360-放款-{}】后置通知：异步执行结果反馈applyId={}----------", payChannel, applyId);
		boolean result = rongStatusFeedBackService.rongLendStatusFeedBack(applyId);
		if (!result) {
			this.toRedis(applyId, Global.SLL_LEND_FEEDBACK);
		}
		log.info("----------离开【融360-放款-{}】后置通知applyId={}----------", payChannel, applyId);
	}

	private void toRedis(String applyId,String cacheKey) {
		Map<String, String> map = new HashMap<>();
		map.put(applyId, String.valueOf(System.currentTimeMillis()));
		JedisUtils.mapPut(cacheKey, map);
	}

	/**
	 * 
	 * @Title: contractUpdatePointcut
	 * @Description: 合同状态反馈切点
	 */
	/*
	 * @Pointcut(value=
	 * "execution(* com.rongdu.loans.loan.manager.ContractManager.insert(..))")
	 * void contractUpdatePointcut() {}
	 * 
	 * @Async
	 * 
	 * @AfterReturning(value="contractUpdatePointcut()&&args(loanApply,payTime)")
	 * public void contractAfterReturning(LoanApply loanApply, Date payTime)
	 * throws Exception{ Thread.sleep(5000); String applyId = loanApply.getId();
	 * if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
	 * log.debug("三方订单表不存在此订单-{}",applyId); return; }
	 * log.debug("----------进入【合同状态更新】后置通知：异步执行结果反馈----------");
	 * log.debug("入参:{}",loanApply.toString());
	 * rongStatusFeedBackService.rongContractStatusFeedBack(applyId);
	 * log.debug("----------离开【合同状态更新】后置通知----------"); }
	 */

	// -----------------------------------------------------------------------------------------------
	// -----------------------------------------【测试方法】--------------------------------------------
	// -----------------------------------------------------------------------------------------------

	@Async
//	@AfterReturning(value="test1()&&args(name)")
	public void loanApplyAfterReturning(String name) throws Exception {
		log.debug("----------进入【订单转态更新】后置通知：异步执行结果反馈----------");
		Thread.sleep(6000);
		log.debug("入参:{}", name.toString());
//		rongStatusFeedBackService.rongApplyStatusFeedBack(name, 410);
		log.debug("----------离开【订单转态更新】后置通知----------");
	}

}
