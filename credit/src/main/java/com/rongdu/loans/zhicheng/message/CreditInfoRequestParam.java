package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 查询借款、风险和逾期信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class CreditInfoRequestParam implements Serializable{
	
	private static final long serialVersionUID = 49742755577050125L;

	/**
	 * 被查询人姓名
	 */
	private String name;
	/**
	 * 被查询人身份证号
	 */
	private String idNo;
	/**
	 * 查询原因
	 */
	private String queryReason = "10";

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

	
}