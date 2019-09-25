package com.rongdu.loans.baiqishi.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 白骑士-决策结果码
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class BaiqishiRiskDecision {


	// 通过 ，无风险
	public static String ACCEPT = "Accept";

	// 审核 ，风险评估决策为低风险 ，建议人工审核
	public static String REVIEW = "Review";

	// 拒绝 ，风险评估决策为高风险 ，建议拒绝
	public static String REJECT = "Reject";


}
