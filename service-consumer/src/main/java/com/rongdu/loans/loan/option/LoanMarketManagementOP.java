package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.util.Date;

public class LoanMarketManagementOP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5768964472178600650L;

	private String productId;

	private String channel;

	private String mobile;

	private String idNo;

	private String userName;

	private String userId;

	private Integer status;

	private String applyTimeStart;

	private String applyTimeEnd;
	
	
	private String updateStart;
	
	private String updateEnd;

	private Integer isPush;
	private Integer pageNo = 1;
	private Integer pageSize = 10;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApplyTimeStart() {
		return applyTimeStart;
	}

	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}

	public String getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	public String getUpdateStart() {
		return updateStart;
	}

	public void setUpdateStart(String updateStart) {
		this.updateStart = updateStart;
	}

	public String getUpdateEnd() {
		return updateEnd;
	}

	public void setUpdateEnd(String updateEnd) {
		this.updateEnd = updateEnd;
	}

}
