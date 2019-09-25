package com.rongdu.loans.loan.dto;

import java.io.Serializable;

/**
 * 图片上传请求参数
 */
public class UploadPhotoDTO implements Serializable {
	
	private static final long serialVersionUID = -713933551759782609L;

	//图片上传url地址 ，多个url用;分隔，只上传文件的名称.如:abc.jpg；ddd.jpg
	private String url;
	
	//标编号
	private int lid;
	
	//备注信息 多个备注用;分隔 借款人身份证； 借款人结婚证
	private String remark;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
