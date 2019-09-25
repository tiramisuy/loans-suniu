/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 同盾-反欺诈命中规则详情Entity
 * @author sunda
 * @version 2017-08-14
 */
public class AntifraudDetail extends BaseEntity<AntifraudDetail> {
	
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
	  *反欺诈服务流水号
	  */
	private String seqId;		
	/**
	  *规则ID
	  */
	private String ruleId;		
	/**
	  *决策结果。当策略模式为首次匹配或者最坏匹配时，才有这个字段。
	  */
	private String decision;		
	/**
	  *权重分数。当策略模式为权重模式时，才有这个字段。
	  */
	private Integer score;		
	/**
	  *规则的类型
	  */
	private String type;		
	/**
	  *主属性的字段名
	  */
	private String dimType;		
	/**
	  *dim_value
	  */
	private String dimValue;		
	/**
	  *从属性的字段名
	  */
	private String subDimType;		
	/**
	  *计算结果
	  */
	private BigDecimal result;		
	/**
	  *命中规则详情
	  */
	private String detail;		
	
	public AntifraudDetail() {
		super();
	}

	public AntifraudDetail(String id){
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
	
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDimType() {
		return dimType;
	}

	public void setDimType(String dimType) {
		this.dimType = dimType;
	}
	
	public String getDimValue() {
		return dimValue;
	}

	public void setDimValue(String dimValue) {
		this.dimValue = dimValue;
	}
	
	public String getSubDimType() {
		return subDimType;
	}

	public void setSubDimType(String subDimType) {
		this.subDimType = subDimType;
	}
	
	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}