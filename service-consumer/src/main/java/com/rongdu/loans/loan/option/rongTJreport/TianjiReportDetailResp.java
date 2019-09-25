package com.rongdu.loans.loan.option.rongTJreport;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-17 19:0:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class TianjiReportDetailResp implements Serializable{

	private static final long serialVersionUID = -3162798621094274397L;
	
	@JsonProperty("downloadUrl")
    private String downloadurl;
    private String html;
    private Json json;
    
    private String orderNo;
    private String userId;

}