package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.ReportServiceImpl;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageOP;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageVO;


public class ReportPageTest {

	public static void main(String[] args) {
		
		ReportServiceImpl service = new ReportServiceImpl();
		ReportPageOP op  = new ReportPageOP();
		op.setIdNo("430921198909242210");
		op.setName("李星");
		op.setMobile("13657238400");

		ReportPageVO vo = service.getReportPage(op);
		System.out.println(JsonMapper.toJsonString(vo));
	}

}
