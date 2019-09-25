package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.ZhimaServiceImpl;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListOP;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListVO;


public class ZmWatchListTest {

	public static void main(String[] args) {
		
		ZhimaServiceImpl zhimaService = new ZhimaServiceImpl();
		ZmWatchListOP op  = new ZmWatchListOP();
		op.setOpenId("268804532456683020524867062");
		op.setUserId("123456");
		op.setApplyId("123456");
		ZmWatchListVO vo = zhimaService.getZmWatchList(op);
		System.out.println(JsonMapper.toJsonString(vo));
	}

}
