package com.rongdu.loans.loan.option.rong360Model;

import lombok.Data;

import java.io.Serializable;
@Data
public class Rong360Resp implements Serializable {
    public static final String SUCCESS = "200"; // 接口调用成功
    public static final String FAILURE = "400"; // 接口调用失败

    private static final long serialVersionUID = 345468739282512101L;
    /**
     * 服务器响应编号
     */
    private String code;
    /**
     * 服务器响应消息
     */
    private String msg;
    /**
     * 服务器响应结果对象
     */
    private Object data;

}
