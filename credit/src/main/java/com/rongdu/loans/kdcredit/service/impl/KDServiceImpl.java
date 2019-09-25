/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.kdcredit.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.kdcredit.common.KDConfig;
import com.rongdu.loans.kdcredit.service.KDService;
import com.rongdu.loans.kdcredit.vo.KDBlackListOP;
import com.rongdu.loans.kdcredit.vo.KDBlackListVO;
import com.rongdu.loans.kdcredit.vo.KDTokenVO;

/**
 * 口袋
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
@Service
public class KDServiceImpl extends PartnerApiService implements KDService {

	/**
	 * 口袋获取token
	 */
	public KDTokenVO getToken() {
		// 配置参数
		String partnerId = KDConfig.partner_id;
		String partnerName = KDConfig.partner_name;
		String bizCode = KDConfig.token_biz_code;
		String bizName = KDConfig.token_biz_name;
		String url = KDConfig.token_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		KDTokenVO vo = null;
		vo = (KDTokenVO) JedisUtils.getObject("kdcreditAccessToken");
		if (vo == null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", KDConfig.partner_username);
			params.put("password", KDConfig.partner_password);
			// 发送请求
			vo = (KDTokenVO) postForJson(url, params, KDTokenVO.class, log);
			// 应答成功时，缓存Token
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject("kdcreditAccessToken", vo, 60 * 60 * 23 + 60 * 50);// 默认24小时，这里设置23小时50分钟
			}
			return vo;
		} else {
			return vo;
		}
	}

	@Override
	public KDBlackListVO searchOne(KDBlackListOP op) {
		// 配置参数
		String partnerId = KDConfig.partner_id;
		String partnerName = KDConfig.partner_name;
		String bizCode = KDConfig.blacklist_biz_code;
		String bizName = KDConfig.blacklist_biz_name;
		String url = KDConfig.blacklist_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("token", getToken().getData().getToken());
		params.put("mobile", op.getMobile());
		params.put("name", op.getName());
		params.put("id_card", op.getId_card());
		// 发送请求
		KDBlackListVO vo = (KDBlackListVO) postForJson(url, params, KDBlackListVO.class, log);
		return vo;
	}
}