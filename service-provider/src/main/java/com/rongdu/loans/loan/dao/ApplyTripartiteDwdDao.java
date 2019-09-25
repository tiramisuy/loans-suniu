/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ApplyTripartiteDwd;

/**
 * 工单映射（大王贷）-数据访问接口
 * @author Lee
 * @version 2018-10-30
 */
@MyBatisDao
public interface ApplyTripartiteDwdDao extends BaseDao<ApplyTripartiteDwd,String> {
	
	List<String> findThirdIdsByApplyIds(@Param("applyIds")List<String> applyIds);
	
}