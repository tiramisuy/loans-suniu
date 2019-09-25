package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class UserVerify implements Serializable {

    private static final long serialVersionUID = -4321680823947955377L;
    @JsonProperty("operator_verify")
    private OperatorVerify operatorVerify;
    @JsonProperty("operator_report_verify")
    private OperatorReportVerify operatorReportVerify;
    @JsonProperty("idcard_info")
    private IdcardInfo idcardInfo;
}