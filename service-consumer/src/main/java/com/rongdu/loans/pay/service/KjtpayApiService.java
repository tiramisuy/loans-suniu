package com.rongdu.loans.pay.service;

import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;

/**
 * 
* @Description:  海尔支付接口
* @author: RaoWenbiao
* @date 2018年11月16日
 */
public interface KjtpayApiService {

	/**
	 * 
	* @Title: transferToCard
	* @Description: 快捷通转账到银行卡,transfer_to_card
	* @return ApiResultVO    返回类型
	* @throws
	 */
	ApiResultVO transferToCard(String tradeNo,String userId,String amount);
	/**
	 * 
	* @Title: transferToCard
	* @Description: 快捷通转账到银行卡,transfer_to_card
	* @return ApiResultVO    返回类型
	* @throws
	 */
	public ApiResultVO transferToCard(String tradeNo,String cardNo,String realName,String bankCode, String amount);
	
	/**
	 * 
	* @Title: transferToCard
	* @Description: 快捷通转账到银行卡 查询
	* @return void    返回类型
	* @throws
	 */
	void queryTransferToCard(String tradeNo);
//	/**
//	 * 
//	* @Title: transferToCardNotify
//	* @Description: 快捷通转账到银行卡 异步通知
//	* @return void    返回类型
//	* @throws
//	 */
//	String transferToCardNotify(TransferToCardNotifyOP op);
	
	
	
	/**
	 * 
	* @Title: tradeBankWitholding
	* @Description: 银行卡代扣,trade_bank_witholding
	* @return void    返回类型
	* @throws
	 */
	WithholdResultVO tradeBankWitholding(String tradeNo,String amount,CustUserVO custUserVO);
	
	/**
	 * 
	* @Title: queryTradeBankWitholding
	* @Description: 银行卡代扣 查询
	* @return void    返回类型
	* @throws
	 */
	WithholdQueryResultVO queryTradeBankWitholding(String tradeNo);
	
//	/**
//	 * 
//	* @Title: tradeBankWitholdingNotify
//	* @Description: 代扣 异步通知
//	* @return void    返回类型
//	* @throws
//	 */
//	String tradeBankWitholdingNotify(TradeBankWitholdingNotifyOP op);
	
}
