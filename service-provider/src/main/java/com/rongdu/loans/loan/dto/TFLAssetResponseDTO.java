package com.rongdu.loans.loan.dto;

import java.io.Serializable;

/**
 * 投复利发标应答参数
 * Created by zhangxiaolong on 2017/7/25.
 */
public class TFLAssetResponseDTO implements Serializable {
	
	private static final long serialVersionUID = -3546755519299191454L;

	//返回状态
	private String status;
	
	//标编号
	private int lid;
	
	//描述
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
