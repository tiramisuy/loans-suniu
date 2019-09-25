/**
 * 聚宝钱包
 */
package com.rongdu.loans.sys.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rongdu.common.utils.StringUtils;

/**
 * 日志拦截器
 * @author sunda
 * @version 2017-08-12
 */
public class LogInterceptor  implements HandlerInterceptor {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 当前线程变量
	 */
	private static final ThreadLocal<Long> startTimeThreadLocal =new NamedThreadLocal<Long>("ThreadLocal StartTime");
	/**
	 * 使用Map缓存URL与模块名称的映射关系
	 */
	private Map<String, String> urlMap = new ConcurrentHashMap<String, String>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		if (logger.isDebugEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		// 仅当日志级别为Debug以上时，才打印JVM信息
		if (logger.isDebugEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
			long endTime = System.currentTimeMillis(); 	//2、结束时间  
			String uri = request.getRequestURI();
			String name = getRequestMappingName(uri,handler);
	        logger.debug("{}：{}，耗时：{}ms，内存使用情况：最大/可用/已分配/剩余- {}/{}/{}/{}",
	        		StringUtils.isBlank(name)?"正在访问":name,
	        		uri,
	        		endTime - beginTime,
	        		Runtime.getRuntime().maxMemory()/1024/1024,
	        		(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024,
					Runtime.getRuntime().totalMemory()/1024/1024, 
					Runtime.getRuntime().freeMemory()/1024/1024 
				); 
		}
				
	}
	
	/**
	 * 根据URL获取对应模块名称
	 * @param uri
	 * @param handler
	 * @return
	 */
	private String getRequestMappingName(String uri,Object handler) {
		String name = urlMap.get(uri);
		if (StringUtils.isBlank(name)) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			RequestMapping mapping =  handlerMethod.getMethodAnnotation(RequestMapping.class);
			name = mapping.name();
			if (StringUtils.isNotBlank(name)) {				
				urlMap.put(uri, name);
			}
		}
		return name;
	}
	
}
