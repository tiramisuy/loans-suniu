package com.rongdu.test;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class WithdrawTest extends SpringTransactionalContextTests {

	@Autowired
	public BaofooWithdrawService baofooWithdrawService;

	@Test
  public void withdraw() throws Exception{
//		logger.debug("代付结果：{}");
//		BaofooWithdrawService baofooWithdrawService = SpringContextHolder.getBean("baofooWithdrawService");
//		RepayLogVO order = new RepayLogVO();
//		order.setSuccAmt(new BigDecimal("1003.50"));
//		order.setId("1");
//		order.setUserId("9075d331cf7f47d497a316024eba7ebd");
//		String cnName = "";
//		String mobile = "";
//		String idNo = "";
//		String accountid = "";
//		baofooWithdrawService.withdraw(order, cnName, idNo, mobile, accountid, order.getSuccAmt());
		
  }


}
