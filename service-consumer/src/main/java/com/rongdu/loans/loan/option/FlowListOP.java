package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class FlowListOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6217468386813584730L;

	private String realName; // 真实姓名
	private String idNo; // 用户证件号码
	private String mobile; // 手机号码
	private BigDecimal txAmt; // 实还金额
	private String txType; // 付款方式
	private String payType;// 付款分类
	private String chlCode;// 付款渠道
	/**
	 * 状态：1成功 SUCCESS 0失败
	 */
	private String status;

	private Date repayTimeStart; // 还款时间
	private Date repayTimeEnd;
	
	private Integer remarkNo;

	private Integer pageNo = 1;
	private Integer pageSize = 10;

	private String termType;	//申请期限类型 1:14天；2:90天；3：184天；4：其他
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 放款来源
	 */
	private String withdrawalSource;
	
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public Date getRepayTimeStart() {
		return repayTimeStart;
	}

	public void setRepayTimeStart(Date repayTimeStart) {
		this.repayTimeStart = repayTimeStart;
	}

	public Date getRepayTimeEnd() {
		return repayTimeEnd;
	}

	public void setRepayTimeEnd(Date repayTimeEnd) {
		this.repayTimeEnd = repayTimeEnd;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getWithdrawalSource() {
		return withdrawalSource;
	}

	public void setWithdrawalSource(String withdrawalSource) {
		this.withdrawalSource = withdrawalSource;
	}

	public Integer getRemarkNo() {
		return remarkNo;
	}

	public void setRemarkNo(Integer remarkNo) {
		this.remarkNo = remarkNo;
	}


	
	
}
