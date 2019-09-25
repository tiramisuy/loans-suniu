package com.rongdu.loans.basic.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

/**
 * 短信日志Entity
 * @author likang
 * @version 2017-07-25
 */
public class SmsLog extends BaseEntity<SmsLog>{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2462545993376773035L;

	/**
	  *手机号码
	  */
	private String mobile;		
	/**
	  *短信动态码
	  */
	private String smsCode;		
	/**
	  *短信类型:如：1为注册,2为找回密码....,默认为1
	  */
	private String type;		
	/**
	  *来源于哪个终端（1-ios，2-android，3-H5，4-api，5-网站，6-system）
	  */
	private String source;		
	/**
	  *短信内容
	  */
	private String content;		
	/**
	  *请求IP
	  */
	private String ip;		
	/**
	  *添加时间
	  */
	private Date sendTime;		
	/**
	  *用户ID,注册为0
	  */
	private String userId;		
	/**
	  *短信通道代码
	  */
	private String channelCode;		
	/**
	  *短信通道名称
	  */
	private String channelName;		
	/**
	  *状态（0-待发送，1-已经发送，2-发送成功，3-发送失败）
	  */
	private Integer status;		
	
	public SmsLog() {
		super();
	}

	public SmsLog(String id){
		super(id);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
