/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.dao.HitRuleDao;
import com.rongdu.loans.risk.entity.HitRule;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 命中的风控规则-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("hitRuleManager")
public class HitRuleManager extends BaseManager<HitRuleDao, HitRule, String>{

    /**
     * 按照applyId或者userId来查询命中的风控规则
     * @param applyId
     * @param userId
     * @return
     */
    public List<HitRule> getHitRuleList(String applyId, String userId) {
        if (StringUtils.isNotBlank(applyId)||StringUtils.isNotBlank(userId)){
            Criteria criteria = new Criteria();
            if(StringUtils.isNotBlank(applyId)){
                criteria.add(Criterion.eq("apply_id",applyId));
            }
            if(StringUtils.isNotBlank(userId)){
                criteria.add(Criterion.eq("user_id",userId));
            }
            return findAllByCriteria(criteria);
        }else{
            return null;
        }

    }
}