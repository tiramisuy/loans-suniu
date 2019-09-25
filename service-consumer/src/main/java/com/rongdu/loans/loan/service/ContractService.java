package com.rongdu.loans.loan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.rongdu.loans.loan.vo.ContractVO;
import com.rongdu.loans.loan.vo.MakeLoanRecordVO;

/**
 * 借款合同-业务逻辑接口
 * 
 * @author likang
 * @version 2017-07-11
 */
public interface ContractService {

	/**
	 * 最近三天申请放款成功记录
	 * 
	 * @return
	 */
	List<MakeLoanRecordVO> getRecentThreeDaysRecords();

	/**
	 * 最近三天申请放款成功记录
	 * 
	 * @return
	 */
	List<String> getRecentThreeDaysRecordsStr();

	/**
	 * * 处理放款完成后的异步逻辑
	 * 
	 * 1.签订合同 2.生成还款计划 3.生成还款计划明细 4.自动提现
	 * 
	 * @param outsideSerialNo
	 *            关联的外部流水号
	 * @param payTime
	 *            放款时间
	 * @param notifyType
	 *            通知类型 1=满标回调 2=旅游券598回调 3=旅游券1402回调
	 */
	boolean process(String outsideSerialNo, Date payTime, String notifyType) throws RuntimeException;

	/**
	 * 后台手动放款
	 * 
	 * @param applyId
	 *            申请单编号
	 * @param payTime
	 *            放款时间
	 * @param isPayment
	 *            是否代付
	 */
	public void processAdminLendPay(String applyId, Date payTime, boolean isPayment);

	/**
	 * 口袋放款
	 * 
	 * @param applyId
	 * @param payTime
	 */
	public void processKoudaiLendPay(String applyId, Date payTime);
	/**
	 * 口袋存管放款
	 * 
	 * @param applyId
	 * @param payTime
	 */
	public void processKDDepositLendPay(String applyId, Date payTime);
	

	/**
	 * 乐视放款
	 * 
	 * @param applyId
	 * @param payTime
	 */
	public void processLeshiLendPay(String applyId, Date payTime);


	/**
	 * 通联放款
	 *
	 * @param applyId
	 * @param payTime
	 */
	public void processTltLendPay(String applyId, Date payTime);
	/**
	 * 后台推标放款
	 * 
	 * @param applyId
	 *            申请单编号
	 * @param payTime
	 *            放款时间
	 * @param isPayment
	 *            是否代付
	 */
	public void processBorrowLendPay(String applyId, Date payTime, boolean isPayment);

	/**
	 * 后台手动延期
	 * 
	 * @param repayPlanItemId
	 *            还款计划明细id
	 */
	public void processAdminDelay(String repayPlanItemId);

	/**
	 * 主动延期
	 * 
	 * @param repayPlanItemId
	 *            还款计划明细id
	 * @return
	 */
	public int processManualDelay(String repayPlanItemId);

	/**
	 * 提现
	 * 
	 * @param applyId
	 */
	void withdraw(@NotNull(message = "参数不能为空") String applyId);

	/**
	 * 根据用户id查询未结清合同
	 * 
	 * @param userId
	 * @return
	 */
	ContractVO getUnFinishContractByUserId(String userId);

	Map<String, Object> getDelayAmount(String repayPlanItemId, String delayDate);

	/**
	 * @Title: delayDeal
	 * @Description: 手动延期
	 * @param @param repayPlanItemId
	 * @param @param delayType 1：手动延期 2：代扣延期
	 * @param @param delayDate 延期时间
	 */
	void delayDeal(String repayPlanItemId, int delayType, String delayDate, String repayType, String repayTypeName);

	/**
	 * p2p查询598代扣结果
	 * 
	 * @param lid
	 * @return
	 */
	int getServFeeWithholdResult(String lid);

	/**
	 * 通融放款
	 * 
	 * @param applyId
	 * @param payTime
	 */
	public void processTongRongLendPay(String applyId, Date payTime);
	
	/**
	 * 汉金所放款回调
	 * 
	 * @param applyId
	 * @param payTime
	 */
	public void processHanJSLendPay(String applyId,Date payTime,String payAmount);

	/**
	 * 生成还款计划
	 * @param applyId
	 * @param payTime
	 */
	public void createRepayPlan(String applyId, Date payTime);
}
