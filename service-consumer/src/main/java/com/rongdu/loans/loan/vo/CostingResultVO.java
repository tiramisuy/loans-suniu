package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rongdu.common.mapper.serializer.BigDecimalSerializer;

/**
 * 营销方案VO
 * @author likang
 *
 */
public class CostingResultVO implements Serializable{
	
	/**
     * 序列号
     */
	private static final long serialVersionUID = 2821339918116763074L;
    
    /**
     * 服务费
     */
	@JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal servFee;
    
    /**
     * 日利息
     */
	@JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal dayInterest;
    
    /**
     * 到账金额
     */
	@JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal toAccountAmt;
    
    /**
     * 总利息
     */
	@JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal totalInterest;
    
    /**
     * 到期应还
     */
	@JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal realRepayAmt;
    
    /**
	  *贷款利率（日化）
	  */
    @JsonSerialize(using=BigDecimalSerializer.class)
	private BigDecimal ratePerDay;
	
	/**
	  * 服务费费率
	  */
    @JsonSerialize(using=BigDecimalSerializer.class)
	private BigDecimal servFeeRate;

	public BigDecimal getServFee() {
		return servFee;
	}

	public BigDecimal getDayInterest() {
		return dayInterest;
	}

	public BigDecimal getToAccountAmt() {
		return toAccountAmt;
	}

	public BigDecimal getTotalInterest() {
		return totalInterest;
	}

	public BigDecimal getRealRepayAmt() {
		return realRepayAmt;
	}

	public BigDecimal getRatePerDay() {
		return ratePerDay;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public void setDayInterest(BigDecimal dayInterest) {
		this.dayInterest = dayInterest;
	}

	public void setToAccountAmt(BigDecimal toAccountAmt) {
		this.toAccountAmt = toAccountAmt;
	}

	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}

	public void setRealRepayAmt(BigDecimal realRepayAmt) {
		this.realRepayAmt = realRepayAmt;
	}

	public void setRatePerDay(BigDecimal ratePerDay) {
		this.ratePerDay = ratePerDay;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
	}
}
