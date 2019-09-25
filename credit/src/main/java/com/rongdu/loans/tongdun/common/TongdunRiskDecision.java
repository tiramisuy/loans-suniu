package com.rongdu.loans.tongdun.common;

/**
 * 同盾-决策结果码
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class TongdunRiskDecision {


	// 通过 ，无风险
	public static String ACCEPT = "Accept";

	// 审核 ，风险评估决策为低风险 ，建议人工审核
	public static String REVIEW = "Review";

	// 拒绝 ，风险评估决策为高风险 ，建议拒绝
	public static String REJECT = "Reject";


}
