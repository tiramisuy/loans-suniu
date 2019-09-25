package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class XJDCountVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8031194985195619776L;

	/**
	 * 渠道
	 */
	private String channel;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 总注册人数
	 */
	private String totalReg;
	/**
	 * 14天产品进件量
	 */
	private String oneApply;
	/**
	 * 30天产品进件量
	 */
	private String threeApply;
	/**
	 * 总进件量
	 */
	private String totalApply;
	/**
	 * 总放款
	 */
	private String totalPay;
	/**
	 * 首贷放款
	 */
	private String firstPay;
	/**
	 * 总应还笔数
	 */
	private String totalRePay;
	/**
	 * 未还笔数
	 */
	private String unPay;
	/**
	 * 结算注册人数
	 */
	private String userSettlementCount;
	/**
	 * 结算单价
	 */
	private BigDecimal settlementPrice;
	/**
	 * 初始获客成本
	 */
	private BigDecimal userInitCost;
	/**
	 * 最终获客成本
	 */
	private BigDecimal userFinalCost;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getTotalReg() {
		return totalReg;
	}
	public void setTotalReg(String totalReg) {
		this.totalReg = totalReg;
	}
	public String getOneApply() {
		return oneApply;
	}
	public void setOneApply(String oneApply) {
		this.oneApply = oneApply;
	}
	public String getThreeApply() {
		return threeApply;
	}
	public void setThreeApply(String threeApply) {
		this.threeApply = threeApply;
	}
	public String getTotalApply() {
		return totalApply;
	}
	public void setTotalApply(String totalApply) {
		this.totalApply = totalApply;
	}
	public String getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}
	public String getFirstPay() {
		return firstPay;
	}
	public void setFirstPay(String firstPay) {
		this.firstPay = firstPay;
	}
	public String getTotalRePay() {
		return totalRePay;
	}
	public void setTotalRePay(String totalRePay) {
		this.totalRePay = totalRePay;
	}
	public String getUnPay() {
		return unPay;
	}
	public void setUnPay(String unPay) {
		this.unPay = unPay;
	}
	public String getUserSettlementCount() {
		return userSettlementCount;
	}
	public void setUserSettlementCount(String userSettlementCount) {
		this.userSettlementCount = userSettlementCount;
	}
	public BigDecimal getUserInitCost() {
		return userInitCost;
	}
	public void setUserInitCost(BigDecimal userInitCost) {
		this.userInitCost = userInitCost;
	}
	public BigDecimal getUserFinalCost() {
		return userFinalCost;
	}
	public void setUserFinalCost(BigDecimal userFinalCost) {
		this.userFinalCost = userFinalCost;
	}
	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	
}
