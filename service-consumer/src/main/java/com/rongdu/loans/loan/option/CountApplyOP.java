package com.rongdu.loans.loan.option;

import java.io.Serializable;
/**
 * 
 * @author likang
 *
 */
public class CountApplyOP implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5829917726881763637L;

	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 查询起始日期-yyyMMdd
	 */
	private Integer beginDate;
	/**
	 * 查询截止日期-yyyMMdd
	 */
	private Integer endDate;
	
	private Integer applyStatus;

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Integer beginDate) {
		this.beginDate = beginDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}
}
