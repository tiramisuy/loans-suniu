package com.rongdu.loans.api.common;


import com.rongdu.loans.loan.option.rong360Model.Rong360Req;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Rong360Util {
    // 订单状态映射
    private static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
    // 审核状态映射
    private static Map<String, String> APPROVE_STATUS_MAP = new HashMap<String, String>();
    // 账单状态映射
    private static Map<String, String> BILL_STATUS_MAP = new HashMap<String, String>();


    static {
        ORDER_STATUS_MAP.put("9", "170");
        ORDER_STATUS_MAP.put("13", "180");
        ORDER_STATUS_MAP.put("6", "200");
        ORDER_STATUS_MAP.put("7", "110");
        ORDER_STATUS_MAP.put("8", "110");
        ORDER_STATUS_MAP.put("4", "100");
        ORDER_STATUS_MAP.put("11", "100");
        ORDER_STATUS_MAP.put("12", "100");
        ORDER_STATUS_MAP.put("14", "100");
        ORDER_STATUS_MAP.put("5", "100");

        APPROVE_STATUS_MAP.put("7", "40");
        APPROVE_STATUS_MAP.put("8", "40");
        APPROVE_STATUS_MAP.put("4", "10");
        APPROVE_STATUS_MAP.put("11", "10");
        APPROVE_STATUS_MAP.put("12", "10");
        APPROVE_STATUS_MAP.put("14", "10");
        APPROVE_STATUS_MAP.put("9", "10");
        APPROVE_STATUS_MAP.put("5", "10");
        APPROVE_STATUS_MAP.put("6", "10");
        APPROVE_STATUS_MAP.put("13", "10");

        BILL_STATUS_MAP.put("9", "1");
        BILL_STATUS_MAP.put("6", "2");
        BILL_STATUS_MAP.put("13", "3");
    }

    public static String convertOrderStatus(Long oriStatus) {
        if (oriStatus == null) {
            return null;
        }

        return ORDER_STATUS_MAP.get(String.valueOf(oriStatus));
    }

    public static String convertApproveStatus(Long oriStatus) {
        if (oriStatus == null) {
            return null;
        }

        return APPROVE_STATUS_MAP.get(String.valueOf(oriStatus));
    }

    public static String convertBillStatus(Long oriStatus) {
        if (oriStatus == null) {
            return null;
        }

        return BILL_STATUS_MAP.get(String.valueOf(oriStatus));
    }



    public static String checkSign(Rong360Req req) {
        try {
            RsaEncrypt.loadPublicKey(RsaEncrypt.RONG_PUBLIC_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String encryptStr = req.getBiz_data();
        String signNative = req.getSign();
        try {
            // 签名验证
            Boolean isok = RsaEncrypt.doCheck(encryptStr, Base64Utils.decode(signNative),
                    RsaEncrypt.getPublicKey());
            if (isok == false) {
                return "验证签名失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String check(Rong360Req req) {
        String result = null;

        if (req == null) {
            return "请求内容为空";
        }

        if (StringUtils.isBlank(req.getBiz_data())) {
            return "请求业务数据为空";
        }

        if (StringUtils.isBlank(req.getSign())) {
            return "请求签名为空";
        }
        checkSign(req);
        return result;
    }


    public static int getAgeByIdCard(String idCard) {
        Calendar c = Calendar.getInstance();
        int age = 0;
        int year = c.get(Calendar.YEAR);
        String ageNum = idCard.substring(6, 10);
        age = year - Integer.parseInt(ageNum);

        return age;
    }

    public static int getSexByIdCard(String idCard) {
        int sex = 0;
        String sexNum = idCard.substring(idCard.length() - 2, idCard.length() - 1);
        if ((Integer.parseInt(sexNum)) % 2 == 0) {
            sex = 0;
        } else {
            sex = 1;
        }
        return sex;
    }

    private static char[] getChar() {
        char[] passwordLit = new char[62];
        char fword = 'A';
        char mword = 'a';
        char bword = '0';
        for (int i = 0; i < 62; i++) {
            if (i < 26) {
                passwordLit[i] = fword;
                fword++;
            } else if (i < 52) {
                passwordLit[i] = mword;
                mword++;
            } else {
                passwordLit[i] = bword;
                bword++;
            }
        }
        return passwordLit;
    }


    public static String getRandomPwd() {
        char[] r = getChar();
        Random rr = new Random();
        char[] pw = new char[6];
        for (int i = 0; i < pw.length; i++) {
            int num = rr.nextInt(62);
            pw[i] = r[num];
        }

        return new String(pw);
    }



}