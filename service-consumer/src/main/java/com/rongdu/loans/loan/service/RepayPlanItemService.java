/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.RepayWarnListOP;
import com.rongdu.loans.loan.vo.*;

/**
 * 还款计划明细-业务逻辑接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-08
 */
public interface RepayPlanItemService {

	/**
	 * 后台还款明细列表接口
	 * 
	 * @param op
	 * @return
	 */
	Page<RepayDetailListVO> repayDetailList(@NotNull(message = "参数不能为空") RepayDetailListOP op);
	
	
	/**
	 * 后台还款提醒列表
	 * @param op
	 * @return
	 */
	Page<RepayWarnListVO> repayWarnlList(@NotNull(message = "参数不能为空") RepayWarnListOP op);

	/**
	 * 后台还款明细列表导出接口
	 * 
	 * @param op
	 * @return
	 */
	public List<RepayDetailListVO> repayDetailExportList(RepayDetailListOP op);

	List<RepayDetailListVO> getByApplyIdExecludeDelay(String applyId);

	List<RepayPlanDetailVO> getByTerms(String applyId, List<String> termList);

	RepayDetailListVO getByRepayPlanItemId(String repayPlanItemId);

	/**
	 * 后台查询还款计划列表接口
	 * 
	 * @param contNo
	 *            合同编号
	 * @return
	 */
	RepayPlanItemListVO repayPlanItemService(@NotNull(message = "参数不能为空") String contNo);
	
	RepayPlanItemListVO repayDetailListExecludeDelay(@NotNull(message = "参数不能为空") String contNo);

	/**
	 * 逾期数据计算
	 * 
	 * @param type
	 *            分类 1=XJD 2=TFL
	 */
	TaskResult overdueDataCalc(int type);

	Map<String, Object> getRepayDetailByApplyId(String applyId);

	Map<String, Object> getRepayDetailByContNo(String contNo);
	
	String getApplyIdByRepayPlanItemId(String repayPlanItemId);
	
	/**
	 * 减免当期还款计划
	 * @return 
	 */
	int deductionRepayDetail(String repayPlanItemId,String deductionDetailAmt, String deductionReason, String approverId, String approverName);
	
	
	public Page<TodayRepayListVO> todayRepayList(RepayDetailListOP op);
	public Page<TodayRepayListCalloutVO> todayRepayListCallout(RepayDetailListOP op);

	boolean updateForPartWithhold(String repayPlanItemId, String amount, String tradeDate) throws ParseException;
}