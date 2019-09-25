package com.rongdu.loans.credit100.common;

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
public class Credit100Config {

	//合作厂商代码
	public static String partner_id;
	//合作厂商名称
	public static String partner_name;

	//api用户名
	public static String api_username;
	//api密码
	public static String api_password;
	//api认证码
	public static String api_code;
	//api名称
	public static String api_name;
	
	//接口应答成功代码
	public static String succ_code;
	//是否需要重新登录的标识
	public static String re_login_code;
	
	/************业务/产品/服务 ************/
	//业务代码
	public static String rulespeciallist_biz_code;
	//业务名称
	public static String rulespeciallist_biz_name;
	
	//业务代码
	public static String ruleapplyloan_biz_code;
	//业务名称
	public static String ruleapplyloan_biz_name;
	
	//业务代码
	public static String brcreditpoint_biz_code;
	//业务名称
	public static String brcreditpoint_biz_name;
	
	//业务代码
	public static String speciallistc_biz_code;
	//业务名称
	public static String speciallistc_biz_name;
	
	//业务代码
	public static String applyloand_biz_code;
	//业务名称
	public static String applyloand_biz_name;
	
	//业务代码
	public static String applyloanmon_biz_code;
	//业务名称
	public static String applyloanmon_biz_name;

	static {
		String propertiesFileName = CreditContants.CREDIT100_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = Credit100Config.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<Credit100Config> clazz = Credit100Config.class;
			Credit100Config obj = clazz.newInstance();
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

	private Credit100Config() {
	}

}
