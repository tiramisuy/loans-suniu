/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service;

import com.rongdu.loans.zhicheng.vo.*;

/**
 * 宜信致诚-阿福共享平台征信服务
 * @author sunda
 * @version 2017-08-14
 */
public interface ZhichengService {

    /**
     * 查询借款、风险名单和逾期信息
     * @return
     */
    CreditInfoVO queryCreditInfo(CreditInfoOP op);

    /**
     * 查询风险名单
     * @return
     */
    RiskListVO queryMixedRiskList(RiskListOP op);

    /**
     * 查询借款、风险名单和逾期信息(通过第三方使用宜信阿福  后期有自己账号需修改)
     * @return
     */
    CreditInfoVO queryCreditInfoOther(CreditInfoOP op);

    /**
     * 宜信阿福综合决策报告接口(通过第三方使用宜信阿福  后期有自己账号需修改)
     */
    DecisionVO queryDecisionOther(CreditInfoOP op);

    /**
     * 宜信阿福欺诈甄别接口(通过第三方使用宜信阿福  后期有自己账号需修改)
     */
    FraudScreenVO queryFraudScreenOther(CreditInfoOP op);
}