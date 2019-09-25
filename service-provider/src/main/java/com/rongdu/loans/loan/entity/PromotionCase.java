package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 贷款产品营销专案Entity
 * @author likang
 * @version 2017-07-06
 */
public class PromotionCase extends BaseEntity<PromotionCase> {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8286004530101639875L;
	
	/**
	 * 费用收取类型 - 按照百分比收取
	 */
	public static final Integer FEE_TYPE_RATE = 0;
	
	/**
	 * 费用收取类型 - 按照固定金额收取
	 */
	public static final Integer FEE_TYPE_AMT = 1;
	
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
	
	public PromotionCase() {
		super();
	}

	public PromotionCase(String id){
		super(id);
	}

	public String getProductId() {
		return productId;
	}

	public String getChannelId() {
		return channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public Integer getAmtBegin() {
		return amtBegin;
	}

	public Integer getAmtEnd() {
		return amtEnd;
	}

	public Integer getTermBegin() {
		return termBegin;
	}

	public Integer getTermEnd() {
		return termEnd;
	}

	public BigDecimal getRatePerYear() {
		return ratePerYear;
	}

	public BigDecimal getRatePerDay() {
		return ratePerDay;
	}

	public Integer getServFeeType() {
		return servFeeType;
	}

	public BigDecimal getServValue() {
		return servValue;
	}

	public Integer getPrepayFeeType() {
		return prepayFeeType;
	}

	public BigDecimal getPrepayValue() {
		return prepayValue;
	}

	public Integer getOverdueFeeType() {
		return overdueFeeType;
	}

	public BigDecimal getOverdueValue() {
		return overdueValue;
	}

	public Integer getDiscount() {
		return discount;
	}

	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	public String getStatus() {
		return status;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setAmtBegin(Integer amtBegin) {
		this.amtBegin = amtBegin;
	}

	public void setAmtEnd(Integer amtEnd) {
		this.amtEnd = amtEnd;
	}

	public void setTermBegin(Integer termBegin) {
		this.termBegin = termBegin;
	}

	public void setTermEnd(Integer termEnd) {
		this.termEnd = termEnd;
	}

	public void setRatePerYear(BigDecimal ratePerYear) {
		this.ratePerYear = ratePerYear;
	}

	public void setRatePerDay(BigDecimal ratePerDay) {
		this.ratePerDay = ratePerDay;
	}

	public void setServFeeType(Integer servFeeType) {
		this.servFeeType = servFeeType;
	}

	public void setServValue(BigDecimal servValue) {
		this.servValue = servValue;
	}

	public void setPrepayFeeType(Integer prepayFeeType) {
		this.prepayFeeType = prepayFeeType;
	}

	public void setPrepayValue(BigDecimal prepayValue) {
		this.prepayValue = prepayValue;
	}

	public void setOverdueFeeType(Integer overdueFeeType) {
		this.overdueFeeType = overdueFeeType;
	}

	public void setOverdueValue(BigDecimal overdueValue) {
		this.overdueValue = overdueValue;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}
}
