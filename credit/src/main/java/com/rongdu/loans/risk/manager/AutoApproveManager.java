/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.AutoApproveDao;
import com.rongdu.loans.risk.entity.AutoApprove;
import org.springframework.stereotype.Service;

/**
 * 自动审批结果-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("autoApproveManager")
public class AutoApproveManager extends BaseManager<AutoApproveDao, AutoApprove, String>{
	
}