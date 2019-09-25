package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.utils.excel.annotation.ExcelField;

/**
 * 
 * @author liuzhuang
 * 
 */
public class RepayLogReportVO implements Serializable {
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
	 * 订单信息
	 */
	private String orderInfo;

	@ExcelField(title = "申请订单号", type = 1, align = 2, sort = 1)
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@ExcelField(title = "合同编号", type = 1, align = 2, sort = 2)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@ExcelField(title = "扣款订单号", type = 1, align = 2, sort = 3)
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

	@ExcelField(title = "实还金额", type = 1, align = 2, sort = 7)
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
	}

	@ExcelField(title = "实还时间", type = 1, align = 2, sort = 11)
	public Date getTxTime() {
		return txTime;
	}

	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}

	@ExcelField(title = "备注", type = 1, align = 2, sort = 13)
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
	}

	@ExcelField(title = "借款人姓名", type = 1, align = 2, sort = 4)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ExcelField(title = "证件号码", type = 1, align = 2, sort = 6)
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@ExcelField(title = "手机号码", type = 1, align = 2, sort = 5)
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
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	@ExcelField(title = "付款渠道", type = 1, align = 2, sort = 8)
	public String getChlName() {
		return chlName;
	}

	public void setChlName(String chlName) {
		this.chlName = chlName;
	}

	@ExcelField(title = "付款方式", type = 1, align = 2, sort = 9)
	public String getTxTypeStr() {
		return txTypeStr;
	}

	public void setTxTypeStr(String txTypeStr) {
		this.txTypeStr = txTypeStr;
	}

	@ExcelField(title = "付款分类", type = 1, align = 2, sort = 10)
	public String getPayTypeStr() {
		return payTypeStr;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}

	@ExcelField(title = "状态", type = 1, align = 2, sort = 14)
	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	@ExcelField(title = "订单信息", type = 1, align = 2, sort = 12)
	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	
	
	
}
