package com.rongdu.common.utils;

import java.math.BigDecimal;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.MoneyUtils;

/**
 * 费用计算工具类型
 * @author likang
 *
 */
public class CostUtils {

	/**
	 * 服务费计算
	 * @param servFeeRate 服务费费率(年化)
	 * @param applyAmt    申请金额
	 * @param applyTerm   申请周期（天）
	 * @return
	 */
	public static BigDecimal calServFee(BigDecimal servFeeRate,
			BigDecimal applyAmt, Integer applyTerm) {
		if(null == servFeeRate 
				|| null == applyAmt 
				|| null == applyTerm) {
			return null;
		}
		BigDecimal dayServFeeRate = calDayServFeeRate(servFeeRate);
		BigDecimal servFee = dayServFeeRate.multiply(
				applyAmt).multiply(new BigDecimal(applyTerm))
						.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		return servFee;
	}
	
	/**
	 * 计算日化服务费率
	 * @param servFeeRate 服务费费率(年化)
	 * @return
	 */
	public static BigDecimal calDayServFeeRate(BigDecimal servFeeRate) {
		if(null != servFeeRate) {
			BigDecimal dayServFeeRate =
					servFeeRate.divide(
							new BigDecimal(Global.YEAR_DAYS),
							6, BigDecimal.ROUND_HALF_UP);
			
			return dayServFeeRate;
		}
		return null;
	}
	
	/**
	 * 计算日化利息
	 * @param ratePerDay 日化利率
	 * @param applyAmt   申请金额
	 * @return
	 */
	public static BigDecimal calDayInterest(
			BigDecimal ratePerDay, BigDecimal applyAmt) {
		BigDecimal dayInterest = 
				ratePerDay.multiply(applyAmt)
						.setScale(Global.DEFAULT_AMT_SCALE,
								BigDecimal.ROUND_HALF_UP);
		return dayInterest;
	}
	
	/**
	 * 计算实际日化利息
	 * @param ratePerDay 日化利率
	 * @param applyAmt   申请金额
	 * @param discountVal 折扣贴息比例
	 * @return
	 */
	public static BigDecimal calActualDayInterest(
			BigDecimal ratePerDay, BigDecimal applyAmt, BigDecimal discountVal) {
		if(null == discountVal) {
			return calDayInterest(ratePerDay, applyAmt);
		}
		return ratePerDay.multiply(applyAmt).multiply(
						new BigDecimal(1).subtract(discountVal))
						.setScale(Global.DEFAULT_AMT_SCALE,
								BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 计算总利息
	 * @param dayInterest 日利息
	 * @param applyTerm   申请期数（天）
	 * @return
	 */
	public static BigDecimal calTotalInterest(
			BigDecimal dayInterest, Integer applyTerm) {
		return MoneyUtils.mulBigDecimal(dayInterest, applyTerm);
	}
	
	/**
	 * 计算到账金额
	 * @param applyAmt 申请金额
	 * @param servFee  服务费
	 * @return
	 */
	public static BigDecimal calToAccountAmt(
			BigDecimal applyAmt, BigDecimal servFee) {
		return applyAmt.subtract(servFee).setScale(
				Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 计算实际还款金额
	 * @param applyAmt 申请金额
	 * @param totalInterest
	 * @return
	 */
	public static BigDecimal calRealRepayAmt(
			BigDecimal applyAmt, BigDecimal totalInterest) {
		return applyAmt.add(totalInterest).setScale(
				Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 计算实际年化利率
	 * @param ratePerYear 年化利率
	 * @param discountVal 折扣贴息比例
	 * @return
	 */
	public static BigDecimal calActualYearRate(
			BigDecimal ratePerYear, BigDecimal discountVal) {
		if(null == discountVal) {
			return ratePerYear;
		}
		return ratePerYear.multiply(
				new BigDecimal(1).subtract(discountVal)).setScale(
						Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 计算实际日利率
	 * @param ratePerYear 日化利率
	 * @param discountVal 折扣贴息比例
	 * @return
	 */
	public static BigDecimal calActualDayRate(
			BigDecimal ratePerDay, BigDecimal discountVal) {
		if(null == discountVal) {
			return ratePerDay;
		}
		return ratePerDay.multiply(
				new BigDecimal(1).subtract(discountVal)).setScale(
						Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 计算折扣金额
	 * @param ratePerYear 年化利率
	 * @param discountVal 折扣贴息比例
	 * @param applyTerm   申请期数
	 * @return
	 */
	public static BigDecimal calDiscountAmt(
			BigDecimal ratePerYear, BigDecimal discountVal, Integer applyTerm) {
		if(null == discountVal) {
			return new BigDecimal(0).setScale(Global.DEFAULT_AMT_SCALE);
		}
		return discountVal.multiply(
				MoneyUtils.mulBigDecimal(ratePerYear, applyTerm))
						.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * null 判断转换
	 * @param param
	 * @return
	 */
	public static BigDecimal blankToZero(BigDecimal param) {
		if(null == param) {
			return new BigDecimal(0.00);
		}
		return param;
	}
	
	/**
	 * 加法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		return b1.add(b2).setScale(
				Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 减法运算
	 * 
	 * @param v1被减数
	 * @param v2减数
	 * @return
	 */
	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		return b1.subtract(b2).setScale(
				Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 除法运算
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
		if (b2 == null || b2.compareTo(BigDecimal.ZERO) == 0){
			return null;
		}
		return b1.divide(b2,Global.FOUR_SCALE,BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算当前利息 = （本金*实际利率*借款天数）/360
	 *
	 * @param principal
	 *            本金
	 * @param actualRate
	 *            实际利率
	 * @param days
	 *            借款天数
	 * @return
	 */
	public static BigDecimal calCurInterest(BigDecimal principal, BigDecimal actualRate, int days) {
		if(days > 1) {
			return principal.multiply(actualRate).multiply(BigDecimal.valueOf(days))
					.divide(BigDecimal.valueOf(Global.YEAR_DAYS_FENQI), Global.DEFAULT_ZERO_SCALE, BigDecimal.ROUND_HALF_UP);
		}
		return principal.multiply(actualRate).multiply(BigDecimal.valueOf(days))
				.divide(BigDecimal.valueOf(Global.YEAR_DAYS_FENQI), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
}
