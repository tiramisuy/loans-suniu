/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.RepayWarn;
import com.rongdu.loans.loan.option.RepayWarnOP;
import com.rongdu.loans.loan.vo.RepayWarnVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 还款-预提醒表-数据访问接口
 * @author fy
 * @version 2019-07-23
 */
@MyBatisDao
public interface RepayWarnDao extends BaseDao<RepayWarn,String> {

    List<RepayWarnVO> getRepayWarnList(@Param(value = "page") Page page,
                                       @Param(value = "op") RepayWarnOP op);
}