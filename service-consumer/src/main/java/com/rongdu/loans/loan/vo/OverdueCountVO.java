package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.rongdu.common.persistence.BaseEntity;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class OverdueCountVO extends BaseEntity<OverdueCountVO> implements Serializable {

	private static final long serialVersionUID = -6219847033510500874L;

	/**
	 * 催收员姓名
	 */
	private String operatorName;

	/**
	 * 分单数
	 */
	private Integer stageNum;

	/**
	 * 应还本金
	 */
	private BigDecimal principal;

	/**
	 * 应还利息
	 */
	private BigDecimal interest;

	/**
	 * 分期服务费
	 */
	private BigDecimal servFee;

	/**
	 * 逾期费
	 */
	private BigDecimal overdueFee;

	/**
	 * 减免费用
	 */
	private BigDecimal deduction;

	/**
	 * 已还本金
	 */
	private BigDecimal payedPrincipal;

	/**
	 * 已换利息
	 */
	private BigDecimal payedInterest;

	/**
	 * 待还本金
	 */
	private BigDecimal unpayPrincipal;

	/**
	 * 待还利息
	 */
	private BigDecimal unpayInterest;

	/**
	 * 逾期罚息
	 */
	private BigDecimal penalty;

	/**
	 * 延期笔数
	 */
	private Integer mandelay;
	
	/**
	 * 展期金额（展期的实际还款金额）
	 */
	private BigDecimal mandelayFee;

	/**
	 * 回单数/已还笔数
	 */
	private Integer backNum;

	private Integer pageNo = 1;
	
	private Integer pageSize = 10;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getStageNum() {
		return stageNum;
	}

	public void setStageNum(Integer stageNum) {
		this.stageNum = stageNum;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	public BigDecimal getPayedPrincipal() {
		return payedPrincipal;
	}

	public void setPayedPrincipal(BigDecimal payedPrincipal) {
		this.payedPrincipal = payedPrincipal;
	}

	public BigDecimal getPayedInterest() {
		return payedInterest;
	}

	public void setPayedInterest(BigDecimal payedInterest) {
		this.payedInterest = payedInterest;
	}

	public BigDecimal getUnpayPrincipal() {
		return unpayPrincipal;
	}

	public void setUnpayPrincipal(BigDecimal unpayPrincipal) {
		this.unpayPrincipal = unpayPrincipal;
	}

	public BigDecimal getUnpayInterest() {
		return unpayInterest;
	}

	public void setUnpayInterest(BigDecimal unpayInterest) {
		this.unpayInterest = unpayInterest;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public Integer getMandelay() {
		return mandelay;
	}

	public void setMandelay(Integer mandelay) {
		this.mandelay = mandelay;
	}

	public Integer getBackNum() {
		return backNum;
	}

	public void setBackNum(Integer backNum) {
		this.backNum = backNum;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public BigDecimal getMandelayFee() {
		return mandelayFee;
	}

	public void setMandelayFee(BigDecimal mandelayFee) {
		this.mandelayFee = mandelayFee;
	}

}