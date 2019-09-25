package com.rongdu.loans.pay.op;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhangxiaolong on 2017/8/8.
 */
public class WithholdQueryOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 543689232460661348L;
    /**
     * 原始商户订单号（还款明细ID）
     * 商户提交的标识支付的唯一原订单号
     */
    @NotBlank(message = "原始商户订单号不能为空")
    private String origTransId;
    /**
     * 订单日期
     * 14 位定长。格式：年年年年月月日日时时分分秒秒
     */
    @NotBlank(message = "订单日期不能为空")
    private String origTradeDate;

    public String getOrigTransId() {
        return origTransId;
    }

    public void setOrigTransId(String origTransId) {
        this.origTransId = origTransId;
    }

    public String getOrigTradeDate() {
        return origTradeDate;
    }

    public void setOrigTradeDate(String origTradeDate) {
        this.origTradeDate = origTradeDate;
    }
}
