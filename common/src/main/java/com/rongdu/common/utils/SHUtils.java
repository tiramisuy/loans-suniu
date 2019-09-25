package com.rongdu.common.utils;

/**
 * 根据身份证去生肖
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/28
 * 
 */
public class SHUtils {
    public static boolean trueIdCard(String idCard) {
        if (idCard.length() == 18)
            return true;
        else
            return false;
    }

    public static String getShengXiao(String idCard) {
        if (!trueIdCard(idCard))
            return "";
        String SX[] = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        int year = Integer.parseInt(idCard.substring(6, 10));
        int x = (year - 4) % 12;
        String ret = "";
        ret = SX[x];
        return ret;
    }

}