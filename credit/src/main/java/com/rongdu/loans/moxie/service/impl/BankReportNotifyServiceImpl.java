/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.moxie.entity.BankReportNotify;
import com.rongdu.loans.moxie.manager.BankReportNotifyManager;
import com.rongdu.loans.moxie.service.BankReportNotifyService;

/**
 * 魔蝎网银报告通知表-业务逻辑实现类
 * 
 * @author liuzhuang
 * @version 2018-05-29
 */
@Service("bankReportNotifyService")
public class BankReportNotifyServiceImpl extends BaseService implements BankReportNotifyService {

	/**
	 * 魔蝎网银报告通知表-实体管理接口
	 */
	@Autowired
	private BankReportNotifyManager bankReportNotifyManager;

	@Override
	public int save(BankReportNotify entity) {
		return bankReportNotifyManager.insert(entity);
	}
}