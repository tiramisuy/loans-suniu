package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;

public class KDWithdrawResultDataVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 页面html,form表单自动提交跳转
	 */
	private String html;
	/**
	 * 
	 */
	private String url;
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
