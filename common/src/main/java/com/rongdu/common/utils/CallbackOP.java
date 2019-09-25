package com.rongdu.common.utils;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/19.
 */
public class CallbackOP implements Serializable {
    private static final long serialVersionUID = -2321916022554554991L;
    private String time;
    private String method;
    private String status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
