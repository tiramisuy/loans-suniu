/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.option.FlowListOP;
import com.rongdu.loans.loan.vo.RepayLogListVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.dao.RepayLogDAO;
import com.rongdu.loans.pay.entity.RepayLog;

/**
 * 充值/还款-实体管理实现类
 * 
 * @author zhangxiaolong
 * @version 2017-07-11
 */
@Service("repayLogManager")
public class RepayLogManager extends BaseManager<RepayLogDAO, RepayLog, String> {

	@Autowired
	private RepayLogDAO repayLogDAO;

	public List<RepayLogListVO> getRepayLogList(Page page, FlowListOP op) {
		return repayLogDAO.getRepayLogList(page, op);
	}

	public int updatePayStatus(String origOrderNo, String payStatus) {
		RepayLog log = new RepayLog();
		log.setPayStatus(payStatus);
		Criteria criteria = new Criteria();
		// criteria.and(Criterion.eq("1","1"));
		criteria.and(Criterion.eq("id", origOrderNo));
		return updateByCriteriaSelective(log, criteria);
	}

	public Long countPayingByRepayPlanItemId(String repayPlanItemId) {
		return repayLogDAO.countPayingByRepayPlanItemId(repayPlanItemId);
	}
	
	public Long countPayingByIdNo(String idNo) {
		return repayLogDAO.countPayingByIdNo(idNo);
	}

	public Long countPayingByApplyId(String applyId) {
		return repayLogDAO.countPayingByApplyId(applyId);
	}

	public List<RepayLogVO> findUnsolvedOrders() {
		return repayLogDAO.findUnsolvedOrders();
	}
	
	public RepayLog findByRepayPlanItemId(String repayPlanItemId) {
		return repayLogDAO.findByRepayPlanItemId(repayPlanItemId);
	}
	
}