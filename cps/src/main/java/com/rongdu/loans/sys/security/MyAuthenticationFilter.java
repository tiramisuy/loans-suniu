package com.rongdu.loans.sys.security;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.rongdu.loans.cust.vo.CustUserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.config.Global;
import com.rongdu.common.security.SignUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.api.common.AuthenticationType;
import com.rongdu.loans.api.common.LoginUtils;


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
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(token)) {
			//不允许登录
			return false;
		} else {
			//subject权限对象,类似user
			Subject subject  = SecurityUtils.getSubject();
			CustUserVO shiroUser = (CustUserVO)subject.getPrincipal();
			if (null == shiroUser) {
				//token认证，模拟登录
				simulateLogin(userId, token);	
			} else {
				//防止用户拼接token，使用前一个登录用户的信息
				if (!userId.equals(shiroUser.getId())) {
					subject.logout();
					//模拟登录
					simulateLogin(userId, token);
				}
			}
			// 签名认证开始
			shiroUser = (CustUserVO)subject.getPrincipal();
			if(null == shiroUser) {
				return false;
			}
			// 获取所有请求参数
			Map<String, Object> paramsMap =
					LoginUtils.getResParameters(request);
			if(null != paramsMap) {
				String appKey = JedisUtils.get(Global.APP_TEY_PREFIX+userId);
				paramsMap.put("appKey", appKey);
			}
			if(SignUtils.authenticationSign(paramsMap, sign)) {
				return true;
			} else {
				throw new IncorrectCredentialsException("签名验证失败");
			}
		}
	}

	private void simulateLogin(String userid,String authcode) {
		//用户未登录，就模拟登录
		if (SecurityUtils.getSubject()!=null 
				&& SecurityUtils.getSubject().getPrincipal()==null) {
			//登录方式1-账号密码登录，2-Token登录
			LoginUtils.authenticationType.set(AuthenticationType.TOKEN);
			//创建用户名和密码的令牌
			UsernamePasswordToken token = new UsernamePasswordToken(userid,authcode);
			//记录该令牌
			//token.setRememberMe(true);
			//进行登录
			SecurityUtils.getSubject().getSession().setTimeout(3*60*1000);  
			SecurityUtils.getSubject().login(token);
			//登录之后，权限交由shiro来控制		
		}
	}

}
