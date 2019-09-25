/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.utils.excel.annotation.ExcelField;

/**
 * 还款提醒Entity
 * 
 * @author liuliang
 * @version 2018-05-22
 */
public class LoanMarketManagementVO extends BaseEntity<LoanMarketManagementVO> {

	private static final long serialVersionUID = 1L;
	/**
	 * 申请信息id
	 */
	private String id;
	
	private String marketId;
	/**
	 * 被分配的用户id
	 */
	private String contNo;
	/**
	 * 提醒人员
	 */
	private String userName;

	private String userId;

	/**
	 * 提醒时间
	 */
	private String applyDate;

	private Date applyTime;
	
	private Date approveTime;

	private String idNo;
	private String mobile;
	private String productId;
	private String productName;
	private String applyAmt;
	private String approveAmt;
	private String applyTerm;
	private String approveTerm;
	private String basicRate;
	private String actualRate;
	private String interest;
	private String servFee;
	private String servFeeRate;
	private String overdueRate;
	private String overdueFee;
	private String repayFreq;
	private String repayUnit;
	private String term;
	private String applyStatus;
	private String status;
	private String statusStr;
	private String companyId;
	
	private String opeatorName;
	
	private Integer isWarn;
	
	private Integer isPush;
	
	private String isPushStr;
	
	private String warnTime;
	
	private Date allotDate;
	
	

	public Date getAllotDate() {
		return allotDate;
	}

	public void setAllotDate(Date allotDate) {
		this.allotDate = allotDate;
	}

	@ExcelField(title = "拨打时间", type = 1, align = 2, sort = 5)
	public String getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(String warnTime) {
		this.warnTime = warnTime;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public LoanMarketManagementVO() {
		super();
	}

	public LoanMarketManagementVO(String id) {
		super(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	@ExcelField(title = "客户姓名", type = 1, align = 2, sort = 1)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@ExcelField(title = "电话", type = 1, align = 2, sort = 2)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getApplyAmt() {
		return applyAmt;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setApplyAmt(String applyAmt) {
		this.applyAmt = applyAmt;
	}

	public String getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(String approveAmt) {
		this.approveAmt = approveAmt;
	}

	public String getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(String applyTerm) {
		this.applyTerm = applyTerm;
	}

	public String getApproveTerm() {
		return approveTerm;
	}

	public void setApproveTerm(String approveTerm) {
		this.approveTerm = approveTerm;
	}

	public String getBasicRate() {
		return basicRate;
	}

	public void setBasicRate(String basicRate) {
		this.basicRate = basicRate;
	}

	public String getActualRate() {
		return actualRate;
	}

	public void setActualRate(String actualRate) {
		this.actualRate = actualRate;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getServFee() {
		return servFee;
	}

	public void setServFee(String servFee) {
		this.servFee = servFee;
	}

	public String getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(String servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public String getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(String overdueRate) {
		this.overdueRate = overdueRate;
	}

	public String getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(String overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getRepayFreq() {
		return repayFreq;
	}

	public void setRepayFreq(String repayFreq) {
		this.repayFreq = repayFreq;
	}

	public String getRepayUnit() {
		return repayUnit;
	}

	public void setRepayUnit(String repayUnit) {
		this.repayUnit = repayUnit;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOpeatorName() {
		return opeatorName;
	}

	public void setOpeatorName(String opeatorName) {
		this.opeatorName = opeatorName;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public Integer getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(Integer isWarn) {
		this.isWarn = isWarn;
	}

	@ExcelField(title = "时间", type = 1, align = 2, sort = 3)
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
		if(isPush ==1){
			this.isPushStr ="永久拒绝营销";
		}else if(isPush ==2){
			this.isPushStr ="无人接听";
		}
		else if(isPush ==3){
			this.isPushStr ="考虑";
		}
		else if(isPush ==4){
			this.isPushStr ="需要";
		}
		else if(isPush ==5){
			this.isPushStr ="不需要";
		}
		else if(isPush ==6){
			this.isPushStr ="第三方接听";
		}
	}

	@ExcelField(title = "拨打情况", type = 1, align = 2, sort = 4)
	public String getIsPushStr() {
		return isPushStr;
	}

	public void setIsPushStr(String isPushStr) {
		this.isPushStr = isPushStr;
	}

	
}