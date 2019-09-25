/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.loan.option.xjbk;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款计划Entity
 * @author likang
 * @version 2017-06-22
 */
@Data
public class LoanRepayPlanVO implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2744814435506195827L;
	
	private String applyId;		// 申请流水号
	private String contNo;		// 合同编号
	private String userId;		// 客户号
	private String userName;		// 客户名称
	private String idNo;		// 证件号码
	private String idType;		// 证件类型
	private Date loanStartDate;		// 贷款开始日期
	private Date loanEndDate;		// 贷款终止日期
	private BigDecimal totalAmount;		// 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	private BigDecimal principal;		// 应还本金
	private BigDecimal interest;		// 应还利息
	private BigDecimal servFee;		// 中介服务手续费
	private BigDecimal prepayFee;		// 提前还款手续费
	private BigDecimal penalty;		// 逾期罚息
	private BigDecimal overdueFee;		// 逾期管理费
	private BigDecimal deduction;		// 减免费用
	private Integer totalTerm;		// 贷款期数(月)
	private Integer currentTerm;		// 当前期数
	private Integer payedTerm;		// 已还期数(月)
	private Integer unpayTerm;		// 待还期数(月)
	private BigDecimal payedPrincipal;		// 已还本金
	private BigDecimal unpayPrincipal;		// 待还本金
	private BigDecimal payedInterest;		// 已还利息
	private BigDecimal unpayInterest;		// 待还利息
	private Date curRealRepayDate;    // 当期实际还款日
	private String nextRepayDate;		// 下一期日（或者最后还款日）
	private Integer status;		// 是否已经结清（0-否，1-是）
	

}