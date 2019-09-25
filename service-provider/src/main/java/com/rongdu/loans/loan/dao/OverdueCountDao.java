/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.option.OverdueCountOP;
import com.rongdu.loans.loan.vo.OverdueCountVO;

/**
 * 逾期统计
 * @author liuliang
 * @version 2018-05-31
 */
@MyBatisDao
public interface OverdueCountDao extends BaseDao<OverdueCountVO,String> {
	public List<OverdueCountVO> overdueCountList(
			@Param("page") Page<OverdueCountVO> page, @Param("op") OverdueCountOP op);
	
}