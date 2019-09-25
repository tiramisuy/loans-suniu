package com.rongdu.loans.credit.baiqishi.vo;

import java.io.Serializable;

/**
 * 查询芝麻行业关注名单(扩展信息)-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmWatchListExtendInfo implements Serializable{
	
	private static final long serialVersionUID = 4180575111229587897L;
	/**
	 *对应的字段名
	 */
	private String key;
	/**
	 * 对应的值
	 */
	private String value;
	/**
	 * 对于这个 key  的描述
	 */
	private String description;
	
	public ZmWatchListExtendInfo(){
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}

