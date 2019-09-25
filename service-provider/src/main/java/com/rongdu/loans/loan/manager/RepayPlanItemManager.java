/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.zxing.common.detector.MathUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.loan.vo.TodayRepayListCalloutVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.loan.dao.RepayPlanItemDAO;
import com.rongdu.loans.loan.dto.OverdueItemCalcDTO;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.entity.RepayPlanItemDetail;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.RepayWarnListOP;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.loan.vo.RepayWarnListVO;
import com.rongdu.loans.loan.vo.TodayRepayListVO;

/**
 * 还款计划明细-实体管理接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-08
 */
@Service("repayPlanItemManager")
public class RepayPlanItemManager extends BaseManager<RepayPlanItemDAO, RepayPlanItem, String> {

	public List<RepayDetailListVO> repayDetailList(Page page, RepayDetailListOP op) {
		return dao.repayDetailList(page, op);
	}

	public List<RepayDetailListVO> repayDetailListExecludeDelay(Page page, RepayDetailListOP op) {
		return dao.repayDetailListExecludeDelay(page, op);
	}

	public List<RepayWarnListVO> repayWarnList(Page page, RepayWarnListOP op) {
		return dao.repayWarnList(page, op);
	}

	/**
	 * 更新还款结果
	 * 
	 * @param entity
	 * @return
	 */
	public int updatePayResult(RepayPlanItem entity) {
		return dao.updatePayResult(entity);
	}

	public int updateUnPayResult(RepayPlanItem entity) {
		return dao.updateUnPayResult(entity);
	}

	/**
	 * 还款计划预览
	 */
	public List<RepayPlanItem> generateRepayPlan(LoanApply loanApply, Contract contract, LoanRepayPlan loanRepayPlan){
		List<RepayPlanItem> list = new ArrayList<RepayPlanItem>();
		for (int i = 1; i <= loanRepayPlan.getTotalTerm(); i++) {
			RepayPlanItem repayPlanItem = BeanMapper.map(loanRepayPlan, RepayPlanItem.class);
			repayPlanItem.preInsert();
			String id = i < 10 ? loanRepayPlan.getApplyId() + "0" + i : loanRepayPlan.getApplyId() + i;
			repayPlanItem.setId(id);
			repayPlanItem.setApplyId(loanRepayPlan.getApplyId());
			repayPlanItem.setContNo(loanRepayPlan.getContNo());
			repayPlanItem.setUserId(loanRepayPlan.getUserId());
			repayPlanItem.setUserName(loanRepayPlan.getUserName());
			repayPlanItem.setStartDate(RepayPlanHelper.getStartDate(loanApply, contract, i));
			repayPlanItem.setRepayDate(RepayPlanHelper.getRepayDate(loanApply, contract, i));
			repayPlanItem.setTotalTerm(loanRepayPlan.getTotalTerm());
			repayPlanItem.setThisTerm(i);
			repayPlanItem.setPrincipal(RepayPlanHelper.getTermPrincipal(loanApply, loanRepayPlan, i));
			repayPlanItem.setUnpayPrincipal(repayPlanItem.getPrincipal());
			repayPlanItem.setInterest(RepayPlanHelper.getTermInterest(loanApply, loanRepayPlan, i));
			repayPlanItem.setUnpayInterest(repayPlanItem.getInterest());
			repayPlanItem.setServFee(RepayPlanHelper.getTermServFee(loanApply));
			repayPlanItem.setPrepayFee(BigDecimal.ZERO);
			repayPlanItem.setPenalty(BigDecimal.ZERO);
			repayPlanItem.setOverdueFee(BigDecimal.ZERO);
			repayPlanItem.setDeduction(BigDecimal.ZERO);
			repayPlanItem.setTotalAmount(
					repayPlanItem.getPrincipal().add(repayPlanItem.getInterest()).add(repayPlanItem.getServFee()));
			repayPlanItem.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
			repayPlanItem.setDel(0);
			list.add(repayPlanItem);
		}
		return list;
	}

