package com.rongdu.loans.tongrong.op;

import java.io.Serializable;

public class TongrongPayLogOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String mobile;
	private String payStatus;
	
	private String expectStart; // 应该还款时间
	private String expectEnd;
	
	/*private String kdCreateCode;//创建订单状态
*/
	private Integer pageNo = 1;
	private Integer pageSize = 10;

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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

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

	public String getExpectStart() {
		return expectStart;
	}

	public void setExpectStart(String expectStart) {
		this.expectStart = expectStart;
	}

	public String getExpectEnd() {
		return expectEnd;
	}

	public void setExpectEnd(String expectEnd) {
		this.expectEnd = expectEnd;
	}

	
	

}
