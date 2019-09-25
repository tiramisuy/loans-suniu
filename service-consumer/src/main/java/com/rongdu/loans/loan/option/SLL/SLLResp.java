package com.rongdu.loans.loan.option.SLL;

import lombok.Data;

import java.io.Serializable;

/**  
* @Title: SLLResp.java
* @Package com.rongdu.loans.loan.option.sll
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月30日  
* @version V1.0  
*/
@Data
public class SLLResp implements Serializable {


    private static final long serialVersionUID = -399958484187677565L;

    public static final String SUCCESS = "200"; // 接口调用成功
    public static final String FAILURE = "400"; // 接口调用失败
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

    private String reason;

}
