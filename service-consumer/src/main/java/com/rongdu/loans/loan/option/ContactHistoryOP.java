/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.option;


import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 历次贷款申请时的紧急联系人信息Entity
 * @author zhangxiaolong
 * @version 2017-08-01
 */
public class ContactHistoryOP implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 *用户ID
	 */
	@NotNull(message = "用户ID不能为空")
	private String userId;
	/**
	 *用户姓名
	 */
	@NotNull(message = "用户姓名不能为空")
	private String userName;
	/**
	  *贷款申请编号
	  */
	@NotNull(message = "贷款申请编号不能为空")
	private String applyId;
	/**
	  *手机号码
	  */
	@NotNull(message = "手机号码不能为空")
	private String mobile;

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

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}