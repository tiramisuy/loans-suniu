package com.rongdu.loans.sys.vo;

import java.io.Serializable;
import java.util.Date;

public class UserVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;
	
	/**
	  *归属公司
	  */
	private String companyId;		
	/**
	  *归属部门
	  */
	private String officeId;		
	/**
	  *登录名
	  */
	private String loginName;		
	/**
	  *密码
	  */
	private String password;		
	/**
	  *工号
	  */
	private String no;		
	/**
	  *姓名
	  */
	private String name;		
	/**
	  *邮箱
	  */
	private String email;		
	/**
	  *电话
	  */
	private String phone;		
	/**
	  *手机
	  */
	private String mobile;		
	/**
	  *用户类型
	  */
	private String userType;		
	/**
	  *用户头像
	  */
	private String photo;		
	/**
	  *最后登陆IP
	  */
	private String loginIp;		
	/**
	  *最后登陆时间
	  */
	private Date loginDate;		
	/**
	  *是否可登录
	  */
	private String loginFlag;		
	/**
	  *渠道
	  */
	private String channel;		
	/**
	  *坐席工号
	  */
	private String callId;		
	/**
	  *创建时间
	  */
	private Date createDate;		
	/**
	  *更新时间
	  */
	private Date updateDate;		
	/**
	  *备注信息
	  */
	private String remarks;		
	/**
	  *删除标记
	  */
	private String delFlag;		
	/**
	  *分配开关（0 不可分配  1可分配 ）
	  */
	private Integer allotFlag;		
	

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public Integer getAllotFlag() {
		return allotFlag;
	}

	public void setAllotFlag(Integer allotFlag) {
		this.allotFlag = allotFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
