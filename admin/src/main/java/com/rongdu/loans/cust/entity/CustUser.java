package com.rongdu.loans.cust.entity;

import java.util.Date;



public class CustUser  {
	// 序列号
	private static final long serialVersionUID = -6124291284301882400L;

	/**
	 * 用户状态-正常【1】
	 */
	public static final Integer USER_STATUS_UNLOCK = 1;
	/**
	 * 用户状态-锁定用户【0】
	 */
	public static final Integer USER_STATUS_LOCK = 0;
	
	private String id;
	private String userName; // 姓名
	private String idType; // 证件类型
	private String idNo; // 证件号码
	private String mobile; // 手机号码
	private Integer sex; // 性别
	private Integer marital; // 婚姻状况
	private String degree; // 学历
	private String nation; // 民族
	private String resideType; // 居住性质（0-自由住房，1-租住，2-与亲属同住，3-单位宿舍，4-其他）
	private String resideAddr; // 居住地址
	private String resideYear; // 居住年限（月）
	private String resideProvince; // 居住地所在省份
	private String resideCity; // 居住地所在城市
	private String resideDistrict; // 居住地所在区县
	private String regAddr; // 户籍地址
	private String parentProvince; // 父母居住省份
	private String parentCity; // 父母居住城市
	private String parentDistrict; // 父母居住区县
	private String parentAddress; // 父母居住地址
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
	private String workCity; // 工作所在城市
	private String workDistrict; // 工作所在区县
	private String workPosition; // 岗位
	private String workYear; // 工作年限
	private String comTelZone; // 单位联系电话区号
	private String comTelNo; // 单位联系电话
	private String comTelExt; // 单位联系电话分机号
	private Integer status; // 用户状态
	private String remark; // 相关信息
	
	private String createBy;
	private Date createTime;
	private String updateBy;
	private Date updateTime;
	private String del;
	
	private String applyId;//用于与user_info_history对应     
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
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
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getResideType() {
		return resideType;
	}
	public void setResideType(String resideType) {
		this.resideType = resideType;
	}
	public String getResideAddr() {
		return resideAddr;
	}
	public void setResideAddr(String resideAddr) {
		this.resideAddr = resideAddr;
	}
	public String getResideYear() {
		return resideYear;
	}
	public void setResideYear(String resideYear) {
		this.resideYear = resideYear;
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
	public String getRegAddr() {
		return regAddr;
	}
	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
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
	public String getParentAddress() {
		return parentAddress;
	}
	public void setParentAddress(String parentAddress) {
		this.parentAddress = parentAddress;
	}
	public String getRegProvince() {
		return regProvince;
	}
	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}
	public String getRegCity() {
		return regCity;
	}
	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}
	public String getRegDistrict() {
		return regDistrict;
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
	public Double getFamilyMonthIncome() {
		return familyMonthIncome;
	}
	public void setFamilyMonthIncome(Double familyMonthIncome) {
		this.familyMonthIncome = familyMonthIncome;
	}
	public String getPafNo() {
		return pafNo;
	}
	public void setPafNo(String pafNo) {
		this.pafNo = pafNo;
	}
	public String getSsNo() {
		return ssNo;
	}
	public void setSsNo(String ssNo) {
		this.ssNo = ssNo;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
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
	public String getWorkYear() {
		return workYear;
	}
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	public String getComTelZone() {
		return comTelZone;
	}
	public void setComTelZone(String comTelZone) {
		this.comTelZone = comTelZone;
	}
	public String getComTelNo() {
		return comTelNo;
	}
	public void setComTelNo(String comTelNo) {
		this.comTelNo = comTelNo;
	}
	public String getComTelExt() {
		return comTelExt;
	}
	public void setComTelExt(String comTelExt) {
		this.comTelExt = comTelExt;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	
	
	
	

}
