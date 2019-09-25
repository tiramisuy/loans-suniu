package com.rongdu.loans.pay.utils;

import com.rongdu.common.utils.StringUtils;

import java.text.DecimalFormat;

/**
 * 
 * 宝付手续费工具类
 * 
 * @author sunda
 *
 */
public class BaofooFeeUtils {
	
	/**
	 * 计算认证支付手续费，按照充值金额的0.002，保底1元
	 */
	public static String computeAuthpayFee(String str) {
		DecimalFormat df  = new DecimalFormat("###.##");
		if (StringUtils.isBlank(str)) {
			return df.format(0);
		}else {
			Double d = 0.002*Double.valueOf(str);
			d = d>1?d:1;
			return df.format(d);
		}
	}
	
	/**
	 * 计算网银支付手续费，按照充值金额的0.0015
	 */
	public static String computePcpayFee(String str) {
		DecimalFormat df  = new DecimalFormat("###.##");
		if (StringUtils.isBlank(str)) {
			return df.format(0);
		}else {
			return df.format(0.0015*Double.valueOf(str));
		}
	}
	
	/**
	 * 计算代付手续费，每50万进行拆单，单笔1元
	 */
	public static String computeWithdrawFee(String str) {
		DecimalFormat df  = new DecimalFormat("###.##");
		if (StringUtils.isBlank(str)) {
			return df.format(0);
		}else {
			return df.format(Math.ceil(Double.valueOf(str)/500000));
		}
	}
	
}
