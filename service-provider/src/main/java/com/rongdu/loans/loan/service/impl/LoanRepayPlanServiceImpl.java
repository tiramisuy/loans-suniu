package com.rongdu.loans.loan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.manager.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MyBeanUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.compute.PrincipalInterestDayUtils;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.cust.vo.RepayItemDetailVO;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.LoanPurposeEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.loan.dto.OverdueDTO;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.RepayPlanOP;
import com.rongdu.loans.loan.option.xjbk.LoanRepayPlanVO;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.RepayTotalListVO;

/**
 * Created by zhangxiaolong on 2017/7/29.
 */
@Service("loanRepayPlanService")
public class LoanRepayPlanServiceImpl extends BaseService implements LoanRepayPlanService {

	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private PromotionCaseManager promotionCaseManager;
	@Autowired
	private OverdueManager overdueManager;
	@Autowired
	private LoanProductManager loanProductManager;

	@Override
	public List<RepayItemDetailVO> findUserRepayList(String userId) {
		List<RepayItemDetailVO> repayItemDetailVOList = new ArrayList<>();
		List<LoanApply> loanApplyList = loanApplyManager.getLoanApplyByUserId(userId);
		if (CollectionUtils.isNotEmpty(loanApplyList)) {
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanManager.findAllByUserId(userId);
			List<String> applyIdList = new ArrayList<>();
			for (LoanApply apply : loanApplyList) {
				applyIdList.add(apply.getId());
			}
			List<OverdueDTO> overdueDTOList = overdueManager.maxOverdueNumber(applyIdList);
			for (LoanApply apply : loanApplyList) {
				/** 1.封装贷款申请表数据 */
				RepayItemDetailVO repayItemDetailVO = BeanMapper.map(apply, RepayItemDetailVO.class);
				repayItemDetailVO.setPurpose(LoanPurposeEnum.getDesc(apply.getPurpose()));
				repayItemDetailVO.setRepayMethod(RepayMethodEnum.getDesc(apply.getRepayMethod()));
				repayItemDetailVO.setApplyStatus(apply.getApplyStatus());
				/** 2.封装还款计划表数据 */
				for (LoanRepayPlan plan : loanRepayPlanList) {
					if (StringUtils.equals(apply.getId(), plan.getApplyId())) {
						repayItemDetailVO.setStartDate(plan.getLoanStartDate());
						repayItemDetailVO.setEndDate(plan.getLoanEndDate());
						break;
					}
				}
				/** 3.封装还款计划明细表数据 */
				for (OverdueDTO dto : overdueDTOList) {
					if (StringUtils.equals(apply.getId(), dto.getApplyId())) {
						repayItemDetailVO.setNumber(dto.getNumber() > 0 ? dto.getNumber() : 0);
						break;
					}
				}
				/** 4.数据添加到列表中 */
				repayItemDetailVOList.add(repayItemDetailVO);
			}
		}
		return repayItemDetailVOList;
	}

