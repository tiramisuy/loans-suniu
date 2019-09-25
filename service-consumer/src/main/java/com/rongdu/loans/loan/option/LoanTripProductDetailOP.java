package com.rongdu.loans.loan.option;

import java.io.Serializable;


/**
 * 
* @Description:  旅游产品OP
* @author: 饶文彪
* @date 2018年7月12日
 */
public class LoanTripProductDetailOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2749196106111115771L;
	
	private String productId;		// 旅游产品id
	private String custId;		// 用户id	
	private String applyId;     //借款申请id
	private String overdueTime;		// 过期时间
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public String getOverdueTime() {
		return overdueTime;
	}
	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	
	
	

}
