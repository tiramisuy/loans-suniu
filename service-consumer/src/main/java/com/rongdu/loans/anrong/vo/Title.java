/**
  * Copyright 2019 bejson.com 
  */
package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Title implements Serializable {

    private String applyCancelCount;
    private String applyCancelSumMoney;
    private String applyPassedCount;
    private String applyPassedSumMoney;
    private String applyRejectCount;
    private String applyRejectSumMoney;
    private String applyTotalCount;
    private String applyTotalSumMoney;
    private String applyingCount;
    private String applyingSumMoney;
    private String blackCount;
    private String blackSumMoney;
    private String customerName;
    private String ejqCount;
    private String ejqSumMoney;
    private String etotalCount;
    private String etotalSumMoney;
    private String ewjqCount;
    private String ewjqSumMoney;
    private String jqCount;
    private String jqSumMoney;
    private String paperNumber;
    private String queryCount;
    private String queryCount2Year;
    private String queryCount3Month;
    private String queryCount6Month;
    private String queryNumber;
    private String reportTime;
    private String totalCount;
    private String totalSumMoney;
    private String wjqCount;
    private String wjqSumMoney;

}