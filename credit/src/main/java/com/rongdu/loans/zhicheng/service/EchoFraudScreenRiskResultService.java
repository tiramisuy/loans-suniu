/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service;

import com.rongdu.loans.zhicheng.entity.EchoFraudScreenRiskResult;

import java.util.List;

/**
 * 致诚信用-阿福共享平台-欺诈甄别-风险名单结果集记录(由于不是直接调用,数据不全,后期谨慎修改)-业务逻辑接口
 * @author fy
 * @version 2019-07-05
 */
public interface EchoFraudScreenRiskResultService {

    void saveFraudScreenRiskResultList(List<EchoFraudScreenRiskResult> entityList);

    List<EchoFraudScreenRiskResult> getFraudScreenRiskResult(String applyId);
}