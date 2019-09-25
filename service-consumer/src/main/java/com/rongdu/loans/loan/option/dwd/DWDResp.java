package com.rongdu.loans.loan.option.dwd;

import lombok.Data;

import java.io.Serializable;

/**  
* @Title: DWDResp.java  
* @Package com.rongdu.loans.loan.option.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月30日  
* @version V1.0  
*/
@Data
public class DWDResp implements Serializable {
	
	private static final long serialVersionUID = -7189316973765331844L;
	
	public static final Integer SUCCESS = 200; // 接口调用成功
    public static final Integer FAILURE = 400; // 接口调用失败

    public static final String SM = "SUCCESS"; // 接口调用成功
    public static final String FM= "FAILURE"; // 接口调用失败

    public DWDResp(){
        this.code = SUCCESS;
        this.msg = SM;
    }

    /**
     * 服务器响应编号
     */
    private Integer code;
    /**
     * 服务器响应消息
     */
    private String msg;
    /**
     * 服务器响应结果对象
     */
    private Object data;

}
