package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AbnormalCreditDetails implements Serializable {

    /**
     * 担保方式
     */
    private String assureType;
    /**
     * 到期日期
     */
    private String creditEndDate;
    /**
     * 借款日期
     */
    private String creditStartDate;
    /**
     * 合同金额
     */
    private String loanMoney;
    /**
     * 还款期数
     */
    private String loanPeriods;
    /**
     * 会员类型
     */
    private String memberType;
    /**
     * 借款编号
     */
    private String num;
    /**
     * 逾期记录
     */
    private List<Overdues> overdues;

}
