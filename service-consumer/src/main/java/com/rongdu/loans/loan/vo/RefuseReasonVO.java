/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 贷款审核拒绝原因Entity
 * @author zhangxiaolong
 * @version 2017-07-07
 */
public class RefuseReasonVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * ID，实体唯一标识
	 */
	private String id;
	/**
	  *拒绝原因
	  */
	private String reason;
	/**
	  *层级（1-总体原因，呈现给用户，2-具体原因，用于数据分析）
	  */
	private Integer level;
	/**
	  *由于此原因被拒绝的贷款申请数量
	  */
	private Integer num;
	/**
	  *上级拒绝原因ID
	  */
	private String pid;
	/**
	 * 排序字段
	 */
	private Integer sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}