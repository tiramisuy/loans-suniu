/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.loans.loan.option.CancelLog;

/**
 * loan_cancel_log-业务逻辑接口
 * @author qf
 * @version 2019-02-26
 */
public interface CancelLogService {

    int saveCancelLog(CancelLog cancelLog);

    long countByUserId(String userId);
}