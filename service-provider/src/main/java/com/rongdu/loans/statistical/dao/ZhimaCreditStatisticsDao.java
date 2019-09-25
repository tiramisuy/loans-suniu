/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.statistical.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.statistical.entity.ZhimaCreditStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 芝麻信用每日汇总统计表-数据访问接口
 * @author zhangxiaolong
 * @version 2017-09-12
 */
@MyBatisDao
public interface ZhimaCreditStatisticsDao extends BaseDao<ZhimaCreditStatistics,String> {

    int updatePushStatusBatch(@Param("ids")List<String> ids, @Param("pushStatus")String pushStatus);

    List<ZhimaCreditStatistics> getStatisticsData(@Param("beginTime")Date beginTime,
                                                  @Param("endTime")Date endTime,
                                                  @Param("pushStatus") String pushStatus);

}