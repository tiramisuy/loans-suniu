package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提前还款计算相关参数对象
 * @author likang
 *
 */
public class PrePayCostingVO implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = -2140305633503159113L;
    
    /**
     * 提前还款服务费收费类型（0-按照百分比收取，1-按照固定金额收取）
     */
    private Integer prepayFeeType;
    /**
     * 提前还款服务费
     */
    private BigDecimal prepayValue;
    
    /**
     * 实际贷款本金
     */
    private BigDecimal loanAmt;
    
    /**
     * 实际贷款天（月）数
     */
    private Integer loanTerm;
    
    /**
     * 实际提前还款费用
     */
    private BigDecimal actualPrepayFee;

	public Integer getPrepayFeeType() {
		return prepayFeeType;
	}

	public BigDecimal getPrepayValue() {
		return prepayValue;
	}

	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public BigDecimal getActualPrepayFee() {
		return actualPrepayFee;
	}

	public void setPrepayFeeType(Integer prepayFeeType) {
		this.prepayFeeType = prepayFeeType;
	}

	public void setPrepayValue(BigDecimal prepayValue) {
		this.prepayValue = prepayValue;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}

	public void setActualPrepayFee(BigDecimal actualPrepayFee) {
		this.actualPrepayFee = actualPrepayFee;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
}
