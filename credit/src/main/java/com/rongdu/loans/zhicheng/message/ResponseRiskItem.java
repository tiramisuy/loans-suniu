package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 查询借款、风险和逾期信息-命中风险项
 * @author sunda
 * @version 2017-07-10
 */
public class ResponseRiskItem implements Serializable {

	private static final long serialVersionUID = 7209682953717889561L;
	/**
	 * 提供数据的机构代号
	 * 资金平台”类的机构，会以“机构类别+代号”的形式提供
	 */
	private String orgName;
	/**
	 * 命中项码
	 * 如证件号码（当前命中项仅包括证件号码）
	 */
	private String riskItemTypeCode;
	/**
	 * 命中内容
	 * 身份证号的具体值
	 */
	private String riskItemValue;
	/**
	 * 风险类别码
	 * 合作机构提供的风险类别对应到阿福的风险类别
	 */
	private String riskTypeCode;
	/**
	 * 风险明细
	 * 合作机构提供的借款人的风险类别
	 */
	private String riskDetail;
	/**
	 * 风险明细码
	 */
	private String riskTime;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRiskItemTypeCode() {
		return riskItemTypeCode;
	}
	public void setRiskItemTypeCode(String riskItemTypeCode) {
		this.riskItemTypeCode = riskItemTypeCode;
	}
	public String getRiskItemValue() {
		return riskItemValue;
	}
	public void setRiskItemValue(String riskItemValue) {
		this.riskItemValue = riskItemValue;
	}
	public String getRiskTypeCode() {
		return riskTypeCode;
	}
	public void setRiskTypeCode(String riskTypeCode) {
		this.riskTypeCode = riskTypeCode;
	}
	public String getRiskDetail() {
		return riskDetail;
	}
	public void setRiskDetail(String riskDetail) {
		this.riskDetail = riskDetail;
	}
	public String getRiskTime() {
		return riskTime;
	}
	public void setRiskTime(String riskTime) {
		this.riskTime = riskTime;
	}

	@Override
	public String toString() {
		return "ResponseRiskItem{" +
				"orgName='" + orgName + '\'' +
				", riskItemTypeCode='" + riskItemTypeCode + '\'' +
				", riskItemValue='" + riskItemValue + '\'' +
				", riskTypeCode='" + riskTypeCode + '\'' +
				", riskDetail='" + riskDetail + '\'' +
				", riskTime='" + riskTime + '\'' +
				'}';
	}
}