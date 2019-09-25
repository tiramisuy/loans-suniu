/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.moxie.entity.EmailReportNotify;

/**
 * 魔蝎信用卡邮箱报告通知表-数据访问接口
 * @author liuzhuang
 * @version 2018-05-29
 */
@MyBatisDao
public interface EmailReportNotifyDao extends BaseDao<EmailReportNotify,String> {
	
}