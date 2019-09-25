package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.utils.excel.annotation.ExcelField;

/**
 * Created by fy
 */
public class ApplyAuditPassReportVO implements Serializable {
	
	private String userName;	// 客户名称
	private String mobile;		// 手机号码
	private String productName;		// 产品名称
	private BigDecimal approveAmt;  // 贷款审批金额
	private Integer approveTerm; //审批期限
	private String repayMethodStr;
	private Integer term;//还款期数
	private String basicRateStr;
	private String companyName; //商户名称
	private Date applyTime;	// 申请时间
	private Date time;	// 审核时间
	private String approverName;//审核人员
	private String statusStr;
	
	@ExcelField(title="客户姓名", type=1, align=2, sort=1)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="手机号码", type=1, align=2, sort=2)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="产品名称", type=1, align=2, sort=3)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="贷款审批金额(元)", type=1, align=2, sort=4)
	public BigDecimal getApproveAmt() {
		return approveAmt;
	}
	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}
	
	@ExcelField(title="审批期限(天)", type=1, align=2, sort=5)
	public Integer getApproveTerm() {
		return approveTerm;
	}
	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}
	
	@ExcelField(title="还款方式", type=1, align=2, sort=6)
	public String getRepayMethodStr() {
		return repayMethodStr;
	}
	public void setRepayMethodStr(String repayMethodStr) {
		this.repayMethodStr = repayMethodStr;
	}
	
	@ExcelField(title="还款期数", type=1, align=2, sort=7)
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	
	@ExcelField(title="基准利率(年化)", type=1, align=2, sort=8)
	public String getBasicRateStr() {
		return basicRateStr;
	}
	public void setBasicRateStr(String basicRateStr) {
		this.basicRateStr = basicRateStr;
	}
	
	@ExcelField(title="(商户/门店)", type=1, align=2, sort=9)
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="申请时间", type=1, align=2, sort=10)
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@ExcelField(title="审核时间", type=1, align=2, sort=11)
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	@ExcelField(title="审核人", type=1, align=2, sort=12)
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	@ExcelField(title="订单状态", type=1, align=2, sort=13)
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	
	
}
