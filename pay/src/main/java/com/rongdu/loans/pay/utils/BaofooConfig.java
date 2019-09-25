package com.rongdu.loans.pay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 
 * 宝付支付配置参数
 * 
 * @author sunda
 * 
 */
public class BaofooConfig {

	private static String CONFIG_FILE = "config.properties";
	// 默认编码格式
	public static String char_set;
	// 商户密钥
	public static String md5_key;
	// 网关支付终端号
	public static String pcpay_terminal_id;
	// 认证支付终端号
	public static String authpay_terminal_id;
	// 代扣终端号
	public static String withhold_terminal_id;
	// 代付终端号
	public static String withdraw_terminal_id;
	// 商户号
	public static String member_id;
	// 页面跳转地址(改成商户自已的地址)
	public static String pcpay_page_url;
	// 异步通知接收地址(改成商户自已的接收地址)
	public static String pcpay_return_url;
	// 通知类型（0：服务器通知，1：页面跳转+服务器通知）
	public static String notice_type;
	// 网关支付地址
	public static String pc_pay_url;
	// 付款到银行（订单需拆分）
	public static String withdraw_url;
	// 代付交易状态查证
	public static String withdraw_result_url;
	// 数字证书路径
	public static String keystore_path;
	// 数字证书密码
	public static String keystore_password;
	// 公钥路径
	public static String pubkey_path;
	// 代扣公钥路径
	public static String withhold_pubkey_path;
	// 认证支付地址
	public static String auth_pay_url;
	// 宝付异步通知服务器IP白名单
	public static String baofoo_ip_whitelist;
	// 卡bin查询接口地址
	public static String card_bin_url;
	// 预警短信发送到以下手机
	public static String alarm_mobiles;
	// 红包账户
	public static String hongbao_userid;
	// 内部IP许可名单
	public static String partner_ip_whitelist;

	// 合作厂商代码
	public static String partner_id;
	// 合作厂商名称
	public static String partner_name;

	// 预绑卡-业务代码
	public static String pre_bind_card_biz_code;
	// 预绑卡-业务名称
	public static String pre_bind_card_biz_name;

	// 确认绑卡-业务代码
	public static String confirm_bind_card_biz_code;
	// 确认绑卡--业务名称
	public static String confirm_bind_card_biz_name;

	// 直接绑卡-业务代码
	public static String direct_bind_card_biz_code;
	// 直接绑卡--业务名称
	public static String direct_bind_card_biz_name;

	// 预支付-业务代码
	public static String pre_auth_pay_biz_code;
	// 预支付--业务名称
	public static String pre_auth_pay_biz_name;

	// 确认支付-业务代码
	public static String confirm_auth_pay_biz_code;
	// 确认支付--业务名称
	public static String confirm_auth_pay_biz_name;

	// 代扣-业务代码
	public static String withhold_biz_code;
	// 代扣--业务名称
	public static String withhold_biz_name;
	// 协议支付
	public static String agreement_pay_url;
	public static String agreement_member_id;
	public static String agreement_terminal_id;
	public static String agreement_keystore_path;
	public static String agreement_pubkey_path;
	public static String agreement_keystore_password;
	// ssl版本
	public static String ssl_version;

	/**
	 * 分账代扣
	 */
	public static String cutwithhold_url;
	public static String cutwithhold_biz_code;
	public static String cutwithhold_biz_name;
	public static String cutwithhold_member_id;
	public static String cutwithhold_cut_member_id;
	public static String cutwithhold_terminal_id;
	public static String cutwithhold_member_prikey_path;
	public static String cutwithhold_baofoo_pubkey_path;
	public static String cutwithhold_keystore_password;

	/**
	 * 乐视-宝付代付
	 */
	public static String baofoo_member_id;
	public static String baofoo_withdraw_terminal_id;
	public static String baofoo_withdraw_url;
	public static String baofoo_withdraw_result_url;
	public static String baofoo_keystore_path;
	public static String baofoo_keystore_password;
	public static String baofoo_pubkey_path;

	/**
	 * 通融-宝付代付
	 */
	public static String tr_baofoo_member_id;
	public static String tr_baofoo_withdraw_terminal_id;
	public static String tr_baofoo_withdraw_url;
	public static String tr_baofoo_withdraw_result_url;
	public static String tr_baofoo_keystore_path;
	public static String tr_baofoo_keystore_password;
	public static String tr_baofoo_pubkey_path;

	/**
	 * 宝付查询余额 商户号
	 */
	public static String baofoo_queryBalance_member_id;
	/**
	 * 宝付查询余额 终端号
	 */
	public static String baofoo_queryBalance_terminal_id;
	/**
	 * 宝付查询余额 秘钥
	 */
	public static String baofoo_queryBalance_key;

	/**
	 * 宝付查询余额 请求地址
	 */
	public static String baofoo_queryBalance_url;

	static {
		String propertiesFileName = "/" + CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = BaofooConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<BaofooConfig> clazz = BaofooConfig.class;
			BaofooConfig obj = clazz.newInstance();
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
			System.err.println("载入配置文件“/config.properties”时发生错误");
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

	private BaofooConfig() {
	}

}
