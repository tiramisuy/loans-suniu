/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.service;

import com.rongdu.loans.tongdun.vo.FraudApiOP;
import com.rongdu.loans.tongdun.vo.FraudApiVO;
import com.rongdu.loans.tongdun.vo.RuleDetailOP;
import com.rongdu.loans.tongdun.vo.RuleDetailVO;

/**
 * 同盾-反欺诈决策引擎服务
 * @author sunda
 * @version 2017-08-14
 */
public interface TongdunService {

    /**
     * 反欺诈决策引擎
     * @return
     */
    public FraudApiVO antifraud(FraudApiOP op);

    /**
     * 命中规则详情查询
     * @return
     */
    public RuleDetailVO getRuleDetail(RuleDetailOP op);


}