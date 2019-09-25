package com.rongdu.loans.cust.vo;

import java.io.Serializable;

/**
 * Created by liuzhuang on 2018/02/02.
 */
public class CustHitRuleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8675198828064407079L;
	/**
	 * 规则代码，默认为外部规则代码；如果为空，则同rule_id
	 */
	private String ruleCode;
	/**
	 * 规则名称
	 */
	private String ruleName;
	/**
	 * 风险等级
	 */
	private String riskRank;
	/**
	 * 规则来源
	 */
	private String source;
	/**
	 * 征信厂商
	 */
	private String name;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 序号
	 */
	private Integer sort;

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

	public String getRiskRank() {
		return riskRank;
	}

	public void setRiskRank(String riskRank) {
		this.riskRank = riskRank;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
