/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.RepayWarnDao;
import com.rongdu.loans.loan.entity.RepayWarn;
import com.rongdu.loans.loan.option.RepayWarnOP;
import com.rongdu.loans.loan.vo.RepayWarnVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 还款-预提醒表-实体管理实现类
 * @author fy
 * @version 2019-07-23
 */
@Service("repayWarnManager")
public class RepayWarnManager extends BaseManager<RepayWarnDao, RepayWarn, String> {

    public List<RepayWarnVO> getRepayWarnList(Page voPage, RepayWarnOP op) {
        return dao.getRepayWarnList(voPage, op);
    }
}