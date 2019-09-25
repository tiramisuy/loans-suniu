/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 腾讯-反欺诈服务-风险信息Entity
 * @author sunda
 * @version 2017-08-14
 */
public class AntiFraudRiskInfo extends BaseEntity<AntiFraudRiskInfo> {
	
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
	  *风险代码
	  */
	private Integer riskCode;		
	/**
	  *风险详情
	  */
	private String riskDetail;		
	
	public AntiFraudRiskInfo() {
		super();
	}

	public AntiFraudRiskInfo(String id){
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
	
	public Integer getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(Integer riskCode) {
		this.riskCode = riskCode;
	}
	
	public String getRiskDetail() {
		return riskDetail;
	}

	public void setRiskDetail(String riskDetail) {
		this.riskDetail = riskDetail;
	}
	
}