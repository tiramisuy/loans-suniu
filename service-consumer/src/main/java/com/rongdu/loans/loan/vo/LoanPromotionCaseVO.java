package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈营销方案VO对象〉
 *
 * @author yuanxianchu
 * @create 2018/12/12
 * @since 1.0.0
 */
@Data
public class LoanPromotionCaseVO implements Serializable {
    private static final long serialVersionUID = 6306350584852804427L;
    /**
     *费用ID
     */
    private String id;
    /**
     *产品代码
     */
    private String productId;
    /**
     *渠道代码
     */
    private String channelId;
    /**
     *渠道名称
     */
    private String channelName;
    /**
     *借款金额区间（起始）
     */
    private Integer amtBegin;
    /**
     *借款金额区间（终止）
     */
    private Integer amtEnd;
    /**
     *借款期限区间（起始）
     */
    private Integer termBegin;
    /**
     *借款期限区间（终止）
     */
    private Integer termEnd;
    /**
     *贷款利率（年化）
     */
    private BigDecimal ratePerYear;
    /**
     *贷款利率（日化）
     */
    private BigDecimal ratePerDay;
    /**
     *中介信息服务费收费类型（0-按照百分比收取，1-按照固定金额收取）
     */
    private Integer servFeeType;
    /**
     *中介信息服务费
     */
    private BigDecimal servValue;
    /**
     * 服务费率
     */
    private BigDecimal servFeeRate;
    /**
     *提前还款服务费收费类型（0-按照百分比收取，1-按照固定金额收取）
     */
    private Integer prepayFeeType;
    /**
     *提前还款服务费\费率
     */
    private BigDecimal prepayValue;
    /**
     *逾期还款收费类型（0-按照百分比收取，1-按照固定金额收取）
     */
    private Integer overdueFeeType;
    /**
     *每天逾期还款服务费\费率
     */
    private BigDecimal overdueValue;
    /**
     * 逾期管理费/天(平台收取）
     */
    private BigDecimal overdueFee;
    /**
     *是否贴息（0-否，1-是）
     */
    private Integer discount;
    /**
     *贴息比例
     */
        private BigDecimal discountValue;
    /**
     *专案状态(0-停用，1-正常)
     */
    private String status;
}
