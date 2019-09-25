package com.rongdu.loans.compute;

/**  
 * 等额本息工具类  
 */

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 等额本息还款，也称定期付息。 即借款人每月按相等的金额偿还贷款本息， 其中每月贷款利息按月初剩余贷款本金计算并逐月结清。
 * 把按揭贷款的本金总额与利息总额相加，然后平均分摊到还款期限的每个月中。
 * 作为还款人，每个月还给银行固定金额，但每月还款额中的本金比重逐月递增、利息比重逐月递减。
 */

public class AverageCapitalPlusInterestUtils {

	/**
	 * 每月偿还本金和利息
	 * 
	 * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金和利息,四舍五入
	 */
	public static BigDecimal getMonthPrincipalInterest(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 6, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal one = BigDecimal.ONE;
		BigDecimal calculate_1 = invest.multiply(monthRate).multiply(one.add(monthRate).pow(totalmonth));
		BigDecimal calculate_2 = one.add(monthRate).pow(totalmonth).subtract(one);
		BigDecimal monthIncome = calculate_1.divide(calculate_2, 2, BigDecimal.ROUND_HALF_UP);
		return monthIncome;
	}

	/**
	 * 每月偿还利息
	 * 
	 * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还利息
	 */
	public static Map<Integer, BigDecimal> getMonthInterest(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		Map<Integer, BigDecimal> map = new LinkedHashMap<Integer, BigDecimal>();
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 6, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal one = BigDecimal.ONE;
		BigDecimal monthInterest;
		for (int i = 1; i <= totalmonth; i++) {
			BigDecimal calculate_1 = invest.multiply(monthRate);
			BigDecimal calculate_2 = one.add(monthRate).pow(totalmonth).subtract(one.add(monthRate).pow(i - 1));
			BigDecimal calculate_3 = one.add(monthRate).pow(totalmonth).subtract(one);
			monthInterest = calculate_1.multiply(calculate_2).divide(calculate_3, 2, BigDecimal.ROUND_HALF_UP);
			map.put(i, monthInterest);
		}
		return map;
	}

	/**
	 * 每月偿还本金
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金
	 */
	public static Map<Integer, BigDecimal> getMonthPrincipal(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		BigDecimal monthIncome = getMonthPrincipalInterest(invest, yearRate, totalmonth);
		Map<Integer, BigDecimal> mapInterest = getMonthInterest(invest, yearRate, totalmonth);
		Map<Integer, BigDecimal> mapPrincipal = new LinkedHashMap<Integer, BigDecimal>();
		//BigDecimal remainingPrincipal = invest;// 剩余本金
		for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
			//if (entry.getKey() == totalmonth) {
				//mapPrincipal.put(totalmonth, remainingPrincipal);
			//} else {
				mapPrincipal.put(entry.getKey(), monthIncome.subtract(entry.getValue()));
				//remainingPrincipal = remainingPrincipal.subtract(mapPrincipal.get(entry.getKey()));
			//}
		}
		return mapPrincipal;
	}

	/**
	 * 应还总利息
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 总利息
	 */
	public static BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		BigDecimal count = new BigDecimal(0);
		Map<Integer, BigDecimal> mapInterest = getMonthInterest(invest, yearRate, totalmonth);
		for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
			count = count.add(entry.getValue());
		}
		return count;
	}

	/**
	 * 应还本息总和
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 应还本息总和
	 */
	public static BigDecimal getPrincipalInterestCount(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		BigDecimal count = new BigDecimal(0);
		Map<Integer, BigDecimal> mapPrincipal = getMonthPrincipal(invest, yearRate, totalmonth);
		Map<Integer, BigDecimal> mapInterest = getMonthInterest(invest, yearRate, totalmonth);
		for (int i = 1; i <= totalmonth; i++) {
			count = count.add(mapPrincipal.get(i)).add(mapInterest.get(i));
		}
		return count;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal(10000); // 本金
		int month = 12;
		BigDecimal yearRate = new BigDecimal(0.18); // 年利率
		BigDecimal perMonthPrincipalInterest = getMonthPrincipalInterest(invest, yearRate, month);
		System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
		Map<Integer, BigDecimal> mapInterest = getMonthInterest(invest, yearRate, month);
		System.out.println("等额本息---每月还款利息：" + mapInterest);
		Map<Integer, BigDecimal> mapPrincipal = getMonthPrincipal(invest, yearRate, month);
		System.out.println("等额本息---每月还款本金：" + mapPrincipal);
		BigDecimal count = getInterestCount(invest, yearRate, month);
		System.out.println("等额本息---应还总利息：" + count);
		BigDecimal principalInterestCount = getPrincipalInterestCount(invest, yearRate, month);
		System.out.println("等额本息---应还本息总和：" + principalInterestCount);
	}
}