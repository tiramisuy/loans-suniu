package com.rongdu.loans.cust.option;


import java.io.Serializable;

import com.rongdu.common.persistence.BaseEntity;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class BorrowerOP extends BaseEntity<BorrowerOP> implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String realName;		// 真实姓名
    private String idNo;		// 用户证件号码
    private String mobile;		// 手机号码
    private Integer openAccount = -1;		// 是否开户 1开户，0未开户, -1全部

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String channel; //渠道
    private String registerStart;		// 注册开始时间
    private String registerEnd;		// 注册结束时间
    private String remark;	//外部商户标记

    private Integer isBlack;	//是否黑名单 1是，2否
    
    public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegisterStart() {
		return registerStart;
	}

	public void setRegisterStart(String registerStart) {
		this.registerStart = registerStart;
	}

	public String getRegisterEnd() {
		return registerEnd;
	}

	public void setRegisterEnd(String registerEnd) {
		this.registerEnd = registerEnd;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
