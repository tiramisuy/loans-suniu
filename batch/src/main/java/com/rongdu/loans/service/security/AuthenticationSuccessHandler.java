package com.rongdu.loans.service.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.entity.log.LoginLog;
import com.rongdu.loans.service.account.VRoleAuthorityManager;
import com.rongdu.loans.service.log.LoginLogManager;
import com.rongdu.loans.utils.DateUtil;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private LoginLogManager loginLogManager;
	@Autowired
	private VRoleAuthorityManager vRoleAuthorityManager;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		Long loginId = writeLog(authentication);
		keepUserInfo(request,authentication,loginId);
		super.onAuthenticationSuccess(request, response, authentication);
	}

	private void keepUserInfo(HttpServletRequest request,Authentication authentication,Long loginId) {
		VUser user = (VUser)authentication.getPrincipal();
		user.setLoginId(loginId);
		Map<String, Boolean> opts = vRoleAuthorityManager.loadOperationAuthorityByRole(user.getRoleIds());
		request.getSession().setAttribute("hasPermission", opts);
		request.getSession().setAttribute("loginLogId", loginId);
		request.getSession().setAttribute("username", user.getUsername());
 	}
	
	private Long writeLog(Authentication authentication) {
		WebAuthenticationDetails details  = (WebAuthenticationDetails)authentication.getDetails();
		VUser user = (VUser)authentication.getPrincipal();
		LoginLog log = new LoginLog(null,user.getId(), authentication.getName(), "用户登录", DateUtil.format(),"SERVER",details.getRemoteAddress(),"Y", "登录成功");
		loginLogManager.insert(log);
		return log.getId();
	}

}
