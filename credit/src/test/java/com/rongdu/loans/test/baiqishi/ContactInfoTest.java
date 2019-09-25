package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.DeviceFingerprintServiceImpl;
import com.rongdu.loans.baiqishi.vo.DeviceContactOP;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;

public class ContactInfoTest {

	public static void main(String[] args) {
		DeviceFingerprintServiceImpl deviceFingerprintService = new DeviceFingerprintServiceImpl();
		DeviceContactOP op  = new DeviceContactOP();
		op.setTokenKey("524d05f2-3fda-417f-8d33-061bdc87edd7");
		op.setUserId("123456");
		op.setApplyId("123456");
		op.setIp("127.0.0.1");
		op.setSource("2");
		DeviceContactVO vo = deviceFingerprintService.getContactInfo(op);
		System.out.println(JsonMapper.toJsonString(vo));

	}

}
