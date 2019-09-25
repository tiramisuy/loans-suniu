package com.rongdu.loans.loan.option.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: BindVerifyOP.java  
* @Package com.rongdu.loans.loan.option.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class BankVerifyOP implements Serializable{
	
	private static final long serialVersionUID = 8711602830577799954L;

	@JsonProperty("order_no")
	private String orderNo;
	
	@JsonProperty("bank_card")
	private String bankCard;
	
	@JsonProperty("open_bank")
	private String openBank;
	
	@JsonProperty("card_id")
	private String cardId;
	
	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("id_number")
	private String idNumber;
	
	@JsonProperty("user_mobile")
	private String userMobile;
	
	@JsonProperty("bank_address")
	private String bankAddress;
	
	@JsonProperty("verify_code")
	private String verifyCode;
	
	@JsonProperty("bind_card_src")
	private String bindCardSrc;

}
