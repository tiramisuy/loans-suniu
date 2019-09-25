package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Overdues implements Serializable {

    /**
     * 逾期(开始)日期
     */
    private String checkOverdueDate;
    /**
     * 逾期金额
     */
    private String nbMoney;
    /**
     * 更新日期
     */
    private String operTime;
    /*
     * 逾期时长
     */
    private String overdueDays;
    /**
     * 状态
     */
    private String overdueState;
    /**
     * 备注
     */
    private String remark;

}
