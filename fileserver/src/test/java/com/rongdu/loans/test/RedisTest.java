package com.rongdu.loans.test;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.rongdu.common.config.Global;
import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.common.utils.JedisUtils;

public class RedisTest extends SpringTransactionalContextTests {
	
	@Test
	public void  getAppKey() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		System.out.println("==========getAppKey==========");	
		String appKey = JedisUtils.get(Global.APP_TEY_PREFIX+"fdf35d9bb37d4102bdcae7c09121602a");
		String token = JedisUtils.get(Global.USER_TOKEN_PREFIX+"fdf35d9bb37d4102bdcae7c09121602a");
	}


	
}