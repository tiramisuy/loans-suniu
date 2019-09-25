/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.WithdrawDetailListOP;
import com.rongdu.loans.loan.vo.WithdrawDetailListVO;
import com.rongdu.loans.pay.entity.PayLog;

/**
 * 提现/代付-数据访问接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-10
 */
@MyBatisDao
public interface PayLogDAO extends BaseDao<PayLog, String> {

	public List<WithdrawDetailListVO> withdrawList(@Param(value = "page") Page<ApplyListOP> page,
			@Param(value = "op") WithdrawDetailListOP op);

	public List<WithdrawDetailListVO> exportWithdrawList(@Param(value = "op") WithdrawDetailListOP op);

	public Double findPayedAmt(String origOrderNo);

	public List<String> successLogFilter(@Param(value = "source") List<String> source);

	/**
	 * code y0621
	 * 
	 * @Title: findPayUnsolvedOrders
	 * @Description: 查找先锋处理中记录
	 * @param @return 参数
	 * @return List<PayLog> 返回类型
	 */
	List<PayLog> findPayUnsolvedOrders();

	/**
	 * 查询乐视宝付代付处理中订单
	 * 
	 * @return
	 */
	List<PayLog> findBaofooPayUnsolvedOrders(@Param("statusList") List<String> statusList);


	/**
	 * 查询通联代付处理中订单
	 *
	 * @return
	 */
	List<PayLog> findTonglianPayUnsolvedOrders(@Param("statusList") List<String> statusList);

	/**
	 * 查询乐视宝付代付处理中订单
	 *
	 * @return
	 */
	List<PayLog> findTonglianLoanPayUnsolvedOrders(@Param("statusList") List<String> statusList);
	
	/**
	 * 查询通融宝付代付处理中订单
	 * 
	 * @return
	 */
	List<PayLog> findTRBaofooPayUnsolvedOrders(@Param("statusList") List<String> statusList);

	/**
	 * 查询宝付代付处理中和成功的数量
	 * 
	 * @param applyId
	 * @param statusList
	 * @return
	 */
	Long countBaofooPayUnsolvedAndSuccess(@Param("applyId") String applyId, @Param("statusList") List<String> statusList);

	/**
	 * 查询通联代付处理中和成功的数量
	 *
	 * @param applyId
	 * @param statusList
	 * @return
	 */
	Long countTonglianPayUnsolvedAndSuccess(@Param("applyId") String applyId, @Param("statusList") List<String> statusList);

    
    public List<Map<String, Object>> getBFPayCount(@Param(value = "op") KDPayCountOP op);

	public PayLog findWithdrawAmount(String userId);

    
    /**
     * 查询债权转让列表
     * @param page
     * @param op
     * @return
     */
    public List<WithdrawDetailListVO> equitableAssignmentList(
			@Param(value = "op") WithdrawDetailListOP op);


	/**
	 * 统计汉金所当日已放款金额
	 * @return
	 */
	BigDecimal sumHanjsCurrPayedAmt();

}