/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.RiskTypeDao;
import com.rongdu.loans.risk.entity.RiskType;
import org.springframework.stereotype.Service;

/**
 * 风险分类-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("riskTypeManager")
public class RiskTypeManager extends BaseManager<RiskTypeDao, RiskType, String>{
	
}