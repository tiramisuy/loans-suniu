package com.rongdu.loans.loan.dto;

import java.io.Serializable;

/**
 * 投复利发标更新贷款资料参数 Created by wy 2017/10/20
 */
public class UpdateLoanInfoDTO implements Serializable {

	private static final long serialVersionUID = -3546755519299191454L;

	private Integer uid; // 用户ID
	private Integer education;// 学历ID
	private Integer job_province;// 工作省份ID
	private Integer job_city;// 工作城市ID
	private Integer job_industry;// 工作行业ID
	private Integer job_company_type;// 公司类型ID
	private Integer have_house;// 是否有房ID
	private Integer job_company_scale;// 公司规模ID
	private Integer house_loan;// 有无房贷ID
	private String job_title;// 职位文本
	private Integer have_car;// 是否有车ID
	private Integer car_loan;// 有无车贷ID
	private Integer marriage;// 是否结婚ID
	private Integer job_salary;// 月收入ID
	private Integer job_years;// 工作年数ID
	private Integer gender;// 性别ID

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public Integer getJob_province() {
		return job_province;
	}

	public void setJob_province(Integer job_province) {
		this.job_province = job_province;
	}

	public Integer getJob_city() {
		return job_city;
	}

	public void setJob_city(Integer job_city) {
		this.job_city = job_city;
	}

	public Integer getJob_industry() {
		return job_industry;
	}

	public void setJob_industry(Integer job_industry) {
		this.job_industry = job_industry;
	}

	public Integer getJob_company_type() {
		return job_company_type;
	}

	public void setJob_company_type(Integer job_company_type) {
		this.job_company_type = job_company_type;
	}

	public Integer getHave_house() {
		return have_house;
	}

	public void setHave_house(Integer have_house) {
		this.have_house = have_house;
	}

	public Integer getJob_company_scale() {
		return job_company_scale;
	}

	public void setJob_company_scale(Integer job_company_scale) {
		this.job_company_scale = job_company_scale;
	}

	public Integer getHouse_loan() {
		return house_loan;
	}

	public void setHouse_loan(Integer house_loan) {
		this.house_loan = house_loan;
	}

	public String getJob_title() {
		return job_title;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public Integer getHave_car() {
		return have_car;
	}

	public void setHave_car(Integer have_car) {
		this.have_car = have_car;
	}

	public Integer getCar_loan() {
		return car_loan;
	}

	public void setCar_loan(Integer car_loan) {
		this.car_loan = car_loan;
	}

	public Integer getMarriage() {
		return marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}

	public Integer getJob_salary() {
		return job_salary;
	}

	public void setJob_salary(Integer job_salary) {
		this.job_salary = job_salary;
	}

	public Integer getJob_years() {
		return job_years;
	}

	public void setJob_years(Integer job_years) {
		this.job_years = job_years;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

}
