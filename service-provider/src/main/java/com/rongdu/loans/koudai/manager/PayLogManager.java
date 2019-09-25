/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.koudai.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.koudai.dao.PayLogDao;
import com.rongdu.loans.koudai.entity.PayLog;
import com.rongdu.loans.koudai.op.PayLogListOP;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;

/**
 * 口袋放款日志-实体管理实现类
 * 
 * @author liuzhuang
 * @version 2018-09-19
 */
@Service("payLogManager")
public class PayLogManager extends BaseManager<PayLogDao, PayLog, String> {
	public List<PayLog> findList(Page page, PayLogListOP payLogListOP) {
		return dao.findList(page, payLogListOP);
	}

	public List<PayLog> findPayingList() {
		// Criteria criteria = new Criteria();
		// criteria.add(Criterion.eq("pay_status", 2));
		// criteria.and(Criterion.lt("pay_time",
		// DateUtils.formatDateTime(DateUtils.addMinutes(new Date(), -5))));
		// return dao.findAllByCriteria(criteria);

		return dao.findPayLogList(2, 2, DateUtils.formatDateTime(DateUtils.addMinutes(new Date(), -5)));
	}

	public long countByApplyId(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		return dao.countByCriteria(criteria);
	}

	/**
	 * 
	 * @Title: findCreatingList @Description: 查询需要创建订单数据 @return List<PayLog>
	 * 返回类型 @throws
	 */
	public List<PayLog> findCreatingList(Map<String, Object> param) {
		return dao.findCreatingList(param);
	}

	public BigDecimal sumCurrPayedAmt() {
		return dao.sumCurrPayedAmt();
	}

	/**
	 * 
	 * @Title: findWithdrawRecode @Description: 查询提现记录 @return List<PayLog>
	 * 返回类型 @throws
	 */
	public List<PayLog> findWithdrawRecode(PayLog payLog) {
		return dao.findWithdrawRecode(payLog);
	}

	/**
	 * 
	 * @Title: findKDWaitingLendingList @Description: 查询口袋存管 待放款订单 @return
	 * List<PayLog> 返回类型 @throws
	 */
	public List<PayLog> findKDWaitingLendingList() {
		return dao.findKDWaitingLendingList();
	}

	/**
	 * 
	 * @Title: findKDUnWithdrawOrderList @Description: 查询口袋存管
	 * 已放款未提现订单(一天前订单) @return List<PayLog> 返回类型 @throws
	 */
	public List<PayLog> findKDUnWithdrawOrderList() {
		return dao.findKDUnWithdrawOrderList(DateUtils.formatDateTime(DateUtils.addHours(new Date(), -25)));
	}

	/**
	 * 口袋放款统计
	 * 
	 * @param payop
	 * @return
	 */
	public List<Map<String, Object>> getPayCount(KDPayCountOP payop) {
		return dao.getPayCount(payop);
	}

}