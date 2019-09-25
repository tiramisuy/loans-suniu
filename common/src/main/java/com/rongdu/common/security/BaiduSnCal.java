package com.rongdu.common.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 百度签名工具类
 * @author likang
 * @version 
 */
public class BaiduSnCal {
	/**
	 * 生成SN
	 * @param paramsMap
	 * @param sk
	 * @param intfaceName
	 * @return
	 */
	public static String genSn(LinkedHashMap<String, String> paramsMap, String sk, String intfaceName) {
		if(null != paramsMap && paramsMap.size() > 0) {
			try {
				// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，
				String paramsStr = toQueryString(paramsMap);
				
				// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
				StringBuilder sb = new StringBuilder();
				sb.append(intfaceName).append(paramsStr).append(sk);
			    // String wholeStr = new String(intfaceName + paramsStr + sk);
                
                // 对上面wholeStr再作utf8编码
                // String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
				String tempStr = URLEncoder.encode(sb.toString(), "UTF-8");
 
                // 调用下面的MD5方法得到最后的sn签名
                return MD5(tempStr);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(Map<?, ?> data)
                    throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Entry<?, ?> pair : data.entrySet()) {
                queryString.append(pair.getKey() + "=");
                queryString.append(
                		URLEncoder.encode(
                				(String) pair.getValue(), "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
                queryString.deleteCharAt(
                		queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String MD5(String md5) {
        try {
                java.security.MessageDigest md = java.security.MessageDigest
                                .getInstance("MD5");
                byte[] array = md.digest(md5.getBytes());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < array.length; ++i) {
                        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                                        .substring(1, 3));
                }
                return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
