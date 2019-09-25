package com.rongdu.loans.cust.vo;

import java.io.Serializable;

public class MessageVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4945075439575935601L;

	/**
	 *消息id
	 */
	private String msgId;
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
	 *通知时间(yyyy-MM-dd HH:mm)
	 */
	private String notifyTime;
	/**
	 *通知方式(1-站内信，2-消息推送，3-短信)
	 */
	private Integer notifyType;
	/**
	 *查看时间
	 */
	private String viewTime;

	/**
	 * 消息状态（0-未读，1-已读）
	 */
	private Integer viewStatus;

	/**
	 *状态(0-待发送，1-发送成功，2-发送失败)
	 */
	private Integer status;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
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

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public String getViewTime() {
		return viewTime;
	}

	public void setViewTime(String viewTime) {
		this.viewTime = viewTime;
	}

	public Integer getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
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
