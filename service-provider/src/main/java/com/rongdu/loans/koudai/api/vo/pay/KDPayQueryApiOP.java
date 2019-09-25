package com.rongdu.loans.koudai.api.vo.pay;

import java.io.Serializable;

public class KDPayQueryApiOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String project_name;// 项目名
	private String yur_ref;// 放款订单号，固定30位

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getYur_ref() {
		return yur_ref;
	}

	public void setYur_ref(String yur_ref) {
		this.yur_ref = yur_ref;
	}

}
