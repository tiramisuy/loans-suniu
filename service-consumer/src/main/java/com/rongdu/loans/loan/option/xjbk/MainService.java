package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-08 10:9:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class MainService implements Serializable {

    private static final long serialVersionUID = 2621838981543586058L;
    @JsonProperty("service_details")
    private List<ServiceDetails> serviceDetails;
    @JsonProperty("total_service_cnt")
    private int totalServiceCnt;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("company_name")
    private String companyName;


}