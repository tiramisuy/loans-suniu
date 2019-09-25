/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.CollectionAssignment;

/**
 * 催收分配记录-数据访问接口
 * @author zhangxiaolong
 * @version 2017-09-28
 */
@MyBatisDao
public interface CollectionAssignmentDao extends BaseDao<CollectionAssignment,String> {

    List<CollectionAssignment> getAllByRepayPlanItemId(String repayPlanItemId);

    List<CollectionAssignment> getAllByItemIdAndDel(@Param(value = "idList")List<String> idList, @Param(value = "del")Integer del);

    List<CollectionAssignment> getForReturnBack();

    int deleteByIdList(@Param(value = "idList")List<String> idList);
    
    public CollectionAssignment getOperateByApplyId(@Param(value = "applyId")String applyId);
    

}