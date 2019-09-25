package com.rongdu.loans.basic.entity;

import java.io.Serializable;

public class Store implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -543085063607918479L;

	/**
	 * 门店id
	 */
	private String id;
	
	/**
	  *门店名称
	  */
	private String name;
	
	/**
	 * 门店编号
	 */
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
