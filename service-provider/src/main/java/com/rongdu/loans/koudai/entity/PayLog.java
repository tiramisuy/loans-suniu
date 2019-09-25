/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.koudai.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 口袋放款日志Entity
 * 
 * @author liuzhuang
 * @version 2018-09-19
 */
public class PayLog extends BaseEntity<PayLog> {

	private static final long serialVersionUID = 1L;
	/**
	 * apply_id
	 */
	private String applyId;
	/**
	 * user_id
	 */
	private String userId;
	/**
	 * user_name
	 */
	private String userName;
	/**
	 * mobile
	 */
	private String mobile;
	/**
	 * idNo
	 */
	private String idNo;
	/**
	 * bank_code
	 */
	private String bankCode;
	/**
	 * bank_name
	 */
	private String bankName;
	/**
	 * card_no
	 */
	private String cardNo;
	/**
	 * 放款金额
	 */
	private BigDecimal payAmt;
	/**
	 * 放款时间
	 */
	private Date payTime;
	/**
	 * 成功时间
	 */
	private Date paySuccTime;
	/**
	 * 聚宝放款订单号，固定30位
	 */
	private String payOrderId;
	/**
	 * 放款失败次数
	 */
	private Integer payFailCount;
	/**
	 * 放款状态,0=成功,1=失败,2=处理中，3=取消
	 */
	private Integer payStatus;
	
	/**
	 * 是否提现 1:已提现 0:未提现,2提现中
	 */
	private Integer withdrawStatus;
	
	/**
	 * 口袋用户id(最长9位)
	 */
	private Integer kdPayUserId;
	/**
	 * 第三方通道编号
	 */
	private Integer kdPayThirdPlatform;
	/**
	 * 口袋订单号
	 */
	private String kdPayOrderId;
	/**
	 * kd_pay_code
	 */
	private Integer kdPayCode;
	/**
	 * kd_pay_msg
	 */
	private String kdPayMsg;
	/**
	 * 创建订单id
	 */
	private String kdCreateOrderId;
	/**
	 * kd_create_code
	 */
	private Integer kdCreateCode;
	/**
	 * kd_create_msg
	 */
	private String kdCreateMsg;

	public PayLog() {
		super();
	}

	public PayLog(String id) {
		super(id);
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccTime(Date paySuccTime) {
		this.paySuccTime = paySuccTime;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public Integer getPayFailCount() {
		return payFailCount;
	}

	public void setPayFailCount(Integer payFailCount) {
		this.payFailCount = payFailCount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getKdPayUserId() {
		return kdPayUserId;
	}

	public void setKdPayUserId(Integer kdPayUserId) {
		this.kdPayUserId = kdPayUserId;
	}

	public Integer getKdPayThirdPlatform() {
		return kdPayThirdPlatform;
	}

	public void setKdPayThirdPlatform(Integer kdPayThirdPlatform) {
		this.kdPayThirdPlatform = kdPayThirdPlatform;
	}

	public String getKdPayOrderId() {
		return kdPayOrderId;
	}

	public void setKdPayOrderId(String kdPayOrderId) {
		this.kdPayOrderId = kdPayOrderId;
	}

	public Integer getKdPayCode() {
		return kdPayCode;
	}

	public void setKdPayCode(Integer kdPayCode) {
		this.kdPayCode = kdPayCode;
	}

	public String getKdPayMsg() {
		return kdPayMsg;
	}

	public void setKdPayMsg(String kdPayMsg) {
		this.kdPayMsg = kdPayMsg;
	}

	public String getKdCreateOrderId() {
		return kdCreateOrderId;
	}

	public void setKdCreateOrderId(String kdCreateOrderId) {
		this.kdCreateOrderId = kdCreateOrderId;
	}

	public Integer getKdCreateCode() {
		return kdCreateCode;
	}

	public void setKdCreateCode(Integer kdCreateCode) {
		this.kdCreateCode = kdCreateCode;
	}

	public String getKdCreateMsg() {
		return kdCreateMsg;
	}

	public void setKdCreateMsg(String kdCreateMsg) {
		this.kdCreateMsg = kdCreateMsg;
	}

	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}
	
	

}