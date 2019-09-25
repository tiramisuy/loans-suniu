package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SettlementChannelVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6294249411951608519L;

	/**
	 * 渠道结算id
	 */
	private Integer id;
	/**
	 * 渠道id
	 */
	private String channelId;
	/**
	 * 渠道结算注册人数
	 */
	private BigDecimal userSettlementCount;
	/**
	 * 渠道结算单价
	 */
	private BigDecimal settlementPrice;
	/**
	 * 渠道结算初始获客成本
	 */
	private BigDecimal userInitCost;
	/**
	 * 渠道结算最终获客成本
	 */
	private BigDecimal userFinalCost;
	/**
	 * 渠道结算时间
	 */
	private String countTime;
	/**
	 * 渠道结算更新时间
	 */
	private String updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public BigDecimal getUserSettlementCount() {
		return userSettlementCount;
	}
	public void setUserSettlementCount(BigDecimal userSettlementCount) {
		this.userSettlementCount = userSettlementCount;
	}
	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
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
	public String getCountTime() {
		return countTime;
	}
	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
