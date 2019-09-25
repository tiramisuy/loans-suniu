package com.rongdu.loans.cust.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
public class Blacklist extends BaseEntity<Blacklist> {

    // 序列号
    private static final long serialVersionUID = 873769116246128268L;

    private String userId;		// 客户ID
    private String idType;     // 证件类型
    private String idNo;		// 证件号码
    private String userName;    // 客户名称
    /**
     * 0-网贷行业公布的黑名单
     1-在平台有欠息或有不良贷款
     2-提供虚假材料或拒绝接受检查
     3-由恶意或严重逃废债记录
     */
    private String blackType;
    /**
     * 0－系统内不宜贷款户（系统生成）
     1－系统内不宜贷款户（人工审批）
     2－ 系统外不宜贷款户'
     */
    private String source;
    private BigDecimal overduePeriod;		// '贷款逾期期数'
    private String blackTime;		// '进出时间'
    private String reason;		// '进出黑名单原因'
    private String inOutFlag;		// '进出标志'
    /**
     * 0-预登记
     1-生效
     2-否决
     3-注销
     */
    private String status;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBlackType() {
        return blackType;
    }

    public void setBlackType(String blackType) {
        this.blackType = blackType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getOverduePeriod() {
        return overduePeriod;
    }

    public void setOverduePeriod(BigDecimal overduePeriod) {
        this.overduePeriod = overduePeriod;
    }

    public String getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(String blackTime) {
        this.blackTime = blackTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getInOutFlag() {
        return inOutFlag;
    }

    public void setInOutFlag(String inOutFlag) {
        this.inOutFlag = inOutFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
