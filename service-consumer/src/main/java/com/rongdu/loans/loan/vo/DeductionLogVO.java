package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/7/28.
 */
public class DeductionLogVO implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 减免理由
     */
    private String remark;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 贷款申请单ID
     */
    private String applyId;
    /**
     * 还款明细ID
     */
    private String repayPlanItemId;
    /**
     * 减免费用
     */
    private BigDecimal deduction;
    /**
     * 来源（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
     */
    private Integer source;
    /**
     * 终审人ID
     */
    private String approverId;
    /**
     * 终审人姓名
     */
    private String approverName;
    /**
     * 处理时间
     */
    private Date approveTime;
    /**
     * 状态: 0待审核，1通过，2不通过
     */
    private Integer status;

    /**
     * 	创建者userId
     */
    protected String createBy;
    /**
     * 	创建日期
     */
    protected Date createTime;
    /**
     * 	最后修改者userId
     */
    protected String updateBy;
    /**
     *  更新日期
     */
    protected Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getRepayPlanItemId() {
        return repayPlanItemId;
    }

    public void setRepayPlanItemId(String repayPlanItemId) {
        this.repayPlanItemId = repayPlanItemId;
    }

    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
