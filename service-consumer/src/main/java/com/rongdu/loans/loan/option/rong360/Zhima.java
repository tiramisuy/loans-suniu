package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Zhima implements Serializable{

	private static final long serialVersionUID = 3035452023214598703L;
	
	@JsonProperty("ali_trust_score")
    private AliTrustScore aliTrustScore;
    @JsonProperty("ali_trust_watchlist")
    private AliTrustWatchlist aliTrustWatchlist;
    public void setAliTrustScore(AliTrustScore aliTrustScore) {
         this.aliTrustScore = aliTrustScore;
     }
     public AliTrustScore getAliTrustScore() {
         return aliTrustScore;
     }

    public void setAliTrustWatchlist(AliTrustWatchlist aliTrustWatchlist) {
         this.aliTrustWatchlist = aliTrustWatchlist;
     }
     public AliTrustWatchlist getAliTrustWatchlist() {
         return aliTrustWatchlist;
     }

}