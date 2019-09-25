/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportOP;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportVO;
import com.rongdu.loans.moxie.common.MoxieConfig;
import com.rongdu.loans.moxie.service.MoxieBankService;

/**
 * 新颜
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
@Service
public class MoxieBankServiceImpl extends PartnerApiService implements MoxieBankService {

	@Override
	public BankReportVO getReportData(BankReportOP op) {
		// 配置参数
		String partnerId = MoxieConfig.partner_id;
		String partnerName = MoxieConfig.partner_name;
		String bizCode = MoxieConfig.bank_report_biz_code;
		String bizName = MoxieConfig.bank_report_name;
		String url = MoxieConfig.bank_report_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		url = url.replace("{task_id}", op.getTaskId());

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "token " + MoxieConfig.moxie_api_token);

		// 发送请求
		BankReportVO vo = (BankReportVO) getForJson(url, null, headers, BankReportVO.class, log);
		return vo;
	}
}