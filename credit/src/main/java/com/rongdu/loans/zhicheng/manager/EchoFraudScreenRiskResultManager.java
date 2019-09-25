/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreenRiskResult;
import com.rongdu.loans.zhicheng.dao.EchoFraudScreenRiskResultDao;

/**
 * 致诚信用-阿福共享平台-欺诈甄别-风险名单结果集记录(由于不是直接调用,数据不全,后期谨慎修改)-实体管理实现类
 * @author fy
 * @version 2019-07-05
 */
@Service("echoFraudScreenRiskResultManager")
public class EchoFraudScreenRiskResultManager extends BaseManager<EchoFraudScreenRiskResultDao, EchoFraudScreenRiskResult, String> {
	
}