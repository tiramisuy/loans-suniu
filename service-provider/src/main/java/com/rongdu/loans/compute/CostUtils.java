package com.rongdu.loans.compute;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.MoneyUtils;

import java.math.BigDecimal;

/**
 * 费用计算工具类型
 * 
 * @author likang
 */
public class CostUtils {

	/**
	 * 应还本息 = 应还本金+应还利息+逾期管理费+罚息+提前还款手续费-减免费用
	 * 
	 * @param principal
	 *            应还本金
	 * @param interest
	 *            应还利息
	 * @param overdueFee
	 *            逾期管理费
	 * @param penalty
	 *            罚息
	 * @param prepayFee
	 *            提前还款手续费
	 * @param servFee
	 *            分期服务费
	 * @param deduction
	 *            减免费用
	 * @return
	 */
	public static BigDecimal calTotalAmount(BigDecimal principal, BigDecimal interest, BigDecimal overdueFee,
			BigDecimal penalty, BigDecimal prepayFee, BigDecimal servFee, BigDecimal deduction) {
		return principal.add(interest).add(overdueFee).add(penalty).add(prepayFee).add(servFee).subtract(deduction);
	}

	/**
	 * 逾期管理费 = 逾期管理费(每日)*逾期天数 逾期管理费不超过本金
	 * 
	 * @param overdueFee
	 *            逾期管理费(每日)
	 * @param days
	 *            逾期天数
	 * @param principal
	 *            本金
	 * @return
	 */
	public static BigDecimal calOverDueFee(BigDecimal overdueFee, int days, BigDecimal principal) {
		overdueFee = overdueFee.multiply(BigDecimal.valueOf(days));
		return principal.compareTo(overdueFee) > 0 ? overdueFee : principal;
	}

	/**
	 * 逾期管理费 = 借款本金*逾期管理费(比例)*逾期天数
	 * 
	 * @param overdueFee
	 *            逾期管理费(比例)
	 * @param days
	 *            逾期天数
	 * @param principal
	 *            本金
	 * @return
	 */
	public static BigDecimal calOverFee(BigDecimal overdueFee, int days, BigDecimal principal) {
		return principal.multiply(overdueFee).multiply(BigDecimal.valueOf(days));
	}

	/**
	 * 服务费计算
	 * 
	 * @param servFeeRate
	 *            服务费费率
	 * @param applyAmt
	 *            申请金额
	 * @param applyTerm
	 *            申请周期（天）
	 * @return
	 */
	public static BigDecimal calServFee(BigDecimal servFeeRate, BigDecimal applyAmt) {
		if (null == servFeeRate || null == applyAmt) {
			return null;
		}
		BigDecimal servFee = servFeeRate.multiply(applyAmt)
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		return servFee;
	}

	// /**
	// * 计算日化服务费率
	// *
	// * @param servFeeRate 服务费费率(年化)
	// * @return
	// */
	// public static BigDecimal calDayServFeeRate(BigDecimal servFeeRate) {
	// if (null != servFeeRate) {
	// BigDecimal dayServFeeRate =
	// servFeeRate.divide(
	// new BigDecimal(Global.YEAR_DAYS),
	// Global.SIX_SCALE, BigDecimal.ROUND_HALF_UP);
	//
	// return dayServFeeRate;
	// }
	// return null;
	// }

	/**
	 * 计算日化利息
	 * 
	 * @param ratePerDay
	 *            日化利率
	 * @param applyAmt
	 *            申请金额
	 * @return
	 */
	public static BigDecimal calDayInterest(BigDecimal ratePerDay, BigDecimal applyAmt) {
		BigDecimal dayInterest = ratePerDay.multiply(applyAmt).setScale(Global.DEFAULT_AMT_SCALE,
				BigDecimal.ROUND_HALF_UP);
		return dayInterest;
	}

	/**
	 * 计算罚息
	 * 
	 * @param ratePerDay
	 *            日化利率
	 * @param applyAmt
	 *            申请金额
	 * @return
	 */
	public static BigDecimal calPenalty(BigDecimal ratePerDay, BigDecimal applyAmt, int days) {
		BigDecimal dayInterest = ratePerDay.multiply(applyAmt).multiply(BigDecimal.valueOf(days))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		return dayInterest;
	}

