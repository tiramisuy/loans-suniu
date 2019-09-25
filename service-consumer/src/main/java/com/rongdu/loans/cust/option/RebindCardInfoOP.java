package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2017/6/27.
 */
public class RebindCardInfoOP implements Serializable {
    // 序列号
    private static final long serialVersionUID = -5360657633358261437L;

    /**
     * 真实姓名
     */
    @NotBlank(message="真实姓名不能为空")
    private String trueName;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号
     */
    @NotBlank(message="证件号不能为空")
    private String idNo;

    /**
     *  银行编号
     */
    @NotBlank(message="银行编号不能为空")
    private String bankCode;

    /**
     * 银行卡号
     */
    @NotBlank(message="银行卡号不能为空")
    private String cardNo; // 银行卡号


    /**
     * 账户
     */
    @NotBlank(message="账户不能为空")
    private String account;

    /**
     * 进件来源（1-PC, 2-ios, 3-android,4-h5）
     */
    @NotBlank(message="进件来源不能为空")
    @Pattern(regexp="1|2|3|4",message="消息来源类型有误")
    private String source;	
    
    /**
     * 用户id
     */
    private String userId;
    
	public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
