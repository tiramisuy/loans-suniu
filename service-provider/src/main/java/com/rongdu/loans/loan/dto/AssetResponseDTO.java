package com.rongdu.loans.loan.dto;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/7/25.
 */
public class AssetResponseDTO implements Serializable {

    /**
     * applyId
     */
    private String otherOrderId;
    /**
     * 理财端结果
     */
    private String result;
    /**
     * 外部流水
     */
    private String ssn;
    /**
     * 返回码
     */
    private String code;
    /**
     * 原始报文
     */
    private String resMap;

    public String getOtherOrderId() {
        return otherOrderId;
    }

    public void setOtherOrderId(String otherOrderId) {
        this.otherOrderId = otherOrderId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResMap() {
        return resMap;
    }

    public void setResMap(String resMap) {
        this.resMap = resMap;
    }
}
