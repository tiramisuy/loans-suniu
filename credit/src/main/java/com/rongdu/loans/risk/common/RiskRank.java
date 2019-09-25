package com.rongdu.loans.risk.common;

/**
 * 风控规则的风险等级
 * @author sunda
 * @version 2017-08-14
 */
public class RiskRank {
	
	/**
	 * A-拒绝贷款+进入黑名单
	 */
	public static String A="A";
	/**
	 * B-拒绝贷款
	 */
	public static String B="B";
	/**
	 * C-定义分值，人工审核
	 */
	public static String C="C";
	/**
	 * D-风险较低，定义分值，人工审核
	 */
	public static String D="D";

	/**
	 * P-自动审批通过
	 */
	public static String P="P";
	
}
