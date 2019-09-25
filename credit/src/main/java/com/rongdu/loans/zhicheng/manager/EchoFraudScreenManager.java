/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreen;
import com.rongdu.loans.zhicheng.dao.EchoFraudScreenDao;

/**
 * 致诚信用-阿福共享平台-欺诈甄别记录-实体管理实现类
 * @author fy
 * @version 2019-07-05
 */
@Service("echoFraudScreenManager")
public class EchoFraudScreenManager extends BaseManager<EchoFraudScreenDao, EchoFraudScreen, String> {
	
}