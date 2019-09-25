package com.rongdu.loans.loan.option.rongTJreport;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: TianjiGeneratereportResp.java  
* @Package com.rongdu.loans.loan.option.rong360Model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月17日  
* @version V1.0  
*/
@Data
public class TianjiGeneratereportResp implements Serializable {

	private static final long serialVersionUID = -7211258857018315550L;
	
	@JsonProperty("search_id")
	private String searchId;

}
