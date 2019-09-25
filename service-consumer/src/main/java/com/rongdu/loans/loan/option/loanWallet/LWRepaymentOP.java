package com.rongdu.loans.loan.option.loanWallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @author: fy
* @version V1.0  
*/
@Data
public class LWRepaymentOP implements Serializable{

	/**
	 * 	订单号
	 */
	@JsonProperty("orderId")
	private String orderId;

	/**
	 * 	用户唯一标识
	 */
	@JsonProperty("userId")
	private String userId;

	/**
	 * 	期号（分期必传）例如第一期，传1。第二期，传2
	 */
	@JsonProperty("stageNo")
	private String stageNo;

	/**
	 * 	还款验证码
	 */
	@JsonProperty("verifyCode")
	private String verifyCode;

}
