/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.oa.op;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.rongdu.common.persistence.DataEntity;
import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 数据下载表OP
 * @author zhuchangbing
 * @version 2018-12-24
 */
public class DataDownloadOP implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	  *1:合同，2:导出数据
	  */
	private Integer type;		
	/**
	  *标题
	  */
	private String title;		
	/**
	  *数据连接
	  */
	private String dataUrl;		
	/**
	  *开始时间
	  */
	private String startTime;		
	/**
	  *结束时间
	  */
	private String endTime;		
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 删除标记（0：正常；1：删除；2：审核）
	 */
	private int del;
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}