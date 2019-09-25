package com.rongdu.loans.test.zhichengcredit;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.zhicheng.service.impl.RiskListServiceImpl;
import com.rongdu.loans.zhicheng.vo.RiskListOP;
import com.rongdu.loans.zhicheng.vo.RiskListVO;


public class RistListTest {

	public static void main(String[] args) {
		
		RiskListServiceImpl service = new RiskListServiceImpl();
		RiskListOP op  = new RiskListOP();
		op.setIdNo("410224650724003");
		op.setName("邱振芳");
		op.setMobile("15360171370");

		RiskListVO vo = service.queryMixedRiskList(op);
		System.out.println(JsonMapper.toJsonString(vo));
	}

}
