package com.rongdu.loans.cust.vo;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/17.
 */
public class RiskResultVO implements Serializable{

    /**
     * 命中项码
     */
    private String riskItemTypeCode;
    /**
     * 命中内容
     */
    private String riskItemValue;
    /**
     * 风险明细
     */
    private String riskDetail;
    /**
     * 风险最近时间：YYYYMMDD
     */
    private String riskTime;

    public String getRiskItemTypeCode() {
        return riskItemTypeCode;
    }

    public void setRiskItemTypeCode(String riskItemTypeCode) {
        this.riskItemTypeCode = riskItemTypeCode;
    }

    public String getRiskItemValue() {
        return riskItemValue;
    }

    public void setRiskItemValue(String riskItemValue) {
        this.riskItemValue = riskItemValue;
    }

    public String getRiskDetail() {
        return riskDetail;
    }

    public void setRiskDetail(String riskDetail) {
        this.riskDetail = riskDetail;
    }

    public String getRiskTime() {
        return riskTime;
    }

    public void setRiskTime(String riskTime) {
        this.riskTime = riskTime;
    }
}
