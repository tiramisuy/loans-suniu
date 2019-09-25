package com.rongdu.loans.api.web.option;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
* @Description: 存管回调OP 
* @author: RaoWenbiao
* @date 2018年9月10日
 */
public class BankDepositOP implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 手机号
	 */
	@NotBlank(message="手机号不能为空")
	private String mobile;
	
	/**
	 * 手机号
	 */
	@NotBlank(message="存管账户id不能为空")
	private String accountId;

	/**
	 * 状态
	 * 0000 审核成功
1001 审核中
1002 审核退回
1003 审核拒绝
1004 审核失效

	 */
	private String status;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
