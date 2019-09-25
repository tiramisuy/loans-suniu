package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈还款计划详情VO〉
 *
 * @author yuanxianchu
 * @create 2018/12/14
 * @since 1.0.0
 */
@Data
public class RepayPlanDetailVO implements Serializable {

    private static final long serialVersionUID = 3577678083252111006L;
    /**
     * 还款计划明细ID
     */
    private String id;
    /**
     *申请编号
     */
    private String applyId;
    /**
     *合同编号
     */
    private String contNo;
    /**
     *客户编号
     */
    private String userId;
    /**
     *客户名称
     */
    private String userName;
    /**
     *还款日期
     */
    private Date repayDate;
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     *贷款期数(月)
     */
    private Integer totalTerm;
    /**
     *期数
     */
    private Integer thisTerm;
    /**
     *应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
     */
    private BigDecimal totalAmount;
    /**
     *应还本金
     */
    private BigDecimal principal;
    /**
     *应还利息
     */
    private BigDecimal interest;
    /**
     *中介服务手续费
     */
    private BigDecimal servFee;
    /**
     *提前还款手续费
     */
    private BigDecimal prepayFee;
    /**
     *逾期罚息
     */
    private BigDecimal penalty;
    /**
     * 逾期管理费
     */
    private BigDecimal overdueFee;
    /**
     *减免费用
     */
    private BigDecimal deduction;
    /**
     *已还本金
     */
    private BigDecimal payedPrincipal;
    /**
     *待还本金
     */
    private BigDecimal unpayPrincipal;
    /**
     * 实际还款金额
     */
    private BigDecimal actualRepayAmt;
    /**
     *已还利息
     */
    private BigDecimal payedInterest;
    /**
     *待还利息
     */
    private BigDecimal unpayInterest;
    /**
     *实际还款日期
     */
    private String actualRepayDate;
    /**
     *实际还款时间
     */
    private Date actualRepayTime;
    /**
     *还款类型（0-主动还款，1-自动还款）
     */
    private String repayType;
    /**
     *还款优惠券
     */
    private String couponId;
    /**
     *是否已经结清（0-否，1-是）
     */
    private Integer status;
}
