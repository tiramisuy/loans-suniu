package com.rongdu.loans.compute;

import java.math.BigDecimal;

import com.rongdu.common.config.Global;

/**
 * 等本等息工具类
 * 
 * @author liuzhuang
 * 
 */
public class PrincipalInterestUtils {
	/**
	 * 每月偿还本金
	 * 
	 * @param principal
	 *            总借款额（贷款本金）
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金
	 */
	public static BigDecimal getMonthPrincipal(BigDecimal principal, int totalMonth) {
		BigDecimal monthPrincipal = principal.divide(new BigDecimal(totalMonth), Global.DEFAULT_AMT_SCALE,
				BigDecimal.ROUND_HALF_UP);
		return monthPrincipal;
	}

	/**
	 * 每月偿还利息
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @return 每月偿还利息
	 */
	public static BigDecimal getMonthInterest(BigDecimal principal, BigDecimal yearRate) {
		BigDecimal monthInterest = yearRate.divide(new BigDecimal(12), 6, BigDecimal.ROUND_HALF_EVEN)
				.multiply(principal).setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		return monthInterest;
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
	public static BigDecimal getInterestCount(BigDecimal principal, BigDecimal yearRate, int totalmonth) {
		BigDecimal totalInterest = new BigDecimal(0);
		totalInterest = yearRate.divide(new BigDecimal(12), 6, BigDecimal.ROUND_HALF_EVEN).multiply(principal)
				.multiply(new BigDecimal(totalmonth)).setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		return totalInterest;
	}

	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal(10000); // 本金
		int month = 12;
		BigDecimal yearRate = new BigDecimal(0.17); // 年利率
		BigDecimal monthInterest = getMonthInterest(invest, yearRate);
		System.out.println("等本等息---每月还款利息：" + monthInterest);
		BigDecimal monthPrincipal = getMonthPrincipal(invest, month);
		System.out.println("等本等息---每月还款本金：" + monthPrincipal);
		BigDecimal count = getInterestCount(invest, yearRate, month);
		System.out.println("等本等息---应还总利息：" + count);
	}

}
