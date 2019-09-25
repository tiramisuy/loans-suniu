package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyDetail implements Serializable {

    /**
     * 申请日期
     */
    private String applyDate;
    /**
     * 审批结果
     */
    private String applyResult;
    /**
     * 申请地点
     */
    private String creditAddress;
    /**
     * 申请金额
     */
    private String loanMoney;
    /**
     * 会员类型
     */
    private String memberType;
    /**
     * 备注
     */
    private String remark;

}
