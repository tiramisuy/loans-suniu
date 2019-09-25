/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.tencent.manager.AntiFraudRiskInfoManager;
import com.rongdu.loans.tencent.service.AntiFraudRiskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 腾讯-反欺诈服务-风险信息-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("antiFraudRiskInfoService")
public class AntiFraudRiskInfoServiceImpl  extends BaseService implements  AntiFraudRiskInfoService{
	
	/**
 	* 腾讯-反欺诈服务-风险信息-实体管理接口
 	*/
	@Autowired
	private AntiFraudRiskInfoManager antiFraudRiskInfoManager;
	
}