package com.rongdu.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
public class DateUtil {
    /**
     * 格式化
     */
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 格式化日期字符串为日期
     *
     * @param dateStr
     * @param fmt
     * @return
     */
    public static Date parseDate(String dateStr, String fmt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                fmt == null ? "yyyy-MM-dd HH:mm:ss" : fmt);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当前日期的字符串
     *
     * @return
     */
    public static String getDisplayDate() {
        return yyyyMMdd.format(new Date());
    }


    /**
     * 向前or向后推Day
     *
     * @param day
     * @return
     */
    public static Date getDateToDay(int day) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        date = calendar.getTime();
        return date;
    }

    /**
     * 向前or向后推month
     *
     * @param day
     * @return
     */
    public static Date getDateToMonth(int month) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        date = calendar.getTime();
        return date;
    }


    /**
     * 时间对比,大于返回1,小于返回-1
     *
     * @param date
     * @return
     */
    public static int dateCompare(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(yyyyMMdd.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.roll(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime().compareTo(new Date());
    }

    /**
     * 获取本月最后一天
     *
     * @param month
     * @return
     */
    public static Date getLastDayInMonth(Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取传递月份月第一天
     *
     * @param month
     * @return
     */
    public static Date getFristDayInMonth(Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

}
