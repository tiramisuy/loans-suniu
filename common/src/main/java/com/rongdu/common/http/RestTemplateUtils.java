package com.rongdu.common.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.CharsetUtils;

/**
 * Http请求工具类，基于Spring RestTemplate
 * 
 * @author sunda
 * @version 2017-10-20
 */
public class RestTemplateUtils {

	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String DEFAULT_CHARSET = CharsetUtils.DEFAULT_CHARSET;

	private static RestTemplateUtils restTemplateUtils;
	private RestTemplate restTemplate = null;
	private JsonMapper jsonMapper = null;

	public RestTemplateUtils() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		jsonMapper = JsonMapper.getInstance();
	}

	public static RestTemplateUtils getInstance() {
		if (restTemplateUtils == null) {
			return new RestTemplateUtils();
		}
		return restTemplateUtils;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String getByUrl(String url) {
		// 响应结果
		String respString = null;
		try {
			respString = restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return respString;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String getForJson(String url, Map<String, String> params) {
		// 响应结果
		String respString = null;
		try {
			url = makeQueryString(url, params);
			respString = restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return respString;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public Object getForObject(String url, Class<?> responseClass) {
		// 响应结果
		String respString = null;
		try {
			respString = restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return jsonMapper.fromJson(respString, responseClass);
	}

	/**
	 * getList请求
	 * 
	 * @param url
	 * @return
	 */
	public Object getListForObject(String url, Class<?> responseClass) {
		// 响应结果
		String respString = null;
		try {
			respString = restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		JavaType javaType = jsonMapper.createCollectionType(ArrayList.class, responseClass);
		return jsonMapper.fromJson(respString, javaType);
	}

	/**
	 * post请求 数据形式：raw，请求参数以字符形式封装到请求体中
	 * Map会转为Json字符串：{"phone":"123456","name":"我是中国人"}
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String postForJsonRaw(String url, Map<String, String> params) {
		// 响应结果
		String respString = null;
		try {
			respString = restTemplate.postForObject(url, params, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return respString;
	}

	/**
	 * post请求 数据形式：raw，请求参数以字符形式封装到请求体中
	 * Map会转为Json字符串：{"phone":"123456","name":"我是中国人"}
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public Object postForObjectRaw(String url, Map<String, String> params, Class<?> responseClass) {
		// 响应结果
		String respString = null;
		try {
			respString = restTemplate.postForObject(url, params, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return jsonMapper.fromJson(respString, responseClass);
	}

	/**
	 * post请求 数据形式：raw，请求参数以字符形式封装到请求体中
	 * 
	 * @param url
	 * @param jsonParams
	 * @return
	 */
	public String postForJsonRaw(String url, String jsonParams) {
		// 响应结果
		String respString = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			HttpEntity<String> entity = new HttpEntity<String>(jsonParams, headers);
			respString = restTemplate.postForObject(url, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return respString;
	}

	/**
	 * post请求，数据形式：application/x-www-form-urlencoded
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String postForJson(String url, Map<String, String> params) {
		// 响应结果
		String respString = null;
		MultiValueMap<String, Object> multiMap = toMultiValueMap(params);
		try {
			respString = restTemplate.postForObject(url, multiMap, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return respString;
	}

	/**
	 * post请求，数据形式：application/x-www-form-urlencoded
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public Object postForObject(String url, Map<String, String> params, Class<?> responseClass) {
		// 响应结果
		String respString = null;
		MultiValueMap<String, Object> multiMap = toMultiValueMap(params);
		try {
			respString = restTemplate.postForObject(url, multiMap, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.debug("http请求-响应内容：{}",respString);
		return jsonMapper.fromJson(respString, responseClass);
	}

	/**
	 * 将参数封装到MultiValueMap
	 * 
	 * @param params
	 * @return
	 */
	private MultiValueMap<String, Object> toMultiValueMap(Map<String, String> params) {
		MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
		for (Entry<String, String> entry : params.entrySet()) {
			multiMap.set(entry.getKey(), entry.getValue());
		}
		params = null;
		return multiMap;
	}

	/**
	 * 组装请求字符串
	 * 
	 * @param urlParams
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	// public static String makeQueryString(String url,Map<String, String>
	// urlParams, String charset)
	// throws UnsupportedEncodingException {
	// url = (url == null) ? "" : url;
	// for (Map.Entry<String, String> entry : urlParams.entrySet()) {
	// url += entry.getKey()+ "="+ (charset == null ? entry.getValue() :
	// URLEncoder.encode(entry.getValue(), charset)) + "&";
	// }
	// return url.substring(0, url.length() - 1);
	// }
	public static String makeQueryString(String url, Map<String, String> urlParams, String charset)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (url != null) {
			sb.append(url);
		}
		for (Map.Entry<String, String> entry : urlParams.entrySet()) {
			if (null != entry.getValue()) {
				sb.append(entry.getKey()).append("=");
				if (charset == null) {
					sb.append(entry.getValue());
				} else {
					sb.append(URLEncoder.encode(entry.getValue(), charset));
				}
				sb.append("&");
			}
		}
		url = sb.toString();
		return url.substring(0, url.length() - 1);
	}

	/**
	 * 组装请求字符串
	 * 
	 * @param urlParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String makeQueryString(Map<String, String> urlParams) {
		try {
			return makeQueryString(null, urlParams, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 组装请求字URL
	 * 
	 * @param url
	 * @param urlParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String makeQueryString(String url, Map<String, String> urlParams) {
		String target = url;
		try {
			String queryString = makeQueryString(null, urlParams, DEFAULT_CHARSET);
			if (0 != queryString.length()) {
				String linkOperator = target.contains("?") ? "&" : "?";
				target += linkOperator + queryString;
			}
			return target;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return target;
	}

	public static void main(String[] args) {
		// String url = "http://paytest.rongdu.com/anon/zhima/callback";
		// RestTemplateUtils rest = new RestTemplateUtils();
		// Map params = new java.util.HashMap<String, String>();
		// params.put("phone", "123456");
		// // params.put("bbb", null);
		// params.put("name", "我是中国人");
		//
		// try {
		// // System.out.println(makeQueryString1(null, params,
		// DEFAULT_CHARSET));
		// System.out.println(makeQueryString(null, params, DEFAULT_CHARSET));
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// String respString =
		// rest.postForJsonRaw(url,JsonMapper.toJsonString(params));
		// System.out.println(respString);
	}

}
