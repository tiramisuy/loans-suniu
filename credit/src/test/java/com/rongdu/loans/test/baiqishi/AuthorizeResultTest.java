package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.ZhimaServiceImpl;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultOP;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultVO;


public class AuthorizeResultTest {

	public static void main(String[] args) {
		
		ZhimaServiceImpl zhimaService = new ZhimaServiceImpl();
		AuthorizeResultOP op  = new AuthorizeResultOP();
		op.setOpenId("268804532456683020524867062");
		op.setUserId("123456");
		op.setApplyId("123452");
		op.setIp("127.0.0.1");
		op.setSource("1");
		AuthorizeResultVO vo = zhimaService.getAuthorizeResult(op);
		System.out.println(JsonMapper.toJsonString(vo));
	}

}
