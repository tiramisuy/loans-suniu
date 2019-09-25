package com.rongdu.loans.hanjs.op;

import java.io.Serializable;

public class HanJSApiOrderOP implements Serializable{

	private static final long serialVersionUID = -8089960501234733782L;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 借款人手机号
	 */
	private String borrowerMobile;
	
	/**
	 * 借款人姓名
	 */
	private String borrowerName;
	
	/**
	 * 借款人年龄
	 */
	private String borrowerAge;
	
	/**
	 * 借款人身份证号
	 */
	private String borrowerIdCard;
	
	/**
	 * 借款人学历
	 */
	private String borrowerEdu;
	
	/**
	 * 借款人婚姻状况
	 */
	private String borrowerMarry;
	
	/**
	 * 借款人地址
	 */
	private String borrowerAddr;
	
	/**
	 * 借款人用途
	 */
	private String borrowerUse;
	
	/**
	 * 借款人邮箱
	 */
	private String borrowerMail;
	
	/**
	 * 标的金额
	 */
	private String amount;

	/**
	 * 标的天数
	 */
	private String days;

	/**
	 * 渠道
	 */
	private String channelId;
	
	/**
	 * 时间戳
	 */
	private String datetime;

	/**
	 * 加密后秘钥
	 */
	private String sign;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBorrowerMobile() {
		return borrowerMobile;
	}

	public void setBorrowerMobile(String borrowerMobile) {
		this.borrowerMobile = borrowerMobile;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerAge() {
		return borrowerAge;
	}

	public void setBorrowerAge(String borrowerAge) {
		this.borrowerAge = borrowerAge;
	}

	public String getBorrowerIdCard() {
		return borrowerIdCard;
	}

	public void setBorrowerIdCard(String borrowerIdCard) {
		this.borrowerIdCard = borrowerIdCard;
	}

	public String getBorrowerEdu() {
		return borrowerEdu;
	}

	public void setBorrowerEdu(String borrowerEdu) {
		this.borrowerEdu = borrowerEdu;
	}

	public String getBorrowerMarry() {
		return borrowerMarry;
	}

	public void setBorrowerMarry(String borrowerMarry) {
		this.borrowerMarry = borrowerMarry;
	}

	public String getBorrowerAddr() {
		return borrowerAddr;
	}

	public void setBorrowerAddr(String borrowerAddr) {
		this.borrowerAddr = borrowerAddr;
	}

	public String getBorrowerUse() {
		return borrowerUse;
	}

	public void setBorrowerUse(String borrowerUse) {
		this.borrowerUse = borrowerUse;
	}

	public String getBorrowerMail() {
		return borrowerMail;
	}

	public void setBorrowerMail(String borrowerMail) {
		this.borrowerMail = borrowerMail;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}
}
