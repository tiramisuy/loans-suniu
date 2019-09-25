package com.rongdu.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
* @Description: unicode工具 
* @author: RaoWenbiao
* @date 2018年12月8日
 */
public class UnicodeUtil {
	/*
	 * 中文转unicode编码
	 */
	public static String cnToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 
	* @Title: unicodeToCn
	* @Description: unicode转中文
	* @return String    返回类型
	* @throws
	 */
	public static String unicodeToCn(String str) {

		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;

	}
}
