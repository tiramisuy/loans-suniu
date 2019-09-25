package com.rongdu.loans.loan.option.dkqb;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class LoanWalletVO implements Serializable {

    //success成功 error异常/失败
    public final static String CODE_SUCCESS = "success";
    public final static  String CODE_ERROR = "error";

    private String code;//success成功 error异常/失败
    private String message;//返回信息
    private JSONObject result;//接口的返回参数

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

}
