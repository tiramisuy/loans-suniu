package com.rongdu.loans.koudai.service;

import java.util.List;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.koudai.vo.KDwithdrawRecodeVO;
import com.rongdu.loans.loan.option.ApiResultVO;

/**
 * 
* @Description:  口袋存管服务
* @author: RaoWenbiao
* @date 2018年11月6日
 */
public interface KDDepositService {

	/**
	 * 
	* @Title: comprehensive
	* @Description: 综合页接口(开户、授权、推单、放款)
	* @return ApiResultVO    返回类型
	* @throws
	 */
	ApiResultVO comprehensive(String applyId, String returnUrl);
	
	/**
	 * 
	* @Title: withdraw
	* @Description: 提现
	* @return KDWithdrawResultVO    返回类型
	* @throws
	 */
	ApiResultVO withdraw(String applyId, String returnUrl);
	/**
	 * 
	* @Title: pushAssetRepaymentPeriod
	* @Description: 创建债权还款计划
	* @return ApiResultVO    返回类型
	* @throws
	 */
	ApiResultVO pushAssetRepaymentPeriod(String applyId);
	
	/**
	 * 
	* @Title: saveOrUpdatePayLogStatus
	* @Description: 保存或更新paylog状态
	* payStatus 0=成功,1=失败,2=处理中
	* payStatus为0的不允许修改状态
	* @return ApiResultVO    返回类型
	* @throws
	 */
	ApiResultVO saveOrUpdatePayLogStatus(String applyId,String kdOrderId,int payStatus);
	
	/**
	 * 
	* @Title: savePayLog
	* @Description: 保存paylog
	* @return ApiResultVO    返回类型
	* @throws
	 */
	ApiResultVO updatePayLogStatus(String applyId,Integer withdrawStatus);
	
	/**
	 * 
	* @Title: findWithdrawRecode
	* @Description: 查询提现记录
	* @return List<KDwithdrawRecodeVO>    返回类型
	* @throws
	 */
	List<KDwithdrawRecodeVO> findWithdrawRecode(String userId,Integer withdrawStatus);
	
	/**
     * 
    * @Title: processKDWaitingLending
    * @Description: 处理口袋存管待放款订单查询
    * @return TaskResult    返回类型
    * @throws
     */
    public TaskResult processKDWaitingLending();
    /**
     * 
    * @Title: processKDWithdrawOrder
    * @Description: 处理口袋存管 24小时未提现订单
    * @return TaskResult    返回类型
    * @throws
     */
    public TaskResult processKDWithdrawOrder();
    
}
