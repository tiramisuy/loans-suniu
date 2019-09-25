/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.statistical.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.statistical.dao.ZhimaCreditStatisticsDao;
import com.rongdu.loans.statistical.entity.ZhimaCreditStatistics;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 芝麻信用每日汇总统计表-实体管理实现类
 * @author zhangxiaolong
 * @version 2017-09-12
 */
@Service("creditStatisticsManager")
public class CreditStatisticsManager extends BaseManager<ZhimaCreditStatisticsDao, ZhimaCreditStatistics, String> {

    /**
     * 批量修改推送状态
     * @param ids
     * @param pushStatus
     * @return
     */
    public int updatePushStatusBatch(List<String> ids, String pushStatus){
        return dao.updatePushStatusBatch(ids, pushStatus);
    }

    public List<ZhimaCreditStatistics> getStatisticsData(Date beginTime, Date endTime, String pushStatus){
        return dao.getStatisticsData(beginTime, endTime, pushStatus);
    }
	
}