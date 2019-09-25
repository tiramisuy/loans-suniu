package com.rongdu.loans.baiqishi.message;

import java.io.Serializable;

/**
 * 规则
 * @author sunda
 * @version 2017-07-10
 */
public class Rule implements Serializable {
	
	private static final long serialVersionUID = 8015997981200201454L;
	/**
	 * 规则名称
	 */
	private String ruleName;
	/**
	 * 规则ID
	 */
	private String ruleId;
	/**
	 * 规则风险系数，只有权重策略模式下有效
	 */
	private int score;
	/**
	 * 规则决策结果，权重策略模式下规则无该字段
	 */
	private String decision;
	/**
	 * 规则击中信息备注，如名单匹配规则返回相关名单的分类信息。
	 */
	private String memo;

	public Rule() {
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
