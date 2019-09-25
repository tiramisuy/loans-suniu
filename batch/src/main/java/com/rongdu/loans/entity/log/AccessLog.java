/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 14:55:01
 *
 *******************************************************************************/
package com.rongdu.loans.entity.log;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 访问日志实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="access_log")
public class AccessLog  implements java.io.Serializable {

	/**
 	  *  ID
      */
 	private Long id;
	/**
 	  *  登录日志ID
      */
 	private Long loginLogId;
	/**
 	  *  用户ID
      */
 	private Long userId;
	/**
 	  *  用户名
      */
 	private String username;
	/**
 	  *  姓名
      */
 	private String empName;
	/**
 	  *  请求方式
      */
 	private String method;
	/**
 	  *  访问地址
      */
 	private String requestUrl;
	/**
 	  *  模块名称
      */
 	private String moduleName;
	/**
 	  *  耗时（ms）
      */
 	private Long costedTime;
	/**
 	  *  浏览器信息
      */
 	private String userAgent;
	/**
 	  *  IP地址
      */
 	private String ip;
	/**
 	  *  访问日期
      */
 	private Integer accessDate;
	/**
 	  *  访问时间
      */
 	private Timestamp accessTime;
	/**
 	  *  备注
      */
 	private String params;
	/**
 	  *  状态
      */
 	private Integer status;

    public AccessLog() {
    }
	
    public AccessLog(Long id, String requestUrl, String ip, Timestamp accessTime) {
        this.id = id;
        this.requestUrl = requestUrl;
        this.ip = ip;
        this.accessTime = accessTime;
    }
    public AccessLog(Long id, Long loginLogId, Long userId, String username, String empName, String method, String requestUrl, String moduleName, Long costedTime, String userAgent, String ip, Integer accessDate, Timestamp accessTime, String params, Integer status) {
       this.id = id;
       this.loginLogId = loginLogId;
       this.userId = userId;
       this.username = username;
       this.empName = empName;
       this.method = method;
       this.requestUrl = requestUrl;
       this.moduleName = moduleName;
       this.costedTime = costedTime;
       this.userAgent = userAgent;
       this.ip = ip;
       this.accessDate = accessDate;
       this.accessTime = accessTime;
       this.params = params;
       this.status = status;
    }
   
   /**     
     *访问<ID>属性
     */
    @Id 
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    public Long getId() {
        return this.id;
    }	    
    /**     
     *设置<ID>属性
     */
    public void setId(Long id) {
        this.id = id;
    }
   /**     
     *访问<登录日志ID>属性
     */
    @Column(name="login_log_id")
    public Long getLoginLogId() {
        return this.loginLogId;
    }	    
    /**     
     *设置<登录日志ID>属性
     */
    public void setLoginLogId(Long loginLogId) {
        this.loginLogId = loginLogId;
    }
   /**     
     *访问<用户ID>属性
     */
    @Column(name="user_id")
    public Long getUserId() {
        return this.userId;
    }	    
    /**     
     *设置<用户ID>属性
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
   /**     
     *访问<用户名>属性
     */
    @Column(name="username", length=20)
    public String getUsername() {
        return this.username;
    }	    
    /**     
     *设置<用户名>属性
     */
    public void setUsername(String username) {
        this.username = username;
    }
   /**     
     *访问<姓名>属性
     */
    @Column(name="emp_name", length=20)
    public String getEmpName() {
        return this.empName;
    }	    
    /**     
     *设置<姓名>属性
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }
   /**     
     *访问<请求方式>属性
     */
    @Column(name="method", length=10)
    public String getMethod() {
        return this.method;
    }	    
    /**     
     *设置<请求方式>属性
     */
    public void setMethod(String method) {
        this.method = method;
    }
   /**     
     *访问<访问地址>属性
     */
    @Column(name="request_url", nullable=false, length=100)
    public String getRequestUrl() {
        return this.requestUrl;
    }	    
    /**     
     *设置<访问地址>属性
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
   /**     
     *访问<模块名称>属性
     */
    @Column(name="module_name", length=20)
    public String getModuleName() {
        return this.moduleName;
    }	    
    /**     
     *设置<模块名称>属性
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
   /**     
     *访问<耗时（ms）>属性
     */
    @Column(name="costed_time")
    public Long getCostedTime() {
        return this.costedTime;
    }	    
    /**     
     *设置<耗时（ms）>属性
     */
    public void setCostedTime(Long costedTime) {
        this.costedTime = costedTime;
    }
   /**     
     *访问<浏览器信息>属性
     */
    @Column(name="user_agent", length=100)
    public String getUserAgent() {
        return this.userAgent;
    }	    
    /**     
     *设置<浏览器信息>属性
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
   /**     
     *访问<IP地址>属性
     */
    @Column(name="ip", nullable=false, length=20)
    public String getIp() {
        return this.ip;
    }	    
    /**     
     *设置<IP地址>属性
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
   /**     
     *访问<访问日期>属性
     */
    @Column(name="access_date")
    public Integer getAccessDate() {
        return this.accessDate;
    }	    
    /**     
     *设置<访问日期>属性
     */
    public void setAccessDate(Integer accessDate) {
        this.accessDate = accessDate;
    }
   /**     
     *访问<访问时间>属性
     */
    @Column(name="access_time")
    public Timestamp getAccessTime() {
        return this.accessTime;
    }	    
    /**     
     *设置<访问时间>属性
     */
    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
    }
   /**     
     *访问<备注>属性
     */
    @Column(name="params", length=500)
    public String getParams() {
        return this.params;
    }	    
    /**     
     *设置<备注>属性
     */
    public void setParams(String params) {
        this.params = params;
    }
   /**     
     *访问<状态>属性
     */
    @Column(name="status")
    public Integer getStatus() {
        return this.status;
    }	    
    /**     
     *设置<状态>属性
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


}
