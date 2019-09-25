package com.rongdu.loans.loan.dto;

import java.io.Serializable;

/**
 * 推送投复利发标图片应答参数
 * Created by zhangxiaolong on 2017/7/25.
 */
public class UploadPhotoRespDTO implements Serializable {
	
	private static final long serialVersionUID = -3546755519299191454L;

	//返回状态
	private String status;
	
	//描述
	private String message;

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

	
}
