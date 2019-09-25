package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2018/12/25
 * @since 1.0.0
 */
@Data
public class BorrowerInfoVO implements Serializable {

    private static final long serialVersionUID = -7680782069403864874L;
    /**
     * 贷款申请编号
     */
    private String applyId;
    /**
     * 合同号
     */
    private String contNo;
    /**
     * 借款用户ID
     */
    private String userId;
    /**
     * 借款用户姓名
     */
    private String userName;
    /**
     * 借款标题
     */
    private String title;
    /**
     * 借款总额
     */
    private BigDecimal borrowAmt;
    /**
     * 借款日期
     */
    private String borrowDate;
    /**
     * 实际利率
     */
    private BigDecimal actualRate;
    /**
     * 服务费率
     */
    private BigDecimal servFeeRate;
    /**
     * 逾期罚息日利率
     */
    private BigDecimal overdueRate;
    /**
     * 提前还款服务费率
     */
    private BigDecimal prepayValue;
    /**
     * 每日逾期管理费
     */
    private BigDecimal overdueFee;
    /**
     * 电子账户
     */
    private String accountId;
    /**
     * 借款人用途
     */
    private String purpose;
    /**
     * 借款期限单位（M-月，D-天）
     */
    private String periodUnit;
    /**
     * 借款期限，结合period_unit使用
     */
    private Integer period;
    /**
     * 还款方式（1按月等额本息，2按月等额本金，3次性还本付息，4按月付息、到期还本）
     */
    private Integer repayMethod;
    /**
     * 投资项目状态（0初始，1投标中，2审核中 ，3还款中 ，4已还清 ，5逾期 ，6撤标 ，7流标 ，8审核失败）
     */
    private String status;
    /**
     * 资产分销商ID
     */
    private String partnerId;
    /**
     * 资产分销商名称
     */
    private String partnerName;
    /**
     * 其他信息
     */
    private String extInfo;
    /**
     * 外部流水号
     */
    private String outsideSerialNo;
    /**
     * 推送时间
     */
    private Date pushTime;
    /**
     * 推送状态（0待推送，1已推送 ，2失败）
     */
    private Integer pushStatus;
    /**
     * 标的类型 信用 1 抵押 2 担保标3 质押 5 车保宝6
     */
    private Integer BorrowType;
    /**
     * 放款渠道 0=线上，1=线下，2=口袋
     */
    private Integer payChannel;
}
