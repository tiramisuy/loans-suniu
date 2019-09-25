package com.rongdu.loans.loan.option.share;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/20
 * @since 1.0.0
 */
@Data
public class ShareApiResult implements Serializable {

    private static final long serialVersionUID = 7762545068165068665L;
    //success成功 error异常/失败
    public final static String CODE_SUCCESS = "success";
    public final static  String CODE_ERROR = "error";

    private String code;//success成功 error异常/失败
    private String message;//返回信息
}
