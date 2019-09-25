/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.moxie.vo.bank;

import java.io.Serializable;

/**
 * 魔蝎网银报告通知VO
 * 
 * @author liuzhuang
 * @version 2018-05-29
 */
public class BankReportNotifyVO implements Serializable {

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
	 * 用户姓名
	 */
	private String name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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