package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 白骑士-催收指标-请求报文
 * @author liuzhuang
 * @version 2017-12-28
 */
public class CuishouDataOP implements Serializable {

	private static final long serialVersionUID = 1687027729736356630L;
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


	public CuishouDataOP() {
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
