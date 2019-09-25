package com.rongdu.loans.api.common;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.utils.MD5Util;

public class LoanWalletUtil {

    private final static String SIGN_KEY = "123";


    public static boolean checkSign(String sign, String code, String method, JSONObject dataJSONObject) {
        String data = dataJSONObject.toString();
        //拼装code、method、data与signKey拼装生成signStr
        String signStr = new StringBuilder().append(code).append(method).append(data).append(SIGN_KEY).toString();
        String md5 = MD5Util.string2MD5(signStr);
        System.out.println(md5);
        return md5.equals(sign);
    }
}
