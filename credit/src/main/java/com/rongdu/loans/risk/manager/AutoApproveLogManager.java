/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.AutoApproveLogDao;
import com.rongdu.loans.risk.entity.AutoApproveLog;
import org.springframework.stereotype.Service;

/**
 * 自动审批日志-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("autoApproveLogManager")
public class AutoApproveLogManager extends BaseManager<AutoApproveLogDao, AutoApproveLog, String>{
	
}