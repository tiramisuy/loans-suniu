/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import java.math.BigDecimal;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 导流平台产品信息Entity
 * @author raowb
 * @version 2018-08-29
 */
public class LoanTraffic extends BaseEntity<LoanTraffic> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *产品类型（0-现金贷）
	  */
	private String type;		
	/**
	  *平台名称
	  */
	private String name;		
	/**
	  *起始金额
	  */
	private BigDecimal minAmt;		
	/**
	  *最大金额
	  */
	private BigDecimal maxAmt;		
	/**
	  *最小周期
	  */
	private Integer minTerm;		
	/**
	  *最大周期
	  */
	private Integer maxTerm;		
	/**
	  *借款单位（M-月、Q-季、Y-年、D-天）
	  */
	private String repayUnit;		
	/**
	  *logo
	  */
	private String logoUrl;		
	/**
	  *跳转链接
	  */
	private String platformUrl;		
	/**
	  *产品描述
	  */
	private String desc;		
	/**
	  *点击量
	  */
	private Integer hits;		
	/**
	  *排位
	  */
	private Integer scort;		
	/**
	  *产品状态(0-初始，1-正常，2-下架)
	  */
	private String status;		
	
	public LoanTraffic() {
		super();
	}

	public LoanTraffic(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}
	
	public BigDecimal getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}
	
	public Integer getMinTerm() {
		return minTerm;
	}

	public void setMinTerm(Integer minTerm) {
		this.minTerm = minTerm;
	}
	
	public Integer getMaxTerm() {
		return maxTerm;
	}

	public void setMaxTerm(Integer maxTerm) {
		this.maxTerm = maxTerm;
	}
	
	public String getRepayUnit() {
		return repayUnit;
	}

	public void setRepayUnit(String repayUnit) {
		this.repayUnit = repayUnit;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	public String getPlatformUrl() {
		return platformUrl;
	}

	public void setPlatformUrl(String platformUrl) {
		this.platformUrl = platformUrl;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}
	
	public Integer getScort() {
		return scort;
	}

	public void setScort(Integer scort) {
		this.scort = scort;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}