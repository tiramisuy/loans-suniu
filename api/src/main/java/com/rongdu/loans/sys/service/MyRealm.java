package com.rongdu.loans.sys.service;

import com.rongdu.loans.cust.vo.CustUserVO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.BizException;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.api.common.AuthenticationType;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.cust.service.CustUserService;

/**
 * 用户访问授权校验器
 *
 * @author likang
 * @version 2017-06-16
 */
public class MyRealm extends AuthorizingRealm {

    // 日志输出对象
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static final String NAME = "rongdu";

    @Autowired
    private CustUserService custUserService;

    /**
     * 用于授权
     *
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
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        // 登录方式1-账号密码登录，2-Token登录
        AuthenticationType authTypeClass = LoginUtils.authenticationType.get();
        // 获取登录类型
        String loginType = authTypeClass == null ? AuthenticationType.LOGIN.getCode() : authTypeClass.getCode();
        //密码登录
        if (AuthenticationType.LOGIN.getCode().equals(loginType)) {
            // 获取手机号
            String mobNum = token.getPrincipal().toString();
            // 获取登录密码
            String password = new String((char[]) token.getCredentials());
            // 获取用户信息
            CustUserVO vo = custUserService.getCustUserByMobile(mobNum);
            if (null == vo) {
                logger.error("认证用户[{}]不存在", mobNum);
                throw new UnknownAccountException("用户或者密码不正确");
            } else {
                // 验证密码
                if (StringUtils.equals(vo.getPassword(), password)) {
                    vo = LoginUtils.cacheCustUserInfo(vo);
                } else {
                    logger.error("认证用户[{}]密码不正确", mobNum);
                    LoginUtils.countPwdError(mobNum);
                    throw new UnknownAccountException("用户或者密码不正确");
                }
            }
            return new SimpleAuthenticationInfo(vo, password, NAME);
        } else if (AuthenticationType.MSG_PWD_LOGIN.getCode().equals(loginType)) {
            // 短信密码登录
            // 获取手机号
            String mobNum = token.getPrincipal().toString();
            // 获取登录密码
            String password = new String((char[]) token.getCredentials());
            // 获取用户信息
            CustUserVO vo = custUserService.getCustUserByMobile(mobNum);
            if (null == vo) {
                logger.error("认证用户[{}]不存在", mobNum);
                throw new UnknownAccountException("用户或者密码不正确");
            } else {
                String msgCode = JedisUtils.get(mobNum + Global.LOGIN_MCODE_SUFFIX);
                if (StringUtils.isBlank(msgCode) || !password.equals(LoginUtils.pwdToSHA1(msgCode))) {
                    logger.error("认证用户[{}]短信验证码过期或不正确", mobNum);
                    LoginUtils.countPwdError(mobNum);
                    throw new UnknownAccountException("短信验证码过期或不正确");
                } else {
                    vo = LoginUtils.cacheCustUserInfo(vo);
                }
            }
            return new SimpleAuthenticationInfo(vo, password, NAME);
        } else {
            // 获取用户id
            String userId = token.getPrincipal().toString();
            // 获取tokenid
            String loginToken = new String((char[]) token.getCredentials());
            // 从缓存获取tokenid
            String cloudTokenid = JedisUtils.get(Global.USER_TOKEN_PREFIX + userId);
            // 认证
            if (null == cloudTokenid) {
                logger.error("{}, 会话失效，请登录后访问", userId);
                //throw new BizException(ErrInfo.FORBIDDEN);
                throw new IncorrectCredentialsException("请重新登录");
            } else if (!cloudTokenid.equals(loginToken)) {
                throw new IncorrectCredentialsException("认证失败,请重新登录");
            }
            logger.info("{} token认证成功", userId);
            // 从缓存获取登录信息
            CustUserVO userInfo = (CustUserVO) JedisUtils.getObject(
                    Global.USER_CACHE_PREFIX + userId);
            if (null == userInfo) {
                // 重新获取登录信息
                userInfo = custUserService.getCustUserById(userId);
                LoginUtils.cacheCustUserInfo(userInfo);
                // 判断tokenId是否为空，为空则生成
                LoginUtils.generateTokenId(userId);
                // 判断appkey是否为空，为空则生成
                LoginUtils.generateAppKey(userId);
            }
            return new SimpleAuthenticationInfo(userInfo, loginToken, NAME);
        }
    }
}
