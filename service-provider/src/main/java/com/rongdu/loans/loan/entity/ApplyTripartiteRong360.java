/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 工单映射（融360）Entity
 * @author yuanxianchu
 * @version 2018-06-29
 */
public class ApplyTripartiteRong360 extends BaseEntity<ApplyTripartiteRong360> {
	
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
	
	public ApplyTripartiteRong360() {
		super();
	}

	public ApplyTripartiteRong360(String id){
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