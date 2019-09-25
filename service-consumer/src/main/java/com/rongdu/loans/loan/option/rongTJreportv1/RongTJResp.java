package com.rongdu.loans.loan.option.rongTJreportv1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongTianJiResp.java  
* @Package com.rongdu.loans.loan.option.rong360Model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月17日  
* @version V1.0  
*/
@Data
public class RongTJResp implements Serializable {
	
	private static final long serialVersionUID = 4236284445482566806L;
	
	private String error;
	
	private String msg;
	
	@JsonProperty("tianji_api_taojinyunreport_generatereport_response")
	private TianjiGeneratereportResp tianjiApiTaojinyunreportGeneratereportResponse;

}
