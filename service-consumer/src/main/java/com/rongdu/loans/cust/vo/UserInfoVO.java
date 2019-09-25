package com.rongdu.loans.cust.vo;

import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.enums.IdTypeEnum;
import com.rongdu.loans.enums.SexEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
public class UserInfoVO implements Serializable {

	private static final long serialVersionUID = -7472436330059264845L;

	/**
	 * CustUser 信息
	 */
	private String birthday; // 生日
	private Integer age; // 年龄
	private String qq; // qq
	private String accountId; // 电子账户
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private Integer status; // 用户状态：1-正常，0-锁定账户
	private String type;
	
	private String bankCode; // 银行代码
	private String bankName; // 银行名称
	private String cardNo; // 银行卡号
	/**
	 * UserIdent 信息
	 */
	private String idTermBegin; // 证件有效期(开始)
	private String idTermEnd; // 证件有效期(结束)
	private String idRegOrg; // 证件登记机关
	/**
	 * CustUserInfo 信息
	 */
	private String id; // id
	private String realName; // 真实姓名
	private String idType; // 用户证件类型
	private String idNo; // 用户证件号码
	private Integer sex; // 用户性别
	private String sexStr; // 用户性别
	private String mobile; // 手机号码
	private String email;		// 用户邮箱
	private String degree; // 学历
	private String regProvince; // 户籍所在省份
	private String regCity; // 户籍所在城市
	private String regDistrict; // 户籍所在区县
	private String resideProvince; // 居住地所在省份
	private String resideCity; // 居住地所在城市
	private String resideDistrict; // 居住地所在区县
	private String resideAddr; // 居住地址
	private String marital; // 婚姻状况
	private String resideYear; // 居住年限（月）
	private String regAddr; // 户籍地址
	private String comName; // 工作单位
	private String workPosition; // 岗位
	private String workProvince; // 工作所在省份
	private String workCity; // 工作所在城市
	private String workDistrict; // 工作所在区县
	private String workAddr; // 工作地址
	private String comTelZone; // 单位联系电话区号
	private String comTelNo; // 单位联系电话
	private String comTelExt; // 单位联系电话分机号
	private String parentProvince;// 父母所在省
	private String parentCity;// 父母所在市
	private String parentDistrict;// 父母所在区
	private String parentAddress;// 父母所在街道
	private String workYear; // 工作时间
	private String indivMonthIncome;// 工作收入
	private String industry;// 公司行业
	private String workCategory;// 公司类型
	private String workSize;// 公司规模
	private String house;// 是否有房
	private String houseLoan;// 是否有房贷
	private String car;// 是否有车
	private String carLoan;// 是否有车贷
	private String channelName;// 是否有车贷

	/**
	 * custBlacklist 信息
	 */
	private Integer blacklist; // 是否黑名单
	/**
	 * custContarct 信息
	 */
	private List<CustContactVO> contactList;

	/**
	 * 申请贷款次数
	 */
	private Integer count;
	/**
	 * 贷款成功次数
	 */
	private Integer loanSuccCount;
	/**
	 * 页面标识
	 */
	private String sign;

	/**
	 * 借款信息
	 */
	List<RepayItemDetailVO> repayItemDetailList;

	/**
	 * 影像信息
	 */
	List<FileInfoVO> fileList;

	private String adminPath;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		String type = IdTypeEnum.getDesc(idType);
		this.idType = type != null ? type : idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
		this.sexStr = SexEnum.getDesc(sex);
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIdTermBegin() {
		return idTermBegin;
	}

	public void setIdTermBegin(String idTermBegin) {
		this.idTermBegin = idTermBegin;
	}

	public String getIdTermEnd() {
		return idTermEnd;
	}

	public void setIdTermEnd(String idTermEnd) {
		this.idTermEnd = idTermEnd;
	}

	public String getIdRegOrg() {
		return idRegOrg;
	}

	public void setIdRegOrg(String idRegOrg) {
		this.idRegOrg = idRegOrg;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getResideAddr() {
		return resideAddr;
	}

	public void setResideAddr(String resideAddr) {
		this.resideAddr = resideAddr;
	}

	public String getMarital() {
		return marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	public String getResideYear() {
		return resideYear;
	}

	public void setResideYear(String resideYear) {
		this.resideYear = resideYear;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getWorkPosition() {
		return workPosition;
	}

	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
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

	public Integer getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Integer blacklist) {
		this.blacklist = blacklist;
	}

	public List<CustContactVO> getContactList() {
		return contactList;
	}

	public void setContactList(List<CustContactVO> contactList) {
		this.contactList = contactList;
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getLoanSuccCount() {
		return loanSuccCount;
	}

	public void setLoanSuccCount(Integer loanSuccCount) {
		this.loanSuccCount = loanSuccCount;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getIndivMonthIncome() {
		return indivMonthIncome;
	}

	public void setIndivMonthIncome(String indivMonthIncome) {
		this.indivMonthIncome = indivMonthIncome;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
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

	public List<RepayItemDetailVO> getRepayItemDetailList() {
		return repayItemDetailList;
	}

	public void setRepayItemDetailList(List<RepayItemDetailVO> repayItemDetailList) {
		this.repayItemDetailList = repayItemDetailList;
	}

	public String getAdminPath() {
		return adminPath;
	}

	public void setAdminPath(String adminPath) {
		this.adminPath = adminPath;
	}

	public List<FileInfoVO> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileInfoVO> fileList) {
		this.fileList = fileList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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
}
