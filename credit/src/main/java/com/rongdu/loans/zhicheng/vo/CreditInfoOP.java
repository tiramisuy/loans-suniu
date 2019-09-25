package com.rongdu.loans.zhicheng.vo;

import java.io.Serializable;

/**
 * 查询借款、风险和逾期信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class CreditInfoOP implements Serializable{
	
	private static final long serialVersionUID = 49742755577050125L;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 贷款申请编号
	 */
	private String applyId;

	/**
	 * 被查询人姓名
	 */
	private String name;
	/**
	 * 被查询人身份证号
	 */
	private String idNo;
	/**
	 * 被查询人手机号
	 */
	private String mobile;
	/**
	 * 查询原因
	 */
	private String queryReason = "10";

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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}