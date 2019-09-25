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
public class ConfirmLoanOP implements Serializable{
	
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
	 * 用户姓名
	 */
	@JsonProperty("user_name")
	private String userName;

	/**
	 * 身份证号
	 */
	@JsonProperty("id_number")
	private String idNumber;
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
