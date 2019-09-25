/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service.impl;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.zhicheng.entity.EchoLoanRecord;
import com.rongdu.loans.zhicheng.manager.EchoLoanRecordManager;
import com.rongdu.loans.zhicheng.service.EchoLoanRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-借款记录历史-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("echoLoanRecordService")
public class EchoLoanRecordServiceImpl  extends BaseService implements  EchoLoanRecordService{
	
	/**
 	* 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-借款记录历史-实体管理接口
 	*/
	@Autowired
	private EchoLoanRecordManager echoLoanRecordManager;

	@Override
	public void saveLoanRecordList(List<EchoLoanRecord> list) {
		echoLoanRecordManager.insertBatch(list);
	}

	@Override
	public List<EchoLoanRecord> getLoanRecordListByApplyId(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		return echoLoanRecordManager.findAllByCriteria(criteria);
	}
}