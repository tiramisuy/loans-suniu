package com.rongdu.loans.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource.AuthenticationType;
import javax.servlet.http.HttpServletRequest;

import com.rongdu.loans.cust.vo.CustUserVO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.config.Global;
import com.rongdu.common.security.Digests;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;

/**
 * 登录工具类
 * @author likang
 *
 */
public class LoginUtils {

	// 日志对象
	protected static Logger logger = LoggerFactory.getLogger(LoginUtils.class);
	
	// 用户信息缓存时间
	private static final int CACHESECONDS =
			60*60*24*Integer.parseInt(Global.getConfig("user.info.cache.days"));
	
    // 修改密码密钥缓存时间(3分钟)
	private static final int PWD_CACHESECONDS = 3*60;
	
	// 修改密码密钥缓存时间(24小时)
	private static final int USER_CACHESECONDS = 60*60*24;
	  
	public static final ThreadLocal<AuthenticationType> authenticationType 
	       = new ThreadLocal<AuthenticationType>();
	
	/**
	 * 生成tokenid
	 * @param userid 用户id
	 * @return
	 */
	public static String generateTokenId(String userid) {
		if(StringUtils.isBlank(userid)) {
			logger.error("用户id为空");
			return null;
		}
		// 拼接token的缓存key
		String tokenKey = Global.USER_TOKEN_PREFIX+userid;
		// 从缓存获取tokenid
		String tokenid = JedisUtils.get(tokenKey);
		// 缓存不存在重新生成tokenId
		if(StringUtils.isBlank(tokenid)) {
			tokenid = IdGen.uuid().toLowerCase();
		}
		if(StringUtils.isNotBlank(tokenid)) {
			// tokenid存入缓存
			JedisUtils.set(tokenKey, tokenid, CACHESECONDS);
		}
		return tokenid;
	}
	
	/**
	 * 密码SHA1消息摘要散列处理
	 * @param pwd
	 * @return
	 */
	public static String pwdToSHA1(String pwd) {
		if(StringUtils.isNotBlank(pwd)) {
			return Digests.sha1(pwd);
		} else {
			return pwd;
		}
	}

	/**
	 * 生成appKey
	 * @param userid 用户id
	 * @return
	 */
	public static String generateAppKey(String userid) {
		if(StringUtils.isBlank(userid)) {
			logger.error("用户id为空");
			return null;
		}
		// 拼接缓存appKey的缓存key
		String cacheKey = Global.APP_TEY_PREFIX+userid;
		// 从缓存获取tokenid
		String appKey = JedisUtils.get(cacheKey);
		if(StringUtils.isBlank(appKey)) {
			Long randomLong = RandomUtils.nextLong(1, 999999999);
			String randomString = String.valueOf(randomLong);
			appKey = StringUtils.leftPad(randomString, 10, '0');
		}
		if(StringUtils.isNotBlank(appKey)) {
			// appKey存入缓存
			JedisUtils.set(cacheKey, appKey, CACHESECONDS);
		}
		return appKey;
	}
	
	/**
	 * 生成忘记密码修改密码的密钥
	 * @return
	 */
	public static String genUpdatePwdKey(String account) {
		// 生成密码
		String pwdToken = IdGen.uuid().toLowerCase();
		// 缓存密钥
		String cacheKey = account+Global.FORGET_PWD_SUFFIX;
		if(StringUtils.isNotBlank(pwdToken)) {
			// appKey存入缓存
			JedisUtils.set(cacheKey, pwdToken, PWD_CACHESECONDS);
		}
		return pwdToken;
	}
	
	/**
	 * 获取缓存中的修改密码密钥
	 * @param account
	 * @return
	 */
	public static String getUpdatePwdKey(String account) {
		// 缓存密钥
		String cacheKey = account+Global.FORGET_PWD_SUFFIX;
		return JedisUtils.get(cacheKey);
	}
	
