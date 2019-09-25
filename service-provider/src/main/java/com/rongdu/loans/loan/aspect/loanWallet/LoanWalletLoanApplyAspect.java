package com.rongdu.loans.loan.aspect.loanWallet;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.option.LoanCheckOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.service.JDQService;
import com.rongdu.loans.loan.service.JDQStatusFeedBackService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author: fy
*/
@Slf4j
//@Aspect
//@Component
public class LoanWalletLoanApplyAspect {
	
	@Autowired
	private JDQService jdqService;
	@Autowired
	private JDQStatusFeedBackService jdqStatusFeedBackService;
	@Autowired
	private BorrowInfoManager borrowInfoManager;
	@Autowired
	private RepayPlanItemService repayPlanItemService;
	
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
		if (!jdqService.isExistApplyId(applyId)) {
			log.debug("【借点钱】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.debug("----------进入【借点钱-审批】后置通知：异步执行结果反馈applyId={}----------",applyId);
		this.orderStatusFeedBack(applyId);
		log.debug("----------离开【借点钱-审批】后置通知applyId={}----------",applyId);
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
		if (!jdqService.isExistApplyId(applyId)) {
			log.debug("【借点钱】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.debug("----------进入【借点钱-取消审批】后置通知：异步执行结果反馈applyId={}----------",applyId);
		this.orderStatusFeedBack(applyId);
		log.debug("----------离开【借点钱-取消审批】后置通知applyId={}----------",applyId);
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
    void overdueFeedBack(List<String> applyIdList) throws Exception {
        List<String> rongOrderNos = jdqService.findThirdIdsByApplyIds(applyIdList);
        if (rongOrderNos == null || rongOrderNos.isEmpty()) {
            log.debug("【借点钱】三方订单表不存在此订单-{}", applyIdList);
            return;
        }
		Thread.sleep(2000);
        log.debug("----------进入【借点钱-逾期】后置通知：异步执行结果反馈----------");
        for (String orderNo : rongOrderNos) {
        	String applyId = jdqService.getApplyId(orderNo);
        	this.orderStatusFeedBack(applyId);
        }
        log.debug("----------离开【借点钱-逾期】后置通知----------");
    }
    
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

    /**
     * 借点钱还款（还款方式 - 自动扣款"auto"） 
     */
    @Pointcut(value = "execution(* com.rongdu.loans.pay.service.WithholdService.agreementTonglianPay(..))")
    void settlementFeedBackPointcut4() {
    }
    @Async
    @AfterReturning(value = "settlementFeedBackPointcut4()&&args(rePayOP)", returning = "returnValue")
    public void settlementFeedBack4(RePayOP rePayOP, ConfirmAuthPayVO returnValue) throws Exception {
        settlementFeedBack(rePayOP.getRepayPlanItemId(), returnValue.isSuccess());
    }

    private void settlementFeedBack(String repayPlanItemId, boolean result) throws Exception {
		if (!result) {
			return;
		}
		String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
		if (!jdqService.isExistApplyId(applyId)) {
			log.debug("【借点钱】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
        log.debug("----------进入【借点钱-还款】后置通知：异步执行结果反馈applyId={},repayPlanItemId={}----------",applyId,repayPlanItemId);
        this.orderStatusFeedBack(applyId);
        log.debug("----------离开【借点钱-还款】后置通知applyId={},repayPlanItemId={}----------",applyId,repayPlanItemId);
    }
    
	/**
	 * 订单状态反馈，失败则加redis后续补偿推送
	 */
	private void orderStatusFeedBack(String applyId) {
		boolean result = jdqStatusFeedBackService.orderStatusFeedBack(applyId);
		if (!result) {
			this.toRedis(applyId);
		}
	}
    
	private void toRedis(String applyId) {
		Map<String, String> map = new HashMap<>();
		map.put(applyId, String.valueOf(System.currentTimeMillis()));
		JedisUtils.mapPut(Global.JDQ_ORDERSTATUS_FEEDBACK, map);
	}
	
    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.test(..))")
    void testPoint() {
    }
    @Async
    @After(value = "testPoint()")
    public void test() throws Exception {
        System.out.println("----------------@After----------------");
    }
    @Async
    @AfterReturning(value = "testPoint()")
    public void test1() throws Exception {
        System.out.println("----------------@AfterReturning----------------");
    }
    
}
