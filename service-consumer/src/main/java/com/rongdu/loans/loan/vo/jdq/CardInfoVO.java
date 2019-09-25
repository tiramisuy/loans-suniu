package com.rongdu.loans.loan.vo.jdq;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.loans.loan.option.jdq.JDQBankCardInfo;

import lombok.Data;

/**  
* @Title: CardInfo.java  
* @Package com.rongdu.loans.loan.option.jdq  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月14日  
* @version V1.0  
*/
@Data
public class CardInfoVO implements Serializable{

	private static final long serialVersionUID = 3230428744124534L;

	/**
	 * 是否可添加新卡：1-是，0-否
	 */
	@JsonProperty("add_card_flag")
	private int addCardFlag;

	/**
	 * 自定义文案描述(已绑列表页展示给用户提示，可不传)
	 */
	private String  customDesc;
	
	/**
	 * 银行卡信息，未绑卡可不传
	 */
	@JsonProperty("card_list")
	private List<JDQBankCardInfo> jdqBankCardInfo;
}
