package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * OCR 调用入参
 */
public class OcrOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8903421175387207315L;

	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	@NotBlank(message="进件来源不能为空")
	@Pattern(regexp="1|2|3|4",message="进件来源类型有误")
	private String source;
	
	/**
	 * 聚宝钱包业务流水号
	 */
	// @NotBlank(message="业务流水号不能为空")
	private String orderNo;
	
	/**
	 * 聚宝钱包业务流水号
	 */
	// @NotBlank(message="身份证正面业务流水号不能为空")
	private String frontPhotoOrderNo;
	/**
	 * 聚宝钱包业务流水号
	 */
	// @NotBlank(message="身份证反面业务流水号不能为空")
	private String backPhotoOrderNo;
	
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 申请编号
	 */
	private String applyId;
	private String ip;		// IP地址
	
	private int count;      // 调用次数
	
	/**
	 * 证件姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 出生日期
	 */
	private String birth;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 身份证号
	 */
	private String idcard;
	/**
	 * 证件的有效期
	 */
	private String validDate;
	/**
	 * 发证机关
	 */
	private String authority;
	
	/**
	 *人像面照片，转换后为 JPG 格式
	 */
	private String frontPhoto;
	/**
	 * 国徽面照片，转换后为 JPG 格式
	 */
	private String backPhoto;
	
	private String linkFaceFrontPhoto;
	
	private String linkFaceBackPhoto;
	
	public String getLinkFaceFrontPhoto() {
		return linkFaceFrontPhoto;
	}

	public void setLinkFaceFrontPhoto(String linkFaceFrontPhoto) {
		this.linkFaceFrontPhoto = linkFaceFrontPhoto;
	}

	public String getLinkFaceBackPhoto() {
		return linkFaceBackPhoto;
	}

	public void setLinkFaceBackPhoto(String linkFaceBackPhoto) {
		this.linkFaceBackPhoto = linkFaceBackPhoto;
	}

	public String getFrontPhoto() {
		return frontPhoto;
	}

	public void setFrontPhoto(String frontPhoto) {
		this.frontPhoto = frontPhoto;
	}

	public String getBackPhoto() {
		return backPhoto;
	}

	public void setBackPhoto(String backPhoto) {
		this.backPhoto = backPhoto;
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

	public String getSource() {
		return source;
	}

	public String getIp() {
		return ip;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public String getNation() {
		return nation;
	}

	public String getBirth() {
		return birth;
	}

	public String getAddress() {
		return address;
	}

	public String getIdcard() {
		return idcard;
	}

	public String getValidDate() {
		return validDate;
	}

	public String getAuthority() {
		return authority;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public String getFrontPhotoOrderNo() {
		return frontPhotoOrderNo;
	}

	public String getBackPhotoOrderNo() {
		return backPhotoOrderNo;
	}

	public void setFrontPhotoOrderNo(String frontPhotoOrderNo) {
		this.frontPhotoOrderNo = frontPhotoOrderNo;
	}

	public void setBackPhotoOrderNo(String backPhotoOrderNo) {
		this.backPhotoOrderNo = backPhotoOrderNo;
	}
}
