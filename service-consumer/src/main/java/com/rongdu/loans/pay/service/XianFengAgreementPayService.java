package com.rongdu.loans.pay.service;

import com.rongdu.loans.pay.op.AuthPayOP;
import com.rongdu.loans.pay.op.XfAgreementAdminPayOP;
import com.rongdu.loans.pay.op.XfAgreementPayOP;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

/**
 * @Description: 先锋支付
 * @author: liuzhuang
 * @date 2018年6月13日
 * @version V1.0
 */
public interface XianFengAgreementPayService {

	/**
	 * 支付
	 * 
	 * @param op
	 * @return
	 */
	XfAgreementPayResultVO agreementPay(XfAgreementPayOP op);

	ConfirmAuthPayVO pay(AuthPayOP op);

	/**
	 * 支付结果查询
	 * 
	 * @param merchantNo
	 * @return
	 */
	XfAgreementPayResultVO agreementPayQuery(String merchantNo);

	/**
	 * 手台手动代扣支付
	 * 
	 * @param op
	 * @return
	 */
	XfAgreementPayResultVO agreementAdminPay(XfAgreementAdminPayOP op);
}
