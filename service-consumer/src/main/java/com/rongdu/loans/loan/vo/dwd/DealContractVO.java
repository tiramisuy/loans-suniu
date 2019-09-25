package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: DealContractVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class DealContractVO implements Serializable{
	
	private static final long serialVersionUID = -1279088116150354493L;
	
	@JsonProperty("contract_url")
	private String contractUrl;

}
