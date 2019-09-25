package com.rongdu.loans.zhicheng.common;

import com.rongdu.common.config.Global;
import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 宜信致诚-配置参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class ZhichengcreditConfig {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;

	// 用户ID
	public static String userId;
	// 秘钥
	public static String password;

	/************ 业务/产品/服务 ************/
	// 风险名单查询-业务代码
	public static String mixed_risk_list_biz_code;
	// 风险名单查询-业务名称
	public static String mixed_risk_list_biz_name;
	// 风险名单查询-服务地址
	public static String mixed_risk_list_url;
	
	//查询借款、风险和逾期信息-业务代码
	public static String echo_query_api_biz_code;
	//查询借款、风险和逾期信息-业务名称
	public static String echo_query_api_biz_name;
	//查询借款、风险和逾期信息-服务地址
	public static String echo_query_api_url;

	//查询宜信阿福综合决策报告接口-业务代码
	public static String echo_query_decision_biz_code;
	//查询宜信阿福综合决策报告接口-业务名称
	public static String echo_query_decision_biz_name;
	//查询宜信阿福综合决策报告接口
	public static String echo_query_decision_url;

	//宜信阿福欺诈甄别接口-业务代码
	public static String echo_query_fraudscreen_biz_code;
	//宜信阿福欺诈甄别接口-业务名称
	public static String echo_query_fraudscreen_biz_name;
	//宜信阿福欺诈甄别接口
	public static String echo_query_fraudscreen_url;

	static {
		String propertiesFileName = Global.getConfig("zhicheng.config");
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = ZhichengcreditConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<ZhichengcreditConfig> clazz = ZhichengcreditConfig.class;
			ZhichengcreditConfig obj = clazz.newInstance();
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
			System.err.println("载入配置文件"+CreditContants.ZHICHENGCREDIT_CONFIG_FILE+"时发生错误");
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

	private ZhichengcreditConfig() {
	}

}
