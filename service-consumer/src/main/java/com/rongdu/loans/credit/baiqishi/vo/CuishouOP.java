package com.rongdu.loans.credit.baiqishi.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 上传查询催收指标用户-请求报文
 * @author liuzhuang
 * @version 2017-12-18
 */
public class CuishouOP implements Serializable {
	
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
	
	/**
	 * 申请订单号
	 */
	@NotNull(message = "申请订单号不能为空")
	private String applyId;
	
	public CuishouOP() {
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

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	

}
