/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 致诚信用-阿福共享平台-欺诈甄别-风险名单结果集记录(由于不是直接调用,数据不全,后期谨慎修改)Entity
 * @author fy
 * @version 2019-07-05
 */
public class EchoFraudScreenRiskResult extends BaseEntity<EchoFraudScreenRiskResult> {
	
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
	  *返回查询的流水号
	  */
	private String flowId;		
	/**
	  *名单类型（CREDITEASE-网贷 COURT_LITIGATION-法院诉讼 OTHER-其他 NETWORK-网络 ）
	  */
	private String dataType;		
	/**
	  *风险明细
	  */
	private String riskDetail;		
	/**
	  *风险命中项(ID_NO-本人身份证号，CONTACT_ID_NO-联系人身份证号 MOBILE-本人手机号码 CONTACT_MOBILE_NO-联系人手机号码 FAMILY_TEL-家庭固话 FAMILY_ADDR-家庭地址 BANK_NO-银行卡号 QQ-QQ EMAIL-EMAIL CORP_NAME-单位名称 CORP_TEL-单位固话 CORP_ADDR-单位地址)
	  */
	private String riskItemType;		
	/**
	  *风险命中内容（命中身份证号或手机号时会返回脱敏的值，命中其他项不返回值）
	  */
	private String riskItemValue;		
	/**
	  *风险最近时间
	  */
	private String riskTime;		
	
	public EchoFraudScreenRiskResult() {
		super();
	}

	public EchoFraudScreenRiskResult(String id){
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
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getRiskDetail() {
		return riskDetail;
	}

	public void setRiskDetail(String riskDetail) {
		this.riskDetail = riskDetail;
	}
	
	public String getRiskItemType() {
		return riskItemType;
	}

	public void setRiskItemType(String riskItemType) {
		this.riskItemType = riskItemType;
	}
	
	public String getRiskItemValue() {
		return riskItemValue;
	}

	public void setRiskItemValue(String riskItemValue) {
		this.riskItemValue = riskItemValue;
	}
	
	public String getRiskTime() {
		return riskTime;
	}

	public void setRiskTime(String riskTime) {
		this.riskTime = riskTime;
	}
	
}