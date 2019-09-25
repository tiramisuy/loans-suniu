package com.rongdu.loans.test.xinyan;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.xinyan.service.XinyanService;
import com.rongdu.loans.xinyan.service.impl.XinyanServiceImpl;
import com.rongdu.loans.xinyan.vo.BlackOP;
import com.rongdu.loans.xinyan.vo.BlackVO;

public class BlackTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		XinyanService service = new XinyanServiceImpl();
		BlackOP op = new BlackOP();
		op.setIdNo("370830198811116536");
		op.setName("杨会会");

		BlackVO vo = service.black(op);
		System.out.println(JsonMapper.toJsonString(vo));

	}

}
