package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 查询借款、风险和逾期信息-借款记录历史
 * @author sunda
 * @version 2017-07-10
 */
public class QueryHistory implements Serializable {

	private static final long serialVersionUID = 908699796982147798L;
	/**
	 * 机构代号
	 * 具体为历史上在阿福平台查询此借款人的机构名称（编码）
	 */
	private String orgName;
	/**
	 * 查询时间
	 * 具体为历史上在阿福平台查询此借款人的查询时间 ， 格 式YYYY-MM-DD
	 */
	private String time;
	/**
	 * 机构类型
	 * 具体为历史上在阿福平台查询此借款人的机构类型
	 */
	private String orgType;
	/**
	 * 查询原因
	 * 具体为历史上在阿福平台查询此借款人的查询原因
	 */
	private String queryReason;  
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}

}