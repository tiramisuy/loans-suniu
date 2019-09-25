/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 致诚信用-阿福共享平台-综合决策报告记录Entity
 * @author fy
 * @version 2019-07-05
 */
public class EchoDecisionReport extends BaseEntity<EchoDecisionReport> {
	
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
	  *综合评分(分值范围 300-650)
	  */
	private String compositeScore;		
	/**
	  *决策建议(0-数据不足，未得出决策建议 1-资质良好，通过 2-资质尚可，审核后无风险通过 3-资质一般，正常审核 4-存在一定风险，严格审核 5-资质较差，拒绝)
	  */
	private String decisionSuggest;		
	
	public EchoDecisionReport() {
		super();
	}

	public EchoDecisionReport(String id){
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
	
	public String getCompositeScore() {
		return compositeScore;
	}

	public void setCompositeScore(String compositeScore) {
		this.compositeScore = compositeScore;
	}
	
	public String getDecisionSuggest() {
		return decisionSuggest;
	}

	public void setDecisionSuggest(String decisionSuggest) {
		this.decisionSuggest = decisionSuggest;
	}
	
}