	/**
	 * 生产还款计划明细
	 * 
	 * @param loanApply
	 * @param loanRepayPlan
	 * @return
	 */
	public List<RepayPlanItem> insertBatch(LoanApply loanApply, Contract contract, LoanRepayPlan loanRepayPlan) {
		List<RepayPlanItem> list = generateRepayPlan(loanApply, contract, loanRepayPlan);
		for (RepayPlanItem item : list) {
			dao.insert(item);
		}
		return list;
	}

	/**
	 * 查询所有逾期的还款计划
	 * 
	 * @return
	 */
	public List<OverdueItemCalcDTO> findOverdueItem() {
		return dao.findOverdueItem();
	}

	/**
	 * 查询所有逾期的还款计划 For TFL
	 * 
	 * @return
	 */
	public List<OverdueItemCalcDTO> findOverdueItemForTFL() {
		return dao.findOverdueItemForTFL();
	}

	public List<RepayPlanItem> getByApplyId(String applyId) {
		return dao.getByApplyId(applyId);
	}

	/**
	 * code y0601
	 * 
	 * @Title: getByApplyIdForApp
	 * @Description: 根据订单ID获取还款计划明细（APP上展示不包括已延期的还款记录）
	 * @param @param
	 *            applyId
	 * @param @return
	 *            参数
	 * @return List<RepayPlanItem> 返回类型
	 */
	public List<RepayPlanItem> getByApplyIdForApp(String applyId) {
		return dao.getByApplyIdForApp(applyId);
	}

	/**
	 * 查询当日到期的还款计划明细 termType 1=单期，2=多期
	 * 
	 * @return
	 */
	public List<RepayPlanItem> curdateRepayList(Integer termType) {
		return dao.curdateRepayList(termType);
	}

	// ytodo 0317
	public List<RepayPlanItem> afterCurdateRepayList(Integer termType, String withholdDate, Integer limit) {
		return dao.afterCurdateRepayList(termType, withholdDate, limit);
	}

	/**
	 * 查询明日到期的还款计划明细
	 * 
	 * @return
	 */
	public List<RepayPlanItem> tomorrowRepayList() {
		return dao.tomorrowRepayList();
	}

	public List<RepayPlanItemDetail> noticeRepayList() {
		return dao.noticeRepayList();
	}

	/**
	 * 查询某日的未还款记录
	 * 
	 * @param date
	 * @return
	 */
	public List<RepayPlanItem> somedayUnrepayList(Date date) {
		return dao.somedayUnrepayList(date);
	}

	public List<RepayPlanItem> getByUserId(String userId) {
		return dao.getByUserId(userId);
	}

	/**
	 * 更新为处理中状态
	 * 
	 * @param id
	 * @return
	 */
	public int processing(String id) {
		return dao.processing(id);
	}

	public int unfinish(String id) {
		return dao.unfinish(id);
	}

	/**
	 * 根据期数查询未完结的还款计划明细
	 * 
	 * @param applyId
	 * @param term
	 * @return
	 */
	public RepayPlanItem getUnoverItemByTerm(String applyId, int term) {
		return dao.getUnoverItemByTerm(applyId, term);
	}

	public List<Map<String, Object>> getRepayDetailByContNo(String contNo) {
		return dao.getRepayDetailByContNo(contNo);
	}

	public List<TodayRepayListVO> todayRepayList(Page page, RepayDetailListOP op) {
		return dao.todayRepayList(page, op);
	}

	public List<TodayRepayListCalloutVO> todayRepayListCallout(Page page, RepayDetailListOP op) {
		return dao.todayRepayListCallout(page, op);
	}

	public int updateRepayTime(RepayPlanItem entity) {
		return dao.updateRepayTime(entity);
	}

	/**
	 * 统计用户二三期逾期的订单数
	 * 
	 * @param userId
	 * @param applyId
	 * @return
	 */
	public int countOverdueInTwoOrThreeTerm(String userId, String applyId) {
		return dao.countOverdueInTwoOrThreeTerm(userId, applyId);
	}

	public int delByApplyId(String applyId) {
		return dao.delByApplyId(applyId);
	}

