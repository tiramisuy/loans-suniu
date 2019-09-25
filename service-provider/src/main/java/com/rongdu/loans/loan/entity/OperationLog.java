package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.loans.cust.entity.Message;

import java.util.Date;

/**
 * 贷款操作日志Entity
 * @author likang
 * @version 2017-07-04
 */
public class OperationLog extends BaseEntity<Message> {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4760282533062394723L;
	
	/**
	 * 默认操作id
	 */
	public static final String DEFAULT_OPERATOR_ID = "0";
	
	/**
	 * 默认操作id
	 */
	public static final String DEFAULT_OPERATOR_NAME = "system";;
	
	/**
	  *用户ID
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
	  *操作人员
	  */
	private String operatorName;
	/**
	  *处理时间
	  */
	private Date time;		
	/**
	  *来源（1-ios，2-android，3-H5，4-网站，5-系统）
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
	
	public OperationLog() {
		super();
	}

	public OperationLog(String id){
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

	public String getOperatorId() {
		return operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public void defOperatorIdAndName() {
		this.operatorId = DEFAULT_OPERATOR_ID;
		this.operatorName = DEFAULT_OPERATOR_NAME;
		this.time = new Date();
	}
}
