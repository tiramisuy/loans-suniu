package com.rongdu.loans.linkface.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 
* @Description:   商汤-配置参数
* @author: 饶文彪
* @date 2018年7月3日 上午10:17:25
 */
public class LinkfaceConfig {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;
	//人脸识别-业务代码
	public static String selfie_idnumber_verification_biz_code;
	//人脸识别-业务名称
	public static String selfie_idnumber_verification_biz_name;
	
	// API 账户
	public static String app_id;
	// API 密钥
	public static String api_secret;
	// 人脸识别接口
	public static String selfie_idnumber_verification_url;


	static {
		String propertiesFileName = CreditContants.LINKFACE_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = LinkfaceConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<LinkfaceConfig> clazz = LinkfaceConfig.class;
			LinkfaceConfig obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				fieldName = field.getName();
				if (properties.containsKey(fieldName)) {
					method = Properties.class.getDeclaredMethod("getProperty", String.class);
					value = (String) method.invoke(properties, fieldName);
					if (null != value) {
						fieldType = field.getType().getName();
						if (String.class.getName().equals(fieldType)) {
							field.set(obj, value);
						} else if ("int".equals(fieldType)) {
							field.setInt(obj, Integer.parseInt(value));
						} else if ("long".equals(fieldType)) {
							field.setLong(obj, Long.parseLong(value));
						} else if ("boolean".equals(fieldType)) {
							field.setBoolean(obj, Boolean.parseBoolean(value));
						} else {

						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("载入配置文件" + CreditContants.LINKFACE_CONFIG_FILE + "时发生错误");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private LinkfaceConfig() {
	}

}
