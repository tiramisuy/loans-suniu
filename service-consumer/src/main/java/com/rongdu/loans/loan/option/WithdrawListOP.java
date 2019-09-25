package com.rongdu.loans.loan.option;


import java.io.Serializable;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.utils.DateUtils;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class WithdrawListOP extends BaseEntity<WithdrawListOP> implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String userName;		// 真实姓名
    private String idNo;		// 用户证件号码
    private String mobile;		// 手机号码
    private Date applyTime;		// 申请时间
    private String applyTimeStr;		// 申请时间

    private Integer stage = 1;		// 1待审核 , 2已过审， 3已否决 ，4办理中

    private Integer pageNo = 1;
    private Integer pageSize = 10;



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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
        this.applyTimeStr = DateUtils.formatDate(applyTime);
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
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

    public String getApplyTimeStr() {
        return applyTimeStr;
    }

    public void setApplyTimeStr(String applyTimeStr) {
        this.applyTimeStr = applyTimeStr;
    }
}
