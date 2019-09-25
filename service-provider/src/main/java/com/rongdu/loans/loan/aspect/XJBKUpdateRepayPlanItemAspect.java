package com.rongdu.loans.loan.aspect;

import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rongdu.loans.loan.manager.ApplyTripartiteManager;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.service.ApplyTripartiteService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.service.XJBKStatusFeedBackService;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;

import lombok.extern.slf4j.Slf4j;

/**  
* @Title: XJBKUpdateRepayPlanItemAspect.java  
* @Package com.rongdu.loans.loan.aspect  
* @Description: 现金白卡订单逾期，还款，异步反馈结果切面 （针对现金白卡订单，还款计划变化的结果反馈） 
* @author: yuanxianchu  
* @date 2018年8月20日  
* @version V1.0  
*/
@Slf4j
@Aspect
@Component
public class XJBKUpdateRepayPlanItemAspect {
	
	@Autowired
	private RepayPlanItemService repayPlanItemService;
	@Autowired
	private ApplyTripartiteService applyTripartiteService;
	@Autowired
	private XJBKStatusFeedBackService xjbkStatusFeedBackService;
	@Autowired
	private ApplyTripartiteManager applyTripartiteManager;
	
	/**
	 * @Title: settlementFeedBackPointcut
	 * @Description: 贷款结清-订单状态反馈切点 app还款（还款方式 - 主动还款"manual"）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.manager.SettlementManager.Settlement(..))")
	void settlementFeedBackPointcut1() {
	}
	@Async
	@AfterReturning(value = "settlementFeedBackPointcut1()&&args(rePayOP)", returning = "returnValue")
	public void settlementFeedBack1(RePayOP rePayOP, ConfirmPayResultVO returnValue) throws Exception {
		boolean result = false;
		if (returnValue.getResult().intValue() == 1) {
			result = true;
		}
		settlementFeedBack(rePayOP.getRepayPlanItemId(), result);
	}

	/**
	 * 逾期手动/系统代扣（还款方式 - 自动扣款"auto"）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.settlementPoint(..))")
	void settlementFeedBackPointcut2() {
	}
	@Async
	@AfterReturning(value = "settlementFeedBackPointcut2()&&args(repayPlanItemId,result)")
	public void settlementFeedBack2(String repayPlanItemId, boolean result) throws Exception {
		settlementFeedBack(repayPlanItemId, result);
	}

	/**
	 * 后台手动还款（还款方式 - 手动还款"manpay"）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.settlementManPayPoint(..))")
	void settlementFeedBackPointcut3() {
	}
	@Async
	@AfterReturning(value = "settlementFeedBackPointcut3()&&args(repayPlanItemId,result)")
	public void settlementFeedBack3(String repayPlanItemId, boolean result) throws Exception {
		settlementFeedBack(repayPlanItemId, true);
	}

	private void settlementFeedBack(String repayPlanItemId, boolean result) throws InterruptedException{
		String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
		if (!applyTripartiteService.isExistApplyId(applyId)) {
			log.debug("现金白卡-三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【现金白卡-还款】后置通知：异步执行结果反馈----------");
		log.debug("入参repayPlanItemId:{}", repayPlanItemId);
		xjbkStatusFeedBackService.xjbkRepayStatusFeedBack(repayPlanItemId, applyId, result);
		log.info("----------离开【现金白卡-还款】后置通知----------");
	}
	
	
	/**
	 * @Title: overduePointcut
	 * @Description: 贷款逾期-订单状态反馈切点
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.overduePoint(..))")
	void overduePointcut() {
	}
	@Async
	@AfterReturning(value = "overduePointcut()&&args(applyIdList)")
	void overdueFeedBack(List<String> applyIdList) throws InterruptedException{
		List<String> rongOrderNos = applyTripartiteManager.findThirdIdsByApplyIds(applyIdList);
		if (rongOrderNos == null || rongOrderNos.isEmpty()) {
			log.debug("现金白卡-三方订单表不存在此订单-{}", applyIdList);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【现金白卡-逾期】后置通知：异步执行结果反馈----------");
		for (String orderNo : rongOrderNos) {
			log.debug("入参applyIdList:{}", applyIdList);
			xjbkStatusFeedBackService.xjbkOverdueStatusFeedBack(orderNo);
		}
		log.info("----------离开【现金白卡-逾期】后置通知----------");
	}
}
