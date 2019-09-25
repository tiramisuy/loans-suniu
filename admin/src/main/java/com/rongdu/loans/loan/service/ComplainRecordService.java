/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.BaseCrudService;
import com.rongdu.loans.loan.dao.ComplainRecordDao;
import com.rongdu.loans.loan.entity.ComplainRecord;

/**
 * 
* @Description: 投诉工单-业务逻辑接口 
* @author: RaoWenbiao
* @date 2018年8月20日
 */
@Service
public class ComplainRecordService extends BaseCrudService<ComplainRecordDao, ComplainRecord>{
	
	@Autowired
	private ComplainRecordDao complainRecordDao;
	
	public void updateByIdSelective(ComplainRecord complainRecord) {
		complainRecordDao.updateByIdSelective(complainRecord);
	}
}