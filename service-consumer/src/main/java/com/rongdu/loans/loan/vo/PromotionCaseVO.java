package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 营销方案VO
 * 
 * @author likang
 * 
 */
public class PromotionCaseVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4880723427789795784L;

	private String id;
	/**
	 * 服务费
	 */
	private BigDecimal servFee;

	/**
	 * 日利息
	 */
	private BigDecimal dayInterest;

	/**
	 * 日利息
	 */
	private BigDecimal yearInterest;

	/**
	 * 产品周期
	 */
	private Integer period;

	/**
	 * 产品状态
	 */
	private String pStatus;
	
	private Date updateTime;

	public String getpStatus() {
		return pStatus;
	}

	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public BigDecimal getDayInterest() {
		return dayInterest;
	}

	public void setDayInterest(BigDecimal dayInterest) {
		this.dayInterest = dayInterest;
	}

	public BigDecimal getYearInterest() {
		return yearInterest;
	}

	public void setYearInterest(BigDecimal yearInterest) {
		this.yearInterest = yearInterest;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
