package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class TjyModel implements Serializable{

	private static final long serialVersionUID = 2786199530769335138L;
	
	@JsonProperty("decision_score")
    private int decisionScore;
    public void setDecisionScore(int decisionScore) {
         this.decisionScore = decisionScore;
     }
     public int getDecisionScore() {
         return decisionScore;
     }

}