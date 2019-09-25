package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 合同信息VO
 * @author likang
 *
 */
public class ContractVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7684506937935200512L;

	/**
	  * 合同编号
	  */
	private String contractNo;

    /**
	  *申请编号
	  */
	private String applyId;		
	/**
	  *客户号
	  */
	private String userId;
	/**
	  *客户名称
	  */
	private String userName;
	
	/**
	  *手机号
	  */
	private String mobile;
	/**
	  *证件号码
	  */
	private String idNo;

	/**
	 *证件类型
	 */
	private String idType;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
}
