package com.rongdu.loans.fileserver.option;

import com.rongdu.loans.fileserver.common.validator.SupportFileExt;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 文档上传请求参数
 * @author sunda
 * @version 2017-06-27
 */
public class UploadDocumentOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5968189183759588166L;
	
	/**
	 * 用户ID
	 */
	@NotBlank(message="请填写用户ID")
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
	@NotBlank(message="请填写业务类型")
	private String bizCode;

	@NotNull(message="上传的文档不能为空")
	@SupportFileExt(regexp="doc|pdf|txt|xls|xlsx|json|csv")
	private MultipartFile file;
	
	@NotBlank(message="请填写文件类型")
	@Pattern(regexp="DOC",message="文件类型有误")
	private String fileType;
	
	@NotBlank(message="请填写文件原名")
	private String origName;
	
	@NotBlank(message="用户端的IP地址不能为空")
	private String ip;	
	/**
	 * 来源（1-ios，2-android，3-H5，4-网站，5-system）
	 */
	@NotBlank(message="请选择文件来源")
	@Pattern(regexp="1|2|3|4|5",message="文件来源有误")
	private String source;

	private String remark;

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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
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


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
