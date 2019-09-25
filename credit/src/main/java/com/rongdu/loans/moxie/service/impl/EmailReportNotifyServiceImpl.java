/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.moxie.entity.EmailReportNotify;
import com.rongdu.loans.moxie.manager.EmailReportNotifyManager;
import com.rongdu.loans.moxie.service.EmailReportNotifyService;

/**
 * 魔蝎信用卡邮箱报告通知表-业务逻辑实现类
 * 
 * @author liuzhuang
 * @version 2018-05-29
 */
@Service("emailReportNotifyService")
public class EmailReportNotifyServiceImpl extends BaseService implements EmailReportNotifyService {

	/**
	 * 魔蝎信用卡邮箱报告通知表-实体管理接口
	 */
	@Autowired
	private EmailReportNotifyManager emailReportNotifyManager;

	@Override
	public int save(EmailReportNotify entity) {
		return emailReportNotifyManager.insert(entity);
	}

}