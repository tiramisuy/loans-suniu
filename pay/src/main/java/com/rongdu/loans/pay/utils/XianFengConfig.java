package com.rongdu.loans.pay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**  
* @Title: XianFengConfig.java  
* @Description: 先锋支付配置参数
* @author: yuanxianchu  
* @date 2018年6月2日  
* @version V1.0  
*/
public class XianFengConfig {
	
	private static String CONFIG_FILE = "config.properties";
	//
	public static String merchantId;
	//
	public static String merRSAKey;
	//
	public static String gateway;
	//
	public static String req_withholding_returnUrl;
	//
	public static String req_withholding_noticeUrl;
	
	static {
		String propertiesFileName = "/"+CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method =null;
		try {
			in = XianFengConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);	
			Class<XianFengConfig> clazz = XianFengConfig.class;
			XianFengConfig obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields){
				fieldName = field.getName();
				if(properties.containsKey(fieldName)){
					method = Properties.class.getDeclaredMethod("getProperty", String.class);
					value = (String) method.invoke(properties, fieldName);
					if (null!=value) {
						fieldType = field.getType().getName();
					    if (String.class.getName().equals(fieldType)) {
							field.set(obj, value);
						}else if ("int".equals(fieldType)) {
							field.setInt(obj, Integer.parseInt(value));
						}else if ("long".equals(fieldType)) {
							field.setLong(obj, Long.parseLong(value));
						}else if ("boolean".equals(fieldType)) {
							field.setBoolean(obj, Boolean.parseBoolean(value));
						}else {
							
						}
					}					
				}
			}
		} catch (Exception e) {
			System.err.println("载入配置文件“/config.properties”时发生错误");
			e.printStackTrace();
		}finally{
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
