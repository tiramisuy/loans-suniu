package com.rongdu.loans.cust.op;

import java.io.Serializable;
import java.sql.Date;


public class CustUserExportOP  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;

	private String mobile;

	private Integer status;

	private Integer approveResult;

	private Date approveBeginTime; // 查询开始时间

	private Date approveEndTime; // 查询结束时间

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
	}

	public Date getApproveBeginTime() {
		return approveBeginTime;
	}

	public void setApproveBeginTime(Date approveBeginTime) {
		this.approveBeginTime = approveBeginTime;
	}

	public Date getApproveEndTime() {
		return approveEndTime;
	}

	public void setApproveEndTime(Date approveEndTime) {
		this.approveEndTime = approveEndTime;
	}


}
