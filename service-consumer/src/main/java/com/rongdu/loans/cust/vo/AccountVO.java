package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
public class AccountVO implements Serializable {
    private static final long serialVersionUID = -7472436330059264845L;

    /**
     * 资金账户
     */
    private BigDecimal currBal;     //总额
    private BigDecimal availBal;        //可用余额
    private BigDecimal freezeBal;       //冻结金额


    public BigDecimal getCurrBal() {
        return currBal;
    }

    public void setCurrBal(BigDecimal currBal) {
        this.currBal = currBal;
    }

    public BigDecimal getAvailBal() {
        return availBal;
    }

    public void setAvailBal(BigDecimal availBal) {
        this.availBal = availBal;
    }

    public BigDecimal getFreezeBal() {
        return freezeBal;
    }

    public void setFreezeBal(BigDecimal freezeBal) {
        this.freezeBal = freezeBal;
    }
}
