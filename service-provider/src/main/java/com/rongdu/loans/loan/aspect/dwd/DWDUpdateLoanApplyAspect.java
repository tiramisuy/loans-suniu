package com.rongdu.loans.loan.aspect.dwd;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.option.LoanCheckOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.option.dwd.BankVerifyOP;
import com.rongdu.loans.loan.option.dwd.DWDResp;
import com.rongdu.loans.loan.service.DWDService;
import com.rongdu.loans.loan.service.DWDStatusFeedBackService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import lombok.extern.slf4j.Slf4j;
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
* @Title: DWDUpdateLoanApplyAspect.java  
* @Package com.rongdu.loans.loan.aspect.dwd  
* @Description: 大王贷用户绑卡，订单审批，放款，逾期，还款异步反馈结果切面 （针对大王贷订单，订单状态变化的结果反馈）  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Slf4j
@Component
@Aspect
public class DWDUpdateLoanApplyAspect {
	
	@Autowired
	private DWDService dwdService;
	@Autowired
	private DWDStatusFeedBackService dwdStatusFeedBackService;
	@Autowired
	private BorrowInfoManager borrowInfoManager;
	@Autowired
	private RepayPlanItemService repayPlanItemService;
	
	@Pointcut(value = "execution(* com.rongdu.loans.loan.service.DWDService.bankVerify(..))")
	void bankVerifyStatusFeedBackPointcut() {
	}
	@Async
	@AfterReturning(value = "bankVerifyStatusFeedBackPointcut()&&args(bankVerifyOP)", returning = "returnValue")
	public void bankVerifyStatusFeedBack(BankVerifyOP bankVerifyOP, DWDResp returnValue) throws Exception {
		log.debug("----------进入【大王贷-绑卡】后置通知：异步执行结果反馈----------");
//		log.debug("入参bindCardOP:{}", bankVerifyOP);
		DWDResp resp = dwdStatusFeedBackService.bankVerifyFeedBack(bankVerifyOP,returnValue);
		log.debug("----------离开【大王贷-绑卡】后置通知----------");
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
		if (!dwdService.isExistApplyId(applyId)) {
			log.debug("【大王贷】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.debug("----------进入【大王贷-审批】后置通知：异步执行结果反馈applyId={}----------",applyId);
		boolean result = dwdStatusFeedBackService.approveFeedBack(applyId);
		if (!result) {
			this.toRedis(applyId,Global.DWD_APPROVE_FEEDBACK);
		}
		log.debug("----------离开【大王贷-审批】后置通知applyId={}----------",applyId);
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
		if (!dwdService.isExistApplyId(applyId)) {
			log.debug("【大王贷】三方订单表不存在此订单-{}", applyId);
			return;
		}
		String orderNo = dwdService.getOrderNo(applyId);
		log.debug("----------进入【大王贷-取消审批】后置通知：异步执行结果反馈applyId={}----------",applyId);
		boolean result = dwdStatusFeedBackService.orderStatusFeedback(orderNo, true);
		if (!result) {
			this.toRedis(applyId, Global.DWD_APPROVE_FEEDBACK);
		}
		log.debug("----------离开【大王贷-取消审批】后置通知applyId={}----------",applyId);
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
	public void lendFeedBackP2P(String outsideSerialNo, Date payTime, String notifyType, boolean returnValue) throws Exception{
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
	public void lendFeedBackTL(String applyId, Date payTime) throws Exception {
		lendingFeedback(applyId, WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getDesc());
	}

	private void lendingFeedback(String applyId, String payChannel) throws Exception {
		if (!dwdService.isExistApplyId(applyId)) {
			log.debug("【大王贷】-三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
		log.info("----------进入【大王贷-放款-{}】后置通知：异步执行结果反馈applyId={}----------", payChannel, applyId);
		boolean result = dwdStatusFeedBackService.lendFeedBack(applyId);
		if (!result) {
			this.toRedis(applyId, Global.DWD_LEND_FEEDBACK);
		}
		log.info("----------离开【大王贷-放款-{}】后置通知applyId={}----------", payChannel, applyId);
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
        List<String> dwdOrderNos = dwdService.findThirdIdsByApplyIds(applyIdList);
        if (dwdOrderNos == null || dwdOrderNos.isEmpty()) {
            log.debug("【大王贷】三方订单表不存在此订单-{}", applyIdList);
            return;
        }
		Thread.sleep(2000);
        for (String orderNo : dwdOrderNos) {
        	String applyId = dwdService.getApplyId(orderNo);
        	log.debug("----------进入【大王贷-逾期】后置通知：异步执行结果反馈applyId={}----------",applyId);
    		boolean result = dwdStatusFeedBackService.overdueFeedBack(applyId);
    		if (!result) {
    			this.toRedis(applyId, Global.DWD_LEND_FEEDBACK);
    		}
    		log.debug("----------离开【大王贷-逾期】后置通知applyId={}----------",applyId);
        }
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
     * 大王贷还款（还款方式 - 自动扣款"auto"） 
     */
    @Pointcut(value = "execution(* com.rongdu.loans.pay.service.WithholdService.agreementPay(..))")
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
		if (!dwdService.isExistApplyId(applyId)) {
			log.debug("【大王贷】三方订单表不存在此订单-{}", applyId);
			return;
		}
		Thread.sleep(2000);
        log.debug("----------进入【大王贷-还款】后置通知：异步执行结果反馈applyId={},repayPlanItemId={}----------",applyId,repayPlanItemId);
//        this.toRedis(applyId);
		boolean flag = dwdStatusFeedBackService.settlementFeedBack(applyId,repayPlanItemId);
		if (!flag) {
			this.toRedis(repayPlanItemId, Global.DWD_SETTLEMENT_FEEDBACK);
		}
        log.debug("----------离开【大王贷-还款】后置通知applyId={},repayPlanItemId={}----------",applyId,repayPlanItemId);
    }
    
	private void toRedis(String applyId,String cacheKey) {
		Map<String, String> map = new HashMap<>();
		map.put(applyId, String.valueOf(System.currentTimeMillis()));
		JedisUtils.mapPut(cacheKey, map);
	}

}
