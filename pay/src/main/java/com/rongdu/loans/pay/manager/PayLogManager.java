/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.manager;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;
import com.rongdu.loans.loan.option.WithdrawDetailListOP;
import com.rongdu.loans.loan.vo.WithdrawDetailListVO;
import com.rongdu.loans.pay.dao.PayLogDAO;
import com.rongdu.loans.pay.entity.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 提现/代付-实体管理实现类
 * 
 * @author zhangxiaolong
 * @version 2017-07-10
 */
@Service("payLogManager")
public class PayLogManager extends BaseManager<PayLogDAO, PayLog, String> {

	@Autowired
	private PayLogDAO payLogDAO;

	/**
	 * 提现明细列表查询
	 * 
	 * @param page
	 * @param op
	 * @return
	 */
	public List<WithdrawDetailListVO> withdrawList(Page page, WithdrawDetailListOP op) {
		return payLogDAO.withdrawList(page, op);
	}

	/**
	 * 导出提现明细
	 * 
	 * @param op
	 * @return
	 */
	public List<WithdrawDetailListVO> exportWithdrawList(WithdrawDetailListOP op) {
		return payLogDAO.exportWithdrawList(op);
	}

	/**
	 * 查询某个充值订单已经代付成功的金额
	 * 
	 * @param origOrderNo
	 * @return
	 */
	public Double findPayedAmt(String origOrderNo) {
		// TODO Auto-generated method stub
		return payLogDAO.findPayedAmt(origOrderNo);
	}

	/**
	 * 从source中过滤出提现成功的数据
	 * 
	 * @param source
	 * @return
	 */
	public List<String> successLogFilter(List<String> source) {
		return payLogDAO.successLogFilter(source);
	}

    
    public List<Map<String, Object>> getBFPayCount(KDPayCountOP payop){
    	return payLogDAO.getBFPayCount(payop);
    }
	public List<PayLog> findPayUnsolvedOrders() {
		return payLogDAO.findPayUnsolvedOrders();
	}

	public List<PayLog> findBaofooPayUnsolvedOrders(List<String> statusList) {
		return payLogDAO.findBaofooPayUnsolvedOrders(statusList);
	}

	public List<PayLog> findTonglianPayUnsolvedOrders(List<String> statusList) {
		return payLogDAO.findTonglianPayUnsolvedOrders(statusList);
	}

	public List<PayLog> findTonglianLoanPayUnsolvedOrders(List<String> statusList) {
		return payLogDAO.findTonglianLoanPayUnsolvedOrders(statusList);
	}


	public List<PayLog> findTRBaofooPayUnsolvedOrders(List<String> statusList) {
		return payLogDAO.findTRBaofooPayUnsolvedOrders(statusList);
	}

	/**
	 * 查询宝付代付处理中和成功的数量
	 * 
	 * @param applyId
	 * @param statusList
	 * @return
	 */
	public Long countBaofooPayUnsolvedAndSuccess(String applyId, List<String> statusList) {
		return payLogDAO.countBaofooPayUnsolvedAndSuccess(applyId, statusList);
	}

	/**
	 * 查询通联代付处理中和成功的数量
	 *
	 * @param applyId
	 * @param statusList
	 * @return
	 */
	public Long countTonglianPayUnsolvedAndSuccess(String applyId, List<String> statusList) {
		return payLogDAO.countTonglianPayUnsolvedAndSuccess(applyId, statusList);
	}


	public PayLog findWithdrawAmount(String userId) {
		return payLogDAO.findWithdrawAmount(userId);
	}

	
	
	/**
     * 查询债权转让列表
     * @param page
     * @param op
     * @return
     */
    public List<WithdrawDetailListVO> equitableAssignmentList( WithdrawDetailListOP op) {
    	return payLogDAO.equitableAssignmentList(op);
    }

	public BigDecimal sumHanjsCurrPayedAmt() {
		return dao.sumHanjsCurrPayedAmt();
	}

	public PayLog findWithdrawLogByApplyId(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		return payLogDAO.getByCriteria(criteria);
	}

	public PayLog findPayLogByApplyId(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		criteria.and(Criterion.eq("del", 0));
		return payLogDAO.getByCriteria(criteria);
	}

}