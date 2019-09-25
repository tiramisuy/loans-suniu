package com.rongdu.loans.test.baiqishi;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.service.impl.DeviceFingerprintServiceImpl;
import com.rongdu.loans.baiqishi.vo.DeviceInfoOP;
import com.rongdu.loans.baiqishi.vo.DeviceInfoVO;

public class DeviceInfoTest {

	public static void main(String[] args) {
		DeviceFingerprintServiceImpl deviceFingerprintService = new DeviceFingerprintServiceImpl();
		DeviceInfoOP op  = new DeviceInfoOP();
		op.setTokenKey("D3143028-7633-4AC6-AAFC-217D14C3A9A8");
		op.setUserId("123456");
		op.setApplyId("123456");
		op.setSource("1");
		DeviceInfoVO vo = deviceFingerprintService.getDeviceInfo(op);
		System.out.println(JsonMapper.toJsonString(vo));

	}

}
