package com.rongdu.loans.tencent.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 腾讯-配置参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class TencentConfig {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;

	// 密钥ID
	public static String secret_id;
	// 密钥值
	public static String secret_key;

	// 应用ID
	public static String appid;
	// 应用秘钥
	public static String secret;
	// SDK公钥
	public static String sdk_license;
	
	/************ 业务/产品/服务 ************/
	// 业务代码
	public static String antifraud_biz_code;
	// 业务名称
	public static String antifraud_biz_name;
	// 接口URL
	public static String antifraud_url;
	// 费用
	public static String antifraud_fee;
	
	// 业务代码
	public static String access_token_biz_code;
	// 业务名称
	public static String access_token_biz_name;
	// 接口URL
	public static String access_token_url;
	
	//业务代码
	public static String sign_ticket_biz_code;
	//业务名称
	public static String sign_ticket_biz_name;
	//接口URL
	public static String api_ticket_url;
	
	//业务代码
	public static String nonce_ticket_biz_code;
	//业务名称
	public static String nonce_ticket_biz_name;
	
	// 业务代码
	public static String face_verify_biz_code;
	// 业务名称
	public static String face_verify_biz_name;
	// 接口URL
	public static String face_verify_url;
	
	//业务代码
	public static String get_ocr_result_biz_code;
	//业务名称
	public static String get_ocr_result_biz_name;
	//接口URL
	public static String get_ocr_result_url;

	static {
		String propertiesFileName = CreditContants.TENCENT_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = TencentConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<TencentConfig> clazz = TencentConfig.class;
			TencentConfig obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				fieldName = field.getName();
				if (properties.containsKey(fieldName)) {
					method = Properties.class.getDeclaredMethod("getProperty",String.class);
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
			System.err.println("载入配置文件"+CreditContants.CREDIT100_CONFIG_FILE+"时发生错误");
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

	private TencentConfig() {
	}

}
