package com.rongdu.loans.compute;

import java.math.BigDecimal;

/**
 * 按天等本等息工具类
 * 
 * @author liuzhuang
 * 
 */
public class PrincipalInterestDayUtils {
	/**
	 * 每期偿还本金
	 * 
	 * @param principal
	 *            总借款额（贷款本金）
	 * @param totalTerm
	 *            还款总期数
	 * @return 每月偿还本金
	 */
	public static BigDecimal getTermPrincipal(BigDecimal principal, int totalTerm) {
		BigDecimal termPrincipal = principal.divide(new BigDecimal(totalTerm), 0, BigDecimal.ROUND_HALF_UP);
		return termPrincipal;
	}

	/**
	 * 每期偿还利息
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param termDays
	 *            每期天数
	 * @return 每月偿还利息
	 */
	public static BigDecimal getTermInterest(BigDecimal principal, BigDecimal yearRate, int termDays) {
		BigDecimal termInterest = CostUtils.calCurInterest(principal, yearRate, termDays).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		return termInterest;
	}

	/**
	 * 应还总利息
	 * 
	 * @param Principal
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 总利息
	 */
	public static BigDecimal getInterestCount(BigDecimal principal, BigDecimal yearRate, int termDays, int totalTerm) {
		BigDecimal totalInterest = getTermInterest(principal, yearRate, termDays).multiply(new BigDecimal(totalTerm));
		return totalInterest;
	}

	public static void main(String[] args) {
		BigDecimal principal = new BigDecimal(2500); // 本金
		BigDecimal yearRate = new BigDecimal(0.36); // 年利率
		int termDay = 7;
		int totalTerm = 4;
		BigDecimal TermInterest = getTermInterest(principal, yearRate, termDay);
		System.out.println("等本等息---每月还款利息：" + TermInterest);
		BigDecimal TermPrincipal = getTermPrincipal(principal, totalTerm);
		System.out.println("等本等息---每月还款本金：" + TermPrincipal);
		BigDecimal count = getInterestCount(principal, yearRate, termDay, totalTerm);
		System.out.println("等本等息---应还总利息：" + count);
	}

}
