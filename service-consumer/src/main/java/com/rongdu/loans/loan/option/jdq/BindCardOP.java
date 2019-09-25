package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: BindCardOP.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月15日  
* @version V1.0  
*/
@Data
public class BindCardOP implements Serializable{
	
	private static final long serialVersionUID = -1802487205090993243L;

	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 借点钱订单id
	 */
	@JsonProperty("jdq_order_id")
	private String jdqOrderId;
	/**
	 * 银行卡类型：1-借记卡，2-信用卡
	 */
	@JsonProperty("card_type")
	private int cardType;
	/**
	 * 绑卡类型：1-绑定新卡，2-选定旧卡
	 */
	@JsonProperty("bind_type")
	private int bindType;

	/**
	 * 银行卡号(bind_type=2时必传)
	 */
	@JsonProperty("card_no")
	private String cardNo;

	/**
	 * 银行卡号(bind_type=2时必传)
	 */
	@JsonProperty("bank_code")
	private String bankCode;

	/**
	 * 用户姓名
	 */
	@JsonProperty("user_name")
	private String userName;

	/**
	 * 身份证号
	 */
	@JsonProperty("id_number")
	private String idNumber;

	@JsonProperty("verify_code")
	private String verifyCode;

	/**
	 * 绑卡操作成功
	 */
	@JsonProperty("success_return_url")
	private String successReturnUrl;


	/**
	 * 绑卡操作失败
	 */
	@JsonProperty("fail_return_url")
	private String failReturnUrl;

}
