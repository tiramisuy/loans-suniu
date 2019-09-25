package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustCouponVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID，实体唯一标识
	 */
	protected String id;
	
	/**
	 * 	备注
	 */
	protected String remark;
	/**
	  *用户id
	  */
	private String userId;		
	/**
	  *申请单号
	  */
	private String applyId;		
	/**
	  *卡券类型7=加急券 8=旅游券 9=购物券
	  */
	private Integer type;		
	/**
	  *卡券名称
	  */
	private String couponName;		
	/**
	  *卡券金额
	  */
	private BigDecimal amount;		
	/**
	  *抵扣率
	  */
	private BigDecimal rate;		
	/**
	  *开始时间
	  */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;		
	/**
	  *过期时间
	  */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;		
	/**
	  *来源
	  */
	private String source;		
	/**
	  *是否使用0：未使用 1：已使用
	  */
	private Integer status;	
	
	/**
	  *状态名称
	  */
	private String statusName;	
	
	private String imgUrl; //图片路径	
	private String cardNo;  //券卡号
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
	
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Integer getStatus() {
		if(endTime != null && endTime.compareTo(new Date())<0){
			status = 3;
		}
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getStatusName() {		
		if(getStatus() != null){			
			statusName = status == 0?"未使用":status==3?"已过期":"已使用";
			
		}		
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	
}
