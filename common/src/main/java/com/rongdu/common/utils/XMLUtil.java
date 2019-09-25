package com.rongdu.common.utils;

import org.json.JSONObject;
import org.json.XML;


public class XMLUtil {

	/**
	 * xml转换成json字符串
	 * @param xml
	 * @return
	 */
	public static String xmlToJson(String xml) {
		JSONObject xmlJSONObject = XML.toJSONObject(xml);
		return xmlJSONObject.toString();
	}
}
