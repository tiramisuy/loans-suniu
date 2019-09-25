package com.rongdu.loans.loan.option.dwd.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class BehaviorCheck implements Serializable {

    private static final long serialVersionUID = 973541432933890839L;
    private String result;
    private int score;
    private String evidence;
    @JsonProperty("check_point_cn")
    private String checkPointCn;
    @JsonProperty("check_point")
    private String checkPoint;
    public void setResult(String result) {
         this.result = result;
     }
     public String getResult() {
         return result;
     }

    public void setScore(int score) {
         this.score = score;
     }
     public int getScore() {
         return score;
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

    public void setCheckPoint(String checkPoint) {
         this.checkPoint = checkPoint;
     }
     public String getCheckPoint() {
         return checkPoint;
     }

}