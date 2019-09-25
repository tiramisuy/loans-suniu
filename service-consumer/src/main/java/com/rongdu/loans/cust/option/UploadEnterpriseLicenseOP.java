package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 */
public class UploadEnterpriseLicenseOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8903421175387207315L;

	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */
	@NotBlank(message = "进件来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "进件来源类型有误")
	private String source;

	/**
	 * 用户id
	 */
	private String userId;
	private String ip; // IP地址
	/**
	 * 企业营业执照照片
	 */
	@NotBlank(message = "企业营业执照图片不能为空")
	private String enterpriseLicensePhoto;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEnterpriseLicensePhoto() {
		return enterpriseLicensePhoto;
	}

	public void setEnterpriseLicensePhoto(String enterpriseLicensePhoto) {
		this.enterpriseLicensePhoto = enterpriseLicensePhoto;
	}

}
