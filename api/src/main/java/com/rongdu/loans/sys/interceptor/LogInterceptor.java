/**
 * 聚宝钱包
 */
package com.rongdu.loans.sys.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rongdu.common.config.QueneVariable;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.option.AccessLogOP;
import com.rongdu.loans.mq.MessageProductorService;

/**
 * 日志拦截器
 * 
 * @author sunda
 * @version 2017-08-12
 */
public class LogInterceptor implements HandlerInterceptor {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 当前线程变量
	 */
	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	/**
	 * 使用Map缓存URL与模块名称的映射关系
	 */
	private Map<String, String> urlMap = new ConcurrentHashMap<String, String>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (logger.isDebugEnabled()) {
			long beginTime = System.currentTimeMillis();// 1、开始时间
			startTimeThreadLocal.set(beginTime); // 线程绑定变量（该数据只有当前请求的线程可见）
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 仅当日志级别为Debug以上时，才打印JVM信息
		if (logger.isDebugEnabled()) {
			long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
			long endTime = System.currentTimeMillis(); // 2、结束时间
			long costedTime = endTime - beginTime; // 3、耗时
			String uri = request.getRequestURI();
			String name = getRequestMappingName(uri, handler);
			// sendLogToMessageQueue(request, costedTime, name);
			logger.debug("{}：{}，耗时：{}ms，内存使用情况：最大/可用/已分配/剩余- {}/{}/{}/{}", StringUtils.isBlank(name) ? "正在访问" : name,
					uri, costedTime, Runtime.getRuntime().maxMemory() / 1024 / 1024,
					(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()
							+ Runtime.getRuntime().freeMemory()) / 1024 / 1024,
					Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024);
		}

	}

	/**
	 * 根据URL获取对应模块名称
	 * 
	 * @param uri
	 * @param handler
	 * @return
	 */
	private String getRequestMappingName(String uri, Object handler) {
		String name = urlMap.get(uri);
		if (StringUtils.isBlank(name)) {
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				RequestMapping mapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
				name = mapping.name();
				if (StringUtils.isNotBlank(name)) {
					urlMap.put(uri, name);
				}
			}
		}
		return name;
	}

	/**
	 * 访问日志推送消息队列
	 * 
	 * @param request
	 * @param costedTime
	 * @param moduleName
	 * 
	 */
	private void sendLogToMessageQueue(HttpServletRequest request, long costedTime, String moduleName) {
		// 不记录系统框架的调用情况
		if (StringUtils.startsWith(request.getServletPath(), "/frame")) {
			return;
		}
		AccessLogOP log = new AccessLogOP();
		// 访问日期
		log.setAccessDate(DateUtils.getYYYYMMDD2Int());
		// 访问时间
		log.setAccessTime(new Date());
		// 访问耗时
		log.setCostTime(costedTime);
		// 访问URL
		log.setRequestUrl(request.getServletPath());
		// 访问IP
		log.setIp(Servlets.getIpAddress(request));
		// 访问浏览器信息
		String userAgent = request.getHeader("User-Agent");
		if (null != userAgent && userAgent.length() > 300) {
			userAgent = userAgent.substring(0, 100);
		}
		log.setUserAgent(userAgent);
		// 访问记录状态
		log.setStatus("0");
		// 获取登录信息
		Subject subject = SecurityUtils.getSubject();
		CustUserVO shiroUser = (CustUserVO) subject.getPrincipal();
		if (null != shiroUser) {
			// 用户姓名
			log.setName(shiroUser.getRealName());
			// 用户id
			log.setUserId(request.getHeader("userId"));
		}
		// 模块名称
		log.setModuleName(moduleName);
		// 访问来源
		log.setSource((String) request.getSession().getAttribute("source"));

		Map<String, String[]> reqMap = new HashMap<String, String[]>();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			if ("linkFaceFrontPhoto".equals(key) || "linkFaceBackPhoto".equals(key)
					|| "enterpriseLicensePhoto".equals(key) || "linkFaceLivePhoto".equals(key)) {
				continue;
			}
			reqMap.put(key, value);
		}

		// 访问请求参数
		String urlParams = JsonMapper.getInstance().toJson(reqMap);
		if (null != urlParams && urlParams.length() > 1000) {
			urlParams = urlParams.substring(0, 998);
		}
		log.setParams(urlParams);
		// 访问请求方法
		log.setMethod(request.getMethod());

		MessageProductorService messageProductorService = SpringContextHolder.getBean("messageProductorService");
		messageProductorService.sendDataToQueue(QueneVariable.LOG_QUENE_NAME, log);
		if (logger.isDebugEnabled()) {
			logger.debug("[{}]:[{}]", QueneVariable.LOG_QUENE_NAME, JsonMapper.toJsonString(log));
		}
		// Authority authority =
		// authorityManager.getAuthorityByUrl(log.getRequestUrl());
		// if (authority!=null) {
		// log.setModuleName(authority.getName());
		// }
		// JdbcLogWriter jdbcLogWriter =
		// SpringContextHolder.getBean("jdbcLogWriter");
		// jdbcLogWriter.processMessage(log);
	}
}
