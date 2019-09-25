package com.rongdu.loans.pay.utils;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;


/**
 * 放款账户的配置
 * 参数的配置，入网商户对接通联通的商户必须找相应的分公司技术颁发唯一的系统对接参数
 * 如，商户号，用户名，密码，私钥证书
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
public class LoanConfig {
    private static String CONFIG_FILE = "config.properties";

    /**
     * 商户号
     */
    public static String loan_merchantid = "";
    /**
     * 商户密钥
     */
    public static String loan_key_path = "";
    /**
     * 对接接口地址
     */
    public static String loan_url = "";

    static {
        String propertiesFileName = "/" + CONFIG_FILE;
        Properties properties = new Properties();
        InputStream in = null;
        String value = null;
        String fieldName = null;
        String fieldType = null;
        Method method = null;
        try {
            in = LoanConfig.class.getResourceAsStream(propertiesFileName);
            properties.load(in);
            Class<LoanConfig> clazz = LoanConfig.class;
            LoanConfig obj = clazz.newInstance();
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

    private LoanConfig() {
    }
}
