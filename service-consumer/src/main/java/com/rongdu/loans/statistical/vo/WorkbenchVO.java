package com.rongdu.loans.statistical.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class WorkbenchVO implements Serializable {

    /**
     * 序列号用户数
     */
    private static final long serialVersionUID = 6217468386813584730L;

    /**
     * 注册用户数
     */
    private Integer registerCurrent = 0;
    private Integer registerTotal = 0;
    /**
     * 申请用户数
     */
    private Integer applyCurrent = 0;
    private Integer applyTotal = 0;
    /**
     * 借款用户数
     */
    private Integer borrowerCurrent = 0;
    private Integer borrowerTotal = 0;
    /**
     * 放款金额
     */
    private BigDecimal loanCurrent = BigDecimal.ZERO;
    private BigDecimal loanTotal = BigDecimal.ZERO;
    /**
     * 待办审批任务总数
     */
    private Integer taskNumber = 0;
    /**
     * 昨日还款失败总笔数
     */
    private  Integer failNumber = 0;
    /**
     * 昨日还款成功笔数
     */
    private Integer succesNumber = 0;
    /**
     * 还款失败总笔数
     */
    private Integer failTotalNumber = 0;
    /**
     * Android注册人数
     */
    private Integer androidTotal = 0;
    /**
     * ios注册人数
     */
    private Integer iosTotal = 0;
    /**
     * h5注册人数
     */
    private Integer h5Total = 0;

    public Integer getRegisterCurrent() {
        return registerCurrent;
    }

    public void setRegisterCurrent(Integer registerCurrent) {
        this.registerCurrent = registerCurrent;
    }

    public Integer getRegisterTotal() {
        return registerTotal;
    }

    public void setRegisterTotal(Integer registerTotal) {
        this.registerTotal = registerTotal;
    }

    public Integer getApplyCurrent() {
        return applyCurrent;
    }

    public void setApplyCurrent(Integer applyCurrent) {
        this.applyCurrent = applyCurrent;
    }

    public Integer getApplyTotal() {
        return applyTotal;
    }

    public void setApplyTotal(Integer applyTotal) {
        this.applyTotal = applyTotal;
    }

    public Integer getBorrowerCurrent() {
        return borrowerCurrent;
    }

    public void setBorrowerCurrent(Integer borrowerCurrent) {
        this.borrowerCurrent = borrowerCurrent;
    }

    public Integer getBorrowerTotal() {
        return borrowerTotal;
    }

    public void setBorrowerTotal(Integer borrowerTotal) {
        this.borrowerTotal = borrowerTotal;
    }

    public BigDecimal getLoanCurrent() {
        return loanCurrent;
    }

    public void setLoanCurrent(BigDecimal loanCurrent) {
        this.loanCurrent = loanCurrent;
    }

    public BigDecimal getLoanTotal() {
        return loanTotal;
    }

    public void setLoanTotal(BigDecimal loanTotal) {
        this.loanTotal = loanTotal;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(Integer taskNumber) {
        this.taskNumber = taskNumber;
    }

    public Integer getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(Integer failNumber) {
        this.failNumber = failNumber;
    }

    public Integer getSuccesNumber() {
        return succesNumber;
    }

    public void setSuccesNumber(Integer succesNumber) {
        this.succesNumber = succesNumber;
    }

    public Integer getFailTotalNumber() {
        return failTotalNumber;
    }

    public void setFailTotalNumber(Integer failTotalNumber) {
        this.failTotalNumber = failTotalNumber;
    }

	public Integer getAndroidTotal() {
		return androidTotal;
	}

	public void setAndroidTotal(Integer androidTotal) {
		this.androidTotal = androidTotal;
	}

	public Integer getIosTotal() {
		return iosTotal;
	}

	public void setIosTotal(Integer iosTotal) {
		this.iosTotal = iosTotal;
	}

	public Integer getH5Total() {
		return h5Total;
	}

	public void setH5Total(Integer h5Total) {
		this.h5Total = h5Total;
	}
    
}
