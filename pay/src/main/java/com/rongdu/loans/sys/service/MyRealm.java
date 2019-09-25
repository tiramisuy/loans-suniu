package com.rongdu.loans.sys.service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  用户访问授权校验器，通过loginToken获取userId，并不检验账号、密码
 * @author sunda
 * @version 2017-03-10
 * 
 */
public class MyRealm extends AuthorizingRealm {
		
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	  /**
     * 用于授权
     * @param principals
     * @return
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}
	
    /**
     * 用于认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	        String userId = token.getPrincipal().toString();
	        String password = new String((char[])token.getCredentials());
//	        if(user==null){
//	        	 logger.error("用户认证-用户不存在：{}",userId);
//	            throw new UnknownAccountException("用户不存在");
//	        }
	        return new SimpleAuthenticationInfo("userInfo","password","username");
	}

}
