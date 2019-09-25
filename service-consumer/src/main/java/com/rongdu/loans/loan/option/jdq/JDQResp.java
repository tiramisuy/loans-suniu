package com.rongdu.loans.loan.option.jdq;

import lombok.Data;

import java.io.Serializable;

@Data
public class JDQResp implements Serializable {
    public static final String SUCCESS = "0"; // 接口调用成功
    public static final String FAILURE = "-2"; // 接口调用失败
    public static final String ERROR = "-3"; // 处理失败

    private static final long serialVersionUID = 345468739282512101L;

    private String code;

    private String desc;

    private String msg;

    private Object data;

}
