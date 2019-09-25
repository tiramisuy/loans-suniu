/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/admins">Admins</a> All rights reserved.
 */
package com.rongdu.loans.sys.service;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.CrudService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.sys.dao.LogDao;
import com.rongdu.loans.sys.entity.Log;

/**
 * 日志Service
 * @author sunda
 * @version 2014-05-16
 */
@Service
public class LogService extends CrudService<LogDao, Log> {

	public Page<Log> findPage(Page<Log> page, Log log) {
		
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}
	
}
