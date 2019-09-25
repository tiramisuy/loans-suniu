package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

/**
 * Created by likang on 2017/7/24.
 */
public class AccountDetailVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -553463143090058033L;

    /**
     * 流水号
     */
    private String traceNo;
    /**
     * 电子账号
     */
    private String accountId;
    /**
     * 对手电子账号
     */
    private String forAccountId;
    /**
     * tranType
     */
    private String tranType;
    /**
     * 交易金额
     */
    private String txAmount;
    /**
     * 交易后余额
     */
    private String currBal;
    /**
     * 入账日期
     */
    private String accDate;

    /**
     * 自然日期
     */
    private String relDate;
    /**
     * 交易日期
     */
    private String inpDate;
    /**
     * 交易时间
     */
    private String inpTime;
    /**
     * 货币代码
     */
    private String currency;
    /**
     * 交易描述
     */
    private String describe;
    /**
     * 冲正撤销标志
     */
    private String orFlag;
    /**
     * 交易金额符号
     */
    private String txFlag;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 用户id
     */
    private String userId;
    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getForAccountId() {
        return forAccountId;
    }

    public void setForAccountId(String forAccountId) {
        this.forAccountId = forAccountId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    public String getCurrBal() {
        return currBal;
    }

    public void setCurrBal(String currBal) {
        this.currBal = currBal;
    }

    public String getAccDate() {
        return accDate;
    }

    public void setAccDate(String accDate) {
        this.accDate = accDate;
    }

    public String getRelDate() {
        return relDate;
    }

    public void setRelDate(String relDate) {
        this.relDate = relDate;
    }

    public String getInpDate() {
        return inpDate;
    }

    public void setInpDate(String inpDate) {
        this.inpDate = inpDate;
    }

    public String getInpTime() {
        return inpTime;
    }

    public void setInpTime(String inpTime) {
        this.inpTime = inpTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getOrFlag() {
        return orFlag;
    }

    public void setOrFlag(String orFlag) {
        this.orFlag = orFlag;
    }

    public String getTxFlag() {
        return txFlag;
    }

    public void setTxFlag(String txFlag) {
        this.txFlag = txFlag;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
