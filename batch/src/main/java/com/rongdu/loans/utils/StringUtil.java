package com.rongdu.loans.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;


public class StringUtil {
	
	public static Object convertType(String strValue, Class fieldType) {
		Object value =null;
		String type = fieldType.getName();
		if("java.lang.Integer".equals(type)){
			value = Integer.parseInt(strValue);
		}else if ("java.lang.Long".equals(type)){
			value = Long.parseLong(strValue);
		}else if ("java.lang.Double".equals(type)){
			value = Double.parseDouble(strValue);
		}else {
			value = strValue;
		}
		return value;
	}
	
	public static String printJarPath(Class<?> clazz){
		return clazz.getName()+"："+clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
	}
	
	
	public static String getShortname(String str) {
		char[] array = str.toCharArray();
		int len = array.length;
		char j = 0;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < len; i++) {
			j =array[i];
			if (j <97) {
				j +=32; 
				builder.append(j);
			}
		}
		return builder.toString();
	}
	
	public static String tryCharset(String str){
		String[] cs  = {"UTF-8","GBK","GB2312","ISO-8859-1","GB18030"};
		String result = null;
		String from = null,to = null;
		for (int i = 0; i < cs.length; i++) {
			for (int j = 0; j < cs.length; j++) {
				from = cs[i];
				to = cs[j];
				try {
					result = new String(str.getBytes(from), to);
					System.out.println("--------------------------"+from+"—>"+to+"------------------------");
					System.out.println(result);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String tryCharset(byte[] input){
		String[] cs  = {"UTF-8","GBK","GB2312","ISO-8859-1","GB18030"};
		String result = null;
		String from = null,to = null;
		try {
			for (int i = 0; i < cs.length; i++) {
				System.out.println("--------------------------"+cs[i]+"------------------------");
				System.out.println(IOUtils.toString(input, cs[i]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
