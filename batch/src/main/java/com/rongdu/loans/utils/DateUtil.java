package com.rongdu.loans.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间操作工具类
 * 
 * @author sunda
 *
 */
public class DateUtil {
	/**
	 * 将当前时间格式化为指定的字符串
	 * 
	 * @param format 字符串格式 如：yyyy-MM-dd HH:mm:ss SSS
	 * 
	 * @return
	 */
	public static String format(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String s = df.format(new Date());
		return s;
	}
	
	/**
	 * 格式化当前时间
	 * 
	 * @param format 时间格式 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return 时间串
	 */
	public static String format() {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		return format(defaultFormat);
	}
	
	/**
	 * 格式化当前时间
	 * 
	 * @param format 时间格式 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return 时间串
	 */
	public static String format(Date date) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
		String s = df.format(date);
		return s;
	}
	
	/**
	 * 将指定的时间格式格式化为指定的时间字符串
	 * 
	 * @param date 时间对象
	 * 
	 * @param format 格式
	 * 
	 * @return 时间字符串
	 */
	public static String format(Date date,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String s = df.format(date);
		return s;
	}
	
	/**
	 * 将指定格式的字符串格式化成时间
	 * 
	 * @param date 时间串
	 * 
	 * @param format 格式
	 * 
	 * @return 时间对象
	 */
	public static Date parse(String date,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dt = null;
		try {
			dt = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}	
	
	/**
	 * 获取昨天年月日  yyyy-MM-dd
	 * @return
	 */
	public static String getPrevDay(String format){
		Calendar calendar=Calendar.getInstance();
		long time=calendar.getTimeInMillis()-1000*60*60*24;
		calendar.setTimeInMillis(time);
		String date=format(calendar.getTime(), format);
		return date;
		
		
		
	}
	
	public static void main(String[] args) {
//		System.out.println(parse("2014/1/15","yyyy/MM/dd"));;
		System.out.println(getPrevDay("yyyy/MM/dd"));
	}
	
}