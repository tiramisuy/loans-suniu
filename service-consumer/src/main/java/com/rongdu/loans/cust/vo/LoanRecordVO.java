package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangxiaolong on 2017/8/17.
 */
public class LoanRecordVO implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String certNo;
    /**
     * 借款时间: YYYYMMDD
     */
    private String loanDate;
    /**
     * 期数
     */
    private String periods;
    /**
     * 借款金额
     */
    private BigDecimal loanAmount;
    /**
     * 结果审批码
     */
    private String approvalStatusCode;
    /**
     * 还款审批码
     */
    private String loanStatusCode;
    /**
     * 借款类型码
     */
    private String loanTypeCode;
    /**
     * 逾期金额
     */
    private BigDecimal overdueAmount;
    /**
     * 逾期情况
     */
    private String overdueStatus;
    /**
     * 历史逾期总次数
     */
    private Integer overdueTotal;
    /**
     * 历史逾期  M3+次数 (不含 M3 ，包括 M6 及以上）
     */
    private Integer overdueM3;
    /**
     * 历史逾期  M6+次数 (不含 M6 ）
     */
    private Integer overdueM6;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getApprovalStatusCode() {
        return approvalStatusCode;
    }

    public void setApprovalStatusCode(String approvalStatusCode) {
        this.approvalStatusCode = approvalStatusCode;
    }

    public String getLoanStatusCode() {
        return loanStatusCode;
    }

    public void setLoanStatusCode(String loanStatusCode) {
        this.loanStatusCode = loanStatusCode;
    }

    public String getLoanTypeCode() {
        return loanTypeCode;
    }

    public void setLoanTypeCode(String loanTypeCode) {
        this.loanTypeCode = loanTypeCode;
    }

    public BigDecimal getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(BigDecimal overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getOverdueStatus() {
        return overdueStatus;
    }

    public void setOverdueStatus(String overdueStatus) {
        this.overdueStatus = overdueStatus;
    }

    public Integer getOverdueTotal() {
        return overdueTotal;
    }

    public void setOverdueTotal(Integer overdueTotal) {
        this.overdueTotal = overdueTotal;
    }

    public Integer getOverdueM3() {
        return overdueM3;
    }

    public void setOverdueM3(Integer overdueM3) {
        this.overdueM3 = overdueM3;
    }

    public Integer getOverdueM6() {
        return overdueM6;
    }

    public void setOverdueM6(Integer overdueM6) {
        this.overdueM6 = overdueM6;
    }
}
