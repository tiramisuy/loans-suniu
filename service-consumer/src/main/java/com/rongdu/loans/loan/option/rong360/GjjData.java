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
public class GjjData implements Serializable{

	private static final long serialVersionUID = 2904492831494729098L;
	
	@JsonProperty("gjj_brief")
    private GjjBrief gjjBrief;
    @JsonProperty("gjj_detail")
    private List<GjjDetail> gjjDetail;
    @JsonProperty("gjj_account_analyzed_data")
    private GjjAccountAnalyzedData gjjAccountAnalyzedData;
    public void setGjjBrief(GjjBrief gjjBrief) {
         this.gjjBrief = gjjBrief;
     }
     public GjjBrief getGjjBrief() {
         return gjjBrief;
     }

    public void setGjjDetail(List<GjjDetail> gjjDetail) {
         this.gjjDetail = gjjDetail;
     }
     public List<GjjDetail> getGjjDetail() {
         return gjjDetail;
     }

    public void setGjjAccountAnalyzedData(GjjAccountAnalyzedData gjjAccountAnalyzedData) {
         this.gjjAccountAnalyzedData = gjjAccountAnalyzedData;
     }
     public GjjAccountAnalyzedData getGjjAccountAnalyzedData() {
         return gjjAccountAnalyzedData;
     }

}