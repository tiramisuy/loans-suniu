package com.rongdu.loans.loan.option.rongTJreport;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongTJReportDetailResp.java  
* @Package com.rongdu.loans.loan.option.rongTJreport  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月17日  
* @version V1.0  
*/
@Data
public class RongTJReportDetailResp implements Serializable {

	private static final long serialVersionUID = -67844427158675356L;

	private String error;
	
	private String msg;
	
	@JsonProperty("tianji_api_taojinyunreport_reportdetail_response")
	private TianjiReportDetailResp tianjiApiTaojinyunreportReportdetailResponse;
	
	@JsonProperty("request_id")
	private String requestId;
}
