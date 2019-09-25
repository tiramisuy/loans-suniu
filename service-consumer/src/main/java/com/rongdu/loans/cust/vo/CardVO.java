package com.rongdu.loans.cust.vo;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
public class CardVO implements Serializable {
    private static final long serialVersionUID = -7472436330059264845L;

    /**
     * CustUser 信息
     */
    private String id;          //id
    private String realName;        // 真实姓名
    private String accountId;    //电子账户
    private String bankCode;    //银行代码
    private String bankName;    //银行
    private String cardNo;    //银行卡号



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

}
