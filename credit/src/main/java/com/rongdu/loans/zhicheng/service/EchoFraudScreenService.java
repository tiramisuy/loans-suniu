/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service;

import com.rongdu.loans.zhicheng.entity.EchoFraudScreen;

/**
 * 致诚信用-阿福共享平台-欺诈甄别记录-业务逻辑接口
 * @author fy
 * @version 2019-07-05
 */
public interface EchoFraudScreenService {

    void saveFraudScreen(EchoFraudScreen echoFraudScreen);

    EchoFraudScreen getFraudScreen(String applyId);
}