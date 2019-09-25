package com.rongdu.loans.basic.vo;

import java.io.Serializable;

public class AreaSimpleVO implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 6090442605480494203L;

	/**
	  * 地区代码
	  */
	private String areaCode;
	
	/**
	  * 地区名称
	  */
	private String areaName;

	public String getAreaCode() {
		return areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
