/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 自动审批结果Entity
 * @author sunda
 * @version 2017-08-27
 */
@Data
public class AutoApprove extends BaseEntity<AutoApprove> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *用户姓名
	  */
	private String name;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *策略集ID
	  */
	private String strategyId;		
	/**
	  *策略集名称
	  */
	private String strategyName;		
	/**
	  *欺诈分
	  */
	private Integer score;		
	/**
	  *风险决策：ACCEPT-通过，REVIEW-人工审批，REJECT-拒绝
	  */
	private String decision;		
	/**
	  *耗时（毫秒）
	  */
	private Integer costTime;		
	/**
	  *命中规则的数量
	  */
	private Integer hitNum;		
	/**
	  *审核时间
	  */
	private Date approveTime;		
	/**
	  *审批状态：SUCCESS-成功，FAILURE-失败
	  */
	private String status;

	private Integer autoApproveStatus2;// 二次机审状态（0：拒绝，1：通过）
	
}