/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.tencent.entity.AntiFraudRiskInfo;

/**
 * 腾讯-反欺诈服务-风险信息-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface AntiFraudRiskInfoDao extends BaseDao<AntiFraudRiskInfo,String> {
	
}