package com.rongdu.loans.koudai.api.service;

import com.rongdu.loans.koudai.api.vo.deposit.*;
import com.rongdu.loans.koudai.op.deposit.KDDepositComprehensiveOP;
import com.rongdu.loans.koudai.op.deposit.KDDepositWithdrawOP;
import com.rongdu.loans.koudai.op.deposit.KDPushAssetRepaymentPeriodOP;

import java.util.Map;
/**
 * 
* @Description:  口袋存管订单服务
* @author: RaoWenbiao
* @date 2018年11月6日
 */
public interface KDDepositOrderApiService {
	/**
	 * 
	* @Title: createOrderLendPay
	* @Description: 推单
	* @return KDCreateOrderLendPayResultVO    返回类型
	* @throws
	 */
	@Deprecated
	KDCreateOrderLendPayResultVO createOrderLendPay(String applyId);
	
	/**
	 * 
	* @Title: withdraw
	* @Description: 提现
	* @return KDWithdrawResultVO    返回类型
	* @throws
	 */
	KDWithdrawResultVO withdraw(KDDepositWithdrawOP pack);
	
	/**
	 * 
	* @Title: orderCancel
	* @Description: 取消订单
	* @return KDDepositRetVO    返回类型
	* @throws
	 */
	KDDepositResultVO orderCancel(String applyId);

	/**
	 * 
	* @Title: getContract
	* @Description:查询合同
	* @return MAP    返回类型
	* @throws
	 */
	Map<String, Object> getContract(String applyId);

	/**
	 * 
	* @Title: comprehensive
	* @Description: 综合页接口
	* @return KDComprehensiveResultVO    返回类型
	* @throws
	 */
	KDComprehensiveResultVO comprehensive(KDDepositComprehensiveOP pack);
	/**
	 * 
	* @Title: pushAssetRepaymentPeriod
	* @Description: 创建债权还款计划
	* @return KDDepositRetVO    返回类型
	* @throws
	 */
	KDDepositResultVO pushAssetRepaymentPeriod(KDPushAssetRepaymentPeriodOP pack);

	/**
	 * 
	* @Title: queryStatus
	* @Description:查询放款状态
	* @return MAP    返回类型
	* @throws
	 */
	Map<String, Object> queryStatus(String applyId);
	
	/**
     * 
    * @Title: queryComprehensive
    * @Description: 综合页查询
    * @return KDComprehensiveResultVO    返回类型
    * @throws
     */
    public KDQueryComprehensiveVO queryComprehensive(String applyId);

	/**
	 *
	 * @Title: queryAccount
	 * @Description:查询口袋开户信息
	 * @return MAP    返回类型
	 * @throws
	 */
	Map<String, Object> queryAccount(String idNo);
}
