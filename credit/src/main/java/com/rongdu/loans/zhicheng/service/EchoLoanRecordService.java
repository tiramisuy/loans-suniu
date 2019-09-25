/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service;

import com.rongdu.loans.zhicheng.entity.EchoLoanRecord;

import java.util.List;

/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-借款记录历史-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface EchoLoanRecordService {

    void saveLoanRecordList(List<EchoLoanRecord> list);

    List<EchoLoanRecord> getLoanRecordListByApplyId(String applyId);
}