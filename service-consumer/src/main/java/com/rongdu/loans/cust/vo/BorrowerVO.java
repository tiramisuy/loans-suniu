package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.util.Date;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.SexEnum;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class BorrowerVO implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386817584730L;
    private static final String NO = "否";
    private static final String YES = "是";


    private String id;        // id
    private String realName;        // 真实姓名
    private String idNo;        // 用户证件号码
    private String bankCode;        // 银行代码
    private String bankName;        // 银行代码
    private Integer sex;        // 用户性别
    private String sexStr;        // 用户性别
    private String paypwd;        // 支付密码
    private String accountId;        // 电子账户
    private Double availableBalance;        // 可用余额
    private Double frozenBalance;        // 冻结金额
    private Integer status;        // 用户状态：1-正常，0-锁定账户
    private Integer blacklist = 0;  //是否黑名单 0否  1是
    private Date createTime;
    private String mobile;        // 手机号码
    private String channel; //渠道
    private String remark; //门店Id
    private String cardNo;		// 银行卡号
    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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

    public String getMobile() {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getPaypwd() {
        return StringUtils.isBlank(paypwd) ? NO : paypwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = StringUtils.isBlank(paypwd) ? NO : YES;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(Double frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getCardNo() {
		if (null != cardNo) {
			return cardNo.replaceAll("(\\d{4})\\d{6}(\\w{4})","$1*****$2");
		}else {
			return cardNo;
		}

	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
    
    
}
