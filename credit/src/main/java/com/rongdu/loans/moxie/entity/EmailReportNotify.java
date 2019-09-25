/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 魔蝎信用卡邮箱报告通知表Entity
 * @author liuzhuang
 * @version 2018-05-29
 */
public class EmailReportNotify extends BaseEntity<EmailReportNotify> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *任务编号
	  */
	private String taskId;		
	/**
	  *用户编号
	  */
	private String userId;		
	/**
	  *邮箱
	  */
	private String email;		
	/**
	  *邮箱的唯一标识
	  */
	private String emailId;		
	/**
	  *报告处理结果: true:成功,false:没有账单或处理失败
	  */
	private String result;		
	/**
	  *如果result是false, message描述失败的原因. 如果result是true, 则message为前台界面展示的加密请求报文
	  */
	private String message;		
	/**
	  *UNIX timestamp(毫秒)
	  */
	private String timestamp;		
	
	public EmailReportNotify() {
		super();
	}

	public EmailReportNotify(String id){
		super(id);
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}