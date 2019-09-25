package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class GjjAccountAnalyzedData implements Serializable{

	private static final long serialVersionUID = 4054824411104421031L;
	
	@JsonProperty("newest_account")
    private int newestAccount;
    @JsonProperty("cont_last_times")
    private int contLastTimes;
    @JsonProperty("back_cont_last_times")
    private int backContLastTimes;
    @JsonProperty("cont_max_times")
    private int contMaxTimes;
    @JsonProperty("back_cont_max_times")
    private int backContMaxTimes;
    @JsonProperty("sum_times")
    private int sumTimes;
    @JsonProperty("back_sum_times")
    private int backSumTimes;
    public void setNewestAccount(int newestAccount) {
         this.newestAccount = newestAccount;
     }
     public int getNewestAccount() {
         return newestAccount;
     }

    public void setContLastTimes(int contLastTimes) {
         this.contLastTimes = contLastTimes;
     }
     public int getContLastTimes() {
         return contLastTimes;
     }

    public void setBackContLastTimes(int backContLastTimes) {
         this.backContLastTimes = backContLastTimes;
     }
     public int getBackContLastTimes() {
         return backContLastTimes;
     }

    public void setContMaxTimes(int contMaxTimes) {
         this.contMaxTimes = contMaxTimes;
     }
     public int getContMaxTimes() {
         return contMaxTimes;
     }

    public void setBackContMaxTimes(int backContMaxTimes) {
         this.backContMaxTimes = backContMaxTimes;
     }
     public int getBackContMaxTimes() {
         return backContMaxTimes;
     }

    public void setSumTimes(int sumTimes) {
         this.sumTimes = sumTimes;
     }
     public int getSumTimes() {
         return sumTimes;
     }

    public void setBackSumTimes(int backSumTimes) {
         this.backSumTimes = backSumTimes;
     }
     public int getBackSumTimes() {
         return backSumTimes;
     }

}