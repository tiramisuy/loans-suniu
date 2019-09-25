/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ApplyTripartiteAnrong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单映射（安融）-数据访问接口
 * @author fy
 * @version 2019-06-21
 */
@MyBatisDao
public interface ApplyTripartiteAnrongDao extends BaseDao<ApplyTripartiteAnrong,String> {
    List<String> findThirdIdsByApplyIds(@Param("applyIds")List<String> applyIds);
}