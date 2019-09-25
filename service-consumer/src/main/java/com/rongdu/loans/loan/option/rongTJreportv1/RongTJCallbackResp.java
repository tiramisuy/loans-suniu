package com.rongdu.loans.loan.option.rongTJreportv1;

import java.io.Serializable;

import lombok.Data;

/**  
* @Title: RongTJCallbackResp.java  
* @Package com.rongdu.loans.loan.option.rongTJreportv1  
* @Description: 融360天机运营商报告回调响应 
* @author: yuanxianchu  
* @date 2018年9月30日  
* @version V1.0  
*/
@Data
public class RongTJCallbackResp implements Serializable{
	
	private static final long serialVersionUID = 4212713414242095620L;
	/**
     * 服务器响应编号
     */
    private int code;
    /**
     * 服务器响应消息
     */
    private String msg;

}
