package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-12 10:27:22
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class LoanInfo implements Serializable{

    private static final long serialVersionUID = -7546442203466202148L;
    @JsonProperty("loan_amount")
    private String loanAmount;


    @JsonProperty("loan_term")
    private String loanTerm;


}