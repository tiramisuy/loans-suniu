package com.rongdu.loans.bankDeposit.option;

import java.io.Serializable;

/**
 * 免密提现参数对象类
 * @author likang
 * @version 2017-08-25
 */
public class AgreeWithdrawOP implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6093620270545751434L;
	
	/**
	 * 签约订单号
	 *    预约提现签约订单号
	 */
	private String contOrderId;

	/**
	 * 电子账号
	 */
	private String accountId;
	
//	/**
//	 * 证件类型
//	 */
//	private String idType;
	
	/**
	 * 证件号码
	 */
	private String idNo;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 银行卡号
	 */
	private String cardNo;
	
	/**
	 * 交易金额
	 */
	private String txAmount;

	/**
	 * 手续费
	 */
	private String txFee;
	
//	/**
//	 * 路由代码 （选填）
//	 *    {0-本行通道; 1-银联通道; 2-人行通道; 空-自动选择}
//	 */
//	private String routeCode;
//	
//	/**
//	 * 绑定银行联行号（选填）
//	 *       人民银行分配的12位联行号
//	 *       routeCode=2，必输;
//	 *       或者routeCode为空，但交易金额>20万，必输
//	 */
//	private String cardBankCnaps;
//	
//	/**
//	 * 绑定银行代码（选填）
//	 */
//	private String cardBankCode;
//	
//	/**
//	 * 绑定银行中文名称（选填）
//	 */
//	private String cardBankNameCn;
//	
//	/**
//	 * 绑定银行英文名称（选填）
//	 *     绑定的银行卡对应的银行英文名称缩写
//	 */
//	private String cardBankNameEn;
//	
//	/**
//	 * 绑定银行卡开户省份（选填）
//	 */
//	private String cardBankProvince;
//	
//	/**
//	 * 绑定银行卡开户城市（选填）
//	 */
//	private String cardBankCity;

	public String getContOrderId() {
		return contOrderId;
	}

	public String getAccountId() {
		return accountId;
	}

//	public String getIdType() {
//		return idType;
//	}

	public String getIdNo() {
		return idNo;
	}

	public String getName() {
		return name;
	}

	public String getMobile() {
		return mobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public String getTxAmount() {
		return txAmount;
	}

	public String getTxFee() {
		return txFee;
	}

//	public String getRouteCode() {
//		return routeCode;
//	}
//
//	public String getCardBankCnaps() {
//		return cardBankCnaps;
//	}
//
//	public String getCardBankCode() {
//		return cardBankCode;
//	}
//
//	public String getCardBankNameCn() {
//		return cardBankNameCn;
//	}
//
//	public String getCardBankNameEn() {
//		return cardBankNameEn;
//	}
//
//	public String getCardBankProvince() {
//		return cardBankProvince;
//	}
//
//	public String getCardBankCity() {
//		return cardBankCity;
//	}

	public void setContOrderId(String contOrderId) {
		this.contOrderId = contOrderId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

//	public void setIdType(String idType) {
//		this.idType = idType;
//	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setTxAmount(String txAmount) {
		this.txAmount = txAmount;
	}

	public void setTxFee(String txFee) {
		this.txFee = txFee;
	}

//	public void setRouteCode(String routeCode) {
//		this.routeCode = routeCode;
//	}
//
//	public void setCardBankCnaps(String cardBankCnaps) {
//		this.cardBankCnaps = cardBankCnaps;
//	}
//
//	public void setCardBankCode(String cardBankCode) {
//		this.cardBankCode = cardBankCode;
//	}
//
//	public void setCardBankNameCn(String cardBankNameCn) {
//		this.cardBankNameCn = cardBankNameCn;
//	}
//
//	public void setCardBankNameEn(String cardBankNameEn) {
//		this.cardBankNameEn = cardBankNameEn;
//	}
//
//	public void setCardBankProvince(String cardBankProvince) {
//		this.cardBankProvince = cardBankProvince;
//	}
//
//	public void setCardBankCity(String cardBankCity) {
//		this.cardBankCity = cardBankCity;
//	}
}
