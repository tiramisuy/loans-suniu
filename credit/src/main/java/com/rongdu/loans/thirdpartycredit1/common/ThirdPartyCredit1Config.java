package com.rongdu.loans.thirdpartycredit1.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 三方征信1-配置参数
 * 
 * @author liouzhuang
 * 
 * @version 2018-03-26
 */
public class ThirdPartyCredit1Config {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;
	// 合作厂商key
	public static String partner_key;

	// 黑名单查询-业务代码
	public static String blacklist_biz_code;
	// 黑名单查询-业务名称
	public static String blacklist_biz_name;
	// 黑名单查询-接口URL
	public static String blacklist_url;


	// 黑名单查询2-业务代码
	public static String blacklist2_biz_code;
	// 黑名单查询2-业务名称
	public static String blacklist2_biz_name;
	
	// 黑名单查询2-接口URL
	public static String blacklist2_url;

	/************ 业务/产品/服务 ************/

	static {
		String propertiesFileName = CreditContants.THIRDPARTYCREDIT1_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = ThirdPartyCredit1Config.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<ThirdPartyCredit1Config> clazz = ThirdPartyCredit1Config.class;
			ThirdPartyCredit1Config obj = clazz.newInstance();
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
			System.err.println("载入配置文件" + CreditContants.CREDIT100_CONFIG_FILE + "时发生错误");
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

	private ThirdPartyCredit1Config() {
	}

}
