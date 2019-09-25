/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.moxie.entity.EmailReportNotify;
import com.rongdu.loans.moxie.dao.EmailReportNotifyDao;

/**
 * 魔蝎信用卡邮箱报告通知表-实体管理实现类
 * @author liuzhuang
 * @version 2018-05-29
 */
@Service("emailReportNotifyManager")
public class EmailReportNotifyManager extends BaseManager<EmailReportNotifyDao, EmailReportNotify, String> {
	
}