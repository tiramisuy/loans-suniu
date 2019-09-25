package com.rongdu.loans.loan.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/7/26.
 */
@Data
public class OverdueItemCalcDTO implements Serializable {

	private String id;
	private String applyId; // 申请编号
	private Date startDate; // 开始日期
	private Date repayDate; // 还款日期
	private Integer totalTerm; // 贷款期数(月)
	private Integer thisTerm; // 期数
	private BigDecimal totalAmount; // 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	private BigDecimal principal; // 应还本金
	private BigDecimal interest; // 应还利息
	private BigDecimal penalty; // 逾期罚息
	private BigDecimal overdueFee; // 逾期管理费（每日）
	private BigDecimal servFee; // 分期服务费
	private BigDecimal deduction; // 减免费用
	private BigDecimal payedPrincipal; // 已还本金
	private BigDecimal unpayPrincipal; // 待还本金
	private BigDecimal payedInterest; // 已还利息
	private BigDecimal unpayInterest; // 待还利息
	private BigDecimal overdueRate; // 逾期罚息日利率
	private BigDecimal actualRate; // 贷款实际利率（年）
	private BigDecimal approveAmt;// 审批金额
	private Integer approveTerm;// 审批期限
	private Integer repayMethod;// 还款方式
	private Integer repayUnit;// 还款间隔
	private Integer status; // 是否已经结清（0-否，1-是）
	private BigDecimal applyServFee;// 申请表服务费
	private String payChannel;// 申请表放款渠道
	private String productId;// 产品ID
	private String channelId;// 渠道ID


}
