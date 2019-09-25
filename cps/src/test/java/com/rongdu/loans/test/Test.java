//package com.rongdu.loans.test;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.junit.Assert;
//
//import com.rongdu.loans.app.option.LoanProductOP;
//import com.rongdu.loans.app.service.LoanProductService;
//
//public class Test extends TestBase {
//
//	private final Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private LoanProductService loanProductService;
//	
//	public void test_save() {
//		logger.info("test_save>>>>>>>>>>>>>>>>>start.....");
//		try {
//			LoanProductOP rz = new LoanProductOP();
//			rz.setProductId("XFD");
//			rz.setName("消费待");
//			rz.setType("1");
//			rz.setDescription("消费待");
//			rz.setMinIncrAmt(1000);
//			rz.setMinAmt(500d);
//			rz.setMaxAmt(5000d);
//			rz.setRepayMethod(1);
//			rz.setPrepay(1);
//			rz.setMinLoanDay(30);
//			rz.setStartInterest(3);
//			rz.setGraceDay(3);
//			rz.setStatus("2");
//			int vo = loanProductService.saveLoanProduct(rz);
//			logger.info("saveLoanProduct>>>>>>>>>>>>>>>>>result："+rz);
//			Assert.assertEquals("1", vo);
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.info("test_save>>>>>>>>>>>>>>>>>end.....");
//	}
//	
//}
