package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: BankBindVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class BankBindVO implements Serializable{

	private static final long serialVersionUID = 8571756407936584172L;
	
	@JsonProperty("need_confirm")
	private String needConfirm;
	
	@JsonProperty("deal_result")
	private String dealResult;
	
	private String reason;

}
