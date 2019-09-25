/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 工单映射Entity
 * @author Lee
 * @version 2018-05-29
 */
public class ApplyTripartite extends BaseEntity<ApplyTripartite> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *三方工单号
	  */
	private String tripartiteNo;		
	/**
	  *工单号
	  */
	private String applyId;		
	
	public ApplyTripartite() {
		super();
	}

	public ApplyTripartite(String id){
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
	
}