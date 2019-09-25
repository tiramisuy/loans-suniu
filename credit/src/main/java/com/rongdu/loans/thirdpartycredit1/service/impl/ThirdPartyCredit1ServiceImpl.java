/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.thirdpartycredit1.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.thirdpartycredit1.common.ThirdPartyCredit1Config;
import com.rongdu.loans.thirdpartycredit1.service.ThirdPartyCredit1Service;
import com.rongdu.loans.thirdpartycredit1.vo.ThirdPartyCredit1BlackListOP;
import com.rongdu.loans.thirdpartycredit1.vo.ThirdPartyCredit1BlackListVO;

/**
 * 三方征信1
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
@Service
public class ThirdPartyCredit1ServiceImpl extends PartnerApiService implements ThirdPartyCredit1Service {

	@Override
	public ThirdPartyCredit1BlackListVO blacklist(ThirdPartyCredit1BlackListOP op) {
		// 配置参数
		String partnerId = ThirdPartyCredit1Config.partner_id;
		String partnerName = ThirdPartyCredit1Config.partner_name;
		String key = ThirdPartyCredit1Config.partner_key;
		String bizCode = ThirdPartyCredit1Config.blacklist_biz_code;
		String bizName = ThirdPartyCredit1Config.blacklist_biz_name;
		String url = ThirdPartyCredit1Config.blacklist_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("key", key);
		params.put("idCard", op.getIdCard());
		// 发送请求
		ThirdPartyCredit1BlackListVO vo = (ThirdPartyCredit1BlackListVO) getForJson(url, params,
				ThirdPartyCredit1BlackListVO.class, log);
		return vo;
	}
	
	@Override
	public ThirdPartyCredit1BlackListVO blacklist2(ThirdPartyCredit1BlackListOP op) {
		// 配置参数
		String partnerId = ThirdPartyCredit1Config.partner_id;
		String partnerName = ThirdPartyCredit1Config.partner_name;
		String key = ThirdPartyCredit1Config.partner_key;
		String bizCode = ThirdPartyCredit1Config.blacklist2_biz_code;
		String bizName = ThirdPartyCredit1Config.blacklist2_biz_name;
		String url = ThirdPartyCredit1Config.blacklist2_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("key", key);
		params.put("idCard", op.getIdCard());
		// 发送请求
		ThirdPartyCredit1BlackListVO vo = (ThirdPartyCredit1BlackListVO) getForJson(url, params,
				ThirdPartyCredit1BlackListVO.class, log);
		return vo;
	}
}