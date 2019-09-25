package com.rongdu.loans.baiqishi.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 白骑士-配置参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class BaiqishiConfig {

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;

	// 应用ID
	public static String appId;
	// 商户ID
	public static String partnerId;
	// 验证秘钥
	public static String verifyKey;
	//芝麻信用商户ID
	public static String linkedMerchantId;
	
	/************ 业务/产品/服务 ************/
	//芝麻信用相关接口URL
	public static String zhima_url;
	//芝麻信用回调地址
	public static String zhima_callback_url;
	//芝麻信用H5回调地址
	public static String zhima_h5_callback_url;

	//芝麻信用授权-业务代码
	public static String zhima_authorize_biz_code;
	//芝麻信用授权-业务名称
	public static String zhima_authorize_biz_name;

	//查询芝麻信用授权结果-业务代码
	public static String zhima_authorize_result_biz_code;
	//查询芝麻信用授权结果-业务名称
	public static String zhima_authorize_result_biz_name;

	//查询芝麻信用分-业务代码
	public static String zhima_zmscore_biz_code;
	//查询芝麻信用分-业务名称
	public static String zhima_zmscore_biz_name;

	//查询芝麻行业关注名单-业务代码
	public static String zhima_watchlist_biz_code;
	//查询芝麻行业关注名单-业务名称
	public static String zhima_watchlist_biz_name;
	
	//白骑士设备指纹（Device Fingerprint）-查询设备信息
	public static String device_info_url;
	//查询设备信息-业务代码
	public static String device_info_biz_code;
	//查询设备信息-业务名称
	public static String device_info_biz_name;

	//白骑士设备指纹（Device Fingerprint）-通讯信息查询
	public static String contact_info_url;
	//查询设备信息-业务代码
	public static String contact_info_biz_code;
	//查询设备信息-业务名称
	public static String contact_info_biz_name;	
	
	// 白骑士反欺诈风险决策引擎
	public static String decision_url;
	// 反欺诈风险决策引擎-业务代码
	public static String decision_biz_code;
	// 反欺诈风险决策引擎-业务名称
	public static String decision_biz_name;
	// 反欺诈风险决策引擎-事件类型-默认
	public static String decision_event_type_default;
	// 反欺诈风险决策引擎-事件类型-复贷
	public static String decision_event_type_reloan;
	
	//获取登录令牌
	public static String gettoken_url;
	//获取登录令牌-业务代码
	public static String gettoken_biz_code;
	//反欺诈风险决策引擎-业务名称
	public static String gettoken_biz_name;

	//获取资信云报告页面
	public static String getreportpage_url;
	//获取资信云报告页面-业务代码
	public static String getreportpage_biz_code;
	//获取资信云报告页面-业务名称
	public static String getreportpage_biz_name;

	//获取资信云报告数据
	public static String getreport_url;
	//获取资信云报告数据-业务代码
	public static String getreport_biz_code;
	//获取资信云报告数据-业务名称
	public static String getreport_biz_name;

	//给资信云上报额外参数-请求地址
	public static String reportext_url;
	//给资信云上报额外参数-业务代码
	public static String reportext_biz_code;
	//给资信云上报额外参数-业务名称
	public static String reportext_biz_name;

	//获取运营商原始数据
	public static String getoriginal_url;
	//获取运营商原始数据-业务代码
	public static String getoriginal_biz_code;
	//获取运营商原始数据-业务名称
	public static String getoriginal_biz_name;
	
	//上传查询催收指标用户
	public static String uploadcuishou_url;
	//上传查询催收指标用户-业务代码
	public static String uploadcuishou_biz_code;
	//上传查询催收指标用户-业务名称
	public static String uploadcuishou_biz_name;

	static {
		String propertiesFileName = CreditContants.BAIQISHI_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = BaiqishiConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<BaiqishiConfig> clazz = BaiqishiConfig.class;
			BaiqishiConfig obj = clazz.newInstance();
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

	private BaiqishiConfig() {
	}

}
