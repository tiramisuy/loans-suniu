/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.oa.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.rongdu.common.persistence.DataEntity;
import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 数据下载表Entity
 * @author zhuchangbing
 * @version 2018-12-24
 */
public class DataDownload extends DataEntity<DataDownload> {
	
	private static final long serialVersionUID = 1L;
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
	private Date startTime;		
	/**
	  *结束时间
	  */
	private Date endTime;		
	
	/**
	 * 备注
	 */
	private String remark;
	
	private Date createTime;
	
	private Date updateTime;
	
	/**
	 * 删除标记（0：正常；1：删除；2：审核）
	 */
	private int del;
	
	public DataDownload() {
		super();
	}

	public DataDownload(String id){
		super(id);
	}
	
	public DataDownload(Integer type, String title, String dataUrl, Date startTime, Date endTime, String remark,
			int del) {
		super();
		this.type = type;
		this.title = title;
		this.dataUrl = dataUrl;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remark = remark;
		this.del = del;
	}

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
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}
		this.updateTime = new Date();
		this.createTime = this.updateTime;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (user!=null&&StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}
		this.updateTime = new Date();
	}
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}