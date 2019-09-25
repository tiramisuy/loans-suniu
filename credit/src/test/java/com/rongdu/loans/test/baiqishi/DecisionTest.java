package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.BaiqishiServiceImpl;
import com.rongdu.loans.baiqishi.vo.DecisionOP;
import com.rongdu.loans.baiqishi.vo.DecisionVO;

import java.util.HashMap;

public class DecisionTest {

	public static void main(String[] args) {
		BaiqishiServiceImpl decisionServiceImpl = new BaiqishiServiceImpl();
		DecisionOP op  = new DecisionOP();
		op.setTokenKey("288f5f35-88a1-47a1-9bdb-83b307dfd81c");
		op.setUserId("123456");
		op.setApplyId("123456");
		op.setIp("127.0.0.1");
		op.setPlatform("ios");
		HashMap<String, String> extParams = new HashMap<>();
		DecisionVO vo = decisionServiceImpl.doDecision(op, extParams);
		System.out.println(JsonMapper.toJsonString(vo));

	}

}
