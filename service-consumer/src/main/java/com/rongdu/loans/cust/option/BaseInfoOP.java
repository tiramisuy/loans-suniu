package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * Created by likang on 2017/6/27.
 */
public class BaseInfoOP implements Serializable {

    // 序列号
    private static final long serialVersionUID = 2531587648934185011L;

	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	@NotBlank(message="消息来源不能为空")
	@Pattern(regexp="1|2|3|4",message="消息来源类型有误")
	private String source;
    // 单位名称
    @NotBlank(message="单位名称不能为空")
    private String comName;
    // 工作地址明细
    @NotBlank(message="工作地址不能为空")
    private String workAddr;
    // 工作地址省
    @NotBlank(message="工作地址省不能为空")
    private String workProvince;
    // 工作地址市
    @NotBlank(message="工作地址市不能为空")
    private String workCity;
    // 工作所在区县
    @NotBlank(message="工作所在区县不能为空")
    private String workDistrict;
    // 工作岗位
    private String workPosition;
    // 单位联系电话区号
    @Length(max=4, message="区号长度大于4")
    private String comTelZone;
    // 单位联系电话
    @Length(max=8, message="单位联系电话长度大于8")
    private String comTelNo;
    // 单位联系电话分机号
    private String comTelExt;
    // 居住地址
    @NotBlank(message="居住地址不能为空")
    private String resideAddr;
    // 居住地所在省份
    @NotBlank(message="居住地所在省份不能为空")
    private String resideProvince;
    // 居住地所在城市
    @NotBlank(message="居住地所在城市不能为空")
    private String resideCity;
    // 居住地所在区县
    @NotBlank(message="居住地所在区县不能为空")
    private String resideDistrict;
    // 居住年限（月）
    private String resideYear;
    // 婚姻状况
    private Integer marital;
    // 学历
    private String degree;
    // qq
    private String qq;
    private String email;
    // 联系人（父母）
    @NotBlank(message="联系人（父母）不能为空")
    private String contactParent;
    // 联系人（母亲）
    private String contactMother;
    // 联系人（配偶）
    private String contactSpouse;
    // 联系人（朋友）
    private String contactFriend;
    // 联系人（同事）
    private String contactColleague;
    //  真实姓名
    private String userName;
    // userId
    private String userId;
    // 证件类型
    private String idType;
    // 证件号
    private String idNo;
    // 手机号
    private String mobile;
    
    // 父母居住地址
    private String parentAddress;
    // 居住地所在省份
    private String parentProvince;
    // 居住地所在城市
    private String parentCity;
    // 居住地所在区县
    private String parentDistrict;
    
    /**
     * 申请编号
     */
    private String applyId;
    
    private String channelId;

    public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
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

    public String getResideYear() {
        return resideYear;
    }

    public void setResideYear(String resideYear) {
        this.resideYear = resideYear;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getContactParent() {
        return contactParent;
    }

    public void setContactParent(String contactParent) {
        this.contactParent = contactParent;
    }

    public String getContactMother() {
		return contactMother;
	}

	public void setContactMother(String contactMother) {
		this.contactMother = contactMother;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
