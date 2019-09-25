package com.rongdu.loans.pay.service;

import java.util.Map;

import com.rongdu.loans.pay.op.AuthPayQueryOP;
import com.rongdu.loans.pay.op.ConfirmAuthPayOP;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.op.PreAuthPayOP;
import com.rongdu.loans.pay.vo.AuthPayQueryResultVO;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.PreAuthPayVO;

/**
 * 宝付支付接口
 * @author likang
 */
public interface BaofooAuthPayService {

	/**
	 * 四要素验证（宝付绑卡直接流程）
	 * @param param
	 * @return
	 */
	BindCardResultVO directBindCard(DirectBindCardOP param);
	
	/**
	 * 重新绑卡
	 * @param param
	 * @return
	 */
	BindCardResultVO reBindCard(DirectBindCardOP param);
	
	/**
	 * 预支付  （ 宝付支付）
	 * @param param
	 * @return
	 */
	PreAuthPayVO preAuthPay(PreAuthPayOP param);
	
	/**
	 * 确认支付 （ 宝付支付）
	 * @param param
	 * @return
	 */
	ConfirmAuthPayVO confirmAuthPay(ConfirmAuthPayOP param);
	
	/**
	 * 查询交易状态
	 * @param orderNo
	 * @return
	 */
	public Map<String, Object> queryAuthPayStatus(String orderNo);
	/**
	 * 查询交易结果
	 * @param orderNo
	 * @return
	 */
	public AuthPayQueryResultVO queryAuthPayResult(AuthPayQueryOP op);
}
