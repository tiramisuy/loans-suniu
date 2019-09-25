package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 查询运营商通讯信息-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class MnoContactOP implements Serializable {

	private static final long serialVersionUID = 1687027729736356626L;
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

	public MnoContactOP() {
	}

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
}
