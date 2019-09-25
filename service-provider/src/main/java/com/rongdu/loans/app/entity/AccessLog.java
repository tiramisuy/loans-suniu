/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.app.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

/**
 * APP访问日志Entity
 * @author likang
 * @version 2017-08-11
 */
public class AccessLog extends BaseEntity<AccessLog> {
	
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
	  *请求方式
	  */
	private String method;		
	/**
	  *访问地址
	  */
	private String requestUrl;		
	/**
	  *模块名称
	  */
	private String moduleName;		
	/**
	  *耗时（ms）}
	  */
	private Long costTime;		
	/**
	  *来源
	  */
	private String source;		
	/**
	  *IP地址
	  */
	private String ip;		
	/**
	  *访问日期（YYYYMMDD）
	  */
	private Integer accessDate;		
	/**
	  *访问时间
	  */
	private Date accessTime;		
	/**
	  *浏览器信息
	  */
	private String userAgent;		
	/**
	  *请求参数
	  */
	private String params;		
	/**
	  *状态
	  */
	private String status;		
	
	public AccessLog() {
		super();
	}

	public AccessLog(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Integer accessDate) {
		this.accessDate = accessDate;
	}
	
	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}