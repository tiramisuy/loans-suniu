/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.zhicheng.entity.EchoDecisionReport;

/**
 * 致诚信用-阿福共享平台-综合决策报告记录-数据访问接口
 * @author fy
 * @version 2019-07-05
 */
@MyBatisDao
public interface EchoDecisionReportDao extends BaseDao<EchoDecisionReport,String> {
	
}