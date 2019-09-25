
package com.rongdu.loans.api.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XianJinBaiKaUtil {
	private static Logger logger = LoggerFactory.getLogger(XianJinBaiKaUtil.class);

	public static String[] baoFuBankCard = { "工商银行", "中国银行", "建设银行", "交通银行", "光大银行", "广发银行", "兴业银行", "平安银行", "浦发银行",
			"邮储银行", "中信银行" };

	public static Map<String, String> bfMap = new HashMap<>();
	static {
		bfMap.put("工商银行", "单笔0.5W/日5W");
		bfMap.put("中国银行", "单笔0.5W/日5W");
		bfMap.put("建设银行", "单笔0.5W/日5W");
		bfMap.put("交通银行", "单笔0.5W/日5W");
		bfMap.put("光大银行", "单笔0.5W/日5W");
		bfMap.put("广发银行", "单笔0.5W/日5W");
		bfMap.put("兴业银行", "单笔0.5W/日5W");
		bfMap.put("平安银行", "单笔0.5W/日5W");
		bfMap.put("浦发银行", "单笔0.5W/日5W");
		bfMap.put("邮储银行", "单笔0.5W/日0.5W");
		bfMap.put("中信银行", "单笔0.5W/日0.5W");
	}

	/**
	 * API验证签名
	 *
	 * @param call
	 * @param args
	 * @param requestSign
	 * @return
	 */
	public static boolean checkSign(String call, String args, String requestSign) {
		try {
			String ua = XianJinBaiKaConfig.ua_request;
			String signkey = XianJinBaiKaConfig.signkey_request;
			String key = ua + signkey + ua;
			String sign = MD5Util.md5(key + call + key + args + key);

			if (sign.equals(requestSign)) {
				logger.info("验签APIsign= {},签名={}", sign, true);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean checkSignFQ(String call, String args, String requestSign) {
		try {
			String ua = XianJinBaiKaConfig.uafq_request;
			String signkey = XianJinBaiKaConfig.signkeyfq_request;
			String key = ua + signkey + ua;
			String sign = MD5Util.md5(key + call + key + args + key);

			if (sign.equals(requestSign)) {
				logger.info("验签APIsign= {},签名={}", sign, true);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * 根据名称获取银行编码
	 *
	 * @param openBank 银行名称
	 * @return
	 */
	public static String convertToBankCode(String openBank) {
		if (StringUtils.isBlank(openBank)) {
			return "";
		} else if (openBank.contains("工商")) {
			return "ICBC";
		} else if (openBank.contains("农业")) {
			return "ABC";
		} else if (openBank.contains("中国银行")) {
			return "BOC";
		} else if (openBank.contains("建设")) {
			return "CCB";
		} else if (openBank.contains("交通")) {
			return "BCOM";
		} else if (openBank.contains("民生")) {
			return "CMBC";
		} else if (openBank.contains("招商")) {
			return "CMB";
		} else if (openBank.contains("邮政储蓄") || openBank.contains("邮储")) {
			return "PSBC";
		} else if (openBank.contains("平安")) {
			return "PAB";
		} else if (openBank.contains("中信")) {
			return "CITIC";
		} else if (openBank.contains("光大")) {
			return "CEB";
		} else if (openBank.contains("兴业")) {
			return "CIB";
		} else if (openBank.contains("广发")) {
			return "GDB";
		} else if (openBank.contains("华夏")) {
			return "HXB";
		} else if (openBank.contains("南京")) {
			return "NJCB";
		} else if (openBank.contains("浦发")) {
			return "SPDB";
		} else if (openBank.contains("北京")) {
			return "BOB";
		} else if (openBank.contains("杭州")) {
			return "HZB";
		} else if (openBank.contains("宁波")) {
			return "NBCB";
		} else if (openBank.contains("浙商")) {
			return "CZB";
		} else if (openBank.contains("徽商")) {
			return "HSB";
		} else if (openBank.contains("渤海")) {
			return "CBHB";
		} else if (openBank.contains("汉口")) {
			return "HKBANK";
		} else {
			return "";
		}
	}


}
