/**
  * Copyright 2019 bejson.com 
  */
package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NormalCreditDetails implements Serializable {

    /**
     *
     */
    private String assureType;
    /**
     *
     */
    private String creditAddress;
    /**
     *
     */
    private String creditEndDate;
    /**
     * 借款日期
     */
    private String creditStartDate;
    /**
     *
     */
    private String loanMoney;
    /**
     *
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
     *
     */
    private String remark;

}