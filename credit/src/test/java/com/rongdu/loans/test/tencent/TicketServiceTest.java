package com.rongdu.loans.test.tencent;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.credit.tencent.service.TicketService;
import org.junit.Test;

import javax.annotation.Resource;

public class TicketServiceTest extends SpringTransactionalContextTests {
	
	@Resource
	private TicketService ticketService;
	
	@Test
	public void getNonceTicket() throws Exception {
		ticketService.getNonceTicket("123456");
	}
	
	@Test
	public void getSignTicket() throws Exception {
		ticketService.getSignTicket();
	}

	
}
