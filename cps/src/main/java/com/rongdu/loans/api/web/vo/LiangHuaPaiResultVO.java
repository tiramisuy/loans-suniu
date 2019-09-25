package com.rongdu.loans.api.web.vo;

import java.io.Serializable;

public class LiangHuaPaiResultVO implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1;
	
	public LiangHuaPaiResultVO() {
		super();
	}
	
	

	public LiangHuaPaiResultVO(String userType, String returnUrl) {
		super();
		this.userType = userType;
		this.returnUrl = returnUrl;
	}



	private String userType;//0:新用户,1:老用户,2:黑名单用户,3其他用户
	
	private String returnUrl;//重定向地址

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	
	
	
	
	
}
