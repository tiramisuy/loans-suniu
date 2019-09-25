package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 用户旅游产品VO
 * @author likang
 *
 */
public class LoanTripProductListVO implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2943308870425637382L;
	
	private String applyId;          // 借款id

	private String userName;		//借款人姓名
	
	private String idNo;			//证件号码
	
	private String mobile;			//手机号码
	
	private String contNo;			//合同编号
	
	private BigDecimal approveAmt;		//审批金额
	
	private BigDecimal interest;		//利息
	
	private BigDecimal servFee;			//服务费
		
	private BigDecimal overdueFee;		//逾期管理费
	
	private String createTime;	//购买时间
	
	private String updateTime;	//发放时间
	
	private String approveTime;	  //审批时间

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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	
	
	
	
}
