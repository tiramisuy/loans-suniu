package com.rongdu.loans.credit.baiqishi.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 查询资信云报告页面URL-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class ReportPageOP implements Serializable {
	
	private static final long serialVersionUID = -5970251453662082107L;
	/**
	 *用户身份证号
	 */
	@NotNull(message = "身份证号不能为空")
	private String idNo;
	/**
	 * 用户姓名
	 */
	@NotNull(message = "姓名不能为空")
	private String name;
	/**
	 * 用户手机号
	 */
	@NotNull(message = "手机号不能为空")
	private String mobile;
	
	public ReportPageOP() {
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

	

}
