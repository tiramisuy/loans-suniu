package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 白骑士-给资信云上报额外参数-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class ReportExtOP implements Serializable {


	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * 证件号码
	 */
	private String certNo;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 姓名
	 */
	private List<ReportExtContact> contacts;

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

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ReportExtContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<ReportExtContact> contacts) {
		this.contacts = contacts;
	}
}
