/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service;

import com.rongdu.loans.zhicheng.entity.EchoDecisionReport;

/**
 * 致诚信用-阿福共享平台-综合决策报告记录-业务逻辑接口
 * @author fy
 * @version 2019-07-05
 */
public interface EchoDecisionReportService {

    void saveDecisionReport(EchoDecisionReport entity);

    EchoDecisionReport getDecisionReport(String applyId);
}