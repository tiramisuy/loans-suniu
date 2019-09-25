package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 宜信致诚风险名单-请求参数
 * @author sunda
 * @version 2017-07-10
 */
public class RiskListRequestParam implements Serializable{

	private static final long serialVersionUID = 7209682953717889561L;

	/**
	 * 被查询人姓名
	 */
	private String name;
	/**
	 * 被查询人证件号码
	 */
	private String idNo;
	/**
	 * 被查询人手机号码
	 */
	private String mobile;
	/**
	 * 联系人电话号码，多个用逗号分隔；至多 4 个
	 */
	private String contactPhone;
	/**
	 * 工作单位固话，需含“中横线”，示例：010-65419821
	 */
	private String companyPhone;
	/**
	 * 工作单位地址
	 */
	private String companyAddress;
	/**
	 *家庭固话，需含“中横线”，示例：010-84419832
	 */
	private String familyPhone;
	/**
	 *家庭地址
	 */
	private String familyAddress;
	/**
	 *邮箱地址
	 */
	private String email;
	/**
	 * qq号码
	 */
	private String qq;
	/**
	 *银行卡号
	 */
	private String bankCardNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getFamilyPhone() {
		return familyPhone;
	}
	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}
	public String getFamilyAddress() {
		return familyAddress;
	}
	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getBankCardNumber() {
		return bankCardNumber;
	}
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	

}