package com.rongdu.loans.koudai.op.create;

import java.io.Serializable;

public class KDUserBaseOP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id_number;//身份证号，必须唯一
	private String name;//用户姓名
	private Long phone;//用户手机号,11位数字
	private Integer education_level;//文化程度(1:'博士',2:'硕士',3:'本科',4:'大专'5:'中专',6:'高中',7:'初中',8:'初中以下')

	private String property;//男或女
	private Integer type;//借款人类型(1:企业，2:个人)
	private Long birthday;//借款人出生日期的时间戳表示
	private String contact_username;//紧急/其他联系人姓名
	private Integer contact_phone;//紧急/其他联系人手机号
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public Integer getEducation_level() {
		return education_level;
	}
	public void setEducation_level(Integer education_level) {
		this.education_level = education_level;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	public String getContact_username() {
		return contact_username;
	}
	public void setContact_username(String contact_username) {
		this.contact_username = contact_username;
	}
	public Integer getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(Integer contact_phone) {
		this.contact_phone = contact_phone;
	}
	
	
	
	
}
