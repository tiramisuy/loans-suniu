package com.rongdu.loans.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 */
public class XianJinBaiKaConfig {

	private static String CONFIG_FILE = "config.properties";
	public static String xianjinbaika_ua;
	public static String xianjinbaika_signkey;
	public static String xianjinbaika_url;

	public static String xianjinbaikafq_ua;
	public static String xianjinbaikafq_signkey;

	static {
		String propertiesFileName = "/"+CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method =null;
		try {
			in = XianJinBaiKaConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<XianJinBaiKaConfig> clazz = XianJinBaiKaConfig.class;
			XianJinBaiKaConfig obj = clazz.newInstance();
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

	private XianJinBaiKaConfig(){}
	
}

