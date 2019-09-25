package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 还款计划
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/4
 */
@Data
public class RepayDataVo implements Serializable {
    /*
     * 还款期数
     */
    private String periods;
    /**
     * 还款本金
     */
    private String repayPrincipal;
    /**
     * 还款利息
     */
    private String repayInterest;
    /**
     * 还款总额
     */
    private String repayAmt;
    /**
     * 还款时间
     */
    private String repayTime;

    public RepayDataVo() {
    }

    public RepayDataVo(String periods, String repayPrincipal, String repayInterest, String repayAmt, String repayTime) {
        this.periods = periods;
        this.repayPrincipal = repayPrincipal;
        this.repayInterest = repayInterest;
        this.repayAmt = repayAmt;
        this.repayTime = repayTime;
    }
}
