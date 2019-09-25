/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.koudai.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.koudai.entity.PayLog;
import com.rongdu.loans.koudai.manager.PayLogManager;
import com.rongdu.loans.koudai.op.PayLogListOP;
import com.rongdu.loans.koudai.service.PayLogService;
import com.rongdu.loans.koudai.vo.PayLogListVO;

/**
 * 口袋放款日志-业务逻辑实现类
 * 
 * @author liuzhuang
 * @version 2018-09-19
 */
@Service("kdPayLogService")
public class PayLogServiceImpl extends BaseService implements PayLogService {
	/**
	 * 口袋放款日志-实体管理接口
	 */
	@Autowired
	private PayLogManager payLogManager;

	@SuppressWarnings({ "rawtypes" })
	public Page findList(Page page, PayLogListOP payLogListOP) {
		List<PayLog> list = payLogManager.findList(page, payLogListOP);
		if (CollectionUtils.isEmpty(list)) {
			page.setList(Collections.emptyList());
			return page;
		}
		List<PayLogListVO> list2 = BeanMapper.mapList(list, PayLogListVO.class);
		page.setList(list2);
		return page;
	}

	public BigDecimal sumCurrPayedAmt() {
		return payLogManager.sumCurrPayedAmt();
	}

	@Override
	public int updateByApplyId(PayLogListVO payLog) {
		// TODO Auto-generated method stub
		
		if(StringUtils.isBlank(payLog.getApplyId())){
			return 0;
		}
		
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("apply_id", payLog.getApplyId()));
		return payLogManager.updateByCriteriaSelective(BeanMapper.map(payLog,PayLog.class),criteria1);
	}

}