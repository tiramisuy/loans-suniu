/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.credit.dao.PartnerCreditScoreDao;
import com.rongdu.loans.credit.entity.PartnerCreditScore;
import org.springframework.stereotype.Service;

/**
 * 第三方(合作机构）信用分-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("partnerCreditScoreManager")
public class PartnerCreditScoreManager extends BaseManager<PartnerCreditScoreDao, PartnerCreditScore, String>{
	
}