/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 命中的风控规则Entity
 * @author sunda
 * @version 2017-08-16
 */
public class HitRuleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	  *风险类型代码（risk_category的level为2时的风险）
	  */
	private String riskType;
	/**
	  *风险名称
	  */
	private String riskName;
	/**
	  *规则代码，默认为外部规则代码；如果为空，则同rule_id
	  */
	private String ruleCode;
	/**
	  *规则名称
	  */
	private String ruleName;
	/**
	  *规则描述
	  */
	private String ruleDesc;
	/**
	  *规则来源
	  */
	private String source;
	/**
	  *规则来源细分
	  */
	private String subSource;
	/**
	  *风险等级
	  */
	private String riskRank;
	/**
	  *分值
	  */
	private Integer score;
	/**
	  *取值
	  */
	private String value;
	/**
	  *用户ID
	  */
	private String userId;
	/**
	  *贷款申请编号
	  */
	private String applyId;
	/**
	  *风控规则ID
	  */
	private String ruleId;
	/**
	 * 创建时间，命中时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;

	public HitRuleVO() {
		super();
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	
	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSubSource() {
		return subSource;
	}

	public void setSubSource(String subSource) {
		this.subSource = subSource;
	}
	
	public String getRiskRank() {
		return riskRank;
	}

	public void setRiskRank(String riskRank) {
		this.riskRank = riskRank;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}