package com.rongdu.loans.app.vo;

import java.io.Serializable;

/**
 * Created by likang on 2017/6/27.
 */
public class AppBanksVO  implements Serializable {
	
    /**
     * 序列号
     */
    private static final long serialVersionUID = 5457583037925187766L;

    private String bankNo;          // 银行编号
    private String bankCode;		// 银行代码
    private String bankName;		// 银行名称
    private String onceLimit;		// 单笔限额
    private String dayLimit;		// 单日限额
    private String monthLimit;		// 单月限额

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOnceLimit() {
        return onceLimit;
    }

    public void setOnceLimit(String onceLimit) {
        this.onceLimit = onceLimit;
    }

    public String getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(String dayLimit) {
        this.dayLimit = dayLimit;
    }

    public String getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(String monthLimit) {
        this.monthLimit = monthLimit;
    }

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
}
