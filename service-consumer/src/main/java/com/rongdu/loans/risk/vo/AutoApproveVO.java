package com.rongdu.loans.risk.vo;


import java.io.Serializable;
import java.util.Date;

/**
 * 自动审批结果
 * @author sunda
 * @version 2017-08-27
 */
public class AutoApproveVO implements Serializable{

    /**
     *用户ID
     */
    private String userId;
    /**
     *用户姓名
     */
    private String name;
    /**
     *贷款申请编号
     */
    private String applyId;
    /**
     *策略集ID
     */
    private String strategyId;
    /**
     *策略集名称
     */
    private String strategyName;
    /**
     *欺诈分
     */
    private Integer score;
    /**
     *风险决策：ACCEPT-通过，REVIEW-人工审批，REJECT-拒绝
     */
    private String decision;
    /**
     *耗时（毫秒）
     */
    private Integer costTime;
    /**
     *命中规则的数量
     */
    private Integer hitNum;
    /**
     *审核时间
     */
    private Date approveTime;
    /**
     *审批状态：SUCCESS-成功，FAILURE-失败
     */
    private String status;

    public AutoApproveVO() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public Integer getHitNum() {
        return hitNum;
    }

    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}