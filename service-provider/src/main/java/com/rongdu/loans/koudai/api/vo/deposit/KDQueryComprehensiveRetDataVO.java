package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;

/**
 * 
* @Description:  综合页查询 业务数据
* @author: RaoWenbiao
* @date 2018年12月10日
 */
public class KDQueryComprehensiveRetDataVO implements Serializable {
	protected static final long serialVersionUID = 1L;

	/**
	 * 	状态码:1放款中 2放款成功(钱放到电子账户) 4 提现冲正 5放款成功(受托支付) 6提现成功(钱到银行卡)
	 *  7提现失败(可以再次发起提现) 8不存在,9 提现失败（不需要再次发起提现 风控拒绝订单） 10 提现中
	 *   11 订单取消（包含自动取消和主动取消） 新增 100 开户问题 200 授权问题 300 借款合规页 400等待二次确认
	 */
	private Integer status;
	/**
	 * 下单时间
	 */
	private String orderTime;
	/**
	 * 放款时间
	 */
	private String loanTime;
	/**
	 * 提现时间
	 */
	private String withdrawTime;
	/**
	 * 返回说明
	 */
	private String msg;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}
	public String getWithdrawTime() {
		return withdrawTime;
	}
	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
