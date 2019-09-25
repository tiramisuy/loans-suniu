package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.Date;

public class OperationLogVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1884927813462326822L;

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
	private String operator;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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
