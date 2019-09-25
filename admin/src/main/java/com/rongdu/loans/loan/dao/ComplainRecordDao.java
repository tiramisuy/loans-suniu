/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ComplainRecord;

/**
 * 投诉工单-数据访问接口
 * @author system
 * @version 2018-08-20
 */
@MyBatisDao
public interface ComplainRecordDao extends CrudDao<ComplainRecord> {
	
	void updateByIdSelective(@Param(value = "entity")ComplainRecord complainRecord);
}