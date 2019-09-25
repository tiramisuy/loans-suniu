/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author sunda
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen implements SessionIdGenerator {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成令牌Token
	 * @return
	 */
	public static String genToken() {
		return IdGen.uuid().toLowerCase();
	}
	
	/**
	 * 生成数字签名密钥
	 * @return
	 */
	public static String genAppkey() {
		Long randomLong = RandomUtils.nextLong(1, 999999999);
		String randomString = String.valueOf(randomLong);
		return StringUtils.leftPad(randomString, 10, '0');
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	/**
	 * ID 生成
	 */
	public String getNextId() {
		return IdGen.uuid();
	}

	@Override
	public Serializable generateId(Session session) {
		return IdGen.uuid();
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
		System.out.println(new IdGen().getNextId());
		for (int i=0; i<1000; i++){
			System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
		}
	}
	
	/**
	 * 生成短信验证码
	 * @param size 验证码位数
	 * @return
	 */
	public static String genMsgCodeSix() {
		Long randomLong = RandomUtils.nextLong(1, 999999);
		String randomString = String.valueOf(randomLong);
		return StringUtils.leftPad(randomString, Global.MSG_CODE_SIZE, '0');
	}
	
	/**
	 * 生成短信验证码
	 * @param size 验证码位数
	 * @return
	 */
	public static String genMsgCodeFour() {
		Long randomLong = RandomUtils.nextLong(1, 9999);
		String randomString = String.valueOf(randomLong);
		return StringUtils.leftPad(randomString, Global.MSG_CODE_FOUR, '0');
	}

}
