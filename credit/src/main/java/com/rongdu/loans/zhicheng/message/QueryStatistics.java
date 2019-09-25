package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 查询借款、风险和逾期信息-被查询统计
 * @author sunda
 * @version 2017-07-10
 */
public class QueryStatistics implements Serializable {

	private static final long serialVersionUID = 6622956686596447900L;
	/**
	 * 其他机构查询次数
	 */
	private int timesByOtherOrg;
	/**
	 * 其他查询机构数
	 */
	private int otherOrgCount;
	/**
	 * 本机构查询次数
	 */
	private int timesByCurrentOrg;
	
	public int getTimesByOtherOrg() {
		return timesByOtherOrg;
	}
	public void setTimesByOtherOrg(int timesByOtherOrg) {
		this.timesByOtherOrg = timesByOtherOrg;
	}
	public int getOtherOrgCount() {
		return otherOrgCount;
	}
	public void setOtherOrgCount(int otherOrgCount) {
		this.otherOrgCount = otherOrgCount;
	}
	public int getTimesByCurrentOrg() {
		return timesByCurrentOrg;
	}
	public void setTimesByCurrentOrg(int timesByCurrentOrg) {
		this.timesByCurrentOrg = timesByCurrentOrg;
	}

}