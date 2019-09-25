/**
  * Copyright 2019 bejson.com 
  */
package com.rongdu.loans.anrong.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuickRisk implements Serializable {

    /**
     * 基本信息描述
     */
    private BaseInfo baseInfo;
    /**
     * 错误信息代码
     */
    private List<String> errors;
    /**
     * 根据规则匹配是否命中风险
     */
    private RiskRule riskRule;
}