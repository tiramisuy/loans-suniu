package com.rongdu.loans.loan.vo;

import java.io.Serializable;

public class FileVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1926530281606810203L;
	
	private String bizCode; // code
	
	private String fileName; // 文件名称
	
	private String url;     // url

	public String getBizCode() {
		return bizCode;
	}

	public String getFileName() {
		return fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
