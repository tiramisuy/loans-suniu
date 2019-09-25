package com.rongdu.loans.loan.option;

import java.io.Serializable;



/**
 * 购物券OP
 * @author fy
 *
 */
public class ShoppedCouponOP implements Serializable {

	private static final long serialVersionUID = 1862730635272389450L;
	/**
	 * 客户姓名
	 */
	private String userName;		
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 订单编号
	 */
	private String applyId;
	
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
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}	

}
