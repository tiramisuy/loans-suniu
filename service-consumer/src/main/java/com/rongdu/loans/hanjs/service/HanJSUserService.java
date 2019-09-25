package com.rongdu.loans.hanjs.service;

import com.rongdu.loans.hanjs.op.HanJSOrderOP;
import com.rongdu.loans.hanjs.op.HanJSUserOP;
import com.rongdu.loans.hanjs.op.HanJSWithdrawOP;
import com.rongdu.loans.hanjs.vo.HanJSResultVO;
import com.rongdu.loans.loan.vo.AdminWebResult;

public interface HanJSUserService {

	/**
	 * 汉金所开户接口
	 * 
	 * @param op
	 * @return
	 */
	HanJSResultVO openAccount(HanJSUserOP op);

	/**
	 * 汉金所提现接口
	 * 
	 * @param op
	 * @return
	 */
	HanJSResultVO withdraw(HanJSWithdrawOP op);

	/**
	 * 汉金所推标接口
	 * 
	 * @param op
	 * @return
	 */
	HanJSResultVO pushBid(HanJSOrderOP op);

	/**
	 * 查询订单状态
	 * 
	 * @param applyId
	 * @return
	 */
	HanJSResultVO queryOrderState(String applyId);

	/**
	 * 取消订单
	 * 
	 * @param payLogId
	 * @param applyId
	 * @return
	 */
	AdminWebResult cancelOrder(String payLogId, String applyId);

	/**
	 * 修改订单渠道
	 *
	 * @param payLogId
	 * @param applyId
	 * @return
	 */
	AdminWebResult changeOrder(String payLogId, String applyId, String paychannel);
}
