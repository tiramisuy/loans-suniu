/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.entity;


import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

/**
 * 公告Entity
 * @author likang
 * @version 2017-06-30
 */
public class Notification extends BaseEntity<Notification> {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4144545868015490215L;

	/**
	  *消息标题
	  */
	private String title;		
	/**
	  *消息内容
	  */
	private String content;	
    /**
     *消息概要
     */
    private String summary;
    
    /**
	  * 公告发布时间
	  */
	private Date notifyTime;
	/**
	  *公告类型(0-平台公告)
	  */
	private Integer type;		
	/**
	  *状态(0-待发布，1-已发布，2-已下架)
	  */
	private Integer status;		
	
	public Notification() {
		super();
	}

	public Notification(String id){
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
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
}