	/**
	 * 缓存用户信息
	 * @param info CustUser对象
	 * @return
	 */
	public static CustUserVO cacheCustUserInfo(CustUserVO info) {
		if(null != info 
				&& StringUtils.isNotBlank(info.getId())) {
			// 缓存用户信息
			JedisUtils.setObject(
					Global.USER_CACHE_PREFIX+info.getId(),
					info, USER_CACHESECONDS);
		} else {
			logger.error("用户信息为空");
		}
		return info;
	}
	
	/**
	 * 缓存密码输错次数
	 * @param account 账户
	 * @return
	 */
	public static void countPwdError(String account) {
		if(StringUtils.isNotBlank(account)) {
			String cachekey = Global.PWDERROR_CNT_CACHE_PREFIX+account;
			Integer count = (Integer) JedisUtils.getObject(cachekey);
			if(null == count) {
				count = new Integer("1");
				// 缓存用户密码输错次数
				JedisUtils.setObject(cachekey, count, USER_CACHESECONDS);
			} else {
				count++;
				if(count < Global.PWDERROR_MAX_CNT) {
					// 缓存用户密码输错次数
					JedisUtils.setObject(cachekey, count, USER_CACHESECONDS);
				} else if(count == Global.PWDERROR_MAX_CNT) {
					// 锁定账户
					JedisUtils.setObject(
							Global.LOCK_USER_PREFIX+account,
							Global.LOCK_USER_FLAG,
							Global.USER_LOCK_CACHESECONDS);
				}
			}
		}
	}
	
	/**
	 * 获取用户锁定状态
	 * @param account 账户
	 * @return
	 */
	public static boolean isLockedAccount(String account) {
		if(StringUtils.isNotBlank(account)) {
			String cachekey = Global.LOCK_USER_PREFIX+account;
			Integer flag = (Integer) JedisUtils.getObject(cachekey);
			if(flag == null) {
				return false;
			}
			return flag.equals(Global.LOCK_USER_FLAG);
		}
		return false;
	}
	
	/**
	 * 获取缓存中的用户信息
	 * @param userId
	 * @return
	 */
	public static CustUserVO getCustUserInfo(String userId) {
		if(StringUtils.isNotBlank(userId)) {
			// 缓存用户信息
			return (CustUserVO) JedisUtils.getObject(
					Global.USER_CACHE_PREFIX+userId);
		}
		return null;
	}
	/**
	 * 清理用户信息缓存
	 * @param info CustUser对象
	 * @return
	 */
	public static long cleanCustUserInfoCache(String userId) {
		long result = 0;
		if(StringUtils.isNotBlank(userId)) {
			// 缓存用户信息
			result = JedisUtils.delObject(
					Global.USER_CACHE_PREFIX+userId);
		} else {
			logger.error("用户id为空");
		}
		return result;
	}
	
	/**
	 * 从ServletRequest请求中获取所有参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getResParameters(HttpServletRequest request) {
		// 返回值初始化
		Map<String, Object> param = null;
		String ContentType = request.getHeader("content-type");
		if(StringUtils.equals(ContentType, "multipart/form-data")) {
			// multipart/form-data
			try{
				DiskFileItemFactory factory = new DiskFileItemFactory();   
		        ServletFileUpload upload = new ServletFileUpload(factory);   
		        upload.setHeaderEncoding("UTF-8");  
		        List<?> items = upload.parseRequest(request);  
		        param = new HashMap<String, Object>();   
		        for(Object object:items){  
		            FileItem fileItem = (FileItem) object;   
		            if (fileItem.isFormField()) {   
		                param.put(fileItem.getFieldName(),
		                		fileItem.getString("utf-8"));//如果你页面编码是utf-8的   
		            }
		       }
		    } catch(Exception e){
		    	logger.error("获取请求参数异常：[{}]", e);
		    }
		} else if(StringUtils.equals(
				ContentType, "application/x-www-form-urlencoded")) {
			Enumeration<String> paramNames = request.getParameterNames();
			String name = null;
			String[]  values = null;
			param = new HashMap<String, Object>();
			while(paramNames.hasMoreElements()){
				name = paramNames.nextElement();
				values = request.getParameterValues(name);
                if (null!=values && values[0].length() != 0) {
                	param.put(name, values[0].toString());  
                }
		    }
	    }
		return param;
	}
}
