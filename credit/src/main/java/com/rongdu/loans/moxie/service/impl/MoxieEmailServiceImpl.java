/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportOP;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportVO;
import com.rongdu.loans.moxie.common.MoxieConfig;
import com.rongdu.loans.moxie.service.MoxieEmailService;

/**
 * 新颜
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
@Service
public class MoxieEmailServiceImpl extends PartnerApiService implements MoxieEmailService {

	@Override
	public List<EmailReportVO> getReportData(EmailReportOP op) {
		// 配置参数
		String partnerId = MoxieConfig.partner_id;
		String partnerName = MoxieConfig.partner_name;
		String bizCode = MoxieConfig.email_report_biz_code;
		String bizName = MoxieConfig.email_report_name;
		String url = MoxieConfig.email_report_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		url = url.replace("{email_id}", op.getEmailId());
		url = url.replace("{task_id}", op.getTaskId());

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "token " + MoxieConfig.moxie_api_token);

		// 发送请求
		Object response = getListForJson(url, null, headers, EmailReportVO.class, log);
		if (response instanceof EmailReportVO) {
			return null;
		} else {
			List<EmailReportVO> list = (List<EmailReportVO>) response;
			if (!list.isEmpty()) {
				boolean isSuccess = false;
				for (EmailReportVO v : list) {
					if (v != null && v.isSuccess()) {
						isSuccess = true;
						break;
					}
				}
				if (isSuccess)
					return list;
			}
			return null;
		}
	}
}