package com.rongdu.loans.loan.option;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lee on 2018/5/8.
 */
public class GoodsOP implements Serializable {
	private static final long serialVersionUID = 9166290676497901484L;

	/**
	 * 申请编号
	 */
	private String applyId;

	/**
	 * 审批金额
	 */
	private BigDecimal approveAmt;

	/**
	 * 进件来源（1-PC, 2-ios, 3-android,4-h5）
	 */
	@NotBlank(message = "进件来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
	private String source;

	/**
	 * 产品ID
	 */
	@NotBlank(message = "产品ID不能为空")
	private String productId;
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 放款类型 7=加急券 8=旅游券
	 */
	private String loanStatus;

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
