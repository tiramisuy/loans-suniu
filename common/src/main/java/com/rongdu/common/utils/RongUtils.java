package com.rongdu.common.utils;

import com.rongdu.common.security.Digests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 */
public class RongUtils {

    // 日志对象
    protected static Logger logger = LoggerFactory.getLogger(RongUtils.class);


    public static String formatFloat4(Float value) {
        if (value == null) {
            return "0.0000";
        }
        DecimalFormat df = new DecimalFormat("0.0000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    public static String formatFloat2(Float value) {
        if (value == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }


    public static String convertDegree(String degree) {
        String res = null;
        switch (degree) {
            case "1":
                res = "4";
                break;
            case "2":
                res = "3";
                break;
            case "3":
                res = "2";
                break;
            case "4":
                res = "1";
                break;
            default:
                res = "";
        }
        return res;
    }

    public static String convertARelation(String Relation) {
        String res = null;
        switch (Relation) {
            case "1":
            case "3":
            case "4":
            case "6":
            case "7":
                res = "1";
                break;
            case "2":
                res = "2";
                break;
            default:
                res = "1";
        }
        return res;
    }

    public static String convertBRelation(String Relation) {
        String res = null;
        switch (Relation) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                res = "3";
                break;
            default:
                res = "3";
        }
        return res;
    }

}
