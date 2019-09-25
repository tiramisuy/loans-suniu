/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.test.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rongdu.common.persistence.DataEntity;

/**
 * 员工信息Entity
 * @author sunda
 * @version 2016-11-20
 */
public class SysEmp extends DataEntity<SysEmp> {
	
	private static final long serialVersionUID = 1L;
	/**
 	*员工号
	*/
	private String empNo;		
	/**
 	*姓名
	*/
	private String empName;		
	/**
 	*性别
	*/
	private String sex;		
	/**
 	*生日
	*/
	private Date birthday;		
	/**
 	*备注
	*/
	private String remark;		
	/**
 	*创建时间
	*/
	private Date createTime;		
	/**
 	*修改时间
	*/
	private Date updateTime;		
	
	public SysEmp() {
		super();
	}

	public SysEmp(String id){
		super(id);
	}

	@Length(min=1, max=10, message="员工号长度必须介于 1 和 10 之间")
	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	
	@Length(min=1, max=20, message="姓名长度必须介于 1 和 20 之间")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	@Length(min=1, max=1, message="性别长度必须介于 1 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}