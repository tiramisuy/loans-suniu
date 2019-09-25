package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class LoanData implements Serializable{

	private static final long serialVersionUID = 5128756684104291463L;
	
	@JsonProperty("loan_brief")
    private List<String> loanBrief;
    @JsonProperty("loan_detail")
    private List<String> loanDetail;
    public void setLoanBrief(List<String> loanBrief) {
         this.loanBrief = loanBrief;
     }
     public List<String> getLoanBrief() {
         return loanBrief;
     }

    public void setLoanDetail(List<String> loanDetail) {
         this.loanDetail = loanDetail;
     }
     public List<String> getLoanDetail() {
         return loanDetail;
     }

}