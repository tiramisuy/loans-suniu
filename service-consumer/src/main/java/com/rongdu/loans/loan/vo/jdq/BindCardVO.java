package com.rongdu.loans.loan.vo.jdq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: BindCardVO.java  
* @Package com.rongdu.loans.loan.vo.jdq  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月15日  
* @version V1.0  
*/
@Data
public class BindCardVO implements Serializable{

	private static final long serialVersionUID = -6053264989586640425L;

	/**
	 * 绑卡url地址
	 */
	@JsonProperty("bind_card_url")
	private String bindCardUrl;
	
	/**
	 * 卡类型：1:借记卡 2:信用卡
	 */
	@JsonProperty("card_type")
	private String cardType;
}
