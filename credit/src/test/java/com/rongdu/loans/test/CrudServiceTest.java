package com.rongdu.loans.test;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.qhzx.service.MSC8037ServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class CrudServiceTest extends SpringTransactionalContextTests {
	
	@Autowired
	private MSC8037ServiceImpl mSC8037ServiceImpl;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Test
  public void get() throws Exception{
		System.out.println(taskExecutor);
		//mSC8037ServiceImpl.post();
  }


}
