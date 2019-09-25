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
public class RiskAnalysis implements Serializable{

	private static final long serialVersionUID = 7025440970053469068L;
	
	@JsonProperty("blacklist_cnt")
    private int blacklistCnt;
    @JsonProperty("loan_record_cnt")
    private int loanRecordCnt;
    @JsonProperty("searched_cnt")
    private int searchedCnt;

}