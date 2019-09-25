package com.rongdu.loans.constant;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class Constants {
	
	/**
	 *  系统统一的字符集
	 */
	public static String ENCODING;	
	/**
	 *  上下文环境路径
	 */
	public static String CONTEXT_PATH;		
	/**
	 *  应用程序域名或IP
	 */
	public static String SERVER_HOST;	
	/**
	 *  应用程序端口
	 */
	public static int SERVER_PORT;
	/**
	 *  应用程序URL
	 */
	public static String SERVER_URL;
	/**
	 *  应用的绝对路径
	 */
	public static String CONTEXT_REAL_PATH;		
	/**
	 *  会话超时时间(毫秒)
	 */
	public static long SESSION_TIMEOUT;	
	/**
	 *  用户上传附件的相对路径
	 */
	public static String UPLOAD_PATH;
	/**
	 * 上传文件路径
	 */
	public static String UPLOAD_DIR;
	/**
	 * 下载文件地址
	 */
	public static String SERVICES_URL;
	/**
	 * 数据列表分页大小
	 */
	public static final int PAGE_SIZE = 30;
	
	public static int TIME_OUT = 50000;

	/**
	 * 环境模式
	 */
	public static String ENV_MODE;
	/**
	 * 生产环境名称
	 */
	public static String PRO_MODE="pro";
	/**
	 * 是否为开发环境(勿改)
	 */
	public static boolean isProEvn = true;
	/**
	 * 是否为测试环境(勿改)
	 */
	public static boolean isTestEvn = false;
	/**
	 * 是否为开发环境(勿改)
	 */
	public static boolean isDevEvn = false;
	
	/**
	 * 报警短信发送到以下的手机号码，多个号码之间用,分隔
	 */
	public static String  ALARM_MOBILES;
	
	/**
	 * 初始化系统参数
	 */
	static{
		String propertiesFileName = "/constants.properties";
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method =null;
		try {
			in = Constants.class.getResourceAsStream(propertiesFileName);
			properties.load(in);	
			Class<Constants> clazz = Constants.class;
			Constants obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields){
				fieldName = field.getName();
				if(properties.containsKey(fieldName)){
					method = Properties.class.getDeclaredMethod("getProperty", String.class);
					value = (String) method.invoke(properties, fieldName);
//					System.out.println(fieldName+"_______"+value);
					if (null!=value) {
						fieldType = field.getType().getName();
					    if (String.class.getName().equals(fieldType)) {
							field.set(obj, value);
						}else if ("int".equals(fieldType)) {
							field.setInt(obj, Integer.parseInt(value));
						}else if ("boolean".equals(fieldType)) {
							field.setBoolean(obj, Boolean.parseBoolean(value));
						}else if ("long".equals(fieldType)) {
							field.setLong(obj, Long.parseLong(value));
						}else {
							//do nothing
						}
					}		
				}
			}
		} catch (Exception e) {
			System.err.println("载入配置文件“/constants.properties”时发生错误");
			e.printStackTrace();
		}finally{
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 私有构造函数,仅允许Constants在初始化成员变量时创建实例,不允许其他类调用
	 */
	private Constants(){}
	
}

