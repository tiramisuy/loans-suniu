/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongrong.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 通融放款记录表Entity
 * @author fuyuan
 * @version 2018-11-19
 */
public class TongrongPayLogVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	  *借款订单
	  */
	private String applyId;		
	/**
	  *用户id
	  */
	private String user;		
	/**
	  *用户名
	  */
	private String userName;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *身份证号
	  */
	private String idNo;		
	/**
	  *银行编码
	  */
	private String bankCode;		
	/**
	  *开户行
	  */
	private String bankName;		
	/**
	  *银行卡号
	  */
	private String cardNo;		
	/**
	  *放款金额
	  */
	private BigDecimal payAmt;		
	/**
	  *放款时间
	  */
	private Date payTime;		
	/**
	  *成功时间
	  */
	private Date paySuccTime;		
	/**
	  *交易号
	  */
	private String requestNo;		
	/**
	  *放款失败次数
	  */
	private Integer payFailCount;		
	/**
	  *放款状态,0=成功,1=失败,2=处理中,3=取消
	  */
	private Integer payStatus;		
	/**
	  *付款返回信息
	  */
	private String payMsg;		
	/**
	  *合同编号
	  */
	private String contractId;		
	/**
	  *合同地址
	  */
	private String contractUrl;		
	/**
	  *签章结果描述
	  */
	private String contractMsg;		
	/**
	 * 	备注
	 */
	private String remark;
	
	/**
	 * 	创建者userId
	 */
	private String createBy;
	/**
	 * 	创建日期
	 */
	private Date createTime;
	/**
	 * 	最后修改者userId
	 */
	private String updateBy;
	/**
	 *  更新日期
	 */
	private Date updateTime;	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TongrongPayLogVO() {
		super();
	}
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	
	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Date getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccTime(Date paySuccTime) {
		this.paySuccTime = paySuccTime;
	}
	
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	
	public Integer getPayFailCount() {
		return payFailCount;
	}

	public void setPayFailCount(Integer payFailCount) {
		this.payFailCount = payFailCount;
	}
	
	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayMsg() {
		return payMsg;
	}

	public void setPayMsg(String payMsg) {
		this.payMsg = payMsg;
	}
	
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
	public String getContractMsg() {
		return contractMsg;
	}

	public void setContractMsg(String contractMsg) {
		this.contractMsg = contractMsg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}