package com.rongdu.loans.web.filter;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rongdu.core.utils.encode.JsonBinder;
import com.rongdu.core.utils.spring.SpringContextHolder;
import com.rongdu.core.utils.web.ServletUtils;
import com.rongdu.loans.entity.account.Authority;
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.entity.log.AccessLog;
import com.rongdu.loans.service.account.AuthorityManager;
import com.rongdu.loans.service.log.JdbcLogWriter;
import com.rongdu.loans.utils.DateUtil;

/**
 * 记录调用日志，通过消息队列写入数据库
 * @author sunda
 * @version 2017-07-13
 */
public class LogFilter implements Filter {
	
//	private String  excludes = null;
	private JsonBinder jsonBinder;

    public LogFilter() {
     
    }
    
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		long start = System.currentTimeMillis();	  
		chain.doFilter(request, response);			
		long end = System.currentTimeMillis();  
		long costedTime = end-start;
		sendLogToMessageQueue(request, costedTime);
		
	}

	private void sendLogToMessageQueue(HttpServletRequest request,long costedTime) {
		//不记录系统框架的调用情况
		if (StringUtils.startsWith(request.getServletPath(), "/frame")) {
			return;
		}
		AccessLog log = new AccessLog();
		log.setAccessDate(Integer.parseInt(DateUtil.format("yyyyMMdd")));
		log.setAccessTime(new Timestamp(System.currentTimeMillis()));
		log.setCostedTime(costedTime);
		log.setRequestUrl(request.getServletPath());
		log.setIp(ServletUtils.getRemoteAddr(request));
		log.setUserAgent(request.getHeader("User-Agent"));
		log.setStatus(0);
		Object loginLogId = request.getSession().getAttribute("loginLogId");
		if (loginLogId!=null) {
			log.setLoginLogId(Long.parseLong(loginLogId.toString()));	
		}
		log.setParams(jsonBinder.toJson(request.getParameterMap()));
		log.setMethod(request.getMethod());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication!=null) {
			VUser user = (VUser)authentication.getPrincipal();
			if (user!=null) {
				log.setUserId(user.getId());
				log.setEmpName(user.getName());
				log.setUsername(user.getUsername());
			}		
		}
		AuthorityManager authorityManager = SpringContextHolder.getBean("authorityManager");
		Authority authority = authorityManager.getAuthorityByUrl(log.getRequestUrl());
		if (authority!=null) {		
			log.setModuleName(authority.getName());
		}
		JdbcLogWriter jdbcLogWriter = SpringContextHolder.getBean("jdbcLogWriter");
		jdbcLogWriter.processMessage(log);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		jsonBinder = JsonBinder.buildNormalBinder();
	}
	
	@Override
	public void destroy() {
		jsonBinder = null;
	}

}
