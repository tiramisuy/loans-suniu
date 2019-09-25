package com.rongdu.loans.fileserver.option;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.springframework.util.FileCopyUtils;

/**
 * 
* @Description:  附件上传参数
* @author: 饶文彪
* @date 2018年6月25日 下午3:56:30
 */
public class UploadFileOP implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 业务类型从FileBizCode选取，如：
	 * bizCode=APPLY_LOAN
	 * bizName=贷款申请材料
	 */
	private String bizCode;

	/**
	 * 文件大小
	 */
	private Integer fileSize;

	/**
	 * 文件内容
	 */
	private byte[] content;

	/**
	 * 文件类型
	 * FileType
	 * IMG、VIDEO、DOC
	 */
	private String fileType;

	/**
	 * 文件原始名称
	 */
	private String origName;

	/**
	 * 文件来源ip
	 */
	private String ip;
	/**
	 * 来源（1-ios，2-android，3-H5，4-网站，5-system）
	 */
	private String source;

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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getFileSize() {
		return content == null ? 0 : content.length;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void setContent(InputStream in) {
		try {
			this.content = FileCopyUtils.copyToByteArray(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
