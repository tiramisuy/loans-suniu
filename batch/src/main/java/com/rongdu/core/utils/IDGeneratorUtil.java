package com.rongdu.core.utils;


import java.security.SecureRandom;
import java.util.UUID;

/**
 * 类名称：GenerateIdUtil  
 * 类描述：  主键生成工具类
 * @author chenly
 * 创建时间：Jul 10, 2012 8:10:43 AM  
 * 修改人：  
 * 修改时间：Jul 10, 2012 8:10:43 AM  
 * 修改备注：  
 * @version 1.0.0  
 *
 */
public class IDGeneratorUtil {
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
		return uuid().toLowerCase();
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
	
	/**
	 * ID 生成
	 */
	public String getNextId() {
		return uuid();
	}
         
}
