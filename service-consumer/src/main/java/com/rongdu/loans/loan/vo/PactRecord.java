package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 合同存证编码
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/11
 */
@Data
public class PactRecord implements Serializable {
    /**
     * 借款合同存证编码,建议拼接 Global.getConfig("ancun.domain") 该属性作为前缀
     */
    private String loanRecordNo;
    /**
     * 购物协议存证编码,建议拼接 Global.getConfig("ancun.domain") 该属性作为前缀
     */
    private String shoppingRecordNo;

    public PactRecord() {
    }

    public PactRecord(String loanRecordNo, String shoppingRecordNo) {
        this.loanRecordNo = loanRecordNo;
        this.shoppingRecordNo = shoppingRecordNo;
    }

    @Override
    public String toString() {
        return "PactRecord{" +
                "loanRecordNo='" + loanRecordNo + '\'' +
                ", shoppingRecordNo='" + shoppingRecordNo + '\'' +
                '}';
    }
}
