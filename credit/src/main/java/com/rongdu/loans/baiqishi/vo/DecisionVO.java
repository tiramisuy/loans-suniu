package com.rongdu.loans.baiqishi.vo;

import com.rongdu.loans.baiqishi.message.Strategy;
import com.rongdu.loans.credit.common.CreditApiVo;

import java.util.List;


/**
 * 白骑士反欺诈风险决策引擎-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class DecisionVO  extends CreditApiVo{

	private static final long serialVersionUID = 2810707655568689987L;
	/**
	 * 调用结果码
	 */
	private String resultCode;
	/**
	 * 应答消息
	 */
	private String resultDesc;
	/**
	 * 本次请求的流水号，用于事后案件调查
	 */
	private String flowNo;
	/**
	 * 决策结果码，参见decision参考表
	 */
	private String finalDecision;
	/**
	 * 最终风险系数，只有权重策略模式下有效
	 */
	private String finalScore	;
	/**
	 * 策略集内容明细，参考strategySet字段说明
	 */
	private List<Strategy> strategySet;

	public DecisionVO(){
	}
	
	@Override
	public boolean isSuccess() {
		if ("BQS000".equals(getResultCode())) {
			return true;
		}
		return false;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;	
	}

	@Override
	public String getCode() {
		return getResultCode();
	}

	@Override
	public void setCode(String code) {
		
	}

	@Override
	public String getMsg() {
		return getResultDesc();
	}

	@Override
	public void setMsg(String msg) {
		
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getFinalDecision() {
		return finalDecision;
	}

	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public List<Strategy> getStrategySet() {
		return strategySet;
	}

	public void setStrategySet(List<Strategy> strategySet) {
		this.strategySet = strategySet;
	}
	
}
