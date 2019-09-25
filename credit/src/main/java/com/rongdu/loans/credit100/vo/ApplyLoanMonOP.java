package com.rongdu.loans.credit100.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 多次申请检查月度版-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ApplyLoanMonOP implements Serializable {

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 身份证号
	 */
	private String idNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 紧急联系人
	 */
	private List<String> contacts;

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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

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

	public List<String> getContacts() {
		return contacts;
	}

	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}
}
