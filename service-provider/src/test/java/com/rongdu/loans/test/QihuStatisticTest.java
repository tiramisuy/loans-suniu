//package com.rongdu.loans.test;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//
//import com.rongdu.common.test.SpringTransactionalContextTests;
//import com.rongdu.loans.statistical.service.QihuStatisticalService;
//
//public class QihuStatisticTest extends SpringTransactionalContextTests {
//
//	@Autowired
//	private QihuStatisticalService qihuStatisticalService;
//
//	@Rollback(false)
//	@Test
//	public void pushRegist() throws Exception {
//		for (int i = 1; i <= 10; i++) {
//			qihuStatisticalService.pushRegist("000" + i, "1390000000" + i);
//		}
//	}
//
//}
