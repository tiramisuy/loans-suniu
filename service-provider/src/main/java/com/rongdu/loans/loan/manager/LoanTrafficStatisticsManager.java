/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.LoanTrafficStatistics;
import com.rongdu.loans.loan.dao.LoanTrafficStatisticsDao;

/**
 * 贷超导流统计-实体管理实现类
 * @author raowb
 * @version 2018-08-29
 */
@Service("loanTrafficStatisticsManager")
public class LoanTrafficStatisticsManager extends BaseManager<LoanTrafficStatisticsDao, LoanTrafficStatistics, String> {
	
}