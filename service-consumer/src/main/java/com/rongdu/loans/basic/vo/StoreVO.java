package com.rongdu.loans.basic.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 门店Entity
 * 
 * @author fy
 * @version 2018-01-09
 */
public class StoreVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5453166935723388001L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 门店名称
	 */
	private String name;

	/**
	 * 门店编号
	 */
	private String code;

	private List<StoreVO> children;

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

	public List<StoreVO> getChildren() {
		return children;
	}

	public void setChildren(List<StoreVO> children) {
		this.children = children;
	}

}
