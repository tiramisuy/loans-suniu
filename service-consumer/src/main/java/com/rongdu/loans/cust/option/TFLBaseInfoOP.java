package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * Created by likang on 2017/6/27.
 */
public class TFLBaseInfoOP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 165034260642865940L;

	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */
	@NotBlank(message = "消息来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
	private String source;

	// 居住地址
	@NotBlank(message = "居住地址不能为空")
	private String resideAddr;
	// 居住地所在省份
	@NotBlank(message = "居住地所在省份不能为空")
	private String resideProvince;
	// 居住地所在城市
	@NotBlank(message = "居住地所在城市不能为空")
	private String resideCity;
	// 居住地所在区县
	@NotBlank(message = "居住地所在区县不能为空")
	private String resideDistrict;
	// 婚姻状况
	@NotNull(message = "婚姻状况不能为空")
	private Integer marital;
	// 学历
	@NotBlank(message = "学历不能为空")
	private String degree;
	private String email;
	// 工作地址明细
	@NotBlank(message = "工作地址不能为空")
	private String workAddr;
	// 工作地址省
	@NotBlank(message = "工作地址省不能为空")
	private String workProvince;
	// 工作地址市
	@NotBlank(message = "工作地址市不能为空")
	private String workCity;
	// 工作所在区县
	@NotBlank(message = "工作所在区县不能为空")
	private String workDistrict;
	// 工作岗位
	@NotBlank(message = "工作岗位不能为空")
	private String workPosition;
	// 现单位工作时间
	@NotBlank(message = "现单位工作时间不能为空")
	private String workYear;
	// 工作收入
	@NotNull(message = "工作收入不能为空")
	private Integer indivMonthIncome;
	// 工作岗位
	@NotBlank(message = "公司行业不能为空")
	private String industry;
	// 公司类别
	@NotBlank(message = "公司类别不能为空")
	private String workCategory;
	// 公司规模
	@NotBlank(message = "公司规模不能为空")
	private String workSize;

	// 是否购房
	@NotBlank(message = "是否购房不能为空")
	private String house;
	// 有无房贷
	@NotBlank(message = "有无房贷不能为空")
	private String houseLoan;
	// 是否有车
	@NotBlank(message = "是否有车不能为空")
	private String car;
	// 有无车贷
	@NotBlank(message = "有无车贷不能为空")
	private String carLoan;
	// 联系人（父母）
	@NotBlank(message = "联系人（父母）不能为空")
	private String contactParent;
	// 联系人（配偶）
	private String contactSpouse;
	// 联系人（朋友）
	@NotBlank(message = "联系人（朋友）不能为空")
	private String contactFriend;
	// 联系人（同事）
	@NotBlank(message = "联系人（同事）不能为空")
	private String contactColleague;
	
	private String remark;
	private String resideAll;
	private String workAll;
	private String parentAddrAll;
	
	// 父母居住地址
    private String parentAddress;
    // 居住地所在省份
    private String parentProvince;
    // 居住地所在城市
    private String parentCity;
    // 居住地所在区县
    private String parentDistrict;

	// 真实姓名
	private String userName;
	// userId
	private String userId;
	// 证件类型
	private String idType;
	// 证件号
	private String idNo;
	// 手机号
	private String mobile;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getResideAddr() {
		return resideAddr;
	}

	public void setResideAddr(String resideAddr) {
		this.resideAddr = resideAddr;
	}

	public String getResideProvince() {
		return resideProvince;
	}

	public void setResideProvince(String resideProvince) {
		this.resideProvince = resideProvince;
	}

	public String getResideCity() {
		return resideCity;
	}

	public void setResideCity(String resideCity) {
		this.resideCity = resideCity;
	}

	public String getResideDistrict() {
		return resideDistrict;
	}

	public void setResideDistrict(String resideDistrict) {
		this.resideDistrict = resideDistrict;
	}

	public Integer getMarital() {
		return marital;
	}

	public void setMarital(Integer marital) {
		this.marital = marital;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public String getWorkProvince() {
		return workProvince;
	}

	public void setWorkProvince(String workProvince) {
		this.workProvince = workProvince;
	}

	public String getWorkCity() {
		return workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getWorkDistrict() {
		return workDistrict;
	}

	public void setWorkDistrict(String workDistrict) {
		this.workDistrict = workDistrict;
	}

	public String getWorkPosition() {
		return workPosition;
	}

	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
	}

	public String getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(String workCategory) {
		this.workCategory = workCategory;
	}

	public String getWorkSize() {
		return workSize;
	}

	public void setWorkSize(String workSize) {
		this.workSize = workSize;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public Integer getIndivMonthIncome() {
		return indivMonthIncome;
	}

	public void setIndivMonthIncome(Integer indivMonthIncome) {
		this.indivMonthIncome = indivMonthIncome;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getHouseLoan() {
		return houseLoan;
	}

	public void setHouseLoan(String houseLoan) {
		this.houseLoan = houseLoan;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getCarLoan() {
		return carLoan;
	}

	public void setCarLoan(String carLoan) {
		this.carLoan = carLoan;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
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

	public String getContactParent() {
		return contactParent;
	}

	public void setContactParent(String contactParent) {
		this.contactParent = contactParent;
	}

	public String getContactSpouse() {
		return contactSpouse;
	}

	public void setContactSpouse(String contactSpouse) {
		this.contactSpouse = contactSpouse;
	}

	public String getContactFriend() {
		return contactFriend;
	}

	public void setContactFriend(String contactFriend) {
		this.contactFriend = contactFriend;
	}

	public String getContactColleague() {
		return contactColleague;
	}

	public void setContactColleague(String contactColleague) {
		this.contactColleague = contactColleague;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResideAll() {
		return resideAll;
	}

	public void setResideAll(String resideAll) {
		this.resideAll = resideAll;
	}

	public String getWorkAll() {
		return workAll;
	}

	public void setWorkAll(String workAll) {
		this.workAll = workAll;
	}

	public String getParentAddress() {
		return parentAddress;
	}

	public void setParentAddress(String parentAddress) {
		this.parentAddress = parentAddress;
	}

	public String getParentProvince() {
		return parentProvince;
	}

	public void setParentProvince(String parentProvince) {
		this.parentProvince = parentProvince;
	}

	public String getParentCity() {
		return parentCity;
	}

	public void setParentCity(String parentCity) {
		this.parentCity = parentCity;
	}

	public String getParentDistrict() {
		return parentDistrict;
	}

	public void setParentDistrict(String parentDistrict) {
		this.parentDistrict = parentDistrict;
	}

	public String getParentAddrAll() {
		return parentAddrAll;
	}

	public void setParentAddrAll(String parentAddrAll) {
		this.parentAddrAll = parentAddrAll;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
