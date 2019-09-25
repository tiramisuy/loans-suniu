/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.service;

import com.rongdu.loans.tencent.vo.AntiFraudOP;
import com.rongdu.loans.tencent.vo.AntiFraudVO;

/**
 * 腾讯-反欺诈服务-风险信息-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface TencentAntiFraudService {

    /**
     * 腾讯-反欺诈服务
     * @param op
     * @return
     */
    public AntiFraudVO  antiFraud(String userId,String applyId,AntiFraudOP op);
}