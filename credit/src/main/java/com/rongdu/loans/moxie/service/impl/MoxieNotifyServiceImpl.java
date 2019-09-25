/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportNotifyVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportNotifyVO;
import com.rongdu.loans.moxie.entity.BankReportNotify;
import com.rongdu.loans.moxie.entity.EmailReportNotify;
import com.rongdu.loans.moxie.service.BankReportNotifyService;
import com.rongdu.loans.moxie.service.EmailReportNotifyService;
import com.rongdu.loans.moxie.service.MoxieNotifyService;

/**
 * 魔蝎service
 * 
 * @author liuzhuang
 * @version 2018-05-29
 */
@Service("moxieNotifyService")
public class MoxieNotifyServiceImpl extends BaseService implements MoxieNotifyService {

	@Autowired
	private EmailReportNotifyService emailReportNotifyService;
	@Autowired
	private BankReportNotifyService bankReportNotifyService;

	public int saveEmailReportNotify(EmailReportNotifyVO vo) {
		EmailReportNotify entity = new EmailReportNotify();
		entity.setTaskId(vo.getTask_id());
		entity.setUserId(vo.getUser_id());
		entity.setEmail(vo.getEmail());
		entity.setEmailId(vo.getEmail_id());
		entity.setResult(vo.getResult());
		entity.setMessage(vo.getMessage());
		entity.setTimestamp(vo.getTimestamp());
		return emailReportNotifyService.save(entity);
	}

	public int saveBankReportNotify(BankReportNotifyVO vo) {
		BankReportNotify entity = new BankReportNotify();
		entity.setTaskId(vo.getTask_id());
		entity.setUserId(vo.getUser_id());
		entity.setName(vo.getName());
		entity.setResult(vo.getResult());
		entity.setMessage(vo.getMessage());
		entity.setTimestamp(vo.getTimestamp());
		return bankReportNotifyService.save(entity);
	}
}