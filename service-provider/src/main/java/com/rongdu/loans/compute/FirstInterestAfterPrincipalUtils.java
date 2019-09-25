package com.rongdu.loans.compute;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.rongdu.common.config.Global;

/**
 * 先息后本工具类
 * 
 * @author liuzhuang
 * 
 */
public class FirstInterestAfterPrincipalUtils {
	/**
	 * 每月偿还本金
	 * 
	 * @param principal
	 *            总借款额（贷款本金）
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金
	 */
	public static Map<Integer, BigDecimal> getMonthPrincipal(BigDecimal principal, int totalmonth) {
		Map<Integer, BigDecimal> mapPrincipal = new LinkedHashMap<Integer, BigDecimal>();
		for (int i = 1; i <= totalmonth; i++) {
			if (i == totalmonth) {
				mapPrincipal.put(totalmonth, principal);
			} else {
				mapPrincipal.put(i, BigDecimal.ZERO);
			}
		}
		return mapPrincipal;
	}

	/**
	 * 每月偿还利息
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还利息
	 */
	public static Map<Integer, BigDecimal> getMonthInterest(BigDecimal principal, BigDecimal yearRate, int totalmonth) {
		Map<Integer, BigDecimal> map = new LinkedHashMap<Integer, BigDecimal>();
		BigDecimal monthInterest = yearRate.divide(new BigDecimal(12), 6, BigDecimal.ROUND_HALF_EVEN)
				.multiply(principal).setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		for (int i = 1; i <= totalmonth; i++) {
			map.put(i, monthInterest);
		}
		return map;
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
		BigDecimal yearRate = new BigDecimal(0.1); // 年利率
		Map<Integer, BigDecimal> mapInterest = getMonthInterest(invest, yearRate, month);
		System.out.println("先息后本---每月还款利息：" + mapInterest);
		Map<Integer, BigDecimal> mapPrincipal = getMonthPrincipal(invest, month);
		System.out.println("先息后本---每月还款本金：" + mapPrincipal);
		BigDecimal count = getInterestCount(invest, yearRate, month);
		System.out.println("先息后本---应还总利息：" + count);
	}

}
