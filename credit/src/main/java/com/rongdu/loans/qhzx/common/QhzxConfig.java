package com.rongdu.loans.qhzx.common;

import com.rongdu.loans.credit.common.CreditContants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 前海征信-配置参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class QhzxConfig {
	
	/************通用参数************/
	//合作厂商代码
	public static String partner_id;
	//合作厂商名称
	public static String partner_name;
	
	// 网络类型
	public static String net_type;
	// 交易名称
	public static String trans_name;
	// API版本
	public static String api_ver;
	// 机构代码
	public static String org_code;
	// 接入系统ID
	public static String chnl_id;
	// 授权码
	public static String auth_code;
	// 用户名
	public static String user_name;
	// 用户密码
	public static String user_password;
	// 用于数据加密密钥
	public static String secret;
	// 具体交易完整请求URL详见接口文档说明
	public static String domain;
	// 私钥存放目录
	public static String prikey_path;
	// 私钥密码
	public static String prikey_password;
	// 公钥存放目录
	public static String pubkey_path;
	
	/************业务/产品/服务 ************/
	//业务代码
	public static String loanee_biz_code;
	//业务名称
	public static String loanee_biz_name;
	//报文代码
	public static String loanee_message_code;
	
	//业务代码
	public static String rskdoo_biz_code;
	//业务名称
	public static String rskdoo_biz_name;
	//报文代码
	public static String rskdoo_message_code;
	
	//业务代码
	public static String credoo_biz_code;
	//业务名称
	public static String credoo_biz_name;
	//报文代码
	public static String credoo_message_code;
	
	//业务代码
	public static String antiFraudDoo_biz_code;
	//业务名称
	public static String antiFraudDoo_biz_name;
	//报文代码
	public static String antiFraudDoo_message_code;
	
	//业务代码
	public static String address_biz_code;
	//业务名称
	public static String address_biz_name;
	//报文代码
	public static String address_message_code;
	
	//业务代码
	public static String ubzc2m_biz_code;
	//业务名称
	public static String ubzc2m_biz_name;
	//报文代码
	public static String ubzc2m_message_code;
	
	//业务代码
	public static String creBnkCardInc_biz_code;
	//业务名称
	public static String creBnkCardInc_biz_name;
	//报文代码
	public static String creBnkCardInc_message_code;
	
	//业务代码
	public static String eChkPkgsCard_biz_code;
	//业务名称
	public static String eChkPkgsCard_biz_name;
	//报文代码
	public static String eChkPkgsCard_message_code;
	
	//业务代码
	public static String eChkPkgs_biz_code;
	//业务名称
	public static String eChkPkgs_biz_name;
	//报文代码
	public static String eChkPkgs_message_code;
	

	static {
		String propertiesFileName = CreditContants.QHZX_CONFIG_FILE;
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method = null;
		try {
			in = QhzxConfig.class.getResourceAsStream(propertiesFileName);
			properties.load(in);
			Class<QhzxConfig> clazz = QhzxConfig.class;
			QhzxConfig obj = clazz.newInstance();
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
			System.err.println("载入配置文件"+CreditContants.QHZX_CONFIG_FILE+"时发生错误");
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

	private QhzxConfig() {
	}

}
