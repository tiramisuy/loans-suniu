package com.rongdu.loans.baiqishi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 通讯录
 * @author sunda
 * @version 2017-07-10
 */
public class Contact implements Serializable {
	
	private static final long serialVersionUID = 762999129303027544L;
	/**
	 * 手机号码
	 */
	public String mobile;
	/**
	 * 邮箱地址
	 */
	public String email;
	/**
	 * 公司
	 */
	public String company;
	/**
	 * 部门
	 */
	public String department;
	/**
	 * 职位
	 */
	public String position;
	/**
	 * 地址
	 */
	public String address;
	/**
	 * qq号码
	 */
	public String qq;
	/**
	 * 昵称
	 */
	public String nickname;
	/**
	 * 	备注
	 */
	public String note;
	/**
	 * 是否被删除
	 */
	public String deleted;
	/**
	 * 昵称
	 */
	public String name;


	public Contact() {
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}

	@JsonProperty()
	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getDeleted() {
		return deleted;
	}


	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
