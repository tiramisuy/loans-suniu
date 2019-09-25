package com.rongdu.loans.loan.option.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: CertAuthOP.java  
* @Package com.rongdu.loans.loan.option.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class CertAuthOP implements Serializable{
	
	private static final long serialVersionUID = 4856892722139799632L;

	private String md5;
	
	@JsonProperty("id_card")
	private String idCard;
	
	@JsonProperty("user_mobile")
	private String userMobile;
	
	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("order_no")
	private String orderNo;

}
