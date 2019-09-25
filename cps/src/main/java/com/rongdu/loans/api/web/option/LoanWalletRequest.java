package com.rongdu.loans.api.web.option;

import com.alibaba.fastjson.JSONObject;

public class LoanWalletRequest {

    private String code;//开放平台分配给合作方的唯一标识
    private String method;//接口标识; 用来唯一标记当前调用的接口
    private JSONObject data;//接口的调用参数, 要求必须为JSONObject
    private String sign;//请求签名, 参考附录-签名规则
    private Integer timestamp;//以秒为单位的10位UnixTimestamp时间戳

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
