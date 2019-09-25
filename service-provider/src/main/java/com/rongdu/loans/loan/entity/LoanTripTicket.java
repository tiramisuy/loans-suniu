package com.rongdu.loans.loan.entity;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 
* @Description:  旅游券
* @author: 饶文彪
* @date 2018年7月11日
 */
public class LoanTripTicket extends BaseEntity<LoanTripTicket> {
	private static final long serialVersionUID = 1L;
	
	private String productId;// 旅游产品id
	private String cardNo;  //旅游券卡号
	private String status;		// 状态(0-未发放，1-已发放)
	private Date overdueTime;//过期时间
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Date getOverdueTime() {
		return overdueTime;
	}
	public void setOverdueTime(Date overdueTime) {
		this.overdueTime = overdueTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
}
