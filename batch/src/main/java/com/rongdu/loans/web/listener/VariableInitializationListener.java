package com.rongdu.loans.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;

import com.rongdu.loans.constant.Constants;

public class VariableInitializationListener implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//do nothing
	}
	/**
	 * 启动程序时设置系统环境变量
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {		
		Constants.CONTEXT_PATH = event.getServletContext().getContextPath();
		Constants.CONTEXT_REAL_PATH = event.getServletContext().getRealPath("/").replace("\\", "/");
		if (!StringUtils.endsWith(Constants.CONTEXT_REAL_PATH, "/")) {
			Constants.CONTEXT_REAL_PATH+="/";
		}
		Constants.UPLOAD_PATH = Constants.CONTEXT_REAL_PATH+Constants.UPLOAD_PATH;
//		Constants.APK_PATH = Constants.CONTEXT_REAL_PATH+Constants.APK_PATH;
		Constants.SERVER_URL =new StringBuilder().append("https://")
				.append(Constants.SERVER_HOST).append(":")
				.append(Constants.SERVER_PORT)
				.append(Constants.CONTEXT_PATH).append("/").toString();
	}

}
