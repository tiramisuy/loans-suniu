package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class RepayPlanOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3056109832349117834L;

	/**
	 * 贷款金额
	 */
	@NotNull(message = "贷款金额不能为空")
	@Range(min = 500, max = 1000000, message = "贷款金额大小超过限制")
	private BigDecimal applyAmt;
	/**
	 * 还款方式
	 */
	@NotNull(message = "还款方式不能为空")
	private Integer repayMethod;
	/**
	 * 还款期数
	 */
	@NotNull(message = "还款期数不能为空")
	@Range(min = 1, max = 90, message = "还款期数超过限制")
	private Integer repayTerm;

	/**
	 * 产品ID
	 */
	@NotBlank(message = "产品ID不能为空")
	private String productId;
	/**
	 * 渠道ID
	 */
	@NotBlank(message = "渠道ID不能为空")
	private String channelId;

	private String applyId;

	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}

	public Integer getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(Integer repayTerm) {
		this.repayTerm = repayTerm;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

}
