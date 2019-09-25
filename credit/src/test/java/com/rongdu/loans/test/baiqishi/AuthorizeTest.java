package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.ZhimaServiceImpl;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeOP;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeVO;


public class AuthorizeTest {

	public static void main(String[] args) {
		
		ZhimaServiceImpl zhimaService = new ZhimaServiceImpl();
		AuthorizeOP op  = new AuthorizeOP();
		op.setIdNo("420114198604291231");
		op.setName("刘壮");
		op.setUserId("123456");
		op.setApplyId("123456");
		op.setIp("127.0.0.1");
		op.setSource("1");
		AuthorizeVO vo = zhimaService.authorize(op);
		System.out.println(JsonMapper.toJsonString(vo));
	}

}
