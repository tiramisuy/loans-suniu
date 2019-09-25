/**
  * Copyright 2019 bejson.com 
  */
package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaseInfo implements Serializable {

    /**
     * 手机
     */
    private String mobile;
    /**
     * 根据手机号做出的解析内容
     */
    private String mobileDesc;
    /**
     *  姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String paperNumber;
    /**
     * 根据身份证号做出的解析内容
     */
    private String paperNumberDesc;
    /**
     * 查询时间
     */
    private String queryDate;
    /**
     * 报告编号
     */
    private String reportNumber;
}