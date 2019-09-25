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
 *  用户访问授权校验器
 *  
 * @author likang
 * 
 * @version 2017-06-16
 * 
 */
public class MyRealm extends AuthorizingRealm {
	
	// 日志输出对象
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String NAME = "rongdu";
	
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

		return new SimpleAuthenticationInfo("admin", "aaaaa", "bbbbb");
	}
}
