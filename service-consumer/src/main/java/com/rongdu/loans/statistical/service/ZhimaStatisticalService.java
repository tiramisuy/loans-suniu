package com.rongdu.loans.statistical.service;

import com.rongdu.common.task.TaskResult;

import java.util.Date;

/**
 * 芝麻信用统计接口
 * Created by zhangxiaolong on 2017/8/29.
 */
public interface ZhimaStatisticalService {

    /**
     * 收集汇总前一天的芝麻接入数据
     */
    TaskResult collect();

    /**
     * 收集汇总具体某一天的芝麻接入数据
     */
    TaskResult collect(Date date);

    /**
     * 上送某日的芝麻接口数据
     * @param date
     * @return
     */
    TaskResult pushZhimaCreditStatistics(Date date);
}
