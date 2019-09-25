package com.rongdu.loans.test;

import com.google.common.collect.Maps;
import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.statistical.service.ZhimaStatisticalService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

public class ZhimaStatisticTest extends SpringTransactionalContextTests {


	@Autowired
	private ZhimaStatisticalService zhimaStatisticalService;

	@Test
  public void pushZhimaCreditStatistics() throws Exception{
		Map<String, String> rePush = Maps.newHashMap();
		rePush.put("R556437819199463575", String.valueOf(System.currentTimeMillis()));
		JedisUtils.mapPut("JDQ:third_key", rePush);
//		zhimaStatisticalService.pushZhimaCreditStatistics(new Date());
  }


}
