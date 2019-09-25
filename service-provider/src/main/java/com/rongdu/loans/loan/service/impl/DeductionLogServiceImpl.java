/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.DeductionLog;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.Overdue;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.ContractManager;
import com.rongdu.loans.loan.manager.DeductionLogManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanRepayPlanManager;
import com.rongdu.loans.loan.manager.OverdueManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.DeductionApplyOP;
import com.rongdu.loans.loan.option.DeductionApproveOP;
import com.rongdu.loans.loan.service.DeductionLogService;
import com.rongdu.loans.loan.vo.DeductionLogVO;

/**
 * 减免操作日志-业务逻辑实现类
 * 
 * @author zhangxiaolong
 * @version 2017-07-27
 */
@Service("deductionLogService")
public class DeductionLogServiceImpl extends BaseService implements DeductionLogService {

	/**
	 * 减免操作日志-实体管理接口
	 */
	@Autowired
	private DeductionLogManager deductionLogManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private OverdueManager overdueManager;
	@Autowired
	private ContractManager contractManager;

	@Override
	@Transactional
	public int apply(DeductionApplyOP deductionApplyOP) throws Exception {
		Date now = new Date();
		// 校验还款计划状态
		RepayPlanItem repayPlanItem = repayPlanItemStatusCheck(deductionApplyOP.getRepayPlanItemId());
		// 不能重复提交减免申请
		Integer status = deductionLogManager.applyCheck(deductionApplyOP.getRepayPlanItemId());
		if (status == Global.DEDUCTION_INIT) {
			logger.error("申请减免失败，该笔还款计划已经提交减免申请。repayPlanItemId = " + deductionApplyOP.getRepayPlanItemId());
			throw new RuntimeException("该笔还款计划已经提交减免申请，请勿重复提交。");
		}
		// 逾期管理费
		BigDecimal overdueFee = repayPlanItem.getOverdueFee();
		BigDecimal max = repayPlanItem.getPenalty().add(overdueFee);
		if (deductionApplyOP.getDeduction().subtract(max).compareTo(BigDecimal.ZERO) > 0) {
			throw new RuntimeException("减免金额不得大于" + max);
		}
		/** 新增减免记录 */
		DeductionLog deductionLog = BeanMapper.map(deductionApplyOP, DeductionLog.class);
		deductionLog.setApplyId(repayPlanItem.getApplyId());
		deductionLog.setUserId(repayPlanItem.getUserId());
		deductionLog.setStatus(Global.DEDUCTION_INIT);
		deductionLog.setCreateTime(now);
		deductionLog.setUpdateBy(deductionApplyOP.getCreateBy());

		return deductionLogManager.insert(deductionLog);
	}

	@Override
	@Transactional
	public int approve(DeductionApproveOP deductionApproveOP) throws Exception {
		Date now = new Date();
		RepayPlanItem repayPlanItem = repayPlanItemStatusCheck(deductionApproveOP.getRepayPlanItemId());

		/** 修改审核状态 */
		DeductionLog deductionLog = deductionLogManager.getById(deductionApproveOP.getId());
		deductionLog.setStatus(deductionApproveOP.getStatus());
		deductionLog.setApproverId(deductionApproveOP.getApproverId());
		deductionLog.setApproverName(deductionApproveOP.getApproverName());
		deductionLog.setApproveTime(now);
		deductionLog.setUpdateBy(deductionLog.getApproverName());
		deductionLog.setUpdateTime(now);
		deductionLogManager.update(deductionLog);
		// 不通过，返回；通过，修改还款计划
		if (deductionApproveOP.getStatus() == Global.DEDUCTION_NO_PASS) {
			return 1;
		}
		// 计算新的减免金额与原减免金额的差值
		BigDecimal difference = deductionLog.getDeduction().subtract(repayPlanItem.getDeduction());

		/** 修改还款计划明细 */
		repayPlanItem.setTotalAmount(repayPlanItem.getTotalAmount().subtract(difference));
		repayPlanItem.setDeduction(deductionLog.getDeduction());
		repayPlanItem.setUpdateBy(deductionLog.getUpdateBy());
		repayPlanItem.setUpdateTime(now);
		repayPlanItemManager.updatePayResult(repayPlanItem);

		/** 修改还款计划总表 */
		LoanRepayPlan loanRepayPlan = loanRepayPlanManager.getByApplyId(repayPlanItem.getApplyId());
		loanRepayPlan.setTotalAmount(loanRepayPlan.getTotalAmount().subtract(difference));
		loanRepayPlan.setDeduction(deductionLog.getDeduction());
		loanRepayPlan.setUpdateTime(now);
		loanRepayPlan.setUpdateBy(deductionLog.getCreateBy());
		loanRepayPlanManager.updatePayResult(loanRepayPlan);

		/** 减免结清 **/
		// processDeductionSettlement(repayPlanItem, loanRepayPlan);
		return 1;
	}

	@Override
	public List<DeductionLogVO> deductionFrom(String repayPlanItemId) {
		List<DeductionLog> list = deductionLogManager.findByItemId(repayPlanItemId);
		if (CollectionUtils.isEmpty(list)) {
			return Collections.EMPTY_LIST;
		}
		return BeanMapper.mapList(list, DeductionLogVO.class);
	}

