package com.rongdu.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.pay.op.WithholdQueryOP;
import com.rongdu.loans.pay.service.BaofooCutwithholdService;

public class CutwithholdTest extends SpringTransactionalContextTests {

	@Autowired
	public BaofooCutwithholdService baofooCutwithholdService;

	@Test
	public void withhold() throws Exception {
		// WithholdOP param = new WithholdOP();
		// param.setUserId("123");
		// // param.setRealName("刘壮");
		// // param.setIdNo("420114198604291231");
		// // param.setMobile("13986066955");
		// // param.setBankCode("CCB");
		// // param.setCardNo("6236682870005881706");
		//
		// param.setRealName("乐尘");
		// param.setIdNo("310115200501017150");
		// param.setMobile("18689262779");
		// param.setBankCode("ICBC");
		// param.setCardNo("6222021001012347777");
		//
		// param.setTxnAmt(new
		// BigDecimal(100).multiply(BigDecimal.valueOf(100)).toString());
		// param.setApplyId("1");
		// param.setContNo("1");
		// param.setRepayPlanItemId("1");
		// baofooCutwithholdService.withhold(param, 1);
	}

	// "trans_id": "WH119354867124006",
	// "trade_date": "20181127183452",
	@Test
	public void withholdQuery() throws Exception {
		WithholdQueryOP param = new WithholdQueryOP();
		param.setOrigTransId("WH119354867124006");
		param.setOrigTradeDate("20181127183452");
		baofooCutwithholdService.queryWithholdResult(param);
	}

}
