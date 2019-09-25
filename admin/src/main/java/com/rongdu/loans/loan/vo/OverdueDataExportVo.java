package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.utils.excel.annotation.ExcelField;

public class OverdueDataExportVo implements Serializable{

	private static final long serialVersionUID = -3127769896330757484L;

	/**
     * 待还款ID
     */
    private String id;
    /**
     * 合同编号
     */
    private String contNo;
    /**
     * 催收人
     */
    private String operatorName;
    /**
     * 客户名称
     */
    private String userName;
    /**
     * 证件号码
     */
    private String idNo;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 借款本金
     */
    private BigDecimal approveAmt;
    /**
     * 借款期限(按天)
     */
    private Integer approveTerm;
    /**
     * 还款方式
     */
    private String repayMethodStr;
    /**
     * 基准利率
     */
    private String basicRateStr;
    /**
     * 期数
     */
    private Integer thisTerm;
    /**
     * 服务费率
     */
    private String servFeeRateStr;
    /**
     * 中介服务手续费
     */
    private BigDecimal servFee;
    /**
     * 已借天数
     */
    private Integer borrow;
    /**
     * 逾期天数
     */
    private Integer overdue;
    /**
     * 逾期管理费
     */
    private BigDecimal overdueFee;
    /**
     * 减免费用
     */
    private BigDecimal deduction;
    /**
     * 应还总额
     */
    private BigDecimal totalAmount;
    /**
     * 实际还款金额
     */
    private BigDecimal actualRepayAmt; 
    /**
	 * 差额
	 */
	private String subAmt;
    /**
     * 借款日期
     */
    private Date loanStartDate;
    /**
     * 应还款日期
     */
    private Date repayDate;
    /**
     * 实际还款时间
     */
    private Date actualRepayTime;
    /**
     * 逾期次数
     */
    private Integer overdueTimes;
    /**
     * 最长逾期天数
     */
    private Integer maxOverdueDays;
    /**
     * 催收结果
     */
    private String result;
    
    /**
	 *渠道 
	 */
	private String channelId;
    
    @ExcelField(title="待还款ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ExcelField(title="合同编号", type=1, align=2, sort=3)
	public String getContNo() {
		return contNo;
	}
	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	
	@ExcelField(title="催收人", type=1, align=2, sort=4)
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	@ExcelField(title="客户名称", type=1, align=2, sort=5)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="证件号码", type=1, align=2, sort=6)
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@ExcelField(title="手机号码", type=1, align=2, sort=7)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="产品名称", type=1, align=2, sort=8)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="借款本金", type=1, align=2, sort=10)
	public BigDecimal getApproveAmt() {
		return approveAmt;
	}
	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}
	
	@ExcelField(title="借款期限(按天)", type=1, align=2, sort=11)
	public Integer getApproveTerm() {
		return approveTerm;
	}
	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}
	
	@ExcelField(title="还款方式", type=1, align=2, sort=12)
	public String getRepayMethodStr() {
		return repayMethodStr;
	}
	public void setRepayMethodStr(String repayMethodStr) {
		this.repayMethodStr = repayMethodStr;
	}
	
	@ExcelField(title="基准利率", type=1, align=2, sort=13)
	public String getBasicRateStr() {
		return basicRateStr;
	}
	public void setBasicRateStr(String basicRateStr) {
		this.basicRateStr = basicRateStr;
	}
	
	@ExcelField(title="当前期数", type=1, align=2, sort=14)
	public Integer getThisTerm() {
		return thisTerm;
	}
	public void setThisTerm(Integer thisTerm) {
		this.thisTerm = thisTerm;
	}
	
	@ExcelField(title="服务费率", type=1, align=2, sort=15)
	public String getServFeeRateStr() {
		return servFeeRateStr;
	}
	public void setServFeeRateStr(String servFeeRateStr) {
		this.servFeeRateStr = servFeeRateStr;
	}
	
	@ExcelField(title="服务费", type=1, align=2, sort=16)
	public BigDecimal getServFee() {
		return servFee;
	}
	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}
	
	@ExcelField(title="已借天数", type=1, align=2, sort=17)
	public Integer getBorrow() {
		return borrow;
	}
	public void setBorrow(Integer borrow) {
		this.borrow = borrow;
	}
	
	@ExcelField(title="逾期天数", type=1, align=2, sort=18)
	public Integer getOverdue() {
		return overdue;
	}
	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}
	
	@ExcelField(title="逾期管理费", type=1, align=2, sort=19)
	public BigDecimal getOverdueFee() {
		return overdueFee;
	}
	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}
	
	@ExcelField(title="减免费用", type=1, align=2, sort=20)
	public BigDecimal getDeduction() {
		return deduction;
	}
	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}
	
	@ExcelField(title="应还总额", type=1, align=2, sort=21)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@ExcelField(title="实际还款金额", type=1, align=2, sort=22)
	public BigDecimal getActualRepayAmt() {
		return actualRepayAmt;
	}
	public void setActualRepayAmt(BigDecimal actualRepayAmt) {
		this.actualRepayAmt = actualRepayAmt;
	}
	
	@ExcelField(title="差额", type=1, align=2, sort=23)
	public String getSubAmt() {
		return subAmt;
	}
	public void setSubAmt(String subAmt) {
		this.subAmt = subAmt;
	}
	
	@ExcelField(title="借款日期", type=1, align=2, sort=24)
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	
	@ExcelField(title="应还日期", type=1, align=2, sort=25)
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	@ExcelField(title="实际还款日期", type=1, align=2, sort=26)
	public Date getActualRepayTime() {
		return actualRepayTime;
	}
	public void setActualRepayTime(Date actualRepayTime) {
		this.actualRepayTime = actualRepayTime;
	}
	
	//@ExcelField(title="逾期次数", type=1, align=2, sort=27)
	public Integer getOverdueTimes() {
		return overdueTimes;
	}
	public void setOverdueTimes(Integer overdueTimes) {
		this.overdueTimes = overdueTimes;
	}
	
	//@ExcelField(title="最长逾期天数", type=1, align=2, sort=28)
	public Integer getMaxOverdueDays() {
		return maxOverdueDays;
	}
	public void setMaxOverdueDays(Integer maxOverdueDays) {
		this.maxOverdueDays = maxOverdueDays;
	}
	
	@ExcelField(title="催收结果", type=1, align=2, sort=29)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@ExcelField(title="渠道", type=1, align=2, sort=9)
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

    
}
