package com.rongdu.loans.loan.option.dwd.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ContactRegion implements Serializable {

    private static final long serialVersionUID = -6950378723298173411L;
    @JsonProperty("region_avg_call_out_time")
    private int regionAvgCallOutTime;
    @JsonProperty("region_call_in_time_pct")
    private double regionCallInTimePct;
    @JsonProperty("region_call_in_time")
    private double regionCallInTime;
    @JsonProperty("region_call_out_time_pct")
    private int regionCallOutTimePct;
    @JsonProperty("region_call_out_cnt_pct")
    private int regionCallOutCntPct;
    @JsonProperty("region_avg_call_in_time")
    private double regionAvgCallInTime;
    @JsonProperty("region_call_in_cnt_pct")
    private double regionCallInCntPct;
    @JsonProperty("region_loc")
    private String regionLoc;
    @JsonProperty("region_call_out_time")
    private int regionCallOutTime;
    @JsonProperty("region_call_out_cnt")
    private int regionCallOutCnt;
    @JsonProperty("region_call_in_cnt")
    private int regionCallInCnt;
    @JsonProperty("region_uniq_num_cnt")
    private int regionUniqNumCnt;
    public void setRegionAvgCallOutTime(int regionAvgCallOutTime) {
         this.regionAvgCallOutTime = regionAvgCallOutTime;
     }
     public int getRegionAvgCallOutTime() {
         return regionAvgCallOutTime;
     }

    public void setRegionCallInTimePct(double regionCallInTimePct) {
         this.regionCallInTimePct = regionCallInTimePct;
     }
     public double getRegionCallInTimePct() {
         return regionCallInTimePct;
     }

    public void setRegionCallInTime(double regionCallInTime) {
         this.regionCallInTime = regionCallInTime;
     }
     public double getRegionCallInTime() {
         return regionCallInTime;
     }

    public void setRegionCallOutTimePct(int regionCallOutTimePct) {
         this.regionCallOutTimePct = regionCallOutTimePct;
     }
     public int getRegionCallOutTimePct() {
         return regionCallOutTimePct;
     }

    public void setRegionCallOutCntPct(int regionCallOutCntPct) {
         this.regionCallOutCntPct = regionCallOutCntPct;
     }
     public int getRegionCallOutCntPct() {
         return regionCallOutCntPct;
     }

    public void setRegionAvgCallInTime(double regionAvgCallInTime) {
         this.regionAvgCallInTime = regionAvgCallInTime;
     }
     public double getRegionAvgCallInTime() {
         return regionAvgCallInTime;
     }

    public void setRegionCallInCntPct(double regionCallInCntPct) {
         this.regionCallInCntPct = regionCallInCntPct;
     }
     public double getRegionCallInCntPct() {
         return regionCallInCntPct;
     }

    public void setRegionLoc(String regionLoc) {
         this.regionLoc = regionLoc;
     }
     public String getRegionLoc() {
         return regionLoc;
     }

    public void setRegionCallOutTime(int regionCallOutTime) {
         this.regionCallOutTime = regionCallOutTime;
     }
     public int getRegionCallOutTime() {
         return regionCallOutTime;
     }

    public void setRegionCallOutCnt(int regionCallOutCnt) {
         this.regionCallOutCnt = regionCallOutCnt;
     }
     public int getRegionCallOutCnt() {
         return regionCallOutCnt;
     }

    public void setRegionCallInCnt(int regionCallInCnt) {
         this.regionCallInCnt = regionCallInCnt;
     }
     public int getRegionCallInCnt() {
         return regionCallInCnt;
     }

    public void setRegionUniqNumCnt(int regionUniqNumCnt) {
         this.regionUniqNumCnt = regionUniqNumCnt;
     }
     public int getRegionUniqNumCnt() {
         return regionUniqNumCnt;
     }

}