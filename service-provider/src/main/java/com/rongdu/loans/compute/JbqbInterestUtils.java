package com.rongdu.loans.compute;

import java.math.BigDecimal;

/**
 * 聚宝钱包利息计算工具 利息=服务费*0.36+（本金-服务费）*0.24
 * 
 * @author liuzhuang
 * 
 */
public class JbqbInterestUtils {

	/**
	 * 每期偿还利息
	 * 
	 * @param principal
	 *            本金
	 * @param servFee
	 *            服务费
	 * @param principalServRate
	 *            本金服务费部分利率
	 * @param principalRate
	 *            本金剩余部分利率
	 * @param termDays
	 *            每期天数
	 * @return
	 */
	public static BigDecimal getTermInterest(BigDecimal principal, BigDecimal servFee, BigDecimal principalServRate,
			BigDecimal principalRate, int termDays) {
		BigDecimal principalServInterest = CostUtils.calCurInterest(servFee, principalServRate, termDays).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal remainingPrincipal = principal.subtract(servFee);// 剩余本金
		BigDecimal principalInterest = CostUtils.calCurInterest(remainingPrincipal, principalRate, termDays).setScale(
				0, BigDecimal.ROUND_HALF_UP);
		return principalServInterest.add(principalInterest);
	}

	/**
	 * 应还总利息
	 * 
	 * @param principal
	 *            本金
	 * @param servFee
	 *            服务费
	 * @param principalServRate
	 *            本金服务费部分利率
	 * @param principalRate
	 *            本金剩余部分利率
	 * @param termDays
	 *            每期天数
	 * @return
	 */
	public static BigDecimal getInterestCount(BigDecimal principal, BigDecimal servFee, BigDecimal principalServRate,
			BigDecimal principalRate, int termDays, int totalTerm) {
		BigDecimal totalInterest = getTermInterest(principal, servFee, principalServRate, principalRate, termDays)
				.multiply(new BigDecimal(totalTerm));
		return totalInterest;
	}

	public static void main(String[] args) {
		BigDecimal principal = new BigDecimal(2500); // 本金
		BigDecimal servFee = new BigDecimal(725); // 服务费
		BigDecimal principalServRate = new BigDecimal(0.36);// 本金服务费部分利率
		BigDecimal principalRate = new BigDecimal(0.24);// 本金剩余部分利率
		int termDays = 7;
		int totalTerm = 4;
		BigDecimal TermInterest = getTermInterest(principal, servFee, principalServRate, principalRate, termDays);
		System.out.println("等本等息---每月还款利息：" + TermInterest);
		BigDecimal count = getInterestCount(principal, servFee, principalServRate, principalRate, termDays, totalTerm);
		System.out.println("等本等息---应还总利息：" + count);
	}
}
