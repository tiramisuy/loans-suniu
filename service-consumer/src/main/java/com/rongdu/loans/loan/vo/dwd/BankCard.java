package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: BankCard.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class BankCard implements Serializable{

	private static final long serialVersionUID = -8690605743281694453L;
	
	@JsonProperty("card_id")
	private String cardId;
	
	@JsonProperty("open_bank")
	private String openBank;
	
	@JsonProperty("bank_card")
	private String bankCard;
	
	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("user_mobile")
	private String userMobile;

}
