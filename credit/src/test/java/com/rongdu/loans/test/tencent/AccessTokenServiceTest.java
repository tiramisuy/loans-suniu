package com.rongdu.loans.test.tencent;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.credit.tencent.service.AccessTokenService;
import org.junit.Test;

import javax.annotation.Resource;

public class AccessTokenServiceTest extends SpringTransactionalContextTests {
	
	@Resource
	private AccessTokenService accessTokenService;
	
	@Test
	public void getAccessToken() throws Exception{
		accessTokenService.getAccessToken();
	}


}
