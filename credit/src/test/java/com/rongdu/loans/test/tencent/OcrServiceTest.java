package com.rongdu.loans.test.tencent;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.credit.tencent.service.OcrService;
import com.rongdu.loans.credit.tencent.vo.OcrResultVo;
import org.junit.Test;

import javax.annotation.Resource;

public class OcrServiceTest extends SpringTransactionalContextTests {
	
	@Resource
	private OcrService ocrService;
	
	@Test
  public void getFaceVerifyResult() throws Exception{
		OcrResultVo vo = ocrService.getOcrResult("3d7b23dce5c640849718a7117fc3665f");
//		System.out.println(JsonMapper.toJsonString(vo));
  }


}
