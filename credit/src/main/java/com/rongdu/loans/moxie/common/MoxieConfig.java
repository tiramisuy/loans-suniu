package com.rongdu.loans.moxie.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 魔蝎-配置参数
 * 
 * @author liuzhuang
 * 
 * @version 2018-05-18
 */
public class MoxieConfig {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;
	// body校验用的secret，不变
	public static String moxie_signature_secret;
	// 魔蝎分配的token
	public static String moxie_api_token;
	// webhook 设置的自己的token，魔蝎调用的时候，header中需要带着这个token
	public static String moxie_webhook_token;

	// 信用卡邮箱报告-业务代码
	public static String email_report_biz_code;
	// 信用卡邮箱报告-业务名称
	public static String email_report_name;
	// 信用卡邮箱报告-接口URL
	public static String email_report_url;

	// 网银报告-业务代码
	public static String bank_report_biz_code;
	// 网银报告-业务名称
	public static String bank_report_name;
	// 网银报告-接口URL
	public static String bank_report_url;

	/************ 业务/产品/服务 ************/

	static {
		String propertiesFileName = CreditContants.MOXIE_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = MoxieConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<MoxieConfig> clazz = MoxieConfig.class;
			MoxieConfig obj = clazz.newInstance();
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

	private MoxieConfig() {
	}

}
