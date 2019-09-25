package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.enums.RepayMethodEnum;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class RepayTotalListVO implements Serializable {

	/**
	 * 合同编号
	 */
	private String contNo;
	/**
	 * 客户名称
	 */
	private String userName;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 贷款审批金额
	 */
	private BigDecimal approveAmt;
	/**
	 * 基准利率
	 */
	private BigDecimal basicRate;
	private String basicRateStr;
	/**
	 * 服务费率
	 */
	private BigDecimal servFeeRate;
	private String servFeeRateStr;
	/**
	 * 还款方式
	 */
	private Integer repayMethod;
	private String repayMethodStr;
	/**
	 * 期数
	 */
	private Integer thisTerm;
	private Integer contractTerm;
	/**
	 * 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	 */
	private BigDecimal totalAmount;
	/**
	 * 中介服务手续费
	 */
	private BigDecimal servFee;
	/**
	 * 开始时间
	 */
	private Date startDate;
	private String startDateStr;
	/**
	 * 应还本金
	 */
	private BigDecimal principal;
	/**
	 * 借款日期
	 */
	private Date loanStartDate;
	/**
	 * 结束日期
	 */
	private Date loanEndDate;
	private String loanEndDate1;
	/**
	 * 商户
	 */
	private String companyId;
	private String companyName;
	
	private String mouthRateStr;
	private String termRateStr;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}

	public BigDecimal getBasicRate() {
		return basicRate;
	}

	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
		this.basicRateStr = basicRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
	}

	public String getBasicRateStr() {
		return basicRateStr;
	}

	public void setBasicRateStr(String basicRateStr) {
		this.basicRateStr = basicRateStr;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
		this.servFeeRateStr = servFeeRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
	}

	public String getServFeeRateStr() {
		return servFeeRateStr;
	}

	public void setServFeeRateStr(String servFeeRateStr) {
		this.servFeeRateStr = servFeeRateStr;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
		if (5 == repayMethod) {
			this.repayMethodStr = RepayMethodEnum.getDesc(1);
		} else {
			this.repayMethodStr = RepayMethodEnum.getDesc(repayMethod);
		}
	}

	public String getRepayMethodStr() {
		return repayMethodStr;
	}

	public void setRepayMethodStr(String repayMethodStr) {
		this.repayMethodStr = repayMethodStr;
	}

	public Integer getThisTerm() {
		return thisTerm;
	}

	public void setThisTerm(Integer thisTerm) {
		this.thisTerm = thisTerm;
		/*this.contractTerm = thisTerm;*/
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		this.startDateStr = DateUtils.formatDate(DateUtils.addDay(this.startDate, 0));
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public String getLoanStartDate() {
		return DateUtils.formatDate(loanStartDate, null);
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public BigDecimal getLoanMoney() {
		return principal;
	}

	public String getSignDate() {
		return DateUtils.formatDate(loanStartDate, null);
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
		this.loanEndDate1 = DateUtils.formatDate(loanEndDate, "yyyy-MM-dd");
	}
	
	public String getLoanEndDate1() {
		return loanEndDate1;
	}

	public String getMouthRateStr() {
		return mouthRateStr;
	}

	public void setMouthRateStr(String mouthRateStr) {
		this.mouthRateStr = mouthRateStr;
	}

	public String getTermRateStr() {
		return termRateStr;
	}

	public void setTermRateStr(String termRateStr) {
		this.termRateStr = termRateStr;
	}

	public Integer getContractTerm() {
		return contractTerm;
	}

	public void setContractTerm(Integer contractTerm) {
		this.contractTerm = contractTerm;
	}
	
	/*public Integer getContractTerm() {
		if (contractTerm > 1) {
			this.contractTerm = contractTerm / 2;
		}
		return contractTerm;
	}*/
	
	
	
	
}
