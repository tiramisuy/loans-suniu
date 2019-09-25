package com.rongdu.loans.loan.op;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class WithdrawOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String userName;        // 真实姓名
    private String idNo;        // 用户证件号码
    private String mobile;        // 手机号码
    private String applyStart;        // 申请开始时间
    private String applyEnd;        // 申请结束时间
    private String accountStart;        // 到账开始时间
    private String accountEnd;        // 到账结束时间
    private String sendStart;        // 放款开始时间
    private String sendEnd;        // 放款结束时间
    private String txType;              //1自动还款，2手动还款
    private Integer status;              //状态
    private BigDecimal txAmt;              //提现金额
    private String chlCode;              //放款渠道


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

    public String getApplyStart() {
        return applyStart;
    }

    public void setApplyStart(String applyStart) {
        this.applyStart = applyStart;
    }

    public String getApplyEnd() {
        return applyEnd;
    }

    public void setApplyEnd(String applyEnd) {
        this.applyEnd = applyEnd;
    }

    public String getAccountStart() {
        return accountStart;
    }

    public void setAccountStart(String accountStart) {
        this.accountStart = accountStart;
    }

    public String getAccountEnd() {
        return accountEnd;
    }

    public void setAccountEnd(String accountEnd) {
        this.accountEnd = accountEnd;
    }

    public String getSendStart() {
        return sendStart;
    }

    public void setSendStart(String sendStart) {
        this.sendStart = sendStart;
    }

    public String getSendEnd() {
        return sendEnd;
    }

    public void setSendEnd(String sendEnd) {
        this.sendEnd = sendEnd;
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

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTxAmt() {
        return txAmt;
    }

    public void setTxAmt(BigDecimal txAmt) {
        this.txAmt = txAmt;
    }

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	
}
