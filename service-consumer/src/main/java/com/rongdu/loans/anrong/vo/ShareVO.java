/**
  * Copyright 2019 bejson.com 
  */
package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShareVO implements Serializable {

    private String customerName;
    private String paperNumber;
    private String loanId;
    private String id;

    private String overdueStartDate;
    private String nbMoney;
    private String state;
    private String loanTypeDesc;
}