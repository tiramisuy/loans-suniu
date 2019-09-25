/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tongdun.dao.AntifraudApiDao;
import com.rongdu.loans.tongdun.entity.AntifraudApi;
import org.springframework.stereotype.Service;

/**
 * 同盾-反欺诈决策服务-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("antifraudApiManager")
public class AntifraudApiManager extends BaseManager<AntifraudApiDao, AntifraudApi, String>{
	
}