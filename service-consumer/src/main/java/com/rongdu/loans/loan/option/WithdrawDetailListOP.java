package com.rongdu.loans.loan.option;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class WithdrawDetailListOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String userName;        // 真实姓名
    private String idNo;        // 用户证件号码
    private String mobile;        // 手机号码
    private Date applyTimeStart;        // 申请开始时间
    private Date applyTimeEnd;        // 申请结束时间
    private Date accountTimeStart;        // 到账开始时间
    private Date accountTimeEnd;        // 到账结束时间
    private Date sendTimeStart;        // 放款开始时间
    private Date sendTimeEnd;        // 放款结束时间
    private String txType;              //1自动还款，2手动还款
    private Integer status;              //状态状态
    private BigDecimal txAmt;              //提现金额
    private String productId; //产品id
    private String companyId;  //商户Id
    private String chlCode;              //放款渠道


    private Integer pageNo = 1;
    private Integer pageSize = 10;

    
    public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getApplyTimeStart() {
        return applyTimeStart;
    }

    public void setApplyTimeStart(Date applyTimeStart) {
        this.applyTimeStart = applyTimeStart;
    }

    public Date getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(Date applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public Date getAccountTimeStart() {
        return accountTimeStart;
    }

    public void setAccountTimeStart(Date accountTimeStart) {
        this.accountTimeStart = accountTimeStart;
    }

    public Date getAccountTimeEnd() {
        return accountTimeEnd;
    }

    public void setAccountTimeEnd(Date accountTimeEnd) {
        this.accountTimeEnd = accountTimeEnd;
    }

    public Date getSendTimeStart() {
        return sendTimeStart;
    }

    public void setSendTimeStart(Date sendTimeStart) {
        this.sendTimeStart = sendTimeStart;
    }

    public Date getSendTimeEnd() {
        return sendTimeEnd;
    }

    public void setSendTimeEnd(Date sendTimeEnd) {
        this.sendTimeEnd = sendTimeEnd;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTxAmt() {
        return txAmt;
    }

    public void setTxAmt(BigDecimal txAmt) {
        this.txAmt = txAmt;
    }

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}
    
}
