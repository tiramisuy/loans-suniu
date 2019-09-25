/**
 * Copyright 2015-2017 融都钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.credit.common.RedisPrefix;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.risk.manager.RiskRuleManager;
import com.rongdu.loans.risk.service.RiskRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 风控规则-业务逻辑实现类
 *
 * @author sunda
 * @version 2017-08-14
 */
@Service("riskRuleService")
public class RiskRuleServiceImpl extends BaseService implements RiskRuleService {

    /**
     * 风控规则-实体管理接口
     */
    @Autowired
    private RiskRuleManager riskRuleManager;

    private int CACHE_SECONDS = 5 * 60;

    /**
     * 获得所有的风控规则列表
     */
    public List<RiskRule> getAllRiskRuleList() {
        String cacheId = RedisPrefix.RISK_RULE_LIST;
        List<RiskRule> list = (List<RiskRule>) JedisUtils.getObjectList(cacheId);
        if (list == null || list.isEmpty()) {
            list = riskRuleManager.getAllRiskRuleList();
            if (list != null && !list.isEmpty()) {
                JedisUtils.setObjectList(cacheId, list, CACHE_SECONDS);
            }
        }
        return list;
    }

    /**
     * 获得某个特定的风控规则
     */
    public RiskRule getRiskRule(String ruleCode, Integer modelId) {
        Assert.notNull(ruleCode, "风控规则CODE不能为空");
        List<RiskRule> list = getAllRiskRuleList();
        for (RiskRule rule : list) {
            if (ruleCode.equals(rule.getRuleCode()) && modelId.intValue() == rule.getModelId()) {
                return rule;
            }
        }
        return null;
    }

    /**
     * 根据来源查询一组风控规则
     *
     * @param ruleSetId 不能为空
     * @return
     */
    public List<RiskRule> getRiskRuleList(String ruleSetId, Integer modelId) {
        Assert.notNull(ruleSetId, "风控规则集ID不能为空");
        String cacheId = RedisPrefix.RISK_RULE_LIST_PREFIX + ruleSetId;
        List<RiskRule> list = (List<RiskRule>) JedisUtils.getObjectList(cacheId);
        if (list == null) {
            List<RiskRule> allRules = getAllRiskRuleList();
            list = new ArrayList<>();
            for (RiskRule rule : allRules) {
                if (ruleSetId.equals(rule.getParentRuleId()) && rule.getModelId().equals(modelId)) {
                    list.add(rule);
                }
            }
            if (list != null && !list.isEmpty()) {
                JedisUtils.setObjectList(cacheId, list, CACHE_SECONDS);
            }
        }
        return list;
    }

    /**
     * 根据来源查询一组风控规则
     *
     * @param ruleSetId 不能为空
     * @return
     */
    public Map<String, RiskRule> getRiskRuleMap(String ruleSetId, Integer modelId) {
        Assert.notNull(ruleSetId, "规则集ID不能为空");
        String cacheId = RedisPrefix.RISK_RULE_LIST_PREFIX + ruleSetId;
        Map<String, RiskRule> map = (Map) JedisUtils.getObjectMap(cacheId);
        if (map == null) {
            List<RiskRule> allRules = getAllRiskRuleList();
            map = new LinkedHashMap<>();
            for (RiskRule rule : allRules) {
                if (ruleSetId.equals(rule.getParentRuleId()) && rule.getModelId().equals(modelId)) {
                    map.put(rule.getRuleCode(), rule);
                }
            }
            if (map != null && !map.isEmpty()) {
                JedisUtils.setObjectMap(cacheId, map, CACHE_SECONDS);
            }
        }
        return map;
    }
}