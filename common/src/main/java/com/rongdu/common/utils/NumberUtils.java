package com.rongdu.common.utils;

import java.math.BigDecimal;
/**
 * 
 * Object值转为不同类型的数值.
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{
	
	private static final char[] CH_NUM = "零一二三四五六七八九".toCharArray();
	private static final String[] U1 = { "", "十", "百", "千" };
	private static final String[] U2 = { "", "万", "亿" };
	
	/**
	 * 判断对象值是否为纯数字组成(如:001234,7899),只有纯数字的值才能转Long或Integer.
	 * @param obj 要判断的值.
	 * @return true or false.
	 */
	public static boolean isDigits(Object obj) {
		if (obj == null){
			return false;
		}
		return NumberUtils.isDigits(obj.toString());
	}
	
	/**
	 * 判断对象值是否为数字,包含整数和小数(如:001234,7899,7.99,99.00)
	 * @param obj 要判断的值.
	 * @return true or false.
	 */
	public static boolean isNumber(Object obj) {
		if (obj == null){
			return false;
		}
		return NumberUtils.isNumber(obj.toString());
	}

	/**
	 * Object对象转BigDecimal <br/>
	 * 1、如果Object为空或Object不是数值型对象:抛数字格式化异常 <br/>
	 * 2、Object为数值型对象:转为BigDecimal类型并返回 <br/>
	 * @param obj 要转换的Object对象 <br/>
	 * @return BigDecimal
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		if (obj == null || !NumberUtils.isNumber(obj.toString())){
			throw new NumberFormatException("数字格式化异常");
		} else {
			return new BigDecimal(obj.toString());
		}
	}
	
	/**
	 * Object对象转Double <br/>
	 * 1、如果Object为空或Object不是数值型对象:抛数字格式化异常 <br/>
	 * 2、Object为数值型对象:转为Double类型并返回 <br/>
	 * @param obj 要转换的Object对象 <br/>
	 * @return Double
	 */
	public static Double toDouble(Object obj) {
		if (obj == null || !NumberUtils.isNumber(obj.toString())){
			throw new NumberFormatException("数字格式化异常");
		} else {
			return Double.valueOf(obj.toString());
		}
	}
	
	/**
	 * Object对象转Long <br/>
	 * 1、如果Object为空或Object不是整数型对象:抛数字格式化异常 <br/>
	 * 2、Object为整数型对象:转为Long类型并返回 <br/>
	 * @param obj 要转换的Object对象 <br/>
	 * @return Long
	 */
	public static Long toLong(Object obj) {
		if (obj == null || !NumberUtils.isDigits(obj.toString())){
			throw new NumberFormatException("数字格式化异常");
		} else {
			return Long.valueOf(obj.toString());
		}
	}
	
	/**
	 * Object对象转Integer <br/>
	 * 1、如果Object为空或Object不是整数型对象:抛数字格式化异常 <br/>
	 * 2、Object为整数型对象:转为Integer类型并返回 <br/>
	 * @param obj 要转换的Object对象 <br/>
	 * @return Integer
	 */
	public static Integer toInteger(Object obj) {
		if (obj == null || !NumberUtils.isDigits(obj.toString())){
			throw new NumberFormatException("数字格式化异常");
		} else {
			return Integer.valueOf(obj.toString());
		}
	}

	
	/**
	 * 转中文小写
	 * @return
	 */
	public static String toCH(Object obj) {
		if(!isNumber(obj)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		
		BigDecimal bd = toBigDecimal(obj);
		String numberStr = bd.toPlainString();
		String[] splitStr = numberStr.split("\\.");
		String integerStr = splitStr[0];
		// 解析整数位
		// 从个位数开始转换
		int i, j;
		for (i = integerStr.length() - 1, j = 0; i >= 0; i--, j++) {
			char n = integerStr.charAt(i);
			if (n == '0') {
				// 当n是0且n的右边一位不是0时，插入[零]
				if (i < integerStr.length() - 1 && integerStr.charAt(i + 1) != '0') {
					sb.append(CH_NUM[0]);
				}
				// 插入[万]或者[亿]
				if (j % 4 == 0) {
					if (i > 0 && integerStr.charAt(i - 1) != '0' || i > 1
							&& integerStr.charAt(i - 2) != '0' || i > 2
							&& integerStr.charAt(i - 3) != '0') {
						sb.append(U2[j / 4]);
					}
				}
			} else {
				if (j % 4 == 0) {
					sb.append(U2[j / 4]); // 插入[万]或者[亿]
				}
				sb.append(U1[j % 4]); // 插入[拾]、[佰]或[仟]
				sb.append(CH_NUM[n - '0']); // 插入数字
			}
		}
		sb = sb.reverse();
		if(splitStr.length == 1) return sb.toString();
		sb.append("点");
		// 解析小数位
		for (int k = 0; k < splitStr[1].length(); k++) {
			char chNum =  CH_NUM[splitStr[1].charAt(k) - 48];
			sb.append(chNum);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(toCH("20003000.525"));
	}
}
