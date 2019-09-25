package com.rongdu.loans.pay.op;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author liuzhuang
 * 
 */
public class XfAgreementPayOP implements Serializable {

	private static final long serialVersionUID = -5103606434948118357L;
	/**
	 * 签约号
	 */
	private String contractNo;
	/**
	 * 金额(单位元)
	 */
	@NotBlank(message = "金额不能为空")
	private String amount;
	/**
	 * 支付分类
	 */
	@NotNull(message = "支付分类不能为空")
	private Integer payType;
	/**
	 * 用户编号
	 */
	@NotBlank(message = "用户编号不能为空")
	private String userId;
	/**
	 * 订单号
	 */
	@NotBlank(message = "订单号不能为空")
	private String applyId;
	/**
	 * 合同号
	 */
	private String contractId;
	/**
	 * 还款计划明细id
	 */
	private String repayPlanItemId;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 商品Id
	 */
	private String goodsId;

	/**
	 * 购物券id(多个逗号分隔)
	 */
	private String couponId;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
}
