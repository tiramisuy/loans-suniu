/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.List;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.option.FlowListOP;
import com.rongdu.loans.loan.vo.RepayLogListVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.op.AuthPayOP;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;

/**
 * 充值/还款-业务逻辑接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-11
 */
public interface RepayLogService {

	Page<RepayLogListVO> getRepayLogList(FlowListOP op);

	List<RepayLogListVO> getRepayLogListForExport(FlowListOP op);

	// /**
	// * 保存还款记录
	// * @param rePayOP
	// * @return
	// */
	// ConfirmPayResultVO saveRepayLog(RePayOP rePayOP);
	//
	// /**
	// * 根据还款明细id查询还款计划详情
	// * @param repayPlanItemId
	// * @return
	// */
	// public StatementVO getStatementByItemId(String repayPlanItemId);

	/**
	 * 查询充值/还款信息
	 * 
	 * @param id
	 * @return
	 */
	public RepayLogVO get(String id);

	/**
	 * 查询支付处理中的订单
	 * 
	 * @param repayPlanItemId
	 * @return
	 */
	public Long countPayingByRepayPlanItemId(String repayPlanItemId);
	
	/**
	 * 查询支付处理中的订单
	 * 
	 * @param idNo
	 * @return
	 */
	public Long countPayingByIdNo(String idNo);

	/**
	 * 查询支付处理中的订单
	 * 
	 * @param applyId
	 * @return
	 */
	public Long countPayingByApplyId(String applyId);

	/**
	 * 查询所有未完结的订单id
	 * 
	 * @return
	 */
	public List<RepayLogVO> findUnsolvedOrders();

	/**
	 * 根据第三方支付公司的订单号，查询充值/还款订单信息
	 * 
	 * @param id
	 * @return
	 */
	public RepayLogVO findByChlOrderNo(String orderNo);

	/**
	 * 保存充值/还款信息
	 * 
	 * @param vo
	 * @return
	 */
	public int save(RepayLogVO vo);

	/**
	 * 更新充值/还款信息
	 * 
	 * @param vo
	 * @return
	 */
	public int update(RepayLogVO vo);

	/**
	 * 收到支付公司的通知后，更新充值/还款信息
	 * 
	 * @param vo
	 * @return
	 */
	public int updateRepayResult(RepayLogVO vo);

	/**
	 * 代付成功后，更新原支付订单的“payStatus”状态
	 * 
	 * @param origOrderNo
	 * @param payStatus
	 */
	public int updatePayStatus(String origOrderNo, String payStatus);
	
	
	/**
	 * code y0706
	* @Title: findByRepayPlanItemId  
	* @Description: 根据还款计划明细ID查询最近日志记录 
	* @param @param repayPlanItemId
	* @return RepayLogVO    返回类型  
	 */
	RepayLogVO findByRepayPlanItemId(String repayPlanItemId);

	RepayLogVO saveRepayLog(ConfirmAuthPayVO result, AuthPayOP op);
}