package com.rongdu.loans.loan.vo.jdq;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-10-14 10:30:33
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class JDQCalculateInfoVO implements Serializable{

	private static final long serialVersionUID = 6088941568439747580L;
	
	@JsonProperty("min_amount")
    private BigDecimal minAmount;
    @JsonProperty("max_amount")
    private BigDecimal maxAmount;
    private BigDecimal multiple;
    @JsonProperty("card_amount")
    private BigDecimal cardAmount;
    @JsonProperty("repay_plan")
    private List<RepayPlan> repayPlan;
    @JsonProperty("loan_terms")
    private List<String> loanTerms;
    @JsonProperty("loan_term_unit")
    private String loanTermUnit;
    @JsonProperty("loan_term_days")
    private String loanTermDays;
    @JsonProperty("loan_desc")
    private List<String>  loanDesc;

}