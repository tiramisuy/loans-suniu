/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.entity.ApplyTripartiteRong360;
import com.rongdu.loans.loan.manager.ApplyTripartiteRong360Manager;
import com.rongdu.loans.loan.service.ApplyTripartiteRong360Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工单映射（融360）-业务逻辑实现类
 *
 * @author yuanxianchu
 * @version 2018-06-29
 */
@Service("applyTripartiteRong360Service")
public class ApplyTripartiteRong360ServiceImpl extends BaseService implements ApplyTripartiteRong360Service {

    /**
     * 工单映射（融360）-实体管理接口
     */
    @Autowired
    private ApplyTripartiteRong360Manager applyTripartiteRong360Manager;

    @Override
    public boolean isExistApplyId(String applyId) {
        if (applyId == null) {
            applyId = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        criteria.and(Criterion.eq("del", 0));
        long count = applyTripartiteRong360Manager.countByCriteria(criteria);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int testInsertTripartiteOrder(String applyId, String orderSn) {
        /*ApplyTripartiteRong360 applyTripartiteRong360 = new ApplyTripartiteRong360();
        applyTripartiteRong360.setApplyId(applyId);
		applyTripartiteRong360.setTripartiteNo(orderSn);
		int a = applyTripartiteRong360Manager.insert(applyTripartiteRong360);
        return a;*/
        System.out.println("lalalalalalalalalalalalalalla");
        return 666;
    }

    @Override
    public int delOrderNo(String orderNo) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("tripartite_no", orderNo));
        ApplyTripartiteRong360 applyTripartiteRong360 = new ApplyTripartiteRong360();
        applyTripartiteRong360.setDel(1);
        int r = applyTripartiteRong360Manager.updateByCriteriaSelective(applyTripartiteRong360,criteria);
        return r;
    }

    @Override
    public int insertTripartiteOrder(String applyId, String orderSn) {
        ApplyTripartiteRong360 applyTripartiteRong360 = new ApplyTripartiteRong360();
        applyTripartiteRong360.setApplyId(applyId);
        applyTripartiteRong360.setTripartiteNo(orderSn);
        return applyTripartiteRong360Manager.insert(applyTripartiteRong360);
    }

    @Override
    public String getThirdIdByApplyId(String applyId) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        ApplyTripartiteRong360 applyTripartiteRong360 = applyTripartiteRong360Manager.getByCriteria(criteria);
        if (applyTripartiteRong360 == null) {
            return null;
        } else {
            return applyTripartiteRong360.getTripartiteNo();
        }
    }

    @Override
    public String getApplyIdByThirdId(String thirdId) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("tripartite_no", thirdId));
        ApplyTripartiteRong360 applyTripartiteRong360 = applyTripartiteRong360Manager.getByCriteria(criteria);
        if (applyTripartiteRong360 == null) {
            return null;
        } else {
            return applyTripartiteRong360.getApplyId();
        }
    }

    @Override
    public List<String> findThirdIdsByApplyIds(List<String> applyIdList) {

        return applyTripartiteRong360Manager.findThirdIdsByApplyIds(applyIdList);
    }
}