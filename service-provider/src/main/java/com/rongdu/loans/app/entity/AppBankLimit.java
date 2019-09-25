package com.rongdu.loans.app.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 银行信息表映射数据实体
 * @author likang
 *
 */
public class AppBankLimit extends BaseEntity<AppBankLimit> {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	private String channel;		// 支付渠道
	private String isOpen;		// 开启状态
	private String bankNo;          // 银行编号
	private String bankName;		// 银行名称
	private String bankCode;		// 银行代码
	private String onceLimit;		// 单笔限额
	private String dayLimit;		// 单日限额
	private String monthLimit;		// 单月限额
	private Integer num;		// 绑卡数量
	
	public String getChannel() {
		return channel;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public String getBankName() {
		return bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public String getOnceLimit() {
		return onceLimit;
	}
	public String getDayLimit() {
		return dayLimit;
	}
	public String getMonthLimit() {
		return monthLimit;
	}
	public Integer getNum() {
		return num;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public void setOnceLimit(String onceLimit) {
		this.onceLimit = onceLimit;
	}
	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}
	public void setMonthLimit(String monthLimit) {
		this.monthLimit = monthLimit;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
}
