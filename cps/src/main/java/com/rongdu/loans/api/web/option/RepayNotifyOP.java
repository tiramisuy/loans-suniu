package com.rongdu.loans.api.web.option;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhangxiaolong on 2017/8/3.
 */
public class RepayNotifyOP implements Serializable {
	@NotNull
	private BigDecimal repayAmt;
	@NotNull
	private Integer repayTerm;
	@NotBlank
	private String repayDate;
	@NotBlank
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(BigDecimal repayAmt) {
		this.repayAmt = repayAmt;
	}

	public Integer getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(Integer repayTerm) {
		this.repayTerm = repayTerm;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

}
