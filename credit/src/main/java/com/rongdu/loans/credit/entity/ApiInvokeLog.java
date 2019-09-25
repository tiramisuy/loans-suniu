/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

/**
 * 征信数据合作机构接口调用日志Entity
 * @author sunda
 * @version 2017-08-15
 */
public class ApiInvokeLog extends BaseEntity<ApiInvokeLog> {
	
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
	  *交易ID
	  */
	private String bizId;		
	/**
	  *业务/产品/服务代码
	  */
	private String bizCode;		
	/**
	  *业务/产品/服务名称
	  */
	private String bizName;		
	/**
	  *合作机构代码
	  */
	private String partnerId;		
	/**
	  *合作机构名称
	  */
	private String partnerName;		
	/**
	  *月份
	  */
	private Integer month;		
	/**
	  *调用时间
	  */
	private Date invokeTime;		
	/**
	  *耗时（秒）
	  */
	private Long costTime;		
	/**
	  *费用
	  */
	private String fee;		
	/**
	  *url
	  */
	private String url;		
	/**
	  *是否成功
	  */
	private Boolean success;		
	/**
	  *结果
	  */
	private String status;		
	/**
	  *请求内容
	  */
	private String reqContent;		
	/**
	  *同步应答内容
	  */
	private String syncRespContent;		
	/**
	  *异步应答内容
	  */
	private String asyncRespContent;		
	
	public ApiInvokeLog() {
		super();
	}

	public ApiInvokeLog(String id){
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
	
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	
	public Date getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(Date invokeTime) {
		this.invokeTime = invokeTime;
	}
	
	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}
	
	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReqContent() {
		return reqContent;
	}

	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}
	
	public String getSyncRespContent() {
		return syncRespContent;
	}

	public void setSyncRespContent(String syncRespContent) {
		this.syncRespContent = syncRespContent;
	}
	
	public String getAsyncRespContent() {
		return asyncRespContent;
	}

	public void setAsyncRespContent(String asyncRespContent) {
		this.asyncRespContent = asyncRespContent;
	}
	
}