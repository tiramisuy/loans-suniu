/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ApplyTripartiteRong360;

/**
 * 工单映射（融360）-数据访问接口
 * @author yuanxianchu
 * @version 2018-06-29
 */
@MyBatisDao
public interface ApplyTripartiteRong360Dao extends BaseDao<ApplyTripartiteRong360,String> {
	
	List<String> findThirdIdsByApplyIds(@Param("applyIds")List<String> applyIds);
	
}