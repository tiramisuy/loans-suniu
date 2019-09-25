package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/8/17.
 */
public class UserRecordVO implements Serializable {

    private static final long serialVersionUID = -7472436330059264845L;

    private List<LoanRecordVO> loanRecords;

    private List<RiskResultVO> riskResults;

    public List<LoanRecordVO> getLoanRecords() {
        return loanRecords;
    }

    public void setLoanRecords(List<LoanRecordVO> loanRecords) {
        this.loanRecords = loanRecords;
    }

    public List<RiskResultVO> getRiskResults() {
        return riskResults;
    }

    public void setRiskResults(List<RiskResultVO> riskResults) {
        this.riskResults = riskResults;
    }
}
