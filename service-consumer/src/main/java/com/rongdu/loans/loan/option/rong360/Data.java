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
public class Data implements Serializable{

	private static final long serialVersionUID = -4859867484942362257L;
	
	@JsonProperty("gjj_data")
    private List<GjjData> gjjData;
    @JsonProperty("loan_data")
    private List<LoanData> loanData;
    @JsonProperty("general_analyzed_data")
    private GeneralAnalyzedData generalAnalyzedData;
    public void setGjjData(List<GjjData> gjjData) {
         this.gjjData = gjjData;
     }
     public List<GjjData> getGjjData() {
         return gjjData;
     }

    public void setLoanData(List<LoanData> loanData) {
         this.loanData = loanData;
     }
     public List<LoanData> getLoanData() {
         return loanData;
     }

    public void setGeneralAnalyzedData(GeneralAnalyzedData generalAnalyzedData) {
         this.generalAnalyzedData = generalAnalyzedData;
     }
     public GeneralAnalyzedData getGeneralAnalyzedData() {
         return generalAnalyzedData;
     }

}