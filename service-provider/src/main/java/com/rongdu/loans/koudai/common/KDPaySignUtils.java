package com.rongdu.loans.koudai.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class KDPaySignUtils {
	/**
	 * 生成签名字符串
	 * 
	 * @param reqMap
	 * @param key
	 * @return
	 */
	public static String createSign(Map<String, String> reqMap, String key) {
		// 加签原串
		String str = "";
		try {
			// 对请求参数进行urlencode加密
			for (Map.Entry<String, String> entry : reqMap.entrySet()) {
				reqMap.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 对参数进行升序排序
		List<Map.Entry<String, String>> sortMap = argSort(reqMap);
		for (int i = 0; i < sortMap.size(); i++) {
			str += sortMap.get(i).getKey() + "=" + sortMap.get(i).getValue() + "&";
		}
		// 清除最后一个&
		if (str.length() > 1) {
			str = str.substring(0, str.length() - 1);
		}
		str += key;
		// 添加要进行计算摘要的信息
		byte[] bytes = Base64.encodeBase64(str.getBytes());
		return new String(bytes);
	}

	/**
	 * 排序 a-z 根据键排序
	 * 
	 * @param map
	 * @return
	 */
	public static List<Map.Entry<String, String>> argSort(Map<String, String> map) {
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
		// 然后通过比较器来实现排序
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			// 升序排序
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		return list;
	}
}
