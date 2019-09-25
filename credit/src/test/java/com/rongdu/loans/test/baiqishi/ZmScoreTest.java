package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.ZhimaServiceImpl;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreOP;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreVO;


public class ZmScoreTest {

	public static void main(String[] args) {
		
		ZhimaServiceImpl zhimaService = new ZhimaServiceImpl();
		ZmScoreOP op  = new ZmScoreOP();
		op.setOpenId("268804532456683020524867062");
		op.setUserId("123456");
		op.setApplyId("123456");
		op.setIp("127.0.0.1");
		op.setSource("1");
		ZmScoreVO vo = zhimaService.getZmScore(op);
		System.out.println(JsonMapper.toJsonString(vo));
	}

}
