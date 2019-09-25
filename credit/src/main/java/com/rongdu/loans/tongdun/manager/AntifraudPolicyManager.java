/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tongdun.dao.AntifraudPolicyDao;
import com.rongdu.loans.tongdun.entity.AntifraudPolicy;
import org.springframework.stereotype.Service;

/**
 * 同盾-反欺诈策略-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("antifraudPolicyManager")
public class AntifraudPolicyManager extends BaseManager<AntifraudPolicyDao, AntifraudPolicy, String>{
	
}