	/**
	 * 借点钱客户还款计划
	 *
	 * @param loanApply
	 * @param loanRepayPlan
	 * @return
	 */
	public List<RepayPlanItem> insertTLRepayList(LoanApply loanApply, Contract contract, LoanRepayPlan loanRepayPlan) {
		List<RepayPlanItem> list = TLRepayList(loanApply,contract,loanRepayPlan);
		for (RepayPlanItem item : list) {
			dao.insert(item);
		}
		return list;
	}

	public List<RepayPlanItem> insertTLRepayList2(LoanApply loanApply, Contract contract, LoanRepayPlan loanRepayPlan) {
		List<RepayPlanItem> list = TLRepayList2(loanApply,contract,loanRepayPlan);
		for (RepayPlanItem item : list) {
			dao.insert(item);
		}
		return list;
	}




	/**
	 * 借点钱客户还款计划预览
	 *
	 * @param loanApply
	 * @param loanRepayPlan
	 * @return
	 */
	public List<RepayPlanItem> TLRepayList2(LoanApply loanApply, Contract contract, LoanRepayPlan loanRepayPlan) {
		List<RepayPlanItem> list = new ArrayList<RepayPlanItem>();
		for (int i = 1; i <= loanRepayPlan.getTotalTerm(); i++) {
			RepayPlanItem repayPlanItem = BeanMapper.map(loanRepayPlan, RepayPlanItem.class);
			repayPlanItem.preInsert();
			String id = i < 10 ? loanRepayPlan.getApplyId() + "0" + i : loanRepayPlan.getApplyId() + i;
			repayPlanItem.setId(id);
			repayPlanItem.setApplyId(loanRepayPlan.getApplyId());
			repayPlanItem.setContNo(loanRepayPlan.getContNo());
			repayPlanItem.setUserId(loanRepayPlan.getUserId());
			repayPlanItem.setUserName(loanRepayPlan.getUserName());
			repayPlanItem.setStartDate(RepayPlanHelper.getStartDate(loanApply, contract, i));
			repayPlanItem.setRepayDate(RepayPlanHelper.getRepayDate(loanApply, contract, i));
			repayPlanItem.setTotalTerm(loanRepayPlan.getTotalTerm());
			repayPlanItem.setThisTerm(i);
			switch (i){
				case 1 :
					repayPlanItem.setPrincipal(loanApply.getApproveAmt().multiply(new BigDecimal("0.3")));
					repayPlanItem.setUnpayPrincipal(repayPlanItem.getPrincipal());
					repayPlanItem.setInterest(CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
							loanApply.getApproveTerm()).multiply(new BigDecimal("0.3")).setScale(0, BigDecimal.ROUND_HALF_UP));
					repayPlanItem.setUnpayInterest(repayPlanItem.getInterest());
					break;
				case 2 :
					repayPlanItem.setPrincipal(loanApply.getApproveAmt().multiply(new BigDecimal("0.3")));
					repayPlanItem.setUnpayPrincipal(repayPlanItem.getPrincipal());
					repayPlanItem.setInterest(CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
							loanApply.getApproveTerm()).multiply(new BigDecimal("0.3")).setScale(0, BigDecimal.ROUND_HALF_UP));
					repayPlanItem.setUnpayInterest(repayPlanItem.getInterest());
					break;
				case 3 :
					repayPlanItem.setPrincipal(loanApply.getApproveAmt().multiply(new BigDecimal("0.2")));
					repayPlanItem.setUnpayPrincipal(repayPlanItem.getPrincipal());
					repayPlanItem.setInterest(CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
							loanApply.getApproveTerm()).multiply(new BigDecimal("0.2")).setScale(0,BigDecimal.ROUND_HALF_UP));
					repayPlanItem.setUnpayInterest(repayPlanItem.getInterest());
					break;
				case 4 :
					repayPlanItem.setPrincipal(loanApply.getApproveAmt().multiply(new BigDecimal("0.2")));
					repayPlanItem.setUnpayPrincipal(repayPlanItem.getPrincipal());
					repayPlanItem.setInterest(CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
							loanApply.getApproveTerm()).multiply(new BigDecimal("0.2")).setScale(0,BigDecimal.ROUND_HALF_UP));
					repayPlanItem.setUnpayInterest(repayPlanItem.getInterest());
					break;
			}
			repayPlanItem.setServFee(BigDecimal.ZERO);
			repayPlanItem.setPrepayFee(BigDecimal.ZERO);
			repayPlanItem.setPenalty(BigDecimal.ZERO);
			repayPlanItem.setOverdueFee(BigDecimal.ZERO);
			repayPlanItem.setDeduction(BigDecimal.ZERO);
			repayPlanItem.setTotalAmount(
					repayPlanItem.getPrincipal().add(repayPlanItem.getInterest()).add(repayPlanItem.getServFee()));
			repayPlanItem.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
			repayPlanItem.setDel(0);
			list.add(repayPlanItem);
		}
		return list;
	}
	public List<RepayPlanItem> TLRepayList(LoanApply loanApply, Contract contract, LoanRepayPlan loanRepayPlan) {
		List<RepayPlanItem> list = new ArrayList<RepayPlanItem>();
		for (int i = 1; i <= loanRepayPlan.getTotalTerm(); i++) {
			RepayPlanItem repayPlanItem = BeanMapper.map(loanRepayPlan, RepayPlanItem.class);
			repayPlanItem.preInsert();
			String id = i < 10 ? loanRepayPlan.getApplyId() + "0" + i : loanRepayPlan.getApplyId() + i;
			repayPlanItem.setId(id);
			repayPlanItem.setApplyId(loanRepayPlan.getApplyId());
			repayPlanItem.setContNo(loanRepayPlan.getContNo());
			repayPlanItem.setUserId(loanRepayPlan.getUserId());
			repayPlanItem.setUserName(loanRepayPlan.getUserName());
			repayPlanItem.setStartDate(RepayPlanHelper.getStartDate(loanApply, contract, i));
			repayPlanItem.setRepayDate(RepayPlanHelper.getRepayDate(loanApply, contract, i));
			repayPlanItem.setTotalTerm(loanRepayPlan.getTotalTerm());
			repayPlanItem.setThisTerm(i);
			switch (i){
				case 1 :
					repayPlanItem.setPrincipal(BigDecimal.valueOf(700));
					repayPlanItem.setUnpayPrincipal(BigDecimal.valueOf(700));
					repayPlanItem.setInterest(BigDecimal.valueOf(20));
					repayPlanItem.setUnpayInterest(BigDecimal.valueOf(20));
					break;
				case 2 :
					repayPlanItem.setPrincipal(BigDecimal.valueOf(700));
					repayPlanItem.setUnpayPrincipal(BigDecimal.valueOf(700));
					repayPlanItem.setInterest(BigDecimal.valueOf(20));
					repayPlanItem.setUnpayInterest(BigDecimal.valueOf(20));
					break;
				case 3 :
					repayPlanItem.setPrincipal(BigDecimal.valueOf(400));
					repayPlanItem.setUnpayPrincipal(BigDecimal.valueOf(400));
					repayPlanItem.setInterest(BigDecimal.valueOf(11));
					repayPlanItem.setUnpayInterest(BigDecimal.valueOf(11));
					break;
				case 4 :
					repayPlanItem.setPrincipal(BigDecimal.valueOf(200));
					repayPlanItem.setUnpayPrincipal(BigDecimal.valueOf(200));
					repayPlanItem.setInterest(BigDecimal.valueOf(5));
					repayPlanItem.setUnpayInterest(BigDecimal.valueOf(5));
					break;
			}
			repayPlanItem.setServFee(BigDecimal.ZERO);
			repayPlanItem.setPrepayFee(BigDecimal.ZERO);
			repayPlanItem.setPenalty(BigDecimal.ZERO);
			repayPlanItem.setOverdueFee(BigDecimal.ZERO);
			repayPlanItem.setDeduction(BigDecimal.ZERO);
			repayPlanItem.setTotalAmount(
					repayPlanItem.getPrincipal().add(repayPlanItem.getInterest()).add(repayPlanItem.getServFee()));
			repayPlanItem.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
			repayPlanItem.setDel(0);
			list.add(repayPlanItem);
		}
		return list;
	}
}