/**
 * ©2017 聚宝钱包 All rights reserved
 */
package com.rongdu.loans.pay.service;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.RepayLogVO;

import java.util.Map;

/**
 * 宝付代付/提现
 * 
 * @author sunda
 */
public interface BaofooWithdrawService {

	/**
	 * 基于支付订单进行付款： 1、默认情况下付款到当前的用户的电子账户
	 * 
	 * @param order
	 * @throws Exception
	 */
	public void withraw(RepayLogVO order);

	/**
	 * 重新付款
	 * 
	 * @param pw
	 */
	public AdminWebResult reWithdraw(String payNo);

	/**
	 * 对未成功的付款订单进行处理： 1、每隔10分钟定时跑批，查询未成功的付款订单 2、通过"代付交易状态查证接口(BF0040002)"进行查证
	 * 3、如果宝付进行了拆单，记录拆单结果 4、根据查证结果，更新本地付款订单状态 5、如果确认付款订单失败，短信通知相关人员
	 * 
	 * @throws Exception
	 */
	public TaskResult processUnsolvedOrders();

	/**
	 * 什么情况下可以发起重新付款？ 1、该笔付款不成功（本地）：status!=S 2、该笔付款申请失败（宝付）：return_code!=0000
	 * 3、该笔付款申请成功，但是实际银行或者宝付处理失败（宝付）：return_code==0000，state=-1 注意：
	 * 1、处理中、付款成功的订单不能重复付款
	 * 
	 * @return
	 */
	public boolean isReWithdraw(String orderNo, PayLogVO pw);

	/**
	 * 代付
	 * 
	 * @param pw
	 * @return
	 */
	public boolean payment(PayLogVO pw);


	public String queryBaofooBalance(String account_type);

}
