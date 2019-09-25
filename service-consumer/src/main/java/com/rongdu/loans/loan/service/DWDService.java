package com.rongdu.loans.loan.service;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.dwd.*;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.vo.dwd.*;

import java.util.List;

/**  
* @Title: DwdService.java  
* @Package com.rongdu.loans.loan.service  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
public interface DWDService {
	/**
	 * 查询复贷和黑名单信息
	 */
	DWDResp certAuth(CertAuthOP certAuthOP);
	
	/**
	 * 推送用户绑定银行
	 */
	DWDResp bankBind(BankVerifyOP bankVerifyOP);
	
	/**
	 * 推送用户验证银行卡
	 */
	DWDResp bankVerify(BankVerifyOP bankVerifyOP);
	
	/**
	 * 查询审批结论
	 */
	AuditResultVO audiResult(String orderNo);
	
	/**
	 * 试算接口
	 */
	WithdrawTrialVO withdrawTrial(WithdrawTrialOP withdrawTrialOP);

    /**
     * 确认收款接口
     */
	DWDResp withdrawReq(WithdrawReqOP withdrawReqOP);
	
	/**
	 * 查询还款计划
	 */
	RepaymentPlanVO repaymentPlan(String orderNo);
	
	/**
	 * 推送用户还款信息
	 */
	DWDResp payment(PaymentReqOP paymentReqOP);
	
	/**
	 * 查询还款状态
	 */
	PaymentStatusVO paymentStatus(PaymentResultOP paymentResultOP);
	
	/**
	 * 查询订单状态
	 */
	OrderStatusVO orderStatus(String orderNo);

    DWDResp pushBaseInfo(String body);

	DWDResp pushAdditionalInfo(String body);

	boolean saveBaseInfo(DWDBaseInfo intoOrder);

    boolean saveAdditionInfo(DWDAdditionInfo intoOrder);

	boolean saveChargeInfo(ChargeInfo chargeInfo);

	void saveUserAndApplyInfo(String orderNo) throws Exception;

	DWDBaseInfo getPushBaseData(String orderSn);

	DWDAdditionInfo getPushAdditionalData(String orderSn);

	boolean isExistApplyId(String applyId);

	boolean isExistApplyId(String applyId, String status);

	int insertTripartiteOrder(String applyId, String orderSn, String channelCode);

	String getOrderNo(String applyId);
	
	String getApplyId(String orderNo);
	
	List<String> findThirdIdsByApplyIds(List<String> applyIds);
	
	TaskResult approveFeedbackOfRedis();
	
	TaskResult lendFeedbackOfRedis();
	
	TaskResult settlementFeedbackOfRedis();

    DWDReport getReportData(String orderSn);

	ChargeInfo getdwdChargeInfo(String orderSn);
}
