/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

/**
 * 贷款操作日志Entity
 * @author Lee
 * @version 2018-05-15
 */
public class OperationLogLater extends BaseEntity<OperationLogLater> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *user_id
	  */
	private String userId;
	/**
	  *贷款申请ID
	  */
	private String applyId;		
	/**
	  *当前阶段
	  */
	private Integer stage;		
	/**
	  *贷款状态
	  */
	private Integer status;		
	/**
	  *前一阶段
	  */
	private Integer previousStage;		
	/**
	  *前一状态
	  */
	private Integer previousStatus;		
	/**
	  *操作人员ID
	  */
	private String operatorId;		
	/**
	  *操作人员姓名
	  */
	private String operatorName;		
	/**
	  *处理时间
	  */
	private Date time;
	/**
	  *来源（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	  */
	private Integer source;		
	/**
	  *IP地址
	  */
	private String ip;		
	/**
	  *拒绝原因ID
	  */
	private String refuseId;		
	
	public OperationLogLater() {
		super();
	}

	public OperationLogLater(String id){
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
	
	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(Integer previousStage) {
		this.previousStage = previousStage;
	}
	
	public Integer getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(Integer previousStatus) {
		this.previousStatus = previousStatus;
	}
	
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getRefuseId() {
		return refuseId;
	}

	public void setRefuseId(String refuseId) {
		this.refuseId = refuseId;
	}


}