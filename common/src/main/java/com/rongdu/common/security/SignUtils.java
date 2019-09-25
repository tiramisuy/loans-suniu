package com.rongdu.common.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.rongdu.common.utils.StringUtils;

/**
 * 电子签名工具类. 1、 电子签名密文生成 2、认证签名
 * 
 * @author likang
 */
public class SignUtils {

	/**
	 * 生成电子签名密文.
	 * 
	 * @param params
	 *            Map<String, Object>的请求参数
	 * @param encode
	 *            是否强制UTF-8编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Map<String, Object> params, boolean encode) throws UnsupportedEncodingException {

		// 抽取Map参数的key，按字母排序
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		// 拼接的Map参数key与value
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if ("linkFaceFrontPhoto".equals(key) || "linkFaceBackPhoto".equals(key)
					|| "enterpriseLicensePhoto".equals(key) || "linkFaceLivePhoto".equals(key)) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				temp.append("&");
			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueString = "";
			if (null != value) {
				valueString = String.valueOf(value);
			}
			if (encode) {
				temp.append(URLDecoder.decode(valueString, "UTF-8"));
			} else {
				temp.append(valueString);
			}
			// System.out.println("key-val:["+key+", "+valueString+"]");
		}
		System.out.println("temp:" + temp.toString());
		return MD5Utils.MD5(temp.toString()).toUpperCase();
	}



	public static String createSignNew(Map<String, Object> params, boolean encode) throws UnsupportedEncodingException {

		// 抽取Map参数的key，按字母排序
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		// 拼接的Map参数key与value
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if ("linkFaceFrontPhoto".equals(key) || "linkFaceBackPhoto".equals(key)
					|| "enterpriseLicensePhoto".equals(key) || "linkFaceLivePhoto".equals(key)) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				temp.append("&");
			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueString = "";
			if (null != value) {
				valueString = String.valueOf(value);
			}
			if (encode) {
				temp.append(URLDecoder.decode(URLDecoder.decode(valueString, "UTF-8"),"UTF-8"));
			} else {
				temp.append(valueString);
			}
			// System.out.println("key-val:["+key+", "+valueString+"]");
		}
		System.out.println("temp:" + temp.toString());
		return MD5Utils.MD5(temp.toString()).toUpperCase();
	}

	public static void main(String[] args) {

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("trueName", "大王");
		params.put("idType", "1");
		params.put("idNo", "420984198502062536");
		params.put("bankCode", "ICBC");
		params.put("cardNo", "442220225155455667");
		params.put("account", "17714661750");
		params.put("msgVerCode", "665862");
		params.put("appKey", "0688153717");

		try {
			String sg = createSign(params, true);
			System.out.println("密文:" + sg);
		} catch (UnsupportedEncodingException e) {
			System.out.println("签名异常:" + e);
		}
		// boolean rz = authenticationSign(params, "");
		// System.out.println("签名认证结果:"+rz);
	}

	/**
	 * 认证签名
	 * 
	 * @param params
	 *            Map<String, Object>的请求参数
	 * @param oldSign
	 *            原签名密文
	 * @return
	 */
	public static boolean authenticationSign(Map<String, Object> params, String oldSign) {
		if (null == params || params.size() == 0 || StringUtils.isBlank(oldSign)) {
			return false;
		}
		String newSign = null;
		try {
			newSign = createSign(params, true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return StringUtils.equals(newSign, oldSign);
	}
}
