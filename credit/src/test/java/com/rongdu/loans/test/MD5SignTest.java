package com.rongdu.loans.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MD5SignTest {
	
	public static void main(String[] args) {
		String str = "123456";
		String md5 = md5(str);
		System.out.println("明文："+str+"，摘要："+md5);
		
		String appkey = "1234567890";
		Map<String, String> map  = new HashMap<String, String>();
		map.put("userId", "123456");
		map.put("userName", "zhangsan");
		map.put("age", "20");
		map.put("sex", "F");
		getSign(map, appkey);
		System.out.println(md5("rongdu_testusr"+"46bcasdasd4d3d77"));
//		明文：123456，摘要：e10adc3949ba59abbe56e057f20f883e
//		请求字符串：age=20&sex=F&userId=123456&userName=zhangsan&key=1234567890
//		签名：3CFABFB655BB066678B32F477BC1FE87
	}

	public static String md5(String str) {
		MessageDigest md = null;
		byte[]  input= null;
		String md5 = null;
		try {
			md = MessageDigest.getInstance("MD5");
			input = md.digest(str.getBytes("UTF-8"));
			StringBuffer buf = new StringBuffer();
			for (byte b : input) {
				buf.append(String.format("%02x", b & 0xff));
			}
			md5 = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	public static String getSign(Map<String, String> map, String key) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + key;
		System.out.println("请求字符串："+result);	
		result = md5(result).toUpperCase();
		System.out.println("签名："+result);	
		return result;
	}
	
	
	
}
