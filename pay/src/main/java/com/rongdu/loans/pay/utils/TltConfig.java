package com.rongdu.loans.pay.utils;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 参数的配置，入网商户对接通联通的商户必须找相应的分公司技术颁发唯一的系统对接参数
 * 如，商户号，用户名，密码，私钥证书
 **/
public class TltConfig {
    private static String CONFIG_FILE = "config.properties";

    /**
     * /系统对接的商户号,找通联的客户经理分配
     */
    public static String tlt_merchantid = "";
    /**
     * 对接的测试接口地址
     */
    public static String tlt_url = "";
    /**
     * 简单对账文件的接口地址
     */
    public static String tlt_urlFileGet = "";
    /**
     * 用户密码,找通联的客户经理分配
     */
    public static String tlt_userpass = "";
    /**
     * 商户私钥路径,找通联的客户经理分配
     */
    public static String tlt_pathpfx = "";
    /**
     * 私钥密码,找通联的客户经理分配
     */
    public static String tlt_pfxpass = "";
    /**
     * 通联公钥
     */
    public static String tlt_pathcer = "";
    /**
     * 用户名 ,找通联的客户经理分配
     */
    public static String tlt_username = "";

    /**
     * 是否独立商户2放款
     */
    public static String is_merchant_profile = "";

    static {
        String propertiesFileName = "/" + CONFIG_FILE;
        Properties properties = new Properties();
        InputStream in = null;
        String value = null;
        String fieldName = null;
        String fieldType = null;
        Method method = null;
        try {
            in = TltConfig.class.getResourceAsStream(propertiesFileName);
            properties.load(in);
            Class<TltConfig> clazz = TltConfig.class;
            TltConfig obj = clazz.newInstance();
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

    private TltConfig() {
    }
}
