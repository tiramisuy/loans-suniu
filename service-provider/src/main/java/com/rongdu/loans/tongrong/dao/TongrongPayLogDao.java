/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongrong.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.tongrong.entity.TongrongPayLog;
import com.rongdu.loans.tongrong.op.TongrongPayLogOP;

/**
 * 通融放款记录表-数据访问接口
 * @author fuyuan
 * @version 2018-11-19
 */
@MyBatisDao
public interface TongrongPayLogDao extends BaseDao<TongrongPayLog,String> {

	public BigDecimal sumCurrPayedAmt();
	
	
	List<TongrongPayLog> findList(@Param(value = "page") Page page, @Param(value = "op") TongrongPayLogOP op);
}