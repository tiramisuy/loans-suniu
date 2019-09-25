package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LoanTrafficVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID，实体唯一标识
	 */
	protected String id;
	
	/**
	 * 	备注
	 */
	protected String remark;
	
	/**
	 * 	创建者userId
	 */
	protected String createBy;
	/**
	 * 	创建日期
	 */
	protected Date createTime;
	/**
	 * 	最后修改者userId
	 */
	protected String updateBy;
	/**
	 *  更新日期
	 */
	protected Date updateTime;	
	
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
