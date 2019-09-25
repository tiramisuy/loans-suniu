package com.rongdu.loans.loan.vo;

import java.io.Serializable;

public class FeeCeseVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -2471696880877683460L;
    
    /**
     * 贷款金额区间
     */
    private String loanAmtRange;
    
    /**
     * 贷款期限区间
     */
    private String loanTermRange;
    
    /**
     * 服务费率
     */
    private String servFeeRate;
    
    /**
     * 提前还款费率
     */
    private String prepayFeeRate;

    /**
     * 逾期违约费费率
     */
    private String defaultFeeRate;

    /**
     * 逾期管理费（元/日）
     */
    private String overdueFee;

	public String getLoanAmtRange() {
		return loanAmtRange;
	}

	public String getLoanTermRange() {
		return loanTermRange;
	}

	public String getServFeeRate() {
		return servFeeRate;
	}

	public String getPrepayFeeRate() {
		return prepayFeeRate;
	}

	public String getDefaultFeeRate() {
		return defaultFeeRate;
	}

	public String getOverdueFee() {
		return overdueFee;
	}

	public void setLoanAmtRange(String loanAmtRange) {
		this.loanAmtRange = loanAmtRange;
	}

	public void setLoanTermRange(String loanTermRange) {
		this.loanTermRange = loanTermRange;
	}

	public void setServFeeRate(String servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public void setPrepayFeeRate(String prepayFeeRate) {
		this.prepayFeeRate = prepayFeeRate;
	}

	public void setDefaultFeeRate(String defaultFeeRate) {
		this.defaultFeeRate = defaultFeeRate;
	}

	public void setOverdueFee(String overdueFee) {
		this.overdueFee = overdueFee;
	}
}
