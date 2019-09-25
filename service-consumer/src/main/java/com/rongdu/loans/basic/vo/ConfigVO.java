/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.vo;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 公共配置表Entity
 * @author liuzhuang
 * @version 2018-07-16
 */
public class ConfigVO extends BaseEntity<ConfigVO> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *参数名
	  */
	private String key;		
	/**
	  *参数值
	  */
	private String value;
	/**
	  *备注
	  */
	private String remark;
	/**
	 * 	最后修改者userId
	 */
	protected String updateBy;
	/**
	 *  更新日期
	 */
	protected Date updateTime;	
	
	public ConfigVO() {
		super();
	}

	public ConfigVO(String id){
		super(id);
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}