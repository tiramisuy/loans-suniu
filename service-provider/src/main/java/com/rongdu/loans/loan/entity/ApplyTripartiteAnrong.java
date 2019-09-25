/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 工单映射（安融）Entity
 * @author fy
 * @version 2019-06-21
 */
public class ApplyTripartiteAnrong extends BaseEntity<ApplyTripartiteAnrong> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *工单号
	  */
	private String applyId;

	/**
	  *三方工单号
	  */
	private String tripartiteNo;

	/**
	  * 订单状态
	  */
	private String status;

	public ApplyTripartiteAnrong() {
		super();
	}

	public ApplyTripartiteAnrong(String id){
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