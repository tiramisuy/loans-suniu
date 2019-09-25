package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 协议对象查询入参
 * 
 * @author likang
 * 
 */
public class AgreementOP implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3660465658975184557L;

	/**
	 * 贷款申请金额
	 */
	private BigDecimal applyAmt;

	/**
	 * 申请期限(按天)
	 */
	private Integer applyTerm;

	/**
	 * 产品ID
	 */
	@NotBlank(message = "产品ID不能为空")
	private String productId;

	/**
	 * 渠道码
	 */
	private String channelId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 申请编号
	 */
	private String applyId;

	/**
	 * 还款方式
	 */
	private Integer repayMethod;

	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public String getUserId() {
		return userId;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}

}
