package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: CardInfoOP.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月14日  
* @version V1.0  
*/
@Data
public class CardInfoOP implements Serializable{

	private static final long serialVersionUID = -7279171404932758206L;
	
	/**
	 * 用户姓名
	 */
	@JsonProperty("user_name")
	private String userName;
	/**
	 * 用户身份证号
	 */
	@JsonProperty("id_number")
	private String idNumber;

	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 订单id
	 */
	@JsonProperty("jdq_order_id")
	private String jdqOrderId;

	/**
	 * 银行卡类型：1-借记卡，2-信用卡
	 */
	@JsonProperty("card_type")
	private int cardType;

}
