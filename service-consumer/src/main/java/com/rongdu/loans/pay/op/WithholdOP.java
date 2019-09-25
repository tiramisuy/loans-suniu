package com.rongdu.loans.pay.op;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.rongdu.common.utils.DateUtils;

/**
 * Created by zhangxiaolong on 2017/8/8.
 */
public class WithholdOP implements Serializable {

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
     *贷款申请编号
     */
    @NotBlank(message = "贷款申请编号不能为空")
    private String applyId;
    /**
     *合同编号
     */
    @NotBlank(message = "合同编号不能为空")
    private String contNo;
    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
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
    
    private String repayPlanItemId;

    /**
     * 购物券id(多个逗号分隔)
     */
    private String couponId;

    /**
     * 绑定协议号
     */
    private String bindId;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
