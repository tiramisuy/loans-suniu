/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.ApplyTripartiteAnrong;
import com.rongdu.loans.loan.dao.ApplyTripartiteAnrongDao;

import java.util.List;

/**
 * 工单映射（安融）-实体管理实现类
 * @author fy
 * @version 2019-06-21
 */
@Service("applyTripartiteAnrongManager")
public class ApplyTripartiteAnrongManager extends BaseManager<ApplyTripartiteAnrongDao, ApplyTripartiteAnrong, String> {
    public List<String> findThirdIdsByApplyIds(List<String> applyIds){
        return dao.findThirdIdsByApplyIds(applyIds);
    }
}