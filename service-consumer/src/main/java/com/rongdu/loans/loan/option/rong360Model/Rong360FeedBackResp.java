package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import lombok.Data;

/**  
* @Title: Rong360FeedBackResp.java  
* @Package com.rongdu.loans.loan.option.rong360Model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月3日  
* @version V1.0  
*/
@Data
public class Rong360FeedBackResp implements Serializable {

	private static final long serialVersionUID = 5297896933938916574L;
	
	private String code;
	private String error;
	private String mSg;
	private String is_api_v3_order_approvefeedback_responese;//审批结论反馈
	private String is_api_v3_order_contractstatus_responese;//合同状态反馈
	private String is_api_v3_order_pushrepayment_responese;//还款计划推送
	private String is_api_v3_order_orderfeedback_responese;//订单状态反馈
	private String is_api_v3_order_repayfeedback_responese;//还款或展期结果反馈

}
