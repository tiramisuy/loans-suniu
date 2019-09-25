package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;

public class KDComprehensiveResultDataVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 订单id
	 */
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
