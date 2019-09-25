/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 风险分类Entity
 * @author sunda
 * @version 2017-08-14
 */
public class RiskType extends BaseEntity<RiskType> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *风险名称
	  */
	private String name;		
	/**
	  *风险分类(1-风险类别，2-具体风险）
	  */
	private Integer level;		
	/**
	  *规则名称
	  */
	private String pid;		
	
	public RiskType() {
		super();
	}

	public RiskType(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
}