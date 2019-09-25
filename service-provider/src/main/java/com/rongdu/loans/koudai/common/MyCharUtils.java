package com.rongdu.loans.koudai.common;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangwei on 2018/1/30.
 */
public class MyCharUtils {
	/**
	 * 判断是否为中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static String chinese2Unicode(String str) {
		if (StringUtils.isBlank(str))
			return str;
		StringBuilder sb = new StringBuilder();
		int sLength = str.length();
		for (Character c : str.toCharArray()) {
			if (isChinese(c)) {
				sb.append(CharUtils.unicodeEscaped(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static void main(String[] a) {
		String s = "xyz";
		System.out.println(chinese2Unicode(s));
		s = "x王伟yz";
		System.out.println(chinese2Unicode(s));
		s = "{\"annualIncome\":100000,\"cardNo\":\"6228481234567890005\",\"debtState\":\"无\",\"idNumber\":\"310229199304093427\",\"industry\":\"软件\",\"jobNature\":\"全职\",\"mobile\":\"13916196615\",\"name\":\"王思慧\",\"repaymentMoneySource\":\"劳动\",\"routeStrategy\":\"custody\",\"smsCode\":\"111111\",\"srvAuthCode\":\"1092221776671935400\"}";
		System.out.println(chinese2Unicode(s));
	}
}
