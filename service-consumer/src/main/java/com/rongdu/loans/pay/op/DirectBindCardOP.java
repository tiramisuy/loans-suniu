package com.rongdu.loans.pay.op;

import java.io.Serializable;

/**
 * 四要素验证（绑卡）入参对象
 * 
 * @author likang
 */
public class DirectBindCardOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 543689232460661648L;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 身份证号码
	 */
	private String idNo;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 银行代码
	 */
	private String bankCode;

	/**
	 * 银行编码
	 */
	private String bankNo;
	/**
	 * 银行卡号
	 */
	private String cardNo;

	/**
	 * ip
	 */
	private String ipAddr;

	/**
	 * 来源于哪个终端（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	 */
	private String source;
	
	/**
	 * 开户行地址
	 */
	private String cityId;

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}
