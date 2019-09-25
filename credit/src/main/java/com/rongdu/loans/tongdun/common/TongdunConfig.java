package com.rongdu.loans.tongdun.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 百融金服-配置参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class TongdunConfig {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;
	// 合作方标识
	public static String partner_code;
	// 合作方密钥
	public static String partner_key;
	//环境
	public static String env;

	// android应用名称
	public static String android_app_name;
	// android应用秘钥
	public static String android_secret_key;
	// android事件标识
	public static String android_event_id;

	// ios应用名称
	public static String ios_app_name;
	// ios应用秘钥
	public static String ios_secret_key;
	// ios事件标识
	public static String ios_event_id;

	//反欺诈决策引擎-业务代码
	public static String antifraud_biz_code;
	//反欺诈决策引擎-业务名称
	public static String antifraud_biz_name;
	//反欺诈决策引擎-接口URL
	public static String antifraud_url;
	//反欺诈决策引擎-费用
	public static String antifraud_fee;

	//命中规则详情-业务代码
	public static String rule_detail_biz_code;
	//命中规则详情-业务名称
	public static String rule_detail_biz_name;
	//命中规则详情-接口URL
	public static String rule_detail_url;
	//命中规则详情-费用
	public static String rule_detail_fee;

	/************ 业务/产品/服务 ************/

	static {
		String propertiesFileName = CreditContants.TONGDUN_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = TongdunConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<TongdunConfig> clazz = TongdunConfig.class;
			TongdunConfig obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				fieldName = field.getName();
				if (properties.containsKey(fieldName)) {
					method = Properties.class.getDeclaredMethod("getProperty",
							String.class);
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
			System.err.println("载入配置文件" + CreditContants.CREDIT100_CONFIG_FILE
					+ "时发生错误");
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

	private TongdunConfig() {
	}

}
