package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongImageFetchOP.java  
* @Package com.rongdu.loans.loan.option.rong360Model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月23日  
* @version V1.0  
*/
@Data
public class RongImageFetchOP implements Serializable {

	private static final long serialVersionUID = 7868932748780195309L;

	/**
	 * 订单编号
	 */
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 * token
	 */
	private String token;
}
