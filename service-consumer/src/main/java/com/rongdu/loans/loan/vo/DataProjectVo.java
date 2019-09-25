package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 项目信息
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/4
 */
@Data
public class DataProjectVo implements Serializable {
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 贷款金额
     */
    private String money;
    /**
     * 年利率
     */
    private String rate;
    /**
     * 贷款期限
     */
    private String limit;
    /**
     * 还款方式
     */
    private String repayment;
    /**
     * 借款用途
     */
    private String use;
    /**
     * 借款本金数额(小写)
     */
    private String moneyLower;
    /**
     * 借款本金数额(大写)
     */
    private String moneyUpper;
    /**
     * 收款银行帐号
     */
    private String bankCardNo;

    public DataProjectVo() {
    }

    public DataProjectVo(String projectName, String projectNo, String money, String rate, String limit, String repayment, String use, String moneyLower, String moneyUpper, String bankCardNo) {
        this.projectName = projectName;
        this.projectNo = projectNo;
        this.money = money;
        this.rate = rate;
        this.limit = limit;
        this.repayment = repayment;
        this.use = use;
        this.moneyLower = moneyLower;
        this.moneyUpper = moneyUpper;
        this.bankCardNo = bankCardNo;
    }
}
