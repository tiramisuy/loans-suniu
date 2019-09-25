package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LoanTrafficStatisticsVO implements Serializable {
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime;
	/**
	 * 	最后修改者userId
	 */
	protected String updateBy;
	/**
	 *  更新日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateTime;

	/**
	 *贷超产品id
	 */
	private String trafficId;
	/**
	  *贷超产品名称
	  */
	private String trafficName;
	/**
	  *浏览量pv
	  */
	private Integer views;
	/**
	  *点击数uv
	  */
	private Integer hits;
	/**
	  *统计时间年月日YYYYMMDD
	  */
	private Integer statsDate;
	/**
	  *状态(0-有效，1-无效)
	  */
	private String status;
	private Integer beginStatsDate; // 开始 统计时间年月日YYYYMMDD
	private Integer endStatsDate; // 结束 统计时间年月日YYYYMMDD

	public String getTrafficId() {
		return trafficId;
	}

	public void setTrafficId(String trafficId) {
		this.trafficId = trafficId;
	}

	public String getTrafficName() {
		return trafficName;
	}

	public void setTrafficName(String trafficName) {
		this.trafficName = trafficName;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(Integer statsDate) {
		this.statsDate = statsDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getBeginStatsDate() {
		return beginStatsDate;
	}

	public void setBeginStatsDate(Integer beginStatsDate) {
		this.beginStatsDate = beginStatsDate;
	}

	public Integer getEndStatsDate() {
		return endStatsDate;
	}

	public void setEndStatsDate(Integer endStatsDate) {
		this.endStatsDate = endStatsDate;
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
