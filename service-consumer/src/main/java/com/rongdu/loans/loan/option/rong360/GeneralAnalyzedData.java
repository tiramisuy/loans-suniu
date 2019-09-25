package com.rongdu.loans.loan.option.rong360;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@lombok.Data
public class GeneralAnalyzedData implements Serializable{

	private static final long serialVersionUID = -8285933676346883087L;
	
	@JsonProperty("every_company_data")
    private List<String> everyCompanyData;
    @JsonProperty("last_6months_pay_off")
    private String last6monthsPayOff;
    @JsonProperty("last_6months_back_pay_off")
    private String last6monthsBackPayOff;
    @JsonProperty("last_24months_sum_times")
    private String last24monthsSumTimes;
    @JsonProperty("last_24months_back_sum_times")
    private String last24monthsBackSumTimes;
    @JsonProperty("last_24months_company_quantity")
    private String last24monthsCompanyQuantity;
    @JsonProperty("last_12months_has_extract")
    private String last12monthsHasExtract;
    @JsonProperty("last_12months_average")
    private String last12monthsAverage;
    @JsonProperty("last_24months_average")
    private String last24monthsAverage;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private Date endDate;
    @JsonProperty("overdueClassify")
    private List<String> overdueclassify;
    @JsonProperty("loanClassify")
    private List<String> loanclassify;
    private List<String> blacklist;

}