	@Deprecated
	public Map<String, Object> getRepayPlan(RepayPlanOP op) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 申请单
		LoanApply loanApply = new LoanApply();
		if (StringUtils.isNotBlank(op.getApplyId())) {
			loanApply = loanApplyManager.getLoanApplyById(op.getApplyId());
		} else {
			// 获取营销方法
			PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
			promotionCaseOP.setApplyAmt(op.getApplyAmt());
			promotionCaseOP.setApplyTerm(op.getRepayTerm());
			promotionCaseOP.setChannelId(op.getChannelId());
			promotionCaseOP.setProductId(op.getProductId());
			PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
			if (promotionCase == null) {
				return result;
			}
			loanApply.setProductId(op.getProductId());
			loanApply.setApproveAmt(op.getApplyAmt());
			loanApply.setActualRate(promotionCase.getRatePerYear());
			if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
				loanApply.setTerm(op.getRepayTerm());
				loanApply.setRepayMethod(op.getRepayMethod());
			} else if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())
					&& op.getRepayTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90) {
				loanApply.setApproveTerm(op.getRepayTerm());
				loanApply.setTerm(3);
				loanApply.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
				loanApply.setRepayFreq("D");
				loanApply.setRepayUnit(new BigDecimal(10));
			} else if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())
					&& op.getRepayTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28) {
				loanApply.setApproveTerm(op.getRepayTerm());
				loanApply.setTerm(4);
				loanApply.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
				loanApply.setRepayFreq("D");
				loanApply.setRepayUnit(new BigDecimal(7));
			} else {
				loanApply.setApproveTerm(op.getRepayTerm());
				loanApply.setTerm(1);
				loanApply.setRepayMethod(RepayMethodEnum.ONE_TIME.getValue());
			}
		}

		// 合同
		Contract contract = new Contract();
		contract.setLoanStartDate(new Date());
		// 还款计划
		LoanRepayPlan repayPlan = new LoanRepayPlan();
		repayPlan.setPrincipal(loanApply.getApproveAmt());
		repayPlan.setTotalTerm(loanApply.getTerm());

		BigDecimal totalAmt = new BigDecimal(0);
		for (int i = 1; i <= repayPlan.getTotalTerm(); i++) {
			Date repayDate = RepayPlanHelper.getRepayDate(loanApply, contract, i);
			BigDecimal principal = RepayPlanHelper.getTermPrincipal(loanApply, repayPlan, i);
			BigDecimal interest = RepayPlanHelper.getTermInterest(loanApply, repayPlan, i);
			BigDecimal repayAmt = principal.add(interest);

			Map<String, Object> detail = new LinkedHashMap<String, Object>();
			detail.put("repayDate", DateUtils.formatDate(repayDate, "yyyy-MM-dd"));
			detail.put("repayAmt", repayAmt);
			detail.put("preferential", LoanApplySimpleVO.NO);
			list.add(detail);

			totalAmt = totalAmt.add(repayAmt);
			if (i == repayPlan.getTotalTerm()) {
				result.put("loanEndDate", DateUtils.formatDate(repayDate, "yyyy年MM月dd日"));
			}
		}
		// 现金贷小额分期奇葩分期
		if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId()) && loanApply.getTerm().intValue() > 1
				&& loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_90) {
			int month = 1;
			for (int i = loanApply.getTerm() + 1; i <= loanApply.getTerm() + 2; i++) {
				Date repyaDate = DateUtils.addMonth(DateUtils.addDay(contract.getLoanStartDate(), -1), ++month);
				BigDecimal repayAmt = PrincipalInterestDayUtils.getInterestCount(loanApply.getApproveAmt(),
						loanApply.getActualRate(), loanApply.getRepayUnit().intValue(), loanApply.getTerm());

				Map<String, Object> detail = new LinkedHashMap<String, Object>();
				detail.put("repayDate", DateUtils.formatDate(repyaDate, "yyyy-MM-dd"));
				detail.put("repayAmt", repayAmt);
				detail.put("preferential", LoanApplySimpleVO.YES);
				list.add(detail);
			}
		}
		result.put("loanStartDate", DateUtils.formatDate(contract.getLoanStartDate(), "yyyy年MM月dd日"));
		result.put("totalAmt", totalAmt);
		result.put("totalInterest", totalAmt.subtract(loanApply.getApproveAmt()));
		result.put("list", list);
		return result;
	}

	public Map<String, Object> getRepayPlan2(RepayPlanOP op) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 申请单
		LoanApply loanApply = new LoanApply();
		if (StringUtils.isNotBlank(op.getApplyId())) {
			loanApply = loanApplyManager.getLoanApplyById(op.getApplyId());
		} else {
			// 获取营销方法
			PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
			promotionCaseOP.setApplyAmt(op.getApplyAmt());
			promotionCaseOP.setApplyTerm(op.getRepayTerm());
			promotionCaseOP.setChannelId(op.getChannelId());
			promotionCaseOP.setProductId(op.getProductId());
			PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
			if (promotionCase == null) {
				return result;
			}
			loanApply.setProductId(op.getProductId());
			loanApply.setApproveAmt(op.getApplyAmt());
			loanApply.setActualRate(promotionCase.getRatePerYear());
			loanApply.setApproveTerm(op.getRepayTerm());
			loanApply.setTerm(4);
			loanApply.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
			loanApply.setRepayFreq("D");
			loanApply.setRepayUnit(new BigDecimal(7));

		}

		// 合同
		Contract contract = new Contract();
		contract.setLoanStartDate(new Date());
		// 还款计划
		LoanRepayPlan repayPlan = new LoanRepayPlan();
		repayPlan.setPrincipal(loanApply.getApproveAmt());
		repayPlan.setTotalTerm(loanApply.getTerm());

		LoanRepayPlan loanRepayPlan = new LoanRepayPlan();
		loanRepayPlan.setApplyId(loanApply.getId());
		loanRepayPlan.setContNo(loanApply.getContNo());
		loanRepayPlan.setUserId(loanApply.getUserId());
		loanRepayPlan.setUserName(loanApply.getUserName());
		loanRepayPlan.setTotalTerm(loanApply.getTerm());
		//List<RepayPlanItem> items = repayPlanItemManager.generateRepayPlan(loanApply, contract, loanRepayPlan);
		List<RepayPlanItem> items = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);

		BigDecimal totalAmt = new BigDecimal(0);
		for (int i = 1; i <= repayPlan.getTotalTerm(); i++) {
			RepayPlanItem item = items.get(i - 1);
			Date repayDate = item.getRepayDate();
			BigDecimal principal = item.getPrincipal();
			BigDecimal interest = item.getInterest();
			BigDecimal repayAmt = principal.add(interest);

			Map<String, Object> detail = new LinkedHashMap<String, Object>();
			detail.put("repayDate", DateUtils.formatDate(repayDate, "yyyy-MM-dd"));
			detail.put("repayAmt", repayAmt);
			detail.put("preferential", LoanApplySimpleVO.NO);
			list.add(detail);

			totalAmt = totalAmt.add(repayAmt);
			if (i == repayPlan.getTotalTerm()) {
				result.put("loanEndDate", DateUtils.formatDate(repayDate, "yyyy年MM月dd日"));
			}
		}

		result.put("loanStartDate", DateUtils.formatDate(contract.getLoanStartDate(), "yyyy年MM月dd日"));
		result.put("totalAmt", totalAmt);
		result.put("totalInterest", totalAmt.subtract(loanApply.getApproveAmt()));
		result.put("list", list);
		return result;
	}

	@Override
	public Map<String, Object> getJDQRepayPlan(RepayPlanOP op) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 申请单
		LoanApply loanApply = new LoanApply();
		if (StringUtils.isNotBlank(op.getApplyId())) {
			loanApply = loanApplyManager.getLoanApplyById(op.getApplyId());
		} else {
			// 获取营销方法
			PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
			promotionCaseOP.setApplyAmt(op.getApplyAmt());
			promotionCaseOP.setApplyTerm(op.getRepayTerm());
			promotionCaseOP.setChannelId(op.getChannelId());
			promotionCaseOP.setProductId(op.getProductId());
			PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
			if (promotionCase == null) {
				return result;
			}
			LoanProduct loanProduct = loanProductManager.getById(op.getProductId());
			loanApply.setProductId(op.getProductId());
			loanApply.setApproveAmt(op.getApplyAmt());
			loanApply.setActualRate(promotionCase.getRatePerYear());
			loanApply.setApproveTerm(op.getRepayTerm());
			loanApply.setTerm(op.getRepayTerm()/loanProduct.getRepayUnit().intValue());
			loanApply.setRepayMethod(loanProduct.getRepayMethod());
			loanApply.setRepayFreq(loanProduct.getRepayFreq());
			loanApply.setRepayUnit(loanProduct.getRepayUnit());
		}

		// 合同
		Contract contract = new Contract();
		contract.setLoanStartDate(new Date());
		// 还款计划
		LoanRepayPlan repayPlan = new LoanRepayPlan();
		repayPlan.setPrincipal(loanApply.getApproveAmt());
		repayPlan.setTotalTerm(loanApply.getTerm());

		LoanRepayPlan loanRepayPlan = new LoanRepayPlan();
		loanRepayPlan.setApplyId(loanApply.getId());
		loanRepayPlan.setContNo(loanApply.getContNo());
		loanRepayPlan.setUserId(loanApply.getUserId());
		loanRepayPlan.setUserName(loanApply.getUserName());
		loanRepayPlan.setTotalTerm(loanApply.getTerm());

		BigDecimal totalAmt = new BigDecimal(0);
		List<RepayPlanItem> items = null;
		if (LoanProductEnum.JDQ.getId().equals(op.getProductId())) {
			items = repayPlanItemManager.TLRepayList(loanApply, contract, loanRepayPlan);
		} else if (LoanProductEnum.JNFQ.getId().equals(op.getProductId())) {
			items = repayPlanItemManager.TLRepayList2(loanApply, contract, loanRepayPlan);
		} else {
			items = repayPlanItemManager.generateRepayPlan(loanApply, contract, loanRepayPlan);
		}
		for (int i = 1; i <= repayPlan.getTotalTerm(); i++) {
			RepayPlanItem item = items.get(i - 1);
			Date repayDate = item.getRepayDate();
			BigDecimal principal = item.getPrincipal();
			BigDecimal interest = item.getInterest();
			BigDecimal repayAmt = principal.add(interest);

			Map<String, Object> detail = new LinkedHashMap<String, Object>();
			detail.put("repayDate", DateUtils.formatDate(repayDate, "yyyy-MM-dd"));
			detail.put("repayAmt", repayAmt);
			detail.put("preferential", LoanApplySimpleVO.NO);
			list.add(detail);

			totalAmt = totalAmt.add(repayAmt);
			if (i == repayPlan.getTotalTerm()) {
				result.put("loanEndDate", DateUtils.formatDate(repayDate, "yyyy年MM月dd日"));
			}
		}


		result.put("loanStartDate", DateUtils.formatDate(contract.getLoanStartDate(), "yyyy年MM月dd日"));
		result.put("totalAmt", totalAmt);
		result.put("totalInterest", totalAmt.subtract(loanApply.getApproveAmt()));
		result.put("list", list);
		return result;
	}


	@Override
	public Page<RepayTotalListVO> repayTotalList(RepayDetailListOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<RepayTotalListVO> voList = loanRepayPlanManager.repayTotalList(voPage, op);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public List<RepayTotalListVO> repayTotalExportList(RepayDetailListOP op) {
		List<RepayTotalListVO> voList = loanRepayPlanManager.repayTotalList(null, op);
		return voList;
	}

	@Override
	public LoanRepayPlanVO getByApplyId(String applyId) {
		LoanRepayPlan loanRepayPlan = loanRepayPlanManager.getByApplyId(applyId);
		if (loanRepayPlan == null)
			return null;
		LoanRepayPlanVO loanRepayPlanVO = new LoanRepayPlanVO();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(loanRepayPlan, loanRepayPlanVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanRepayPlanVO;
	}

	@Override
	public int delPlanAndItemByApplyId(String applyId) {
		int ret = loanRepayPlanManager.delByApplyId(applyId);
		repayPlanItemManager.delByApplyId(applyId);
		return ret;
	}
}
