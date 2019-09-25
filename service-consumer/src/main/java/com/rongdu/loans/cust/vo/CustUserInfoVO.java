package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.util.List;

public class CustUserInfoVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5613073683065873328L;

	private String userId; // 用户id
	private String userName; // 姓名
	private String idType; // 证件类型
	private String idNo; // 证件号码
	private String mobile; // 手机号码
	private String email; // 手机号码
	private Integer sex; // 性别
	private Integer marital; // 婚姻状况
	private String degree; // 学历
	private String nation; // 民族
	private String resideType; // 居住性质（0-自由住房，1-租住，2-与亲属同住，3-单位宿舍，4-其他）
	private String resideAddr; // 居住地址
	private String resideYear; // 居住年限（月）
	private String resideProvince; // 居住地所在省份
	private String resideProvinceName; // 居住地所在省份名称
	private String resideCity; // 居住地所在城市
	private String resideCityName; // 居住地所在城市名称
	private String resideDistrict; // 居住地所在区县
	private String resideDistrictName; // 居住地所在区县名称
	private String regAddr; // 户籍地址
	private String parentProvince; // 父母居住省份
	private String parentCity; // 父母居住城市
	private String parentDistrict; // 父母居住区县
	private String parentAddress; // 父母居住地址
	private String parentProvinceName; // 父母居住省份名称
	private String parentCityName; // 父母居住城市名称
	private String parentDistrictName; // 父母居住区县名称
	private String regProvince; // 户籍所在省份
	private String regCity; // 户籍所在城市
	private String regDistrict; // 户籍所在区县
	private Integer indivMonthIncome; // 个人月收入
	private Double familyMonthIncome; // 家庭月收入
	private String pafNo; // 公积金账号
	private String ssNo; // 社保账号
	private String industry; // 行业
	private String comName; // 工作单位
	private String workAddr; // 工作地址
	private String workProvince; // 工作所在省份
	private String workProvinceName; // 工作所在省份名称
	private String workCity; // 工作所在城市
	private String workCityName; // 工作所在城市名称
	private String workDistrict; // 工作所在区县
	private String workDistrictName; // 工作所在区县名称
	private String workPosition; // 岗位
	private String workYear; // 工作年限
	private String comTelZone; // 单位联系电话区号
	private String comTelNo; // 单位联系电话
	private String comTelExt; // 单位联系电话分机号
	private Integer status; // 用户状态
	private String qq; // qq号

	private List<CustContactVO> contactList; // 联络人列表

	public CustUserInfoVO() {
		this.userId = ""; // 用户id
		this.userName = ""; // 姓名
		this.idType = ""; // 证件类型
		this.idNo = ""; // 证件号码
		this.mobile = ""; // 手机号码
		this.sex = 1; // 性别
		this.marital = 2; // 婚姻状况(1:已婚，2未婚，3离异，4丧偶)
		this.degree = ""; // 学历
		this.nation = ""; // 民族
		this.resideType = "0"; // 居住性质（0-自由住房，1-租住，2-与亲属同住，3-单位宿舍，4-其他）
		this.resideAddr = ""; // 居住地址
		this.resideYear = ""; // 居住年限（月）
		this.resideProvince = ""; // 居住地所在省份
		this.resideCity = ""; // 居住地所在城市
		this.resideDistrict = ""; // 居住地所在区县
		this.resideProvinceName = ""; // 居住地所在省份名称
		this.resideCityName = ""; // 居住地所在城市名称
		this.resideDistrictName = ""; // 居住地所在区县名称
		this.regAddr = ""; // 户籍地址
		this.regProvince = ""; // 户籍所在省份
		this.regCity = ""; // 户籍所在城市
		this.regDistrict = ""; // 户籍所在区县
		this.pafNo = ""; // 公积金账号
		this.ssNo = ""; // 社保账号
		this.industry = ""; // 行业
		this.comName = ""; // 工作单位
		this.workAddr = ""; // 工作地址
		this.workProvince = ""; // 工作所在省份
		this.workCity = ""; // 工作所在城市
		this.workDistrict = ""; // 工作所在区县
		this.workProvinceName = ""; // 工作所在省份名称
		this.workCityName = ""; // 工作所在城市名称
		this.workDistrictName = ""; // 工作所在区县名称
		this.workPosition = ""; // 岗位
		this.workYear = ""; // 工作年限
		this.comTelZone = ""; // 单位联系电话区号
		this.comTelNo = ""; // 单位联系电话
		this.comTelExt = ""; // 单位联系电话分机号
		this.status = 0; // 用户状态
		this.qq = "";
	}

	public String getUserName() {
		return userName;
	}

	public String getIdType() {
		return idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public Integer getSex() {
		return sex;
	}

	public Integer getMarital() {
		return marital;
	}

	public String getDegree() {
		return degree;
	}

	public String getNation() {
		return nation;
	}

	public String getResideType() {
		return resideType;
	}

	public String getResideAddr() {
		return resideAddr;
	}

	public String getResideYear() {
		return resideYear;
	}

	public String getResideProvince() {
		return resideProvince;
	}

	public String getResideCity() {
		return resideCity;
	}

	public String getResideDistrict() {
		return resideDistrict;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public String getRegProvince() {
		return regProvince;
	}

	public String getRegCity() {
		return regCity;
	}

	public String getRegDistrict() {
		return regDistrict;
	}

	public Double getFamilyMonthIncome() {
		return familyMonthIncome;
	}

	public String getPafNo() {
		return pafNo;
	}

	public String getSsNo() {
		return ssNo;
	}

	public String getIndustry() {
		return industry;
	}

	public String getComName() {
		return comName;
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public String getWorkProvince() {
		return workProvince;
	}

	public String getWorkCity() {
		return workCity;
	}

	public String getWorkDistrict() {
		return workDistrict;
	}

	public String getWorkPosition() {
		return workPosition;
	}

	public String getWorkYear() {
		return workYear;
	}

	public String getComTelZone() {
		return comTelZone;
	}

	public String getComTelNo() {
		return comTelNo;
	}

	public String getComTelExt() {
		return comTelExt;
	}

	public Integer getStatus() {
		return status;
	}

	public List<CustContactVO> getContactList() {
		return contactList;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public void setMarital(Integer marital) {
		this.marital = marital;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public void setResideType(String resideType) {
		this.resideType = resideType;
	}

	public void setResideAddr(String resideAddr) {
		this.resideAddr = resideAddr;
	}

	public void setResideYear(String resideYear) {
		this.resideYear = resideYear;
	}

	public void setResideProvince(String resideProvince) {
		this.resideProvince = resideProvince;
	}

	public void setResideCity(String resideCity) {
		this.resideCity = resideCity;
	}

	public void setResideDistrict(String resideDistrict) {
		this.resideDistrict = resideDistrict;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}

	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}

	public void setRegDistrict(String regDistrict) {
		this.regDistrict = regDistrict;
	}

	public Integer getIndivMonthIncome() {
		return indivMonthIncome;
	}

	public void setIndivMonthIncome(Integer indivMonthIncome) {
		this.indivMonthIncome = indivMonthIncome;
	}

	public void setFamilyMonthIncome(Double familyMonthIncome) {
		this.familyMonthIncome = familyMonthIncome;
	}

	public void setPafNo(String pafNo) {
		this.pafNo = pafNo;
	}

	public void setSsNo(String ssNo) {
		this.ssNo = ssNo;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public void setWorkProvince(String workProvince) {
		this.workProvince = workProvince;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public void setWorkDistrict(String workDistrict) {
		this.workDistrict = workDistrict;
	}

	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public void setComTelZone(String comTelZone) {
		this.comTelZone = comTelZone;
	}

	public void setComTelNo(String comTelNo) {
		this.comTelNo = comTelNo;
	}

	public void setComTelExt(String comTelExt) {
		this.comTelExt = comTelExt;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setContactList(List<CustContactVO> contactList) {
		this.contactList = contactList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getResideProvinceName() {
		return resideProvinceName;
	}

	public String getResideCityName() {
		return resideCityName;
	}

	public String getResideDistrictName() {
		return resideDistrictName;
	}

	public String getWorkProvinceName() {
		return workProvinceName;
	}

	public String getWorkCityName() {
		return workCityName;
	}

	public String getWorkDistrictName() {
		return workDistrictName;
	}

	public void setResideProvinceName(String resideProvinceName) {
		this.resideProvinceName = resideProvinceName;
	}

	public void setResideCityName(String resideCityName) {
		this.resideCityName = resideCityName;
	}

	public void setResideDistrictName(String resideDistrictName) {
		this.resideDistrictName = resideDistrictName;
	}

	public void setWorkProvinceName(String workProvinceName) {
		this.workProvinceName = workProvinceName;
	}

	public void setWorkCityName(String workCityName) {
		this.workCityName = workCityName;
	}

	public void setWorkDistrictName(String workDistrictName) {
		this.workDistrictName = workDistrictName;
	}

	@Deprecated
	public String getCustId() {
		return userId;
	}

	@Deprecated
	public void setCustId(String custId) {
		this.userId = userId;
	}

	@Deprecated
	public String getCustName() {
		return userName;
	}

	@Deprecated
	public void setCustName(String custName) {
		this.userName = userName;
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

	public String getParentProvinceName() {
		return parentProvinceName;
	}

	public void setParentProvinceName(String parentProvinceName) {
		this.parentProvinceName = parentProvinceName;
	}

	public String getParentCityName() {
		return parentCityName;
	}

	public void setParentCityName(String parentCityName) {
		this.parentCityName = parentCityName;
	}

	public String getParentDistrictName() {
		return parentDistrictName;
	}

	public void setParentDistrictName(String parentDistrictName) {
		this.parentDistrictName = parentDistrictName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
