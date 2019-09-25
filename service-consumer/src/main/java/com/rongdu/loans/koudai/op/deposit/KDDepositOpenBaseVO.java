package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
/**
 * 
* @Description:  开户信息
* @author: RaoWenbiao
* @date 2018年11月26日
 */
public class KDDepositOpenBaseVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name; // 姓名 
	private String mobile;// 手机号
	private String gender; // 性别 M 男性 F 女性
	private String retUrl; // 同步跳转地址 不能长于256个字符，否则银行校验失败
	private String notifyUrl; // 异步回调地址 没有为空
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	
}
