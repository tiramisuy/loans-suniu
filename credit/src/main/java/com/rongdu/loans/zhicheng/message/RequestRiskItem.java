package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 宜信致诚风险名单-命中风险项
 * 
 * @author sunda
 * @version 2017-07-10
 */
public class RequestRiskItem implements Serializable {

	private static final long serialVersionUID = 7209682953717889561L;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 数据类型码
	 */
	private String dataTypeCode;
	/**
	 * 风险数据项类型
	 */
	private String riskItemType;
	/**
	 * 风险数据项类型码
	 */
	private String riskItemTypeCode;
	/**
	 * 风险数据项值
	 */
	private String riskValue;
	/**
	 * 风险明细
	 */
	private String riskDetail;
	/**
	 * 风险明细码
	 */
	private String riskDetailCode;
	/**
	 * 风险明细码
	 */
	private String riskTime;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataTypeCode() {
		return dataTypeCode;
	}

	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}

	public String getRiskItemType() {
		return riskItemType;
	}

	public void setRiskItemType(String riskItemType) {
		this.riskItemType = riskItemType;
	}

	public String getRiskItemTypeCode() {
		return riskItemTypeCode;
	}

	public void setRiskItemTypeCode(String riskItemTypeCode) {
		this.riskItemTypeCode = riskItemTypeCode;
	}

	public String getRiskValue() {
		return riskValue;
	}

	public void setRiskValue(String riskValue) {
		this.riskValue = riskValue;
	}

	public String getRiskDetail() {
		return riskDetail;
	}

	public void setRiskDetail(String riskDetail) {
		this.riskDetail = riskDetail;
	}

	public String getRiskDetailCode() {
		return riskDetailCode;
	}

	public void setRiskDetailCode(String riskDetailCode) {
		this.riskDetailCode = riskDetailCode;
	}

	public String getRiskTime() {
		return riskTime;
	}

	public void setRiskTime(String riskTime) {
		this.riskTime = riskTime;
	}

}