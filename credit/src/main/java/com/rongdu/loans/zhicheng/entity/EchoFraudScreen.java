/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 致诚信用-阿福共享平台-欺诈甄别记录Entity
 * @author fy
 * @version 2019-07-05
 */
public class EchoFraudScreen extends BaseEntity<EchoFraudScreen> {
	
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
	  *返回查询的流水号
	  */
	private String flowId;		
	/**
	  *欺诈分（分值范围1-100，未命中数据时不返回分数）
	  */
	private String fraudLevel;		
	/**
	  *决策建议(0-数据不足，无法计算欺诈等级 欺诈等级从一级至五级，资质逐渐降低，风险逐渐升高)
	  */
	private String fraudScore;		
	/**
	  *社会关系网
	  */
	private String socialNetwork;		
	
	public EchoFraudScreen() {
		super();
	}

	public EchoFraudScreen(String id){
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
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	public String getFraudLevel() {
		return fraudLevel;
	}

	public void setFraudLevel(String fraudLevel) {
		this.fraudLevel = fraudLevel;
	}
	
	public String getFraudScore() {
		return fraudScore;
	}

	public void setFraudScore(String fraudScore) {
		this.fraudScore = fraudScore;
	}
	
	public String getSocialNetwork() {
		return socialNetwork;
	}

	public void setSocialNetwork(String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}
	
}