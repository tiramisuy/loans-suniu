package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.baiqishi.message.ContactInfoResponse;
import com.rongdu.loans.baiqishi.vo.DeviceContactOP;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.baiqishi.vo.DeviceInfoOP;
import com.rongdu.loans.baiqishi.vo.DeviceInfoVO;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 白骑士设备指纹-服务实现类
 * @author sunda
 * @version 2017-07-20
 */
@Service("deviceFingerprintService")
public class DeviceFingerprintServiceImpl extends PartnerApiService{
	
	/**
	 * 查询设备信息
	 * @return
	 */
	public DeviceInfoVO getDeviceInfo(DeviceInfoOP op) {
		
		//配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.device_info_biz_code;
		String bizName = BaiqishiConfig.device_info_biz_name;
		String url = BaiqishiConfig.device_info_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		Map<String,String>  params = new HashMap<String,String>();
		params.put("partnerId",BaiqishiConfig.partnerId );
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		if ("1".equals(op.getSource())) {
			params.put("platform", "ios");		
		}
		if ("2".equals(op.getSource())) {
			params.put("platform", "android");		
		}
		params.put("tokenKey", op.getTokenKey());		
		//发送请求
		DeviceInfoVO vo = (DeviceInfoVO) getForJson(url,params,DeviceInfoVO.class,log);

		return vo;
	}
	
	/**
	 * 查询芝麻信用授权结果
	 * @return
	 */
	public DeviceContactVO getContactInfo(DeviceContactOP op) {
		
		//配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.contact_info_biz_code;
		String bizName = BaiqishiConfig.contact_info_biz_name;
		String url = BaiqishiConfig.contact_info_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		Map<String,String>  params = new HashMap<String,String>();
		params.put("partnerId",BaiqishiConfig.partnerId );
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		if ("1".equals(op.getSource())) {
			params.put("platform", "ios");		
		}
		if ("2".equals(op.getSource())) {
			params.put("platform", "android");		
		}
		params.put("tokenKey", op.getTokenKey());	
		String jsonParamsBody  = JsonMapper.toJsonString(params);	
		//发送请求
		ContactInfoResponse response = (ContactInfoResponse) postForJson(url,jsonParamsBody,ContactInfoResponse.class,log);
		DeviceContactVO vo = new DeviceContactVO();
		//应答成功
		if (response!=null) {
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setSuccess(response.isSuccess());
			if (response.isSuccess()) {
				vo.setContactsInfo(response.getResultData().getContactsInfo());
				vo.setCallRecordInfo(response.getResultData().getCallRecordInfo());
				vo.setSmsRecordInfo(response.getResultData().getSmsRecordInfo());
			}
		}	
		return vo;
	}
	
}
