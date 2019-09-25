/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.vo;

import java.io.Serializable;


/**
 * 省Entity
 * @author wy
 * @version 2017-10-23
 */
public class ProvinceVO  implements Serializable{
	
	private static final long serialVersionUID = 2134105422981293830L;

	/**
	 * 省
	 */
	private int id;
	
	/**
	  *地区
	  */
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}