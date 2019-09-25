package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

public class EmailReportOP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086604470230375752L;
	private String emailId;
	private String taskId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
