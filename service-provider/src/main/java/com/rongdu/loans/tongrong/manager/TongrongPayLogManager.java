/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongrong.manager;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tongrong.dao.TongrongPayLogDao;
import com.rongdu.loans.tongrong.entity.TongrongPayLog;
import com.rongdu.loans.tongrong.op.TongrongPayLogOP;

/**
 * 通融放款记录表-实体管理实现类
 * @author fuyuan
 * @version 2018-11-19
 */
@Service("tongrongPayLogManager")
public class TongrongPayLogManager extends BaseManager<TongrongPayLogDao, TongrongPayLog, String> {
	
	public long countByApplyId(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		return dao.countByCriteria(criteria);
	}

	public BigDecimal sumCurrPayedAmt() {
		return dao.sumCurrPayedAmt();
	}
	
	
	public List<TongrongPayLog> findList(Page page, TongrongPayLogOP tongrongPayLogListOP) {
		return dao.findList(page, tongrongPayLogListOP);
	}
	
	
	
}