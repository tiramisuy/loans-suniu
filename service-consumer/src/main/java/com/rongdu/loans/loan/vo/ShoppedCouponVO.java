package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 购物券VO
 * @author fy
 *
 */
public class ShoppedCouponVO implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6061436415074763418L;
	
	/**
	 * 订单id
	 */	
	private String applyId;         
	/**
	 * 客户姓名
	 */
	private String userName;		
	/**
	 * 手机号码
	 */
	private String mobile;	
	/**
	 * 卡券名称
	 */
	private String couponName;			
	/**
	 * 卡券金额
	 */	
	private BigDecimal amount;		
	/**
	 * 订单申请时间
	 */	
	private String approveTime;
	/**
	 * 卡券发放时间
	 */	
	private String startTime;
	/**
	 * 过期时间
	 */	
	private String endTime;
	/**
	 * 卡券状态(0-未使用,1-已使用)
	 */	
	private String status;
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
