package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by likang on 2017/7/24.
 */
public class TransactionListVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -7378701406865927016L;

    /**
     * 交易条数
     */
    private int total;
    /**
     * 交易明细列表
     */
    private List<AccountDetailVO>  rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AccountDetailVO> getRows() {
        return rows;
    }

    public void setRows(List<AccountDetailVO> rows) {
        this.rows = rows;
    }
}
