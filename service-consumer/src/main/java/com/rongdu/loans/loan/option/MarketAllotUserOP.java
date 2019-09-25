package com.rongdu.loans.loan.option;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class MarketAllotUserOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6217468386813584730L;

	private String userId;
	private String userName; // 真实姓名
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
