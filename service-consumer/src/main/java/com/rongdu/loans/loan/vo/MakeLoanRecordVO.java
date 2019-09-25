package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class MakeLoanRecordVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 8235346281875581011L;
    
    /**
     * 身份证四位尾号
     */
    private String idNoLastFour;
    
    /**
     * 贷款金额
     */
    private BigDecimal loanAmt;

	public String getIdNoLastFour() {
		return idNoLastFour;
	}

	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public void setIdNoLastFour(String idNoLastFour) {
		this.idNoLastFour = idNoLastFour;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
}
