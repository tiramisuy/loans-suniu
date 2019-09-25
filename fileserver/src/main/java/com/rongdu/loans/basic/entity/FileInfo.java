/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rongdu.common.persistence.BaseEntity;

/**
 * 影像资料Entity
 * @author sunda
 * @version 2017-07-12
 */
public class FileInfo extends BaseEntity<FileInfo> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *业务代码
	  */
	private String bizCode;		
	/**
	  *业务名称
	  */
	private String bizName;		
	/**
	  *文件名称
	  */
	private String fileName;		
	/**
	  *文件原名
	  */
	private String origName;		
	/**
	  *文件类型
	  */
	private String fileType;		
	/**
	  *文件后缀
	  */
	private String fileExt;		
	/**
	  *文件大小
	  */
	private Long fileSize;		
	/**
	  *文件大小描述
	  */
	private String fileSizeDesc;		
	/**
	  *所在服务器
	  */
	private String server;		
	/**
	  *相对地址
	  */
	private String relativePath;		
	/**
	  *绝对地址
	  */
	private String absolutePath;		
	/**
	  *访问地址
	  */
	private String url;		
	/**
	  *来源（1-ios，2-android，3-H5，4-网站，5-system）
	  */
	private Integer source;		
	/**
	  *文件来源IP
	  */
	private String ip;		
	/**
	  *状态
	  */
	private Integer status;		
	
	public FileInfo() {
		super();
	}

	public FileInfo(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFileSizeDesc() {
		return fileSizeDesc;
	}

	public void setFileSizeDesc(String fileSizeDesc) {
		this.fileSizeDesc = fileSizeDesc;
	}
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	@JsonIgnore
	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	@JsonIgnore
	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}