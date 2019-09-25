package com.rongdu.loans.loan.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 投复利发标参数 Created by wy 2017/10/20
 */
public class TFLSmartAssetDTO implements Serializable {

	private static final long serialVersionUID = -3153696353914440251L;

	// 贷款标题
	private String title;

	// 贷款金额
	private double amount;

	// 分期单位
	private int deadline;

	// 年利率
	private BigDecimal apr;

	// 贷款方式（ m等额本息 i 先息后本）
	private String repay_method;

	// 贷款资料
	private String description;

	// 用户id
	private int uid;
	// 贷款天数
	private int days;

	// 分期类型
	private String deadline_type;

	private BigDecimal chargefortrouble;

	// 信用 1 抵押 2 担保标3 质押 5 车保宝6
	private int type;

	// 标的产品类型
	private String v;
	// 借款用途
	private String purpose;

	public BigDecimal getChargefortrouble() {
		return chargefortrouble;
	}

	public void setChargefortrouble(BigDecimal chargefortrouble) {
		this.chargefortrouble = chargefortrouble;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public String getRepay_method() {
		return repay_method;
	}

	public void setRepay_method(String repay_method) {
		this.repay_method = repay_method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getDeadline_type() {
		return deadline_type;
	}

	public void setDeadline_type(String deadline_type) {
		this.deadline_type = deadline_type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
