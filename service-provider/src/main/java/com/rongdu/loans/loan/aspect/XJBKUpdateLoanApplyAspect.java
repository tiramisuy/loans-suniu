package com.rongdu.loans.loan.aspect;

import java.util.Date;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.option.LoanCheckOP;
import com.rongdu.loans.loan.service.ApplyTripartiteService;
import com.rongdu.loans.loan.service.XJBKStatusFeedBackService;

import lombok.extern.slf4j.Slf4j;

/**
* @Title: XJBKUpdateLoanApplyAspect.java
* @Package com.rongdu.loans.loan.aspect
* @Description: 现金白卡订单审批，放款，异步反馈结果切面 （针对现金白卡订单，订单状态变化的结果反馈）
* @author: yuanxianchu
* @date 2018年8月20日
* @version V1.0
*/
@Slf4j
@Aspect
@Component
public class XJBKUpdateLoanApplyAspect {

	@Autowired
	private ApplyTripartiteService applyTripartiteService;
	@Autowired
	private XJBKStatusFeedBackService xjbkStatusFeedBackService;
	@Autowired
	private BorrowInfoManager borrowInfoManager;

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
	public void approveFeedBack(LoanCheckOP loanCheckOP, boolean returnValue) throws InterruptedException {
		String applyId = loanCheckOP.getApplyId();
		if (returnValue == false) {
			return;
		}
		approveFeedback(applyId);
	}
	
	/**
	 * @Title: approveCancelFeedBackPointcut
	 * @Description: 取消审批-审批结论反馈切点
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.LoanApplyService.updateCancel(..))")
	void approveCancelFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "approveCancelFeedBackPointcut()&&args(applyId,operatorName)", returning = "returnValue")
	public void approveCancelFeedBack(String applyId, String operatorName, int returnValue) throws InterruptedException {
		if (returnValue == 0){
			return;
		}
		approveFeedback(applyId);
	}
	
	private void approveFeedback(String applyId) throws InterruptedException {
		if (!applyTripartiteService.isExistApplyId(applyId)) {
			log.debug("现金白卡-三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【现金白卡-审批】后置通知：异步执行结果反馈----------");
		log.debug("入参applyId:{}", applyId);
		xjbkStatusFeedBackService.xjbkApproveFeedBack(applyId);
		log.info("----------离开【现金白卡-审批】后置通知----------");
	}

	/**
	 *
	 * @Title: lendFeedBackPointcut
	 * @Description: 放款-订单状态反馈切点
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.ContractService.process(..))")
	void lendFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "lendFeedBackPointcut()&&args(outsideSerialNo,payTime,notifyType)",returning = "returnValue")
	public void lendFeedBack(String outsideSerialNo, Date payTime, String notifyType, boolean returnValue) throws InterruptedException {
		if (!"3".equals(notifyType) || !returnValue) {
			return;
		}
		String applyId = borrowInfoManager.getApplyIdByOutSideNum(outsideSerialNo);
		if (!applyTripartiteService.isExistApplyId(applyId)) {
			log.debug("现金白卡-三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【现金白卡-放款】后置通知：异步执行结果反馈----------");
		log.debug("入参applyId:{}", applyId);
		xjbkStatusFeedBackService.xjbkLendingFeedBack(applyId, true);
		log.info("----------离开【现金白卡-放款】后置通知----------");
	}

	/**
	 *
	 * @Title: authFeedBackPointcut
	 * @Description: H5认证结果回调切点
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.LoanApplyService.saveShopedBorrowInfo(..))")
	void authFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "authFeedBackPointcut()&&args(applyId,loanPayType)",returning = "returnValue")
	public void authFeedBack(String applyId,Integer loanPayType, boolean returnValue) throws InterruptedException {
		if (!applyTripartiteService.isExistApplyId(applyId)) {
			log.debug("现金白卡-三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【现金白卡-H5认证】后置通知：异步执行结果反馈----------");
		log.debug("入参applyId:{}", applyId);
		xjbkStatusFeedBackService.xjbkAuthFeedback(applyId, returnValue);
		log.info("----------离开【现金白卡-H5认证】后置通知----------");
	}

}
