package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈运营统计值对象〉
 *
 * @author yuanxianchu
 * @create 2019/7/25
 * @since 1.0.0
 */
@Data
public class OperationalStatisticsVO {

    private int totalReg;   // 用户注册数
    private int totalApply; // 生成订单数
    private int applyAuto1Pass; // 机审通过数
    private int applyAuto2Pass; // 2次机审通过数
    private int applyPass;  // 审核通过数
    private int bindBank;   // 绑卡数
    private int bindBankAuto2;  // 2次机审绑卡数
    private int withdrawCount;  // 放款数
    private int withdrawCountAuto2; // 2次机审放款数
    private BigDecimal withdrawAmt; // 放款金额
    private BigDecimal withdrawAmtAuto2;    //2 次机审放款金额

    private BigDecimal auto1PassApplyRate;  // 机审通过数/生成订单数
    private BigDecimal manPassAuto1Rate;    // 人审通过数/机审通过数
    private BigDecimal bindBankApplyPassRate;    // 绑卡数/人审通过数
    private BigDecimal auto1PassRegRate;    // 机审通过数/用户注册数
    private BigDecimal withdrawCountApplyPassRate;    // 放款数/人审通过数
    private BigDecimal withdrawCountApplyRate;    // 放款数/生成订单数

}
