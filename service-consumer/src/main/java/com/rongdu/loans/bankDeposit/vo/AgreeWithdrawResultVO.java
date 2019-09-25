package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

/**
 * Created by likang on 2017/8/29.
 */
public class AgreeWithdrawResultVO implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -5778654381928931320L;

    /**
     * 电子账号
     */
    private String accountId;

    /**
     * 交易金额
     */
    private String txAmount;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }
}
