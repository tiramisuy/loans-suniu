package com.rongdu.loans.baiqishi.message;

import java.io.Serializable;
import java.util.List;

/**
 * 策略
 * 
 * @author sunda
 * @version 2017-07-10
 */
public class Strategy implements Serializable {

	private static final long serialVersionUID = -3804822860824566361L;
	/**
	 * 策略名称
	 */
	private String strategyName;
	/**
	 * 策略ID
	 */
	private String strategyId;
	/**
	 * 策略决策结果
	 */
	private String strategyDecision;
	/**
	 * 策略匹配模式
	 */
	private String strategyMode;
	/**
	 * 策略风险系数，只有权重策略模式下有效
	 */
	private int strategyScore;
	/**
	 * 权重区间上限系数（只有权重策略模式下有值）
	 */
	private int rejectValue;
	/**
	 * 权重区间下限系数（只有权重策略模式下有值）
	 */
	private int reviewValue;
	/**
	 * 策略风险类型
	 */
	private String riskType;
	/**
	 * 策略击中话术提示
	 */
	private String tips;
	/**
	 * 规则内容明细，参考rule字段说明
	 */
	private List<Rule> hitRules;

	public Strategy() {
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	public String getStrategyDecision() {
		return strategyDecision;
	}

	public void setStrategyDecision(String strategyDecision) {
		this.strategyDecision = strategyDecision;
	}

	public String getStrategyMode() {
		return strategyMode;
	}

	public void setStrategyMode(String strategyMode) {
		this.strategyMode = strategyMode;
	}

	public int getStrategyScore() {
		return strategyScore;
	}

	public void setStrategyScore(int strategyScore) {
		this.strategyScore = strategyScore;
	}

	public int getRejectValue() {
		return rejectValue;
	}

	public void setRejectValue(int rejectValue) {
		this.rejectValue = rejectValue;
	}

	public int getReviewValue() {
		return reviewValue;
	}

	public void setReviewValue(int reviewValue) {
		this.reviewValue = reviewValue;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public List<Rule> getHitRules() {
		return hitRules;
	}

	public void setHitRules(List<Rule> hitRules) {
		this.hitRules = hitRules;
	}

}
