package com.rongdu.loans.api.web.option;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/21.
 */
public class UserRecordOP implements Serializable{

    private String errorCode;

    private String message;

    private String params;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
