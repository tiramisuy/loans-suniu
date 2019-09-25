package com.rongdu.test;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.impl.BaofooAuthPayServiceImpl;
import org.junit.Test;

public class PreBindCardServiceTest extends SpringTransactionalContextTests {
	
	@Test
  public void getFaceVerifyResult() throws Exception{
		BaofooAuthPayServiceImpl service = SpringContextHolder.getBean("baofooAuthPayService");
		DirectBindCardOP param = new DirectBindCardOP();
		param.setUserId("0196deb830fa40bfa290b2ddeca63665");
		param.setSource("1");
		param.setIpAddr("127.0.0.1");
		service.preBindCard(param);
		
  }


}
