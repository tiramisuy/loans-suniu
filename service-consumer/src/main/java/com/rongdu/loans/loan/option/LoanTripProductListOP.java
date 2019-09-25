package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.util.Date;


/**
 * 
* @Description:  旅游产品OP
* @author: 饶文彪
* @date 2018年7月12日
 */
public class LoanTripProductListOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2749196106111115771L;
	
	private String applyId;     //借款申请id
	
	private String userName;
	
	private String mobile;
	
	private String approveStart;	//审批开始时间
	
	private String approveEnd;
	
	
	private String payStart;		//支付开始时间
	
	private String payEnd;
	
	private String issueStart;	//发放开始时间
	
	private String issueEnd;
	
	
	
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

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
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

	public String getApproveStart() {
		return approveStart;
	}

	public void setApproveStart(String approveStart) {
		this.approveStart = approveStart;
	}

	public String getApproveEnd() {
		return approveEnd;
	}

	public void setApproveEnd(String approveEnd) {
		this.approveEnd = approveEnd;
	}

	public String getPayStart() {
		return payStart;
	}

	public void setPayStart(String payStart) {
		this.payStart = payStart;
	}

	public String getPayEnd() {
		return payEnd;
	}

	public void setPayEnd(String payEnd) {
		this.payEnd = payEnd;
	}

	public String getIssueStart() {
		return issueStart;
	}

	public void setIssueStart(String issueStart) {
		this.issueStart = issueStart;
	}

	public String getIssueEnd() {
		return issueEnd;
	}

	public void setIssueEnd(String issueEnd) {
		this.issueEnd = issueEnd;
	}

	
	
	
	
	

}
