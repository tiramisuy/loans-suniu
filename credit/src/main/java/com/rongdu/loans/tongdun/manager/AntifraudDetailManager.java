/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tongdun.dao.AntifraudDetailDao;
import com.rongdu.loans.tongdun.entity.AntifraudDetail;
import org.springframework.stereotype.Service;

/**
 * 同盾-反欺诈命中规则详情-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("antifraudDetailManager")
public class AntifraudDetailManager extends BaseManager<AntifraudDetailDao, AntifraudDetail, String>{
	
}