package com.rongdu.loans.qhzx.vo;


/**
 * 前海征信-请求对象-安全校验信息
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class QhzxResponseSecurityInfo {
	
	/**
	 * 签名
	 * 外部系统专线\内网系统访问为空
	 */
	private String signatureValue = "";
//	/**
//	 * 虚拟用户
//	 */
//	private String userName = "";
//	/**
//	 * 虚拟用户密码
//	 */
//	private String userPassword = "";
	
	public String getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//	public String getUserPassword() {
//		return userPassword;
//	}
//	public void setUserPassword(String userPassword) {
//		this.userPassword = userPassword;
//	}
	
}
