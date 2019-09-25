package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: RepaymentOP.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月14日  
* @version V1.0  
*/
@Data
public class RepaymentOP implements Serializable{

	private static final long serialVersionUID = -7954332113578249645L;
	
	/**
	 * 借点钱订单号
	 */
	@JsonProperty("jdq_order_id")
	private String jdqOrderId;

	private String type;
	
	/**
	 * 操作成功跳转页面（选传，当调接口直接扣款而非跳转H5时，此字段不传递）
	 */
	/*@JsonProperty("success_return_url")
	private String successReturnUrl;*/
	
	/**
	 * 操作失败跳转页面（选传，当调接口直接扣款而非跳转H5时，此字段不传递）
	 */
	/*@JsonProperty("error_return_url")
	private String errorReturnUrl;*/

}
