package com.rongdu.loans.api.vo;

import java.io.Serializable;

public class OcrVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8720768943694396610L;
	
	private String applyId; // 申请编号
	
	private Integer doResult; // 调用结果
	
	private String frontIDUrl; // 身份证正面URL
	
	private String BackIDUrl; // 身份证反面面URL

	public String getApplyId() {
		return applyId;
	}

	public Integer getDoResult() {
		return doResult;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public void setDoResult(Integer doResult) {
		this.doResult = doResult;
	}

	public String getFrontIDUrl() {
		return frontIDUrl;
	}

	public void setFrontIDUrl(String frontIDUrl) {
		this.frontIDUrl = frontIDUrl;
	}

	public String getBackIDUrl() {
		return BackIDUrl;
	}

	public void setBackIDUrl(String backIDUrl) {
		BackIDUrl = backIDUrl;
	}

}
