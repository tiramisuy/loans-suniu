package com.rongdu.loans.external.vo;

import java.io.Serializable;

public class BaiduIpLocationVO implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6839015695885902695L;
    
    /**
     * 地址（国际）
     */
    private String address;

	/**
     * 详细内容
     */
    private BaiduContentVO content;
    
    
    /**
     * 返回状态码 
     */
    private Integer status;
    
    /**
     * 返回消息
     */
    private String message;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BaiduContentVO getContent() {
		return content;
	}

	public void setContent(BaiduContentVO content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
