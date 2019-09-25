package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2018/5/9.
 */
public class LoanGoodsResultVO implements Serializable {


    private static final long serialVersionUID = 8812320071196359840L;
    private String userName; // 姓名
    private String mobile; // 手机号码
    private String resideProvince; // 居住地所在省份
    private String resideProvinceName; // 居住地所在省份名称
    private String resideCity; // 居住地所在城市
    private String resideCityName; // 居住地所在城市名称
    private String resideDistrict; // 居住地所在区县
    private String resideDistrictName; // 居住地所在区县名称
    private String resideAddr; // 居住地址
    private String bagAmt;
    private String loanAmt;
    private List<LoanGoodsVO> details;
    
    private Integer showAddress = 0; //是否显示地址
    private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getShowAddress() {
		return showAddress;
	}

	public void setShowAddress(Integer showAddress) {
		this.showAddress = showAddress;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResideProvinceName() {
        return resideProvinceName;
    }

    public void setResideProvinceName(String resideProvinceName) {
        this.resideProvinceName = resideProvinceName;
    }

    public String getResideCityName() {
        return resideCityName;
    }

    public void setResideCityName(String resideCityName) {
        this.resideCityName = resideCityName;
    }

    public String getResideDistrictName() {
        return resideDistrictName;
    }

    public void setResideDistrictName(String resideDistrictName) {
        this.resideDistrictName = resideDistrictName;
    }

    public String getResideAddr() {
        return resideAddr;
    }

    public void setResideAddr(String resideAddr) {
        this.resideAddr = resideAddr;
    }

    public String getBagAmt() {
        return bagAmt;
    }

    public void setBagAmt(String bagAmt) {
        this.bagAmt = bagAmt;
    }

    public String getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(String loanAmt) {
        this.loanAmt = loanAmt;
    }

    public List<LoanGoodsVO> getDetails() {
        return details;
    }

    public void setDetails(List<LoanGoodsVO> details) {
        this.details = details;
    }
}
