/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.jubao.cps.dao;

import com.jubao.cps.entity.ApplyTripartiteSll;
import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单映射（sll）-数据访问接口
 * @author Lee
 * @version 2018-12-10
 */
@MyBatisDao
public interface ApplyTripartiteSllDao extends BaseDao<ApplyTripartiteSll,String> {
    List<String> findThirdIdsByApplyIds(@Param("applyIds")List<String> applyIds);
}