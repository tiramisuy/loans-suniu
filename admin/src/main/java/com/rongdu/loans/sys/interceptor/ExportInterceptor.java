/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.interceptor;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rongdu.common.annotation.ExportCheck;
import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 导出频率限制 liuzhuang
 */
public class ExportInterceptor implements HandlerInterceptor {
	protected static final Logger logger = LoggerFactory.getLogger(ExportInterceptor.class);
	private static final String EXPORT_LIMIT_CACHE_KEY = "EXPORT_LIMIT_";
	private static final int ttl = 10 * 60;// 10分钟

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (hasExportAnnotation(request, handler, ExportCheck.class)) {
			String key = getCacheKey(request, handler);
			String value = JedisUtils.get(key);
			WebResult result = null;
			if (value == null) {
				result = new WebResult("1", "提交成功", null);
			} else {
				// result = new WebResult("99", "正在导出，请耐心等待...", null);
				result = new WebResult("99", "导出次数频繁，请稍后再试...", null);
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().print(JsonMapper.toJsonString(result));
			return false;
		}
		if (hasExportAnnotation(request, handler, ExportLimit.class)) {
			String key = getCacheKey(request, handler);
			String value = JedisUtils.get(key);
			if (value == null) {
				JedisUtils.set(key, "1", ttl);
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (hasExportAnnotation(request, handler, ExportLimit.class)) {
			String key = getCacheKey(request, handler);
			// JedisUtils.del(key);
			logger.info("{}--->导出完成", key);
		}
	}

	private <A extends Annotation> boolean hasExportAnnotation(HttpServletRequest request, Object handler,
			Class<A> annotationType) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			A annotation = method.getMethodAnnotation(annotationType);
			if (annotation == null) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private String getCacheKey(HttpServletRequest request, Object handler) {
		String identity = "anon";
		Subject subject = SecurityUtils.getSubject();
		Principal principal = (Principal) subject.getPrincipal();
		if (principal != null) {
			identity = principal.getId();
		}
		String url = getUrl(request);
		if (hasExportAnnotation(request, handler, ExportCheck.class)) {
			url = request.getParameter("ecportaction");
		}
		// String key =
		// EXPORT_LIMIT_CACHE_KEY.concat(identity).concat("_").concat(url);
		String key = EXPORT_LIMIT_CACHE_KEY.concat(url);
		// logger.info("导出--->{}--->{}", identity, url);
		return key;
	}

	private String getUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		return (StringUtils.isBlank(queryString)) ? uri : uri + "?" + queryString;
	}
}
