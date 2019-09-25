package com.rongdu.loans.app.vo;

import java.io.Serializable;


public class AppUpgradeVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7127675162504071487L;
	
	private String name;    // 名称
	private String type;   // 类型：1-ios，2-android
	private String version; // 版本号
	private String forced; // 是否强制升级：0-非强制，1-强制
	private String fileId;  // 安装包ID
	private String pkgUrl; // 安装包地址
	private String status; // 状态：0-待发布，1-发布，2-失效
	private String remark; // 版本更新提示    
	private Boolean isNewVersionToReq; // 客户的当前版本是否是最新版本
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getForced() {
		return forced;
	}
	public void setForced(String forced) {
		this.forced = forced;
	}
	/**
	 * 客户的当前版本是否是最新版本
	 * @return true 是;false 否;
	 */
	public Boolean isNewVersionToReq() {
		return isNewVersionToReq;
	}
	public void setNewVersionToReq(Boolean isNewVersionToReq) {
		this.isNewVersionToReq = isNewVersionToReq;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		if(null == remark) {
			this.remark = "";
		} else {
			this.remark = remark;
		}
	}
}
