package com.rongdu.loans.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;


/**  
* @Title: Rong360Config.java  
* @Package com.rongdu.loans.common  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月3日  
* @version V1.0  
*/
public class Rong360Config {

	private static String CONFIG_FILE = "config.properties";
	public static String rong360_app_id;
	public static String rong360_gateway;
	public static String rong360_private_key;
	
	public static String rong_tianji_app_id;
	public static String rong_tianji_gateway;
	public static String rong_tianji_private_key;
	public static String rong_tianji_report_notifyurl = "http://cps.jubaoqiandai.com/rong360/tianJiReportCallback";
	static {
		String propertiesFileName = "/"+CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method =null;
		try {
			in = Rong360Config.class.getResourceAsStream(propertiesFileName);
			properties.load(in);	
			Class<Rong360Config> clazz = Rong360Config.class;
			Rong360Config obj = clazz.newInstance();
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
