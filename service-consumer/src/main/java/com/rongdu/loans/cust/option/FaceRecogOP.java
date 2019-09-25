package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class FaceRecogOP implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 64366327410348315L;

	/**
	 * 聚宝钱包业务流水号
	 */
	private String orderNo;
	
	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	@NotBlank(message="消息来源不能为空")
	@Pattern(regexp="1|2|3|4",message="消息来源类型有误")
	private String source;
	
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * IP地址
	 */
	private String ip;

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

	public String getSource() {
		return source;
	}

	public String getIp() {
		return ip;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
