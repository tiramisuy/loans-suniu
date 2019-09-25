package com.rongdu.loans.test;

import org.junit.Test;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.common.P2PAreaUtils;

public class P2PAreaTest extends SpringTransactionalContextTests {

	@Test
	public void getProvince() throws Exception {
		System.out.println("省" + P2PAreaUtils.getProvince("河南省"));
	}

	@Test
	public void getCity() throws Exception {
		System.out.println("市" + P2PAreaUtils.getCity("河南省", "开封市"));
	}
}
