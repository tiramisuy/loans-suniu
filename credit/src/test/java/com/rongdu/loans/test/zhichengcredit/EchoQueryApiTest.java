package com.rongdu.loans.test.zhichengcredit;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.zhicheng.service.impl.ZhichengServiceImpl;
import com.rongdu.loans.zhicheng.vo.CreditInfoOP;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;


public class EchoQueryApiTest {

	public static void main(String[] args) {
		
		ZhichengServiceImpl service = new ZhichengServiceImpl();
		CreditInfoOP op  = new CreditInfoOP();
//		String name = "兰江洋";
//		String idNo = "420527198911033893";
		String name = "朱培培";
		String idNo = "320305198905040963";
		op.setIdNo(idNo);
		op.setName(name);

		CreditInfoVO vo = service.queryCreditInfo(op);
		System.out.println(JsonMapper.toJsonString(vo));
	}

}
