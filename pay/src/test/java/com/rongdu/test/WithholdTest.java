package com.rongdu.test;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.service.BaofooWithholdService;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WithholdTest extends SpringTransactionalContextTests {

	@Autowired
	public BaofooWithholdService baofooWithholdService;

	@Test
  public void withhold() throws Exception{
		WithholdOP op = new WithholdOP();
		
  }


}
