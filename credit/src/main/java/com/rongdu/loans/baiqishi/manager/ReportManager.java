/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.baiqishi.dao.ReportDao;
import com.rongdu.loans.baiqishi.entity.Report;
import org.springframework.stereotype.Service;

/**
 * 白骑士-资信报告-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("reportManager")
public class ReportManager extends BaseManager<ReportDao, Report, String>{
	
}