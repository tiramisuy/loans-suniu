/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import java.math.BigDecimal;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 还款提醒Entity
 * 
 * @author liuliang
 * @version 2018-05-22
 */
public class LoanMarketCountVO extends BaseEntity<LoanMarketCountVO> {

	private static final long serialVersionUID = 1L;
	
	private String userName;

	//总分单数
	private Integer allNum;
	//出单数
	private Integer outNum;
	//出单率
	private BigDecimal outPersent;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getOutNum() {
		return outNum;
	}
	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}
	public BigDecimal getOutPersent() {
		return outPersent;
	}
	public void setOutPersent(BigDecimal outPersent) {
		this.outPersent = outPersent;
	}
	
}