package com.rongdu.loans.test.tencent;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.credit.tencent.service.FaceVerifyService;
import org.junit.Test;

import javax.annotation.Resource;

public class FaceVerifyServiceTest extends SpringTransactionalContextTests {
	
	@Resource
	private FaceVerifyService faceVerifyService;
	
	@Test
  public void getFaceVerifyResult() throws Exception{
		faceVerifyService.getFaceVerifyResult("67e634111b124b1290a65ecf9f6c1013");
  }


}
