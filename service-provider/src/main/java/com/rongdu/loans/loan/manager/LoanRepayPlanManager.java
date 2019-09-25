package com.rongdu.loans.loan.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.XianJinCardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.loan.dao.LoanRepayPlanDAO;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.vo.LoanRepayDetailVO;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.loan.vo.RepayTotalListVO;
import com.rongdu.loans.loan.vo.StatementVO;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("loanRepayPlanManager")
public class LoanRepayPlanManager extends BaseService {

	@Autowired
	private LoanRepayPlanDAO loanRepayPlanDAO;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;

	/**
	 * 根据申请编号获取还款计划信息
	 *
	 * @param applyId
	 * @return
	 */
	public LoanRepayPlan getByApplyId(String applyId) {
		return loanRepayPlanDAO.getByApplyId(applyId);
	}

	public List<LoanRepayPlan> getByApplyIdList(List<String> applyIdList) {
		return loanRepayPlanDAO.getByApplyIdList(applyIdList);
	}

	public List<LoanRepayPlan> findAllByUserId(String userId) {
		return loanRepayPlanDAO.findAllByUserId(userId);
	}

	/**
	 * 根据申请编号获取还款计划明细
	 *
	 * @param applyId
	 * @return
	 */
	public LoanRepayDetailVO getLoanRepayDetail(String applyId, Integer status) {
		return loanRepayPlanDAO.getLoanRepayDetail(applyId, status);
	}

	/**
	 * 根据明细id获取当前期账单
	 *
	 * @param repayPlanItemId
	 * @return
	 */
	public StatementVO getStatementByItemId(String repayPlanItemId) {
		return loanRepayPlanDAO.getStatementByItemId(repayPlanItemId);
	}

	/**
	 * 更新还款结果
	 *
	 * @param loanRepayPlan
	 * @return
	 */
	public int updatePayResult(LoanRepayPlan loanRepayPlan) {
		return loanRepayPlanDAO.updatePayResult(loanRepayPlan);
	}

	/**
	 * 根据合同生成还款计划
	 *
	 * @param contract
	 * @return
	 */
	public LoanRepayPlan insert(LoanApply loanApply, Contract contract,int withdrawalSource) {
		LoanRepayPlan loanRepayPlan = BeanMapper.map(contract, LoanRepayPlan.class);
		loanRepayPlan.preInsert();
		loanRepayPlan.setContNo(contract.getId());
		loanRepayPlan.setServFee(RepayPlanHelper.getTotalServFee(loanApply));
		loanRepayPlan.setOverdueFee(contract.getOverdueFee());
		loanRepayPlan.setPayedTerm(0);
		loanRepayPlan.setUnpayTerm(contract.getTotalTerm());
		loanRepayPlan.setPayedPrincipal(BigDecimal.ZERO);
		loanRepayPlan.setUnpayPrincipal(contract.getPrincipal());
		loanRepayPlan.setPayedInterest(BigDecimal.ZERO);
		loanRepayPlan.setUnpayInterest(contract.getInterest());
		loanRepayPlan.setNextRepayDate(RepayPlanHelper.getRepayDateStr(loanApply, contract, 1));
		loanRepayPlan.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
		loanRepayPlan.setCurrentTerm(1);
		loanRepayPlan.setWithdrawalSource(withdrawalSource);
		loanRepayPlanDAO.insert(loanRepayPlan);
		return loanRepayPlan;
	}

	/**
	 * 汇总还款计划，每次更新还款明细后调用该方法
	 *
	 * @param applyId
	 * @return
	 */
	public int summary(String applyId) throws Exception {
		/** 获取原始数据 */
		LoanRepayPlan source = loanRepayPlanDAO.getByApplyId(applyId);
		if (source == null) {
			logger.warn("调用汇总还款计划接口，数据不存在，applyId = {}", applyId);
			return 0;
		}
		/** 获取所有明细 */
		List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(applyId);
		/** 组装目标数据 */
		LoanRepayPlan destination = RepayPlanHelper.summaryLoanRepayPlan(source, itemList);

		return loanRepayPlanDAO.update(destination);
	}

	public List<RepayTotalListVO> repayTotalList(Page page, RepayDetailListOP op) {
		return loanRepayPlanDAO.repayTotalList(page, op);
	}

	public int update(LoanRepayPlan entity) {
        XianJinCardUtils.setRepayPlanFeedbackToRedis(entity.getApplyId());
		//XianJinCardUtils.rongPayFeedback(entity.getApplyId());
		return loanRepayPlanDAO.update(entity);
	}
	
	/**
	 * 用于修改还款日功能的相关时间的
	 * @param entity
	 * @return
	 */
	public int updateRepayTime(LoanRepayPlan entity){
		return loanRepayPlanDAO.updateRepayTime(entity);
	}
	
	public int delByApplyId(String applyId){
		return loanRepayPlanDAO.delByApplyId(applyId);
	}
}
