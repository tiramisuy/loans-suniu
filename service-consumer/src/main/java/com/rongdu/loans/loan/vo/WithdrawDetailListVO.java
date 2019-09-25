package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.rongdu.common.utils.excel.annotation.ExcelField;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class WithdrawDetailListVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String applyId; // 借款订单号
    private String id; // 提现订单号
    private String userId; // 用户ID
    private String name; // 真实姓名
    private String idNo; // 用户证件号码
    private String mobile; // 手机号码
    private Date txTime; // 申请时间
    private Date succTime; // 到账时间
    private Date loanStartDate; // 放款时间
    private Date loanEndDate; // 还款时间
    private String txType; // 1自动提现，2手动提现
    private String status; // 状态状态
    private String statusStr;
    private BigDecimal txAmt; // 提现金额
    private String lastStatus; // 最新状态。同一笔订单可能出现先失败，后成功的情况
    @ExcelField(title = "支付订单号", type = 1, align = 2, sort = 0)
    private String chlOrderNo;
    private String chlCode;
    private String chlName;
    private String remark;
    private String contractUrl;
    private String payLogId;
    private String toBankName;
    private String toAccNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ExcelField(title = "借款订单号", type = 1, align = 2, sort = 1)
    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    @ExcelField(title = "提现订单号", type = 1, align = 2, sort = 2)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ExcelField(title = "借款人姓名", type = 1, align = 2, sort = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "证件号码", type = 1, align = 2, sort = 5)
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @ExcelField(title = "手机号码", type = 1, align = 2, sort = 4)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Length(min = 100, max = 200)
    @ExcelField(title = "提现申请时间", type = 1, align = 2, sort = 9)
    public Date getTxTime() {
        return txTime;
    }

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }

    @Length(min = 100, max = 200)
    @ExcelField(title = "到账时间", type = 1, align = 2, sort = 10)
    public Date getSuccTime() {
        return succTime;
    }

    public void setSuccTime(Date succTime) {
        this.succTime = succTime;
    }

    // @ExcelField(title = "是否自动提现", type = 1, align = 2, sort = 6)
    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if (status.equals("I")) {
            this.statusStr = "处理中";
        } else if (status.equals("F")) {
            this.statusStr = "失败";
        } else if (status.equals("S")) {
            this.statusStr = "成功";
        } else {
            this.statusStr = ApplyStatusLifeCycleEnum.getDesc(Integer.valueOf(status));
        }
        this.lastStatus = status;
    }

    @ExcelField(title = "提现状态", type = 1, align = 2, sort = 11)
    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @ExcelField(title = "提现金额", type = 1, align = 2, sort = 7)
    public BigDecimal getTxAmt() {
        return txAmt;
    }

    public void setTxAmt(BigDecimal txAmt) {
        this.txAmt = txAmt;
    }

    // @ExcelField(title = "提现状态", type = 1, align = 2, sort = 11)
    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getChlCode() {
        return chlCode;
    }

    public void setChlCode(String chlCode) {
        this.chlCode = chlCode;
    }

    @ExcelField(title = "支付渠道", type = 1, align = 2, sort = 8)
    public String getChlName() {
        return chlName;
    }

    public void setChlName(String chlName) {
        this.chlName = chlName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public String getPayLogId() {
        return payLogId;
    }

    public void setPayLogId(String payLogId) {
        this.payLogId = payLogId;
    }

    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Date loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getToBankName() {
        return toBankName;
    }

    public void setToBankName(String toBankName) {
        this.toBankName = toBankName;
    }

    public String getToAccNo() {
        return toAccNo;
    }

    public void setToAccNo(String toAccNo) {
        this.toAccNo = toAccNo;
    }

    public String getChlOrderNo() {
        return chlOrderNo;
    }

    public void setChlOrderNo(String chlOrderNo) {
        this.chlOrderNo = chlOrderNo;
    }
}
