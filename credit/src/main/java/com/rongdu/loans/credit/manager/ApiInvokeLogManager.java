/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.credit.dao.ApiInvokeLogDao;
import com.rongdu.loans.credit.entity.ApiInvokeLog;
import org.springframework.stereotype.Service;

/**
 * 征信数据合作机构接口调用日志-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("apiInvokeLogManager")
public class ApiInvokeLogManager extends BaseManager<ApiInvokeLogDao, ApiInvokeLog, String>{
	
}