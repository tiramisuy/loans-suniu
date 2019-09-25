/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.entity;

import java.io.Serializable;


/**
 * 地区Entity
 * @author wy
 * @version 2017-10-23
 */
public class City  implements Serializable{
	
	private static final long serialVersionUID = 2621431516564823801L;

	/**
	 * id
	 */
	private int id;
	
	/**
	 * 省id
	 */
	private int fid;
	
	/**
	  *地区
	  */
	private String name;
	
	/**
	 *省名
	 */
	private String fname;

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

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	
}