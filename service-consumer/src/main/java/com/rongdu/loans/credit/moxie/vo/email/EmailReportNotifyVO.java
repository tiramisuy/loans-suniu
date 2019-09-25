/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 魔蝎信用卡邮箱报告通知VO
 * 
 * @author liuzhuang
 * @version 2018-05-29
 */
public class EmailReportNotifyVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 任务编号
	 */
	private String task_id;
	/**
	 * 用户编号
	 */
	private String user_id;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 邮箱的唯一标识
	 */
	private String email_id;
	/**
	 * 报告处理结果: true:成功,false:没有账单或处理失败
	 */
	private String result;
	/**
	 * 如果result是false, message描述失败的原因. 如果result是true, 则message为前台界面展示的加密请求报文
	 */
	private String message;
	/**
	 * UNIX timestamp(毫秒)
	 */
	private String timestamp;

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
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