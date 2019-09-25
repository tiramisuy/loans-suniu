/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.entity.ApplyTripartite;
import com.rongdu.loans.loan.entity.ApplyTripartiteNotice;
import com.rongdu.loans.loan.manager.ApplyTripartiteManager;
import com.rongdu.loans.loan.manager.ApplyTripartiteNoticeManager;
import com.rongdu.loans.loan.service.ApplyTripartiteService;

/**
 * 工单映射-业务逻辑实现类
 *
 * @author Lee
 * @version 2018-05-29
 */
@Service("applyTripartiteService")
public class ApplyTripartiteServiceImpl extends BaseService implements ApplyTripartiteService {

    /**
     * 工单映射-实体管理接口
     */
    @Autowired
    private ApplyTripartiteManager applyTripartiteManager;
    @Autowired
    private ApplyTripartiteNoticeManager applyTripartiteNoticeManager;

    @Override
    public int insertTripartiteOrder(String applyId, String orderSn) {
        ApplyTripartite applyTripartite = new ApplyTripartite();
        applyTripartite.setApplyId(applyId);
        applyTripartite.setTripartiteNo(orderSn);
        return applyTripartiteManager.insert(applyTripartite);
    }

    @Override
    public boolean isExistApplyId(String applyId) {
    	if (applyId == null) {
			applyId = "";
		}
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        long count = applyTripartiteManager.countByCriteria(criteria);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getApplyIdByThirdId(String order_sn) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("tripartite_no", order_sn));
        ApplyTripartite applyTripartite = applyTripartiteManager.getByCriteria(criteria);
        if (applyTripartite == null) {
            return null;
        } else {
            return applyTripartite.getApplyId();
        }
    }

    @Override
    public int insertTripartiteOrderNotice(String applyId, String orderSn, String notice) {
        ApplyTripartiteNotice applyTripartiteNotice = new ApplyTripartiteNotice();
        applyTripartiteNotice.setApplyId(applyId);
        applyTripartiteNotice.setTripartiteNo(orderSn);
        applyTripartiteNotice.setNotice(notice);
        return applyTripartiteNoticeManager.insert(applyTripartiteNotice);
    }

    @Override
    public String getThirdIdByApplyId(String applyId) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        ApplyTripartite applyTripartite = applyTripartiteManager.getByCriteria(criteria);
        if (applyTripartite == null) {
            return null;
        } else {
            return applyTripartite.getTripartiteNo();
        }
    }


}