	/**
	 * 已结清的还款计划不允许减免
	 * 
	 * @param repayPlanItemId
	 * @return
	 * @throws Exception
	 */
	private RepayPlanItem repayPlanItemStatusCheck(String repayPlanItemId) throws Exception {
		RepayPlanItem repayPlanItem = repayPlanItemManager.getById(repayPlanItemId);
		// 参数校验，必须是对未结清逾期的还款计划申请减免
		if (repayPlanItem == null || Global.REPAY_PLAN_STATUS_OVER == repayPlanItem.getStatus()
				|| DateUtils.daysBetween(repayPlanItem.getRepayDate(), new Date()) <= 0) {
			logger.error("减免失败，还款计划不存在或状态不正确。repayPlanItemId = " + repayPlanItemId);
			throw new RuntimeException("还款计划不存在或状态不正确。");
		}
		return repayPlanItem;
	}

	/**
	 * 减免结清
	 * 
	 * @param repayPlanItemId
	 * @return
	 */
	public boolean processDeductionSettlement(RepayPlanItem item, LoanRepayPlan plan) {
		LoanApply apply = loanApplyManager.getLoanApplyById(item.getApplyId());
		if (apply == null || !LoanProductEnum.CCD.getId().equals(apply.getProductId())) {
			logger.warn("减免结清失败，产品Id错误，applyId:{}", item.getApplyId());
			return false;
		}
		int currTerm = (plan.getTotalTerm() > 1) ? plan.getCurrentTerm() : 1;
		if (currTerm != item.getThisTerm().intValue()) {
			logger.warn("减免结清失败，还款期数错误，itemId:{}", item.getId());
			return false;
		}
		BigDecimal actualRepayAmt = item.getActualRepayAmt();
		actualRepayAmt = actualRepayAmt == null ? BigDecimal.ZERO : actualRepayAmt;
		if (actualRepayAmt.compareTo(item.getTotalAmount()) < 0) {
			logger.warn("减免结清失败，实还金额必须>=应还金额，itemId:{}", item.getId());
			return false;
		}
		String actualRepayTime = DateUtils.formatDateTime(item.getActualRepayTime());
		/** 修改还款明细 **/
		List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(item.getApplyId());
		for (RepayPlanItem d : itemList) {
			if (d.getThisTerm() == item.getThisTerm()) {
				RepayPlanHelper.setOverRepayPlanItem(d, item.getTotalAmount(), item.getPrincipal(), item.getInterest(),
						item.getOverdueFee(), item.getPenalty(), item.getPrepayFee(), item.getDeduction(),
						actualRepayAmt, actualRepayTime, Global.REPAY_TYPE_MANPAY);
				repayPlanItemManager.update(d);
			}
		}
		/** 汇总还款计划明细，更新还款计划 */
		plan = RepayPlanHelper.summaryLoanRepayPlan(plan, itemList);
		loanRepayPlanManager.update(plan);

		ApplyStatusLifeCycleEnum statusEnum = ApplyStatusLifeCycleEnum.REPAY;
		// 修改逾期表
		int rz = updateOverdue(item.getId(), actualRepayAmt, actualRepayTime);
		if (rz > 0) {
			statusEnum = ApplyStatusLifeCycleEnum.OVERDUE_REPAY;
		}
		// 结清后修改合同和订单
		if (plan.getStatus().equals(Global.REPAY_PLAN_STATUS_OVER)) {
			Contract contract = contractManager.getByApplyId(item.getApplyId());
			/** 修改合同状态 */
			updateContract(contract);
			/** 修改贷款申请单状态 */
			updateApply(apply, statusEnum);
		}
		return true;
	}

	/**
	 * 更新逾期
	 * 
	 * @param itemId
	 * @param actualRepayAmt
	 * @param actualRepayDate
	 * @param actualRepayTime
	 * @return
	 */
	private int updateOverdue(String itemId, BigDecimal actualRepayAmt, String actualRepayTime) {
		Overdue overdue = overdueManager.get(itemId);
		if (overdue != null) {
			Date repayTime = DateUtils.parse(actualRepayTime, "yyyy-MM-dd HH:mm:ss");
			String repayDate = DateUtils.formatDate(repayTime, "yyyy-MM-dd");
			overdue.setActualRepayAmt(actualRepayAmt);
			overdue.setActualRepayDate(repayDate);
			overdue.setActualRepayTime(repayTime);
			overdue.setOverdueEndDate(new Date());
			overdue.setStatus(ApplyStatusEnum.FINISHED.getValue());
			return overdueManager.update(overdue);
		}
		return 0;
	}

	/**
	 * 更新合同
	 * 
	 * @param source
	 * @return
	 */
	private int updateContract(Contract source) {
		Contract contract = new Contract();
		contract.setApplyId(source.getApplyId());
		return contractManager.OverContract(contract);
	}

	/**
	 * 更新贷款申请单
	 * 
	 * @param source
	 * @return
	 */
	private int updateApply(LoanApply source, ApplyStatusLifeCycleEnum value) {
		LoanApply loanApply = new LoanApply();
		loanApply.setId(source.getId());
		loanApply.setStage(value.getStage());
		loanApply.setStatus(value.getValue());
		loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
		loanApply.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
		loanApply.setUpdateTime(new Date());
		return loanApplyManager.updateLoanApplyInfo(loanApply);
	}

	@Override
	public Integer applyCheck(String repayPlanItemId) {
		return deductionLogManager.applyCheck(repayPlanItemId);
	}
}