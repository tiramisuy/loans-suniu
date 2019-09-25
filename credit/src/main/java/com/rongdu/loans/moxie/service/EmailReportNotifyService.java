/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service;

import com.rongdu.loans.moxie.entity.EmailReportNotify;

/**
 * 魔蝎信用卡邮箱报告通知表-业务逻辑接口
 * 
 * @author liuzhuang
 * @version 2018-05-29
 */
public interface EmailReportNotifyService {

	public int save(EmailReportNotify entity);
}