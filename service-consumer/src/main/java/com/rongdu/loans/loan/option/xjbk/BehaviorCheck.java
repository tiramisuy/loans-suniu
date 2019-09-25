package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class BehaviorCheck implements Serializable{

    private static final long serialVersionUID = -3229312414127455704L;
    @JsonProperty("check_point")
    private String checkPoint;
    private int score;
    private String result;
    private String evidence;
    @JsonProperty("check_point_cn")
    private String checkPointCn;
    public void setCheckPoint(String checkPoint) {
         this.checkPoint = checkPoint;
     }
     public String getCheckPoint() {
         return checkPoint;
     }

    public void setScore(int score) {
         this.score = score;
     }
     public int getScore() {
         return score;
     }

    public void setResult(String result) {
         this.result = result;
     }
     public String getResult() {
         return result;
     }

    public void setEvidence(String evidence) {
         this.evidence = evidence;
     }
     public String getEvidence() {
         return evidence;
     }

    public void setCheckPointCn(String checkPointCn) {
         this.checkPointCn = checkPointCn;
     }
     public String getCheckPointCn() {
         return checkPointCn;
     }

}