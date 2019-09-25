/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 工单映射（贷款钱包）Entity
 * @author fy
 * @version 2019-04-16
 */
public class ApplyTripartiteDkqb extends BaseEntity<ApplyTripartiteDkqb> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *三方工单号
	  */
	private String tripartiteNo;		
	/**
	  *工单号
	  */
	private String applyId;		
	/**
	  *status
	  */
	private String status;		
	
	public ApplyTripartiteDkqb() {
		super();
	}

	public ApplyTripartiteDkqb(String id){
		super(id);
	}

	public String getTripartiteNo() {
		return tripartiteNo;
	}

	public void setTripartiteNo(String tripartiteNo) {
		this.tripartiteNo = tripartiteNo;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}