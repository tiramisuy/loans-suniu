package com.rongdu.loans.loan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/7/24.
 */
public class SmartAssetDTO implements Serializable {

    private String id;
    private int numId;
    private int productTypeId;
    private String basicContractNo;
    private String accountId;
    private BigDecimal assetAmount;
    private BigDecimal yield;
    private String borrowPurpose;
    private int interestId;
    private int borrowDay;
    private Integer borrowMonth;
    private int payMethodId;
    private Boolean isdeposit;
    private Integer status;
    private String actualBorrower;
    private Integer priority;
    private Date investStartDate;
    private Date investEndDate;
    private BigDecimal feeYield;
    private BigDecimal fee;
    private String description;
    private Date creatTime;
    private Date updateTime;
    private Date auditTime;
    private String productName;
    private String borrowTypeStr;
    private String guaranteeParty;
    private String guaranteeNo;
    private Date effectTime;
    private BigDecimal finishAmount;
    private String otherOrderId;
    private BigDecimal overdueRate;
    private BigDecimal overdueFee;
    private BigDecimal prepayValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getBasicContractNo() {
        return basicContractNo;
    }

    public void setBasicContractNo(String basicContractNo) {
        this.basicContractNo = basicContractNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(BigDecimal assetAmount) {
        this.assetAmount = assetAmount;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public String getBorrowPurpose() {
        return borrowPurpose;
    }

    public void setBorrowPurpose(String borrowPurpose) {
        this.borrowPurpose = borrowPurpose;
    }

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    public int getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(int borrowDay) {
        this.borrowDay = borrowDay;
    }

    public Integer getBorrowMonth() {
        return borrowMonth;
    }

    public void setBorrowMonth(Integer borrowMonth) {
        this.borrowMonth = borrowMonth;
    }

    public int getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(int payMethodId) {
        this.payMethodId = payMethodId;
    }

    public Boolean getIsdeposit() {
        return isdeposit;
    }

    public void setIsdeposit(Boolean isdeposit) {
        this.isdeposit = isdeposit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActualBorrower() {
        return actualBorrower;
    }

    public void setActualBorrower(String actualBorrower) {
        this.actualBorrower = actualBorrower;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getInvestStartDate() {
        return investStartDate;
    }

    public void setInvestStartDate(Date investStartDate) {
        this.investStartDate = investStartDate;
    }

    public Date getInvestEndDate() {
        return investEndDate;
    }

    public void setInvestEndDate(Date investEndDate) {
        this.investEndDate = investEndDate;
    }

    public BigDecimal getFeeYield() {
        return feeYield;
    }

    public void setFeeYield(BigDecimal feeYield) {
        this.feeYield = feeYield;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBorrowTypeStr() {
        return borrowTypeStr;
    }

    public void setBorrowTypeStr(String borrowTypeStr) {
        this.borrowTypeStr = borrowTypeStr;
    }

    public String getGuaranteeParty() {
        return guaranteeParty;
    }

    public void setGuaranteeParty(String guaranteeParty) {
        this.guaranteeParty = guaranteeParty;
    }

    public String getGuaranteeNo() {
        return guaranteeNo;
    }

    public void setGuaranteeNo(String guaranteeNo) {
        this.guaranteeNo = guaranteeNo;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public BigDecimal getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(BigDecimal finishAmount) {
        this.finishAmount = finishAmount;
    }

    public String getOtherOrderId() {
        return otherOrderId;
    }

    public void setOtherOrderId(String otherOrderId) {
        this.otherOrderId = otherOrderId;
    }

    public BigDecimal getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(BigDecimal overdueRate) {
        this.overdueRate = overdueRate;
    }

    public BigDecimal getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(BigDecimal overdueFee) {
        this.overdueFee = overdueFee;
    }

    public BigDecimal getPrepayValue() {
        return prepayValue;
    }

    public void setPrepayValue(BigDecimal prepayValue) {
        this.prepayValue = prepayValue;
    }
}
