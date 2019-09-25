/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.credit.manager.PartnerCreditScoreManager;
import com.rongdu.loans.credit.service.PartnerCreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 第三方(合作机构）信用分-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("partnerCreditScoreService")
public class PartnerCreditScoreServiceImpl  extends BaseService implements  PartnerCreditScoreService{
	
	/**
 	* 第三方(合作机构）信用分-实体管理接口
 	*/
	@Autowired
	private PartnerCreditScoreManager partnerCreditScoreManager;
	
}