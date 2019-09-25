package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongBindcardfeedbackOP.java  
* @Package com.rongdu.loans.loan.option.rong360  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年6月29日  
* @version V1.0  
*/
@Data
public class RongBindcardfeedbackOP implements Serializable {

	private static final long serialVersionUID = 9173124295997931128L;
	
	/**
	 * 订单编号
	 */
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 * 绑卡状态
	 */
	@JsonProperty("bind_status")
	private String bindStatus;
	
	/**
	 * 绑卡的卡类型
	 */
	@JsonProperty("bind_card_type")
	private String bindCardType;
	
	/**
	 * 失败原因
	 */
	private String reason;
}
