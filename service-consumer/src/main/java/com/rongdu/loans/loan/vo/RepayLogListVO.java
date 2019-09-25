package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷前，贷中管理列表
 * 
 * @author zhangxiaolong
 * 
 */
public class RepayLogListVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1173151302538488485L;

	/**
	 * 贷款申请单ID
	 */
	private String applyId;
	/**
	 * 合同ID
	 */
	private String contractId;
	/**
	 * 流水ID
	 */
	private String id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 交易金额
	 */
	private BigDecimal txAmt;
	/**
	 * 类型 manual-主动还款,auto-如果借款人没有主动还款，就自动扣款
	 */
	private String txType;
	private String txTypeStr;
	/**
	 * 时间
	 */
	private Date txTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态 1处理中，2成功，3失败
	 */
	private String status;
	private String statusStr;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 证件号
	 */
	private String idNo;
	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 支付类型
	 */
	private String payType;
	private String payTypeStr;

	/**
	 * 支付渠道编码
	 */
	private String chlCode;
	/**
	 * 支付渠道名称
	 */
	private String chlName;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品ID
	 */
	private String productId;

	/**
	 * 订单信息
	 */
	private String orderInfo;
	
	/**
	 * 放款渠道
	 */
	private String withdrawalSource;

	private String cardNo;

	private String bankName;



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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
		if ("AUTH_PAY".equals(txType)) {
			this.txTypeStr = "快捷还款";
		} else if ("AM_PAY".equals(txType)) {
			this.txTypeStr = "直接还款";
		} else if ("WITHHOLD".equals(txType)) {
			this.txTypeStr = "系统代扣";
		} else if ("MANPAY".equals(txType)) {
			this.txTypeStr = "手动还款";
		}else if("WH_ADMIN".equals(txType)){
			this.txTypeStr = "手动代扣";
		}else if("AUTO_PAY".equals(txType)){
			this.txTypeStr = "主动支付";
		}
	}

	public Date getTxTime() {
		return txTime;
	}

	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		if ("SUCCESS".equals(status)) {
			statusStr = "交易成功";
		} else {
			statusStr = "交易失败";
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
		if ("1".equals(payType)) {
			payTypeStr = "还款";
		} else if ("2".equals(payType)) {
			payTypeStr = "延期";
		} else if ("3".equals(payType)) {
			payTypeStr = "加急券";
		} else if ("4".equals(payType)) {
			payTypeStr = "购物金";
		} else if ("6".equals(payType)) {
			payTypeStr = "商城购物";
		} else if ("7".equals(payType)) {
			payTypeStr = "助贷服务";
		}
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	public String getChlName() {
		return chlName;
	}

	public void setChlName(String chlName) {
		this.chlName = chlName;
	}

	public String getTxTypeStr() {
		return txTypeStr;
	}

	public void setTxTypeStr(String txTypeStr) {
		this.txTypeStr = txTypeStr;
	}

	public String getPayTypeStr() {
		return payTypeStr;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getWithdrawalSource() {
		if ("0".equals(withdrawalSource)) {
			return "线上放款";
		} else if ("1".equals(withdrawalSource)) {
			return "线下放款";
		} else if ("2".equals(withdrawalSource)) {
			return "口袋";
		}else if ("3".equals(withdrawalSource)) {
			return "口袋存管";
		}else if ("4".equals(withdrawalSource)) {
			return "乐视";
		}else if ("5".equals(withdrawalSource)) {
			return "汉金所";
		}else {
			return "";
		}
	}

	public void setWithdrawalSource(String withdrawalSource) {
		this.withdrawalSource = withdrawalSource;
	}


	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
