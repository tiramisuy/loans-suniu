package com.rongdu.loans.loan.option.rongTJreportv1;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-19 11:4:24
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class TianjiReportDetailResp implements Serializable{

	private static final long serialVersionUID = 1073289172883905126L;
	
	@JsonProperty("downloadUrl")
    private String downloadurl;
    private String html;
    private Json json;

    private String orderNo;
    private String userId;
}