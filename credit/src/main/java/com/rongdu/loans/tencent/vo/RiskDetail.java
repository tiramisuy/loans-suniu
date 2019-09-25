package com.rongdu.loans.tencent.vo;

import java.io.Serializable;

/**
 * 腾讯-反欺诈服务-风险代码
 * @author sunda
 * @version 2017-08-10
 */
public class RiskDetail implements Serializable{

	private static final long serialVersionUID = -4695418047175301690L;

	/**
	 * 风险代码
	 */
	private String riskCode;

	public RiskDetail() {
	}

	public RiskDetail(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

}