/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息Entity
 * @author sunda
 * @version 2017-08-14
 */
public class EchoApi extends BaseEntity<EchoApi> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *致诚信用分，取值 300~850
	  */
	private Integer zcCreditScore;		
	/**
	  *违约概率，取值 0.73%~73.6%
	  */
	private String contractBreakRate;		
	
	public EchoApi() {
		super();
	}

	public EchoApi(String id){
		super(id);
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
	
	public Integer getZcCreditScore() {
		return zcCreditScore;
	}

	public void setZcCreditScore(Integer zcCreditScore) {
		this.zcCreditScore = zcCreditScore;
	}
	
	public String getContractBreakRate() {
		return contractBreakRate;
	}

	public void setContractBreakRate(String contractBreakRate) {
		this.contractBreakRate = contractBreakRate;
	}
	
}