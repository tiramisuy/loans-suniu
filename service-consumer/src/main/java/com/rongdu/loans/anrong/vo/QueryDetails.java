/**
  * Copyright 2019 bejson.com 
  */
package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryDetails implements Serializable {

    /**
     * 会员类型
     */
    private String memberType;
    /**
     * 查询日期
     */
    private String queryDate;
    /**
     * 查询类别
     */
    private String queryType;
    /**
     * 备注
     */
    private String remark;
}