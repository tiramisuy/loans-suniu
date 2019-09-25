//package com.rongdu.loans.test;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import redis.clients.jedis.Jedis;
//
//import com.rongdu.common.mapper.JsonMapper;
//import com.rongdu.common.test.SpringTransactionalContextTests;
//import com.rongdu.common.utils.JedisUtils;
//import com.rongdu.loans.app.entity.AppUpgrade;
//import com.rongdu.loans.app.service.AppUpgradeService;
//
//public class CrudServiceTest extends SpringTransactionalContextTests {
//	
////	@Autowired
////	private AppUpgradeService appUpgradeService;
//	
////	@Test
////    public void get() throws Exception{
////		long start = System.currentTimeMillis();
////		AppUpgrade entity = appUpgradeService.get("1");
////		long end = System.currentTimeMillis();
////		logger.debug("time cost：{}", (end-start));
////		logger.debug("result：{}", JsonMapper.toJsonString(entity));
////    }
//	
//	@Test
//  public void get() throws Exception{
////		long start = System.currentTimeMillis();
////		AppUpgrade entity = appUpgradeService.get("1");
////		long end = System.currentTimeMillis();
////		logger.debug("time cost：{}", (end-start));
////		logger.debug("result：{}", JsonMapper.toJsonString(entity));
//		JedisUtils.set("AAA", "BBB", 1000);
//		System.out.println(JedisUtils.get("AAA"));
//  }
//
//
//}
