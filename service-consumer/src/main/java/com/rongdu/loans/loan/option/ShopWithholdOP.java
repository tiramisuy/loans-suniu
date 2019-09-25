package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/8/8.
 */
public class ShopWithholdOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 543689232460661348L;

	private String id;
	/**
	 * 申请Id
	 */
	private String applyId;
	/**
	  *扣款用户ID
	  */
	private String custUserId;
	/**
	 * 代扣次数
	 */
	private Integer withholdNumber;
	/**
	 * 代扣时间
	 */
	private Date withholdTime;
	/**
	 * 代扣状态
	 */
	private Integer withholdStatus;
	/**
	 * 代扣金额
	 */
	private BigDecimal withholdFee;
	
	
	private String realName;
	
	private String idNo;
	
	private String mobile;
	
	private String searchStrat;
	
	private String searchEnd;
	
	private String remake;
	

	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	private Integer pageNo = 1;

	private Integer pageSize = 10;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getWithholdNumber() {
		return withholdNumber;
	}

	public void setWithholdNumber(Integer withholdNumber) {
		this.withholdNumber = withholdNumber;
	}

	public Date getWithholdTime() {
		return withholdTime;
	}

	public void setWithholdTime(Date withholdTime) {
		this.withholdTime = withholdTime;
	}

	public Integer getWithholdStatus() {
		return withholdStatus;
	}

	public void setWithholdStatus(Integer withholdStatus) {
		this.withholdStatus = withholdStatus;
	}

	public BigDecimal getWithholdFee() {
		return withholdFee;
	}

	public void setWithholdFee(BigDecimal withholdFee) {
		this.withholdFee = withholdFee;
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

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
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

	public String getSearchStrat() {
		return searchStrat;
	}

	public void setSearchStrat(String searchStrat) {
		this.searchStrat = searchStrat;
	}

	public String getSearchEnd() {
		return searchEnd;
	}

	public void setSearchEnd(String searchEnd) {
		this.searchEnd = searchEnd;
	}


}
