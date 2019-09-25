package com.rongdu.loans.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.rongdu.loans.entity.log.LoginLog;
import com.rongdu.loans.service.log.LoginLogManager;
import com.rongdu.loans.utils.DateUtil;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private LoginLogManager loginLogManager;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,HttpServletResponse response, AuthenticationException exception)throws IOException, ServletException {		
		Authentication authentication = exception.getAuthentication();
		if(authentication!=null){
			WebAuthenticationDetails details  = (WebAuthenticationDetails)authentication.getDetails();
			LoginLog log = new LoginLog(null, null,authentication.getName(), "用户登录", DateUtil.format(),"WEB",details.getRemoteAddress(),"N", exception.getMessage());
			loginLogManager.insert(log);		
		}
		request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
		request.getRequestDispatcher("/").forward(request, response);
	}
	
}
