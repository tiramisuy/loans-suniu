package com.rongdu.loans.loan.entity;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 
* @Description:  用户旅游产品明细
* @author: 饶文彪
* @date 2018年7月11日
 */
public class LoanTripProductDetail extends BaseEntity<LoanTripProductDetail> {
	private static final long serialVersionUID = 1L;
	
		
	private String productId;		// 旅游产品id
	private String custId;		// 用户id
	private String applyId;    //借款申请id
	private String cardNo;  //旅游券卡号
	private Date overdueTime;//过期时间

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
	
	public Date getOverdueTime() {
		return overdueTime;
	}

	public void setOverdueTime(Date overdueTime) {
		this.overdueTime = overdueTime;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
	
	
	
}
