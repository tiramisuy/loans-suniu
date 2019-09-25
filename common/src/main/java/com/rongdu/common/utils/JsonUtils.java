package com.rongdu.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json转换工具
 * @author zcb
 *
 */
public class JsonUtils {

	private static final Logger logger = Logger.getLogger(JsonUtils.class);
	
	/**
	 * 将object对象转换成Json字符串
	 * 
	 * @param object
	 *            待转换的数据对象
	 * @return 返回Json数据格式
	 */
	public static String writeJsonAsString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("writeJsonAsString JsonGenerationException convert json error", e);
		} catch (JsonMappingException e) {
			logger.error("writeJsonAsString JsonMappingException convert json error", e);
		} catch (IOException e) {
			logger.error("writeJsonAsString IOException convert json error", e);
		}
		return "";
	}

	/**
	 * 返回接口JSON
	 * 
	 * @param code数据请求状态
	 * @param msg信息返回
	 * @param data信息数据
	 * @return
	 */
	public static String jsonResult(int code, String msg, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", data);
		return writeJsonAsString(map);
	}
}
