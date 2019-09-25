/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.statistical.dao;

import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.statistical.vo.WorkbenchVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 消费用户DAO接口
 * @author likang
 * @version 2017-06-12
 */
@MyBatisDao
public interface StatisticalDAO {
	
	WorkbenchVO userDataForWorkbench();

	WorkbenchVO contractDataForWorkbench();

	int applyDataForWorkbench();

	WorkbenchVO applyDataForRepayPlanItem(@Param("begin")Date begin, @Param("end")Date end);
}