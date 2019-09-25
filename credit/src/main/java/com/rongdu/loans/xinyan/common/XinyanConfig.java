package com.rongdu.loans.xinyan.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 新颜-配置参数
 * 
 * @author liuzhuang
 * 
 * @version 2018-05-18
 */
public class XinyanConfig {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;
	// 商户号
	public static String member_id;
	// 终端号
	public static String terminal_id;
	// 公钥
	public static String cer_path;
	// 私钥
	public static String pfx_path;
	// 私钥密码
	public static String pfx_password;

	// 负面拉黑-业务代码
	public static String black_biz_code;
	// 负面拉黑-业务名称
	public static String black_biz_name;
	// 负面拉黑-接口URL
	public static String black_url;

	// 共债档案-业务代码
	public static String totaldebt_biz_code;
	// 共债档案-业务名称
	public static String totaldebt_biz_name;
	// 共债档案-接口URL
	public static String totaldebt_url;

	// 逾期档案-业务代码
	public static String overdue_biz_code;
	// 逾期档案-业务名称
	public static String overdue_biz_name;
	// 逾期档案-接口URL
	public static String overdue_url;

	// 全景雷达-业务代码
	public static String radar_biz_code;
	// 全景雷达-业务名称
	public static String radar_biz_name;
	// 全景雷达-接口URL
	public static String radar_url;

	// 负面拉黑-聚宝钱包-业务代码
	public static String black_jbqb_biz_code;
	// 负面拉黑-聚宝钱包-业务名称
	public static String black_jbqb_biz_name;
	// 负面拉黑-聚宝钱包-接口URL
	public static String black_jbqb_url;

	/************ 业务/产品/服务 ************/

	static {
		String propertiesFileName = CreditContants.XINYAN_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = XinyanConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<XinyanConfig> clazz = XinyanConfig.class;
			XinyanConfig obj = clazz.newInstance();
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

	private XinyanConfig() {
	}

}
