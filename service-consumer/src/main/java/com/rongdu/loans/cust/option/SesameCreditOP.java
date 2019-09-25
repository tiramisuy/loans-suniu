package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SesameCreditOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8853644631935135249L;

	
	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	@NotBlank(message="消息来源不能为空")
	@Pattern(regexp="1|2|3|4",message="消息来源类型有误")
	private String source;
	
	/**
	 * 是否授权  true, false
	 */
	private boolean authorized;
	
	/**
	 * 白骑士对于每一次请求返回的业务号,后续可以通过此业务号进行对账
	 */
	private String flowNo; 
	
	/**
	 * 芝麻信用对于每一次请求返回的业务号,后续可以通过此业务号进行对账
	 */
	private String bizNo;
	
	/**
	 *  用户的芝麻信用评分。分值范围 [350,950] 。如果用户数据不足，无法评分时，返回字符串 "N/A"
	 */
	private String zmScore;
	
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
	public String getSource() {
		return source;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public String getBizNo() {
		return bizNo;
	}
	public String getZmScore() {
		return zmScore;
	}
	public String getUserId() {
		return userId;
	}
	public String getApplyId() {
		return applyId;
	}
	public String getIp() {
		return ip;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public void setZmScore(String zmScore) {
		this.zmScore = zmScore;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}
