package com.rongdu.loans.hanjs.util;


import org.apache.commons.codec.digest.DigestUtils;

import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.*;


public class MD5Util {
	
	public static   String[] chars = new String[] { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9" };
	private static final ThreadLocal threadSession = new ThreadLocal();
	/**  
     * 将二进制转换成16进制  
     *   
     * @param buf  
     * @return  
     */  
    public static String parseByte2HexStr(byte buf[]) { 
    	StringBuffer sb = new StringBuffer();  
    	sb=	(StringBuffer) threadSession.get();
    	if(sb.length()==0){
            for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                    hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
            } 
            threadSession.set(sb);
    	}
        

        return sb.toString();  
    }  
  //转换十六进制编码为字符串  
    public static String toStringHex(String s) {  
        if ("0x".equals(s.substring(0, 2))) {  
            s = s.substring(2);  
        }  
        byte[] baKeyword = new byte[s.length() / 2];  
        for (int i = 0; i < baKeyword.length; i++) {  
            try {  
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(  
                        i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
  
        try {  
            s = new String(baKeyword, "utf-8");//UTF-16le:Not  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return s;  
    }  
    //转换成16进制
    public static String toHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch);  
            str = str + s4;  
        }  
        return "0x" + str;//0x表示十六进制  
    }  
    /**  
     * 将16进制转换为二进制  
     *   
     * @param hexStr  
     * @return  
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length() / 2];  
        for (int i = 0; i < hexStr.length() / 2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);  
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),  
                    16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }  
    public static byte[] getMd5(String input) {  
        try {  
            byte[] by = input.getBytes("UTF-8");  
            MessageDigest det = MessageDigest.getInstance("MD5");  
            return det.digest(by);  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        shortBuffer.append("");
//      StringBuffer   sb=	(StringBuffer) threadSession.get();
        String uuid = UUID.randomUUID().toString().replace("-", "");
//    	if(sb==null){
    		
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 10]);
        }
//        threadSession.set(shortBuffer);
        return shortBuffer.toString();
//    	}else{
//    		return sb.toString();
//    	}
     
    }
   public  static String md5(String text) {
		byte[] bts;
		try {
			bts = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bts_hash = md.digest(bts);
			StringBuffer buf = new StringBuffer();
			for (byte b : bts_hash) {
				buf.append(String.format("%02X", b & 0xff));
			}
			return buf.toString();
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
   
   /**
    * 获得MD5加密后的sign
    * @param reqMap
    * @return
    */
   public static String getMd5Sign(Map<String, Object> reqMap){
       //根据key排序
       Map<String, Object> resultMap = MapUtils.sortMapByKey(reqMap);
       //获得没有加密的sign
       String sign = getSignValue(resultMap);
       //System.out.println("待签名字符串:"+sign);
       //获得加密后的sign
       String md5Sign = MD5Util.md5(sign).toLowerCase();

       return md5Sign;
   }

   /**
    * 根据参数获得sign
    *
    * @param map
    * @return
    */
   public static String getSignValue(Map<String, Object> map) {
       StringBuffer singValue = new StringBuffer();
       for (Map.Entry<String, Object> entry : map.entrySet()) {
           if (entry.getValue() != null) {
               singValue.append(entry.getValue());
           }
       }
       return singValue.toString();
   }
   
   /**
    * 验签
    */
   public static Boolean checkSign(String sign,Map<String, Object> reqMap) {
	   String md5Sign = getMd5Sign(reqMap);
	   if (md5Sign.equals(sign)) {
		return true;
	   }
	   return false;
   }

    /**
     * 获取第三方md5
     * @param params
     * @param encode
     * @param privateKey
     * @return
     */
    public static String createSign(Map<String, String> params, boolean encode, String privateKey)  {

        // 抽取Map参数的key，按字母排序
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        // 拼接的Map参数key与value
        StringBuilder temp = new StringBuilder();

        for (Object key : keys) {
            if ("sign".equals(key)) {
                continue;
            }

            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            if (encode) {
                try {
                    temp.append(URLDecoder.decode(valueString, "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                temp.append(valueString);
            }
        }

        String plain = temp.append(privateKey).toString();
        return DigestUtils.md5Hex(plain);
    }
   
    @SuppressWarnings("static-access")  
    public static void main(String[] args) {
		Map<String, String> reqMap = new HashMap<>();
        reqMap.put("name", "冯志勇");
        reqMap.put("idNo", "231005198709300011");
        // 第三方机构提供的渠道号
        reqMap.put("channelId", "10001");
		System.out.println(createSign(reqMap,true,"jcfq2019"));
}
}
