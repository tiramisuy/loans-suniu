package com.rongdu.loans.pay.op;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.rongdu.common.utils.DateUtils;

/**
 * Created by zhangxiaolong on 2017/8/8.
 */
public class BfWithholdOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 543689232460661348L;
    /**
     * 唯一订单号，8-20 位字母和数字，不可重复
     * 规则=WH（WITHHOLD）+纳秒数
     */
    @NotBlank(message = "商户订单号不能为空")
    private String transId = "WH" + System.nanoTime();
    /**
     * 	订单日期
     * 14 位定长,格式：年年年年月月日日时时分分秒秒
     */
    @NotBlank(message = "订单日期不能为空")
    private String tradeDate = DateUtils.getDate("yyyyMMddHHmmss");
    /**
     * 真实姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String realName;
    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证号码不能为空")
    private String idNo;
    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String mobile;
    /**
     * 银行代码
     */
    @NotBlank(message = "银行代码不能为空")
    private String bankCode;
    /**
     * 银行卡号
     */
    @NotBlank(message = "银行卡号不能为空")
    private String cardNo;
    /**
     * 银行卡有效期
     * 格式：YYMM 如：07月/18年则写成1807
     */
    private String validDate;
    /**
     * 卡安全码
     */
    private String validNo;
    /**
     * 交易金额:分
     */
    @NotBlank(message = "交易金额不能为空")
    private String txnAmt;
    

    private String remark;
    
    private String txType;
    
    private String bankName; //银行名称
    

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getValidNo() {
        return validNo;
    }

    public void setValidNo(String validNo) {
        this.validNo = validNo;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTradeDate() {
        return tradeDate;
    }

}
