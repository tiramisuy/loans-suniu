/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;
/**
 * 消息Entity
 * @author likang
 * @version 2017-06-30
 */
public class Message extends BaseEntity<Message> {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3725350581374551500L;

	/**
	  *消息标题
	  */
	private String title;		
	/**
	  *消息内容
	  */
	private String content;		
	/**
	  *消息类型(0-系统提示)
	  */
	private Integer type;		
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *通知时间
	  */
	private Date notifyTime;		
	/**
	  *通知方式(1-站内信，2-消息推送，3-短信)
	  */
	private Integer notifyType;		
	/**
	  *查看时间
	  */
	private Date viewTime;		
	/**
	  *在哪个终端查看（1-ios，2-android，3-H5，4-网站）
	  */
	private Integer viewSource;	
	
	/**
	  * 消息状态（0-未读，1-已读）
	  */
	private Integer viewStatus;
	/**
	  *查阅消息的IP地址
	  */
	private String ip;		
	/**
	  *状态(0-待发送，1-发送成功，2-发送失败)
	  */
	private Integer status;		
	
	public Message() {
		super();
	}

	public Message(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}
	
	public Date getViewTime() {
		return viewTime;
	}

	public void setViewTime(Date viewTime) {
		this.viewTime = viewTime;
	}
	
	public Integer getViewSource() {
		return viewSource;
	}

	public void setViewSource(Integer viewSource) {
		this.viewSource = viewSource;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
