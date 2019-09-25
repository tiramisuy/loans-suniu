/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tencent.dao.AntiFraudRiskInfoDao;
import com.rongdu.loans.tencent.entity.AntiFraudRiskInfo;
import org.springframework.stereotype.Service;

/**
 * 腾讯-反欺诈服务-风险信息-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("antiFraudRiskInfoManager")
public class AntiFraudRiskInfoManager extends BaseManager<AntiFraudRiskInfoDao, AntiFraudRiskInfo, String>{
	
}