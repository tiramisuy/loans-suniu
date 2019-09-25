/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.api.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * 请求转发器 问题场景：用于解决H5程序跨域访问无法设置请求头，API后台认证不通过的问题
 * 
 * @author sunda
 * @version 2017-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/h5")
@Scope("prototype")
public class JsonpDispatherController extends BaseController {

	private CloseableHttpClient httpClient = null;

	/**
	 * 转发JSONP请求
	 * 
	 * @param vo
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/jsonpDispather.jsonp")
	public String jsonpDispather(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("_dispatherUrl");
		String callback = request.getParameter("_callback");
		ApiResult result = new ApiResult(ErrInfo.ERROR);
		if (StringUtils.isBlank(url)) {
			result.setMsg("缺少转发URL");
		}
		if (StringUtils.isBlank(callback)) {
			result.setMsg("非法的JSONP请求");
		}
		if (StringUtils.isAnyBlank(url, callback)) {
			String respString = new StringBuilder(callback).append("(").append(JsonMapper.toJsonString(result))
					.append(");").toString();
			renderString(response, respString, " application/json");
		}
		logger.debug("JsonpDispather-将请求转发至：{}", url);
		Map<String, String> headers = createHttpHeaders(request);
		List<NameValuePair> params = createHttpParams(request);
		if (headers.isEmpty() && params.isEmpty()) {
			result.setMsg("非法的请求");
			String respString = new StringBuilder(callback).append("(").append(JsonMapper.toJsonString(result))
					.append(");").toString();
			renderString(response, respString, " application/json");
		}
		logger.debug("JsonpDispather-请求头：{}", headers);
		logger.debug("JsonpDispather-请求参数：{}", params);
		String respString = dispather(url, params, headers);
		respString = new StringBuilder(callback).append("(").append(respString).append(");").toString();
		renderString(response, respString, " application/json");
		return null;
	}

	/**
	 * 封装请求参数
	 * 
	 * @param request
	 * @return
	 */
	private List<NameValuePair> createHttpParams(HttpServletRequest request) {
		// 创建参数列表
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Enumeration<String> names = request.getParameterNames();
		String name = null;
		String[] values = null;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			values = request.getParameterValues(name);
			if (!StringUtils.startsWith(name, "_")) {
				for (int i = 0; i < values.length; i++) {
					// logger.info("createHttpParams source {}:{}", name,
					// values[i]);
					// try {
					// values[i] = new String(values[i].getBytes("ISO-8859-1"),
					// "UTF-8");
					// } catch (UnsupportedEncodingException e) {
					// logger.debug("createHttpParams UnsupportedEncodingException ---> {}={}",
					// name, values[i]);
					// }
					// logger.info("createHttpParams decode {}:{}", name,
					// values[i]);
					list.add(new BasicNameValuePair(name, values[i]));
				}
			}
		}
		return list;
	}

	/**
	 * 测试接受jsonpDispather转发过来的请求
	 * 
	 * @param vo
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/test")
	@ResponseBody
	public ApiResult test(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("测试接受jsonpDispather转发过来的请求：");
		System.out.println(request.getHeader("userId"));
		System.out.println(request.getParameter("msgId"));
		Servlets.printAllHeaders(request);
		Servlets.printAllParameters(request);
		return new ApiResult();
	}

	/**
	 * 封装请求头信息
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> createHttpHeaders(HttpServletRequest request) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String userId = request.getParameter("_userId");
		String tokenId = request.getParameter("_tokenId");
		String sign = request.getParameter("_sign");
		if (StringUtils.isNotBlank(userId)) {
			map.put("userId", userId);
		}
		if (StringUtils.isNotBlank(tokenId)) {
			map.put("tokenId", tokenId);
		}
		if (StringUtils.isNotBlank(sign)) {
			map.put("sign", sign);
		}
		return map;
	}

	private String dispather(String url, List<NameValuePair> params, Map<String, String> headers) {
		String respString = null;
		CloseableHttpClient httpClient = createHttpClient();
		CloseableHttpResponse httpResponse = null;
		HttpPost post = null;
		try {
			httpClient = HttpClients.createDefault();
			post = new HttpPost(url);
			for (Entry<String, String> set : headers.entrySet()) {
				post.setHeader(set.getKey(), set.getValue());
			}
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			// url格式编码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(uefEntity);
			// 执行请求
			httpResponse = httpClient.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			respString = EntityUtils.toString(entity);
			respString = StringUtils.trim(respString);
			logger.debug("JsonpDispather-响应内容：{}", respString);
		} catch (Exception e) {
			ApiResult result = new ApiResult(ErrInfo.ERROR);
			respString = JsonMapper.toJsonString(result);
			e.printStackTrace();
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (post != null) {
					post.releaseConnection();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return respString;
	}

	private CloseableHttpClient createHttpClient() {
		if (httpClient == null) {
			logger.debug("JsonpDispather-正在创建HttpClient");
			httpClient = HttpClients.createDefault();
		}
		return httpClient;
	}

}