package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;
import java.util.List;

/**
 * 宜信致诚风险名单-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class RiskListRequestData implements Serializable{

	private static final long serialVersionUID = 7209682953717889561L;
	
	/**
	 * 个人身份验证结果
	 */
	private String identityVerify;
	/**
	 * 个人身份验证结果码
	 */
	private String identityVerifyCode;
	/**
	 * 银行卡验证 3 要素
	 */
	private String bankCheckAuth3;
	/**
	 * 银行卡验证 3 要素码
	 */
	private String bankCheckAuth3Code;
	/**
	 * 银行卡验证 4 要素码
	 */
	private String bankCheckAuth4;
	/**
	 * 银行卡验证 4 要素码
	 */
	private String bankCheckAuth4Code;
	/**
	 * 致诚欺诈评分
	 */
	private String zcFraudScore;
	/**
	 * 风险名单结果集
	 */
	private List<RequestRiskItem> resultList;
	/**
	 *社交关系网分析数据
	 */
	private BehaviorFeatures behaviorFeatures;
	
	public String getIdentityVerify() {
		return identityVerify;
	}
	public void setIdentityVerify(String identityVerify) {
		this.identityVerify = identityVerify;
	}
	public String getIdentityVerifyCode() {
		return identityVerifyCode;
	}
	public void setIdentityVerifyCode(String identityVerifyCode) {
		this.identityVerifyCode = identityVerifyCode;
	}
	public String getBankCheckAuth3() {
		return bankCheckAuth3;
	}
	public void setBankCheckAuth3(String bankCheckAuth3) {
		this.bankCheckAuth3 = bankCheckAuth3;
	}
	public String getBankCheckAuth3Code() {
		return bankCheckAuth3Code;
	}
	public void setBankCheckAuth3Code(String bankCheckAuth3Code) {
		this.bankCheckAuth3Code = bankCheckAuth3Code;
	}
	public String getBankCheckAuth4() {
		return bankCheckAuth4;
	}
	public void setBankCheckAuth4(String bankCheckAuth4) {
		this.bankCheckAuth4 = bankCheckAuth4;
	}
	public String getBankCheckAuth4Code() {
		return bankCheckAuth4Code;
	}
	public void setBankCheckAuth4Code(String bankCheckAuth4Code) {
		this.bankCheckAuth4Code = bankCheckAuth4Code;
	}
	public String getZcFraudScore() {
		return zcFraudScore;
	}
	public void setZcFraudScore(String zcFraudScore) {
		this.zcFraudScore = zcFraudScore;
	}
	public List<RequestRiskItem> getResultList() {
		return resultList;
	}
	public void setResultList(List<RequestRiskItem> resultList) {
		this.resultList = resultList;
	}
	public BehaviorFeatures getBehaviorFeatures() {
		return behaviorFeatures;
	}
	public void setBehaviorFeatures(BehaviorFeatures behaviorFeatures) {
		this.behaviorFeatures = behaviorFeatures;
	}
	
}