package com.rongdu.loans.loan.option;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class LoanMarketCountOP implements Serializable {

	private static final long serialVersionUID = -6219847033510500874L;
	
	/**
	 * 催收员姓名
	 */
	private String userName;
	

	private String userId;
	
	private String searchStrat;
	
	private String searchEnd;
	
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

	public String getSearchStrat() {
		return searchStrat;
	}

	public void setSearchStrat(String searchStrat) {
		this.searchStrat = searchStrat;
	}

	public String getSearchEnd() {
		return searchEnd;
	}

	public void setSearchEnd(String searchEnd) {
		this.searchEnd = searchEnd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
