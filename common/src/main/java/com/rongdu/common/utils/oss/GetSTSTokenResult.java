package com.rongdu.common.utils.oss;

import java.io.Serializable;
import java.util.Map;

public class GetSTSTokenResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5120603431007100312L;
	private String status;
	private String message;
	private Map<String, String> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
