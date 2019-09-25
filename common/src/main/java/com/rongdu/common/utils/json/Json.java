package com.rongdu.common.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rongdu.common.utils.DateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * json转换, JSON常用封装
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 * 
 */
public class Json {


    /**
     * 列表map
     */
    public static final Type LIST_MAP_OBJECT = new TypeToken<List<Map<String, Object>>>() {
    }.getType();

    /**
     * map
     */
    public static final Type MAP_OBJECT = new TypeToken<Map<String, Object>>() {
    }.getType();

    /**
     * 构建好的gson对象
     */
    private final static Gson GSON = new GsonBuilder().registerTypeAdapter(Object.class, new NaturalDeserializer())
            .setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm").create();


    /**
     * 对象转为json
     *
     * @param object
     * @return
     */
    public static String parse(Object object) {
        return GSON.toJson(object);
    }

    /**
     * json转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T recover(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /**
     * json转对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T recover(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * 修复从json转来后的集合内对象
     * 修复int和double 的戒定分析不清
     *
     * @param data
     * @param clazz
     */
    public static void correction(List<Map<String, Object>> data, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Map<String, Object> map : data) {
            for (Field field : fields) {
                Object mapValue = map.get(field.getName());
                Class<?> fieldClass = field.getType(), mapValueClass = mapValue == null ? null : mapValue.getClass();
                if (mapValue != null && mapValueClass != fieldClass) {
                    // 修复double 转 int
                    if (fieldClass == Integer.class && mapValueClass == Double.class) {
                        mapValue = new Double(mapValue.toString()).intValue();
                    }
                    // 修复string转int
                    else if (fieldClass == Integer.class && mapValueClass == String.class) {
                        mapValue = new Integer(mapValue.toString()).intValue();
                    }// 修复string转date
                    else if (fieldClass == Date.class && mapValueClass == String.class) {
                        try {
                            mapValue = DateUtil.yyyyMMdd.parse(mapValue.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    map.put(field.getName(), mapValue);
                }
            }
        }

    }
}