	/**
	 * 计算日化利息 = 本金*基础利率（年化）*（1-优惠）/365天
	 * 
	 * @param amount
	 *            本金
	 * @param basicRate
	 *            基础利率（年化）
	 * @param discountRate
	 *            优惠
	 * @return
	 */
	public static BigDecimal calDayInterest(BigDecimal amount, BigDecimal basicRate, BigDecimal discountRate) {
		BigDecimal dayInterest = amount.multiply(basicRate).multiply(BigDecimal.ONE.subtract(discountRate))
				.divide(BigDecimal.valueOf(Global.YEAR_DAYS), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		return dayInterest;
	}

	/**
	 * 计算实际日化利息
	 * 
	 * @param ratePerDay
	 *            日化利率
	 * @param applyAmt
	 *            申请金额
	 * @param discountVal
	 *            折扣贴息比例
	 * @return
	 */
	public static BigDecimal calActualDayInterest(BigDecimal ratePerDay, BigDecimal applyAmt, BigDecimal discountVal) {
		if (null == discountVal) {
			return calDayInterest(ratePerDay, applyAmt);
		}
		return ratePerDay.multiply(applyAmt).multiply(BigDecimal.ONE.subtract(discountVal))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算总利息
	 * 
	 * @param dayInterest
	 *            日利息
	 * @param applyTerm
	 *            申请期数（天）
	 * @return
	 */
	public static BigDecimal calTotalInterest(BigDecimal dayInterest, Integer applyTerm) {
		return MoneyUtils.mulBigDecimal(dayInterest, applyTerm);
	}

	/**
	 * 计算到账金额
	 * 
	 * @param applyAmt
	 *            申请金额
	 * @param servFee
	 *            服务费
	 * @return
	 */
	public static BigDecimal calToAccountAmt(BigDecimal applyAmt, BigDecimal servFee) {
		return applyAmt.subtract(servFee).setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算实际还款金额
	 * 
	 * @param applyAmt
	 *            申请金额
	 * @param totalInterest
	 * @return
	 */
	public static BigDecimal calRealRepayAmt(BigDecimal applyAmt, BigDecimal totalInterest) {
		return applyAmt.add(totalInterest).setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算实际年化利率
	 * 
	 * @param ratePerYear
	 *            年化利率
	 * @param discountVal
	 *            折扣贴息比例
	 * @return
	 */
	public static BigDecimal calActualYearRate(BigDecimal ratePerYear, BigDecimal discountVal) {
		if (null == discountVal) {
			return ratePerYear;
		}
		return ratePerYear.multiply(BigDecimal.ONE.subtract(discountVal)).setScale(Global.SIX_SCALE,
				BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算实际日利率
	 * 
	 * @param ratePerYear
	 *            日化利率
	 * @param discountVal
	 *            折扣贴息比例
	 * @return
	 */
	public static BigDecimal calActualDayRate(BigDecimal ratePerDay, BigDecimal discountVal) {
		if (null == discountVal) {
			return ratePerDay;
		}
		return ratePerDay.multiply(BigDecimal.ONE.subtract(discountVal)).setScale(Global.SIX_SCALE,
				BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算折扣金额
	 * 
	 * @param ratePerYear
	 *            年化利率
	 * @param discountVal
	 *            折扣贴息比例
	 * @param applyTerm
	 *            申请期数
	 * @return
	 */
	public static BigDecimal calDiscountAmt(BigDecimal ratePerYear, BigDecimal discountVal, Integer applyTerm) {
		if (null == discountVal) {
			return new BigDecimal(0.00).setScale(Global.DEFAULT_AMT_SCALE);
		}
		return discountVal.multiply(MoneyUtils.mulBigDecimal(ratePerYear, applyTerm)).setScale(
				Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * null 判断转换
	 * 
	 * @param param
	 * @return
	 */
	public static BigDecimal blankToZero(BigDecimal param) {
		if (null == param) {
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
		if (null == b1) {
			b1 = new BigDecimal(0.00);
		}
		if (null == b2) {
			b2 = new BigDecimal(0.00);
		}
		return b1.add(b2).setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 减法运算
	 * 
	 * @param v1被减数
	 * @param v2减数
	 * @return
	 */
	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		if (null == b1) {
			b1 = new BigDecimal(0.00);
		}
		if (null == b2) {
			b2 = new BigDecimal(0.00);
		}
		return b1.subtract(b2).setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算提前还款节省利息
	 * 
	 * @param interest
	 *            应还总利息
	 * @param approveTerm
	 *            贷款天数
	 * @param prepayDays
	 *            提前天数
	 * @return
	 */
	public static BigDecimal calPrePayInterest(BigDecimal interest, int approveTerm, int prepayDays) {
		// 日利息
		BigDecimal dayInterest = interest.divide(new BigDecimal(approveTerm), Global.FOUR_SCALE,
				BigDecimal.ROUND_HALF_UP);
		return dayInterest.multiply(new BigDecimal(prepayDays)).setScale(Global.DEFAULT_AMT_SCALE,
				BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算当前利息
	 * 
	 * @param interest
	 *            应还总利息
	 * @param approveTerm
	 *            贷款天数
	 * @param passDays
	 *            已借款天数
	 * @return
	 */
	public static BigDecimal calCurInterest(BigDecimal interest, int approveTerm, int passDays) {
		// 日利息
		BigDecimal dayInterest = interest.divide(new BigDecimal(approveTerm), Global.FOUR_SCALE,
				BigDecimal.ROUND_HALF_UP);
		return dayInterest.multiply(new BigDecimal(passDays)).setScale(Global.DEFAULT_AMT_SCALE,
				BigDecimal.ROUND_HALF_UP);
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
	
	/**
	 * 计算逾期利息(分期) = （本金*实际利率*逾期天数）/360
	 * 
	 * @param principal
	 *            本金
	 * @param ratePerYear
	 *            年化利率
	 * @param overdueDays
	 *            逾期天数
	 * @return
	 */
	public static BigDecimal calFenqiCurrOverdueInterest(BigDecimal approveAmt, BigDecimal ratePerYear, int overdueDays) {
		return approveAmt.multiply(ratePerYear).multiply(BigDecimal.valueOf(overdueDays))
				.divide(BigDecimal.valueOf(Global.YEAR_DAYS_FENQI), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算每天利息(分期) = （本金*实际利率）/360
	 * 
	 * @param principal
	 *            本金
	 * @param ratePerYear
	 *            年化利率
	 * @param overdueDays
	 *            逾期天数
	 * @return
	 */
	public static BigDecimal calFenqiDayInterest(BigDecimal approveAmt, BigDecimal ratePerYear) {
		return approveAmt.multiply(ratePerYear).divide(BigDecimal.valueOf(Global.YEAR_DAYS_FENQI),
				Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

}
