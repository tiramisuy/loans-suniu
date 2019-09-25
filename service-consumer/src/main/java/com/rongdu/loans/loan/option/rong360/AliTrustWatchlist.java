package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class AliTrustWatchlist implements Serializable{

	private static final long serialVersionUID = -4198807409811739594L;
	
	private boolean success;
    @JsonProperty("biz_no")
    private String bizNo;
    @JsonProperty("is_matched")
    private boolean isMatched;
    public void setSuccess(boolean success) {
         this.success = success;
     }
     public boolean getSuccess() {
         return success;
     }

    public void setBizNo(String bizNo) {
         this.bizNo = bizNo;
     }
     public String getBizNo() {
         return bizNo;
     }

    public void setIsMatched(boolean isMatched) {
         this.isMatched = isMatched;
     }
     public boolean getIsMatched() {
         return isMatched;
     }

}