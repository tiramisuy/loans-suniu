package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: JDQBankCardInfo.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月12日  
* @version V1.0  
*/
@Data
public class JDQBankCardInfo implements Serializable{

	private static final long serialVersionUID = 8036068785720341384L;

	/**
	 * 卡类型：1：借记卡 2：信用卡
	 */
	@JsonProperty("card_type")
	private String cardType;
	
	/**
	 * 银行代码
	 */
	@JsonProperty("bank_code")
	private String bankCode;
	
	/**
	 * 银行卡号
	 */
	@JsonProperty("card_no")
	private String cardNo;
	
	/**
	 * 银行名
	 */
	@JsonProperty("bank_name")
	private String bankName;
	
	/**
	 * 预留手机号，和注册手机号一致
	 */
	private String phone;
	
	/**
	 * 身份证号
	 */
	@JsonProperty("id_number")
	private String idNumber;
	
	/**
	 * 姓名
	 */
	private String name;
}
