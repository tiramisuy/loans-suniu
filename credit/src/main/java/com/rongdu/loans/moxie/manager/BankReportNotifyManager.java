/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.moxie.entity.BankReportNotify;
import com.rongdu.loans.moxie.dao.BankReportNotifyDao;

/**
 * 魔蝎网银报告通知表-实体管理实现类
 * @author liuzhuang
 * @version 2018-05-29
 */
@Service("bankReportNotifyManager")
public class BankReportNotifyManager extends BaseManager<BankReportNotifyDao, BankReportNotify, String> {
	
}