package com.rongdu.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rongdu.common.config.Global;

/**
 * 金额转化工具类
 * 
 * @author sunda
 * 
 * @version 2017-03-28
 */
public class MoneyUtils {
	// 不考虑分隔符的正确性
	private static final Pattern AMOUNT_PATTERN = Pattern
			.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
	private static final char[] RMB_NUMS = "零壹贰叁肆伍陆柒捌玖".toCharArray();
	private static final String[] UNITS = { "元", "角", "分", "整" };
	private static final String[] U1 = { "", "拾", "佰", "仟" };
	private static final String[] U2 = { "", "万", "亿" };

	/**
	 * 将金额（整数部分等于或少于12位，小数部分2位）转换为中文大写形式.
	 * 
	 * @param amount
	 *            金额数字
	 * @return 中文大写
	 * @throws IllegalArgumentException
	 */
	public static String convert(String amount) throws IllegalArgumentException {
		// 去掉分隔符
		amount = amount.replace(",", "");

		// 验证金额正确性
		if (amount.equals("0.00")) {
			throw new IllegalArgumentException("金额不能为零.");
		}
		Matcher matcher = AMOUNT_PATTERN.matcher(amount);
		if (!matcher.find()) {
			throw new IllegalArgumentException("输入金额有误.");
		}

		String integer = matcher.group(1); // 整数部分
		String fraction = matcher.group(2); // 小数部分

		String result = "";
		if (!integer.equals("0")) {
			result += integer2rmb(integer) + UNITS[0]; // 整数部分
		}
		if (fraction.equals("00")) {
			result += UNITS[3]; // 添加[整]
		} else if (fraction.startsWith("0") && integer.equals("0")) {
			result += fraction2rmb(fraction).substring(1); // 去掉分前面的[零]
		} else {
			result += fraction2rmb(fraction); // 小数部分
		}

		return result;
	}

	// 将金额小数部分转换为中文大写
	private static String fraction2rmb(String fraction) {
		char jiao = fraction.charAt(0); // 角
		char fen = fraction.charAt(1); // 分
		return (RMB_NUMS[jiao - '0'] + (jiao > '0' ? UNITS[1] : ""))
				+ (fen > '0' ? RMB_NUMS[fen - '0'] + UNITS[2] : "");
	}

	// 将金额整数部分转换为中文大写
	private static String integer2rmb(String integer) {
		StringBuilder buffer = new StringBuilder();
		// 从个位数开始转换
		int i, j;
		for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
			char n = integer.charAt(i);
			if (n == '0') {
				// 当n是0且n的右边一位不是0时，插入[零]
				if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
					buffer.append(RMB_NUMS[0]);
				}
				// 插入[万]或者[亿]
				if (j % 4 == 0) {
					if (i > 0 && integer.charAt(i - 1) != '0' || i > 1
							&& integer.charAt(i - 2) != '0' || i > 2
							&& integer.charAt(i - 3) != '0') {
						buffer.append(U2[j / 4]);
					}
				}
			} else {
				if (j % 4 == 0) {
					buffer.append(U2[j / 4]); // 插入[万]或者[亿]
				}
				buffer.append(U1[j % 4]); // 插入[拾]、[佰]或[仟]
				buffer.append(RMB_NUMS[n - '0']); // 插入数字
			}
		}
		return buffer.reverse().toString();
	}

	/**
	 * 对金额的格式调整到分
	 * 
	 * @param money
	 * @return
	 */
	public static String moneyFormat(String money) {// 23->23.00
		StringBuffer sb = new StringBuffer();
		if (money == null) {
			return "0.00";
		}
		int index = money.indexOf(".");
		if (index == -1) {
			return money + ".00";
		} else {
			String s0 = money.substring(0, index);// 整数部分
			String s1 = money.substring(index + 1);// 小数部分
			if (s1.length() == 1) {// 小数点后一位
				s1 = s1 + "0";
			} else if (s1.length() > 2) {// 如果超过3位小数，截取2位就可以了
				s1 = s1.substring(0, 2);
			}
			sb.append(s0);
			sb.append(".");
			sb.append(s1);
		}
		return sb.toString();
	}
	
    /**
     * 将分转化为元
     * @param amt
     * @return
     */
	public static String fen2yuan(String amt) {
		BigDecimal decimal = new BigDecimal(amt).divide(BigDecimal.valueOf(100)); 
		String  txAmt =String.valueOf(decimal.setScale(2));
		return txAmt;
	}
	
    /**
     * 将元转化为分
     * @param amt
     * @return
     */
	public static String yuan2fen(String amt) {
		BigDecimal decimal = new BigDecimal(amt).multiply(BigDecimal.valueOf(100)); 
		String  txAmt =String.valueOf(decimal.setScale(0));
		return txAmt;
	}
	
    /**
     * 将字符串转化为BigDecimal
     * @param amt
     * @return
     */
	public static BigDecimal toDecimal(String amt) {
		BigDecimal decimal = new BigDecimal(amt);
		return decimal;
	}
	
    /**
     * 转化成元
     * @param amt
     * @return
     */
	public static String toYuan(Double amt) {
		DecimalFormat df  = new DecimalFormat("###.00");
		return df.format(amt);
	}
	
	/**
	 * 加法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法运算
	 * 
	 * @param v1被减数
	 * @param v2减数
	 * @return
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 乘法运算
	 * 
	 * @param v1被乘数
	 * @param v2乘数
	 * @return
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 乘法运算 四舍五入保留两位
	 * 
	 * @param v1被乘数
	 * @param v2乘数
	 * @return
	 */
	public static BigDecimal mulBigDecimal(BigDecimal v1, Integer v2) {
		BigDecimal b2 = new BigDecimal(v2);
		return v1.multiply(b2).setScale(
				Global.DEFAULT_ZERO_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 
	 * 除法运算，当发生除不尽的情况时，精确到小数点以后2位，以后的数字四舍五入
	 * 
	 * @param v1被除数
	 * @param v2除数
	 * @return
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, 2);
	}

	/**
	 * 
	 * 除法运算，当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
	 * 
	 * @param v1被除数
	 * @param v2除数
	 * @param scale精确到小数点以后几位
	 * @return
	 */
	public static double div(double v1, double v2, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * 四舍五入
	 * 
	 * @param v需要四舍五入的数字
	 * @param scale小数点后保留几位
	 * @return
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 判断 a 与 b 是否相等
	 * 
	 * @param a
	 * @param b
	 * @return a==b 返回true, a!=b 返回false
	 */
	public static boolean equal(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断 a 是否大于等于 b
	 * 
	 * @param a
	 * @param b
	 * @return a&gt;=b 返回true, a&lt;b 返回false
	 */
	public static boolean greaterThanOrEqualTo(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断 a 是否大于 b
	 * 
	 * @param a
	 * @param b
	 * @return a&gt;b 返回true, a&lt;=b 返回 false
	 */
	public static boolean bigger(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断 a 是否小于 b
	 * 
	 * @param a
	 * @param b
	 * @return a&lt;b 返回true, a&gt;=b 返回 false
	 */
	public static boolean lessThan(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) == -1) {
			return true;
		}
		return false;
	}

	/**
	 * 四舍五入保留小数点后两位
	 * 
	 * @param num
	 * @return
	 */
	public static double roundDown(double num) {
		return Double.valueOf(String.format("%.2f", num));
	}

}
