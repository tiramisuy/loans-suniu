package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

/**
 * Created by likang on 2017/7/24.
 */
public class TransactionRecordVO implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -1499661309431135951L;

    /**
     * 交易时间
     */
    private String txTime;
    /**
     * 交易类型
     */
    private String txTpye;
    /**
     * 交易金额
     */
    private String txAmount;

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }

    public String getTxTpye() {
        return txTpye;
    }

    public void setTxTpye(String txTpye) {
        this.txTpye = txTpye;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }
}
