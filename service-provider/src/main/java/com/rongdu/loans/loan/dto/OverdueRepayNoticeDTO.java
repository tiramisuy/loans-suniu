package com.rongdu.loans.loan.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 逾期还款通知入参
 * Created by zhangxiaolong on 2017/8/24.
 */
public class OverdueRepayNoticeDTO implements Serializable {
    /**
     * 资产ID
     */
    private String assetId;
    /**
     * 电子账户
     */
    private String accountId;
    /**
     * 还款日期 yyyy-MM-dd HH:mm:ss
     */
    private String repayDate;
    /**
     * 罚息
     */
    private BigDecimal overdueInterest;
    /**
     * 逾期管理费
     */
    private BigDecimal overdueFee;
    /**
     * 减免费用
     */
    private BigDecimal reduceFee;
    /**
     * 正常利息
     */
    private BigDecimal interest;
    /**
     * 本金
     */
    private BigDecimal principal;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    public BigDecimal getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(BigDecimal overdueFee) {
        this.overdueFee = overdueFee;
    }

    public BigDecimal getReduceFee() {
        return reduceFee;
    }

    public void setReduceFee(BigDecimal reduceFee) {
        this.reduceFee = reduceFee;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }
}
