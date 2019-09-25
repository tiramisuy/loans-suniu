/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.RefuseReason;

/**
 * 贷款审核拒绝原因-数据访问接口
 * @author zhangxiaolong
 * @version 2017-07-07
 */
@MyBatisDao
public interface RefuseReasonDAO extends BaseDao<RefuseReason,String> {

    int record(String id);
}