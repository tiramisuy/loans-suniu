package com.rongdu.loans.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  定义过滤器，检查loginToken
 *  
 * @author likang
 * 
 * @version 2017-06-16
 * 
 */
public class MyAuthenticationFilter extends AuthorizationFilter {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());	
	
	@Override
	protected boolean isAccessAllowed(ServletRequest arg0, 
			ServletResponse arg1, Object mappedValue) throws Exception {
		HttpServletRequest request = (HttpServletRequest) arg0;
		
		String userId = request.getHeader("userId");
		String token = request.getHeader("tokenId");
		String sign = request.getHeader("sign");
		logger.debug("userid：{}; token：{}",userId, token);
		return true;
	}

}
