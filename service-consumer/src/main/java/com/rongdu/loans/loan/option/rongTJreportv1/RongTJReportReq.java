package com.rongdu.loans.loan.option.rongTJreportv1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongTJReportReq.java  
* @Package com.rongdu.loans.loan.option.rongTJreportv1  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年9月28日  
* @version V1.0  
*/
@Data
public class RongTJReportReq implements Serializable{

	private static final long serialVersionUID = 7838243919836883328L;
	
	//唯一标识id
	@JsonProperty("out_unique_id")
	private String out_unique_id;
	//查询ID
	@JsonProperty("search_id")
	private String search_id;
	//状态 ‘report’, ‘report_fail’
	@JsonProperty("state")
	private String state;
	//推送的报告见报告详情（默认json格式，若需html格式调detail接口获取）
	/*@JsonProperty("report")
	private Json report;*/
	
	/**
	 * 自加字段
	 */
	private String orderNo;
	private String userId;
	private String applyId;
}
