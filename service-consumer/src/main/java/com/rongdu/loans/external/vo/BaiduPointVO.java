package com.rongdu.loans.external.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaiduPointVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4181170799193318670L;

	/**
	 * 经度
	 */
	private BigDecimal x;
	/**
	 * 纬度
	 */
	private BigDecimal y;

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}
}
