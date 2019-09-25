/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

public class AppUpgrade extends BaseEntity<AppUpgrade> {
	private static final long serialVersionUID = 1249706681952387160L;
	
	private String name;		
	private String type;		
	private String version;		
	private String fileId;		
	private String pkgUrl;		
	private String remark;
	private String forced;		
	private String status;	
	
	public AppUpgrade() {
		super();
	}

	public AppUpgrade(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getForced() {
		return forced;
	}

	public void setForced(String forced) {
		this.forced = forced;
	}
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public String getPkgUrl() {
		return pkgUrl;
	}

	public void setPkgUrl(String pkgUrl) {
		this.pkgUrl = pkgUrl;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
	
}