/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.ApplyTripartiteDkqb;
import com.rongdu.loans.loan.dao.ApplyTripartiteDkqbDao;

/**
 * 工单映射（贷款钱包）-实体管理实现类
 * @author fy
 * @version 2019-04-16
 */
@Service("applyTripartiteDkqbManager")
public class ApplyTripartiteDkqbManager extends BaseManager<ApplyTripartiteDkqbDao, ApplyTripartiteDkqb, String> {

    public String getApplyId(String lwOrderId) {
        if (lwOrderId == null) {
            lwOrderId = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", lwOrderId));
        ApplyTripartiteDkqb applyTripartiteJdq = dao.getByCriteria(criteria);
        return applyTripartiteJdq.getTripartiteNo();
    }
}