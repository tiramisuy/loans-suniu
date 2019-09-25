package com.rongdu.loans.loan.service;

import com.rongdu.loans.anrong.vo.Overdues;
import com.rongdu.loans.loan.option.SLL.CardBindOP;
import com.rongdu.loans.loan.option.SLL.SLLResp;
import com.rongdu.loans.loan.vo.OverdueVO;

import javax.print.DocFlavor;
import java.util.List;

/**  
* @Title: RongPointCutService.java  
* @Package com.rongdu.loans.loan.service  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月23日  
* @version V1.0  
*/
public interface RongPointCutService {
	
	/**
	 * 用作Rong360订单代扣失败时，切面通知的切入点标记
	 */
	void repayProcessFailPoint(String repayPlanItemId);
	
	/**
	 * 用作Rong360订单逾期时，切面通知的切入点标记
	 */
	void overduePoint(List<String> applyIdList);

	void overduePointForAnRong(List<OverdueVO> overdueVOS);

	/**
	 * 用作Rong360订单还款时，切面通知的切入点标记
	 */
	void settlementPoint(String repayPlanItemId,boolean result);
	
	/**
	 * 用作Rong360订单还款时，切面通知的切入点标记
	 */
	void settlementManPayPoint(String repayPlanItemId,boolean result);
	
	/**
	 * 用作Rong360订单延期时，切面通知的切入点标记
	 */
	void delayPoint(String repayPlanItemId);
	
	/**
	 * 用作Rong360绑卡成功开户时，切面通知的切入点标记
	 */
	void creatAccount(String applyId, String msg, boolean result);

	/**
	 * Rong,SLL存管放款时，切面通知的切入点标记
	 * @param flag 1：放款标识 2：提现标识
	 */
	void lendPoint(String applyId,int flag);

	/**
	 * 用作奇虎360绑卡成功时，切面通知的切入点标记
	 */
	void sllCardBindConfirm(CardBindOP cardBindOP, SLLResp sllResp);

	/**
	 * 用作奇虎360开户成功时，切面通知的切入点标记
	 */
	void enSureAgreement(String applyId);
	
	void test() throws InterruptedException;

	/**
	 * 用作审批取消时，切面通知的切入点标记
	 */
	int cancelApply(String applyId, String operatorName);

	void sllLendPoint(String applyId, int flag);
}
