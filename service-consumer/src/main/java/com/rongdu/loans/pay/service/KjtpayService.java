package com.rongdu.loans.pay.service;

import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.vo.WithholdResultVO;

/**
 * 
* @Description:快捷通服务  
* @author: RaoWenbiao
* @date 2018年11月20日
 */
public interface KjtpayService {
	/**
	 * 
	* @Title: tradeBankWitholding
	* @Description: 银行卡代扣,trade_bank_witholding
	* @return void    返回类型
	* @throws
	 */
	WithholdResultVO tradeBankWitholding(WithholdOP op);
	
	/**
	 * 
	* @Title: updateProcessOrder
	* @Description: 银行卡代扣 状态更新
	* @return void    返回类型
	* @throws
	 */
	void updateProcessOrder(RepayLogVO repayLogVO)throws Exception;
	
	
	/**
	 * 
	* @Title: transferToCard
	* @Description: 快捷通转账到银行卡,transfer_to_card
	* @return ApiResultVO    返回类型
	* @throws
	 */
	public ApiResultVO transferToCard(String tradeNo,String cardNo,String realName,String bankCode, String amount);
}
