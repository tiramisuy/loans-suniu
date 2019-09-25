package com.rongdu.loans.loan.aspect.jdq;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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

/**  
* @Title: JDQUpdateLoanApplyAspect.java  
* @Package com.rongdu.loans.loan.aspect.jdq  
* @Description: 借点钱订单审批，放款，逾期，还款异步反馈结果切面 （针对借点钱订单，订单状态变化的结果反馈）  
* @author: yuanxianchu  
* @date 2018年10月12日  
* @version V1.0  
*/
@Slf4j
@Aspect
@Component
public class JDQUpdateLoanApplyAspect {
	
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
	 * 
	 * @Title: lendFeedBackPointcut
	 * @Description: 放款-订单状态反馈切点（p2p平台放款）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.ContractService.process(..))")
	void lendFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "lendFeedBackPointcut()&&args(outsideSerialNo,payTime,notifyType)",returning = "returnValue")
	public void lendFeedBackP2P(String outsideSerialNo, Date payTime, String notifyType, boolean returnValue) throws Exception {
		if (!"3".equals(notifyType) || !returnValue) {
			return;
		}
		String applyId = borrowInfoManager.getApplyIdByOutSideNum(outsideSerialNo);
		lendingFeedback(applyId,WithdrawalSourceEnum.WITHDRAWAL_ONLINE.getDesc());
	}
	
	/**
	 * 
	 * @Title: lendFeedBackPointcut
	 * @Description: 放款-订单状态反馈切点（口袋放款）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.ContractService.processKoudaiLendPay(..))")
	void lendFeedBackPointcut2() {
	}
	@Async
	@AfterReturning(value = "lendFeedBackPointcut2()&&args(applyId,payTime)")
	public void lendFeedBackKouDai(String applyId, Date payTime) throws Exception {
		lendingFeedback(applyId,WithdrawalSourceEnum.WITHDRAWAL_KOUDAI.getDesc());
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
	 * @Title: lendFeedBackPointcut
	 * @Description: 放款-订单状态反馈切点（通联放款）
	 */
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.ContractService.createRepayPlan(..))")
	void lendFeedBackPointcut4() {
	}
	@Async
	@AfterReturning(value = "lendFeedBackPointcut4()&&args(applyId,payTime)")
	public void lendFeedBackTl(String applyId, Date payTime) throws Exception {
		lendingFeedback(applyId, WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getDesc());
	}




	private void lendingFeedback(String applyId, String payChannel) throws InterruptedException {
		if (!jdqService.isExistApplyId(applyId)) {
			log.debug("【借点钱】-三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【借点钱-放款-{}】后置通知：异步执行结果反馈applyId={}----------", payChannel, applyId);
		this.orderStatusFeedBack(applyId);
		log.info("----------离开【借点钱-放款-{}】后置通知applyId={}----------", payChannel, applyId);
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
