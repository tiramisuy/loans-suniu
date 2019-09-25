package com.rongdu.loans.loan.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 还款入参对象
 * 
 * @author likang
 * 
 */
public class RePayOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4763285474561172681L;

	/**
	 * 提前还清标识
	 */
	public static final String PREPAY_FLAG_YES = "1";
	/**
	 * 非提前还清标识
	 */
	public static final String PREPAY_FLAG_NO = "2";

	/**
	 * 第三方支付绑定id
	 */
	private String bindId;

	/**
	 * 还款计划明细id
	 */
	private String repayPlanItemId;

	/**
	 * 提前还款标识（1-提前还清, 2-非提前还清）
	 */
	@NotBlank(message = "提前还清标识不能为空")
	@Pattern(regexp = "1|2", message = "提前还清标识有误")
	private String prePayFlag;

	/**
	 * 交易金额
	 */
	@NotBlank(message = "交易金额不能为空")
	private String txAmt;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * ip
	 */
	private String ip;

	/**
	 * 进件来源（1-PC, 2-ios, 3-android,4-h5）
	 */
	@NotBlank(message = "进件来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
	private String source;

	/**
	 * 交易流水号
	 */
	private String transId;

	/**
	 * 支付公司支付订单号 （预支付后返回订单号）
	 */
	private String payComOrderNo;

	/**
	 * 姓名
	 */
	private String fullName;

	/**
	 * 银行代码
	 */
	private String bankCode;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 银行卡号
	 */
	private String cardNo;

	/**
	 * 交易方式
	 */
	private String txType;

	/**
	 * 终端类型
	 */
	private String terminal;

	/**
	 * 终端ID
	 */
	private String terminalId;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 支付订单结果
	 */
	private String orderInfo;

	/**
	 * 支付成功金额
	 */
	private String paySuccAmt;

	/**
	 * 支付成功时间
	 */
	private String paySuccTime;
	/**
	 * 支付类型 1=还款 2=延期
	 */
	private Integer payType;

	/**
	 * 申请编号
	 */
	private String applyId;

	/**
	 * 支付渠道
	 */
	private String chlCode;
	/**
	 * 旅游产品id
	 * 
	 */
	private String tripProductId;
	

	/**
	 * 抵扣购物券（0-否, 1-是）
	 */
	@Pattern(regexp = "0|1", message = "是否抵扣购物券")
	private String isDeduction;
	
	/**
	 * 购物券抵扣金额
	 */
	private String deductionAmt;
	
	/**
	 * 购物券id(多个逗号分隔)
	 */
	private String couponId;

	public String getBindId() {
		return bindId;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public String getUserId() {
		return userId;
	}

	public String getIp() {
		return ip;
	}

	public String getSource() {
		return source;
	}

	public String getPayComOrderNo() {
		return payComOrderNo;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setPayComOrderNo(String payComOrderNo) {
		this.payComOrderNo = payComOrderNo;
	}

	public String getTransId() {
		return transId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getPaySuccAmt() {
		return paySuccAmt;
	}

	public String getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccAmt(String paySuccAmt) {
		this.paySuccAmt = paySuccAmt;
	}

	public void setPaySuccTime(String paySuccTime) {
		this.paySuccTime = paySuccTime;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getPrePayFlag() {
		return prePayFlag;
	}

	public void setPrePayFlag(String prePayFlag) {
		this.prePayFlag = prePayFlag;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	public String getTripProductId() {
		return tripProductId;
	}

	public void setTripProductId(String tripProductId) {
		this.tripProductId = tripProductId;
	}

	public String getIsDeduction() {
		return isDeduction;
	}

	public void setIsDeduction(String isDeduction) {
		this.isDeduction = isDeduction;
	}

	public String getDeductionAmt() {
		return deductionAmt;
	}

	public void setDeductionAmt(String deductionAmt) {
		this.deductionAmt = deductionAmt;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	

}
