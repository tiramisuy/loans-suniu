/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.ApplyTripartiteNotice;
import com.rongdu.loans.loan.dao.ApplyTripartiteNoticeDao;

/**
 * 工单通知-实体管理实现类
 * @author Lee
 * @version 2018-06-01
 */
@Service("applyTripartiteNoticeManager")
public class ApplyTripartiteNoticeManager extends BaseManager<ApplyTripartiteNoticeDao, ApplyTripartiteNotice, String> {
	
}