/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.XianJinCardUtils;
import com.rongdu.loans.basic.manager.AreaManager;
import com.rongdu.loans.compute.AverageCapitalPlusInterestUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.compute.PrincipalInterestDayUtils;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.dto.OverdueDTO;
import com.rongdu.loans.loan.dto.OverdueItemCalcDTO;
import com.rongdu.loans.loan.entity.DeductionLog;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.RepayWarnListOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 还款计划明细-业务逻辑实现类
 * 
 * @author zhangxiaolong
 * @version 2017-07-08
 */
@Service("repayPlanItemService")
public class RepayPlanItemServiceImpl extends BaseService implements RepayPlanItemService {

	/**
	 * 还款计划明细-实体管理接口
	 */
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private DeductionLogManager deductionLogManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private OverdueManager overdueManager;
	@Autowired
	private AreaManager areaManager;
	@Autowired
	private ApplyTripartiteService applyTripartiteService;
	@Autowired
	private ApplyTripartiteRong360Service applyTripartiteRong360Service;
	@Autowired
	private RongPointCutService rongPointCutService;
	@Autowired
	private JDQService jdqService;

	@Override
	public Page<RepayDetailListVO> repayDetailList(RepayDetailListOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(voPage, op);
		setData(op, voList);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public Page<RepayWarnListVO> repayWarnlList(RepayWarnListOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<RepayWarnListVO> voList = repayPlanItemManager.repayWarnList(voPage, op);
		// setWarnData(op, voList);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public List<RepayDetailListVO> repayDetailExportList(RepayDetailListOP op) {
		List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(null, op);
		// setData(op, voList);
		return voList;
	}

	@Override
	public List<RepayDetailListVO> getByApplyIdExecludeDelay(String applyId) {
		List<RepayPlanItem> repayPlanItems = repayPlanItemManager.getByApplyIdForApp(applyId);
		if (CollectionUtils.isEmpty(repayPlanItems)){
			return Collections.emptyList();
		}
		return BeanMapper.mapList(repayPlanItems,RepayDetailListVO.class);
	}

	@Override
	public List<RepayPlanDetailVO> getByTerms(String applyId, List<String> termList) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id",applyId));
		criteria.and(Criterion.ne("IFNULL(repay_type,'')","manualdelay"));
		criteria.and(Criterion.ne("IFNULL(repay_type,'')","mandelay"));
		criteria.and(Criterion.in("this_term",termList));
		List<RepayPlanItem> repayPlanItems = repayPlanItemManager.findAllByCriteria(criteria);
		if (CollectionUtils.isEmpty(repayPlanItems)){
			return Collections.EMPTY_LIST;
		}
		return BeanMapper.mapList(repayPlanItems, RepayPlanDetailVO.class);
	}

	@Override
	public RepayDetailListVO getByRepayPlanItemId(String repayPlanItemId) {
		RepayPlanItem repayPlanItem = repayPlanItemManager.getById(repayPlanItemId);
		if (repayPlanItem == null){
			return null;
		}
		return BeanMapper.map(repayPlanItem, RepayDetailListVO.class);
	}

	private void setData(RepayDetailListOP op, List<RepayDetailListVO> voList) {
		Set<String> userIdSet = new HashSet<>();
		for (RepayDetailListVO vo : voList) {
			vo.setEverydayInterest(CostUtils.calDayInterest(vo.getApproveAmt(), vo.getBasicRate(), vo.getDiscountRate()));
			vo.setCurrentInterest(vo.getEverydayInterest().multiply(BigDecimal.valueOf(vo.getBorrow())));
			// vo.setDeductionStatus(deductionLogManager.applyCheck(vo.getId()));
			userIdSet.add(vo.getUserId());
		}

		/*
		 * if ((op.getStage().equals(1) || op.getStage().equals(2)) &&
		 * CollectionUtils.isNotEmpty(voList)) {
		 *//** 查询用户逾期次数 */
		/*
		 * List<OverdueDTO> countOfRepayList =
		 * overdueManager.countOverdue(userIdSet); if
		 * (CollectionUtils.isEmpty(countOfRepayList)) { return; }
		 *//** 查询用户最长逾期天数 */
		/*
		 * List<OverdueDTO> maxOfRepayList =
		 * overdueManager.maxOverdueDays(userIdSet);
		 *//** 组装数据 */
		/*
		 * for (RepayDetailListVO vo : voList) { setOverdueTimes(vo,
		 * countOfRepayList); setMaxOverdueDays(vo, maxOfRepayList); } }
		 */
	}

	private void setWarnOverdueTimes(RepayWarnListVO vo, List<OverdueDTO> countOfRepayList) {
		for (OverdueDTO dto : countOfRepayList) {
			if (StringUtils.equals(dto.getUserId(), vo.getUserId())) {
				vo.setOverdueTimes(dto.getNumber());
				return;
			}
		}

	}

	private void setWarnMaxOverdueDays(RepayWarnListVO vo, List<OverdueDTO> maxOfRepayList) {
		for (OverdueDTO dto : maxOfRepayList) {
			if (StringUtils.equals(dto.getUserId(), vo.getUserId())) {
				vo.setMaxOverdueDays(dto.getNumber());
				return;
			}
		}

	}

	private void setOverdueTimes(RepayDetailListVO vo, List<OverdueDTO> countOfRepayList) {
		for (OverdueDTO dto : countOfRepayList) {
			if (StringUtils.equals(dto.getUserId(), vo.getUserId())) {
				vo.setOverdueTimes(dto.getNumber());
				return;
			}
		}

	}

	private void setMaxOverdueDays(RepayDetailListVO vo, List<OverdueDTO> maxOfRepayList) {
		for (OverdueDTO dto : maxOfRepayList) {
			if (StringUtils.equals(dto.getUserId(), vo.getUserId())) {
				vo.setMaxOverdueDays(dto.getNumber());
				return;
			}
		}

	}

	@Override
	public RepayPlanItemListVO repayPlanItemService(String contNo) {
		Page voPage = new Page(1, Integer.MAX_VALUE);
		RepayDetailListOP op = new RepayDetailListOP();
		op.setContNo(contNo);

		List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(voPage, op);
		RepayPlanItemListVO vo = new RepayPlanItemListVO();
		vo.setList(voList);
		return vo;
	}

	@Override
	public RepayPlanItemListVO repayDetailListExecludeDelay(String contNo) {
		Page voPage = new Page(1, Integer.MAX_VALUE);
		RepayDetailListOP op = new RepayDetailListOP();
		op.setContNo(contNo);

		List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailListExecludeDelay(voPage, op);
		RepayPlanItemListVO vo = new RepayPlanItemListVO();
		vo.setList(voList);
		return vo;
	}

	@Override
	public TaskResult overdueDataCalc(int type) {
		logger.info("开始更新逾期还款计划。");
		long starTime = System.currentTimeMillis();
		/** 查询所有逾期的数据 */
		List<OverdueItemCalcDTO> overdueList = null;
		if (type == 1)
			overdueList = repayPlanItemManager.findOverdueItem();
		else
			overdueList = repayPlanItemManager.findOverdueItemForTFL();
		if (CollectionUtils.isEmpty(overdueList)) {
			long endTime = System.currentTimeMillis();
			logger.info("更新逾期还款计划结束，暂无逾期数据。执行耗时{}", endTime - starTime);
			return new TaskResult();
		}
		writeLog(overdueList);
		int success = 0;
		int fail = 0;

		//逾期订单集合
		List<String> applyIdList = new ArrayList<String>();
		for (OverdueItemCalcDTO item : overdueList) {
			try {
				/** 更新还款计划明细 */
				RepayPlanItem destination = RepayPlanHelper.calcOverdueItem(item, type);
				repayPlanItemManager.updatePayResult(destination);
				/** 更新还款计划 */
				// y:逾期
				loanRepayPlanManager.summary(item.getApplyId());

				applyIdList.add(item.getApplyId());//还款计划更新成功，加入逾期订单集合

				/** 更新逾期天数 */
				int overdueDays = DateUtils.daysBetween(item.getRepayDate(), new Date());
				if (type == 2) {
					overdueDays++;
				}
				if (overdueDays > 1) {
					overdueManager.updateOverdueDays(item.getId(), overdueDays);
				}
				success++;

				/** 还款计划变更推送到导流平台 */
				if (jdqService.isExistApplyId(item.getApplyId())){
					XianJinCardUtils.setJDQRepayPlanFeedbackToRedis(item.getApplyId());
				}
				/*if (overdueDays <= 1) {
					//XianJinCardUtils.setRepayPlanFeedbackToRedis(item.getApplyId());

					//XianJinCardUtils.rongPayFeedback(item.getApplyId());
				} else {
					String xjbkOrderSn = applyTripartiteService.getThirdIdByApplyId(item.getApplyId());
					if (StringUtils.isNotBlank(xjbkOrderSn)) {
						XianJinCardUtils.setRepayPlanFeedbackToRedis(item.getApplyId());
						continue;
					}
					String rongOrderSn = applyTripartiteRong360Service.getThirdIdByApplyId(item.getApplyId());
					if (StringUtils.isNotBlank(rongOrderSn)) {
						//XianJinCardUtils.rongPayFeedback(item.getApplyId());
						continue;
					}
				}*/
			} catch (Exception e) {
				fail++;
				logger.error("更新逾期还款计划失败，参数： " + JsonMapper.getInstance().toJson(overdueList), e);
			}
		}

		long endTime = System.currentTimeMillis();
		logger.info("更新逾期还款计划结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		rongPointCutService.overduePoint(applyIdList);// 用作三方订单逾期，切面通知的切入点标记
		return new TaskResult(success, fail);
	}

	private void writeLog(List<OverdueItemCalcDTO> overdueList) {
		StringBuffer sb = new StringBuffer("当前需要更新的逾期还款计划数据：");
		for (OverdueItemCalcDTO item : overdueList) {
			sb.append("【applyId = ").append(item.getApplyId()).append(", repayPlanItem.id = ").append(item.getId())
					.append("】 ");
		}
		logger.info(sb.toString());
	}

	@Override
	public Map<String, Object> getRepayDetailByApplyId(String applyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 订单号查订单
		LoanApply apply = loanApplyManager.getLoanApplyById(applyId);
		if (null == apply) {
			return map;
		}

		Integer status = apply.getStatus();
		if (status == XjdLifeCycle.LC_AUTO_AUDIT_2 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2) {
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_REJECT); // 审核未通过4
		} else if (status == XjdLifeCycle.LC_APPLY_1 || status == XjdLifeCycle.LC_AUTO_AUDIT_0
				|| status == XjdLifeCycle.LC_AUTO_AUDIT_3 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0
				|| status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3) {
			// 真正审核中
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_AUDITING); // 审核中2
		} else if (status == XjdLifeCycle.LC_RAISE_0 || status == XjdLifeCycle.LC_RAISE_1
				|| status == XjdLifeCycle.LC_LENDERS_0) {
			// 放款中
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_PAY); // 放款中5
		} else if (ApplyStatusEnum.FINISHED.getValue().equals(apply.getApplyStatus())) {
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_FINISHED); // 已结清
			// 6
		} else if (status == XjdLifeCycle.LC_CASH_4 || status == XjdLifeCycle.LC_REPAY_0) {
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_PASS); // 还款中3
		} else if (status == XjdLifeCycle.LC_OVERDUE_0) {
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_OVERDUE); // 已逾期9
		} else if(status == XjdLifeCycle.LC_CASH_2) {
			// 待提现
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_WITHDRAW); // 待提现
		} else {
			map.put("applyStatus", LoanApplySimpleVO.APPLY_STATUS_AUDITING); // 审核中2
		}

		map.put("loanAmt", apply.getApproveAmt());
		map.put("totalTerm", apply.getTerm());
		map.put("loanPurpose",
				StringUtils.isNoneBlank(apply.getPurpose()) ? LoanPurposeEnum.getDesc(apply.getPurpose()) : "");
		map.put("repayMethod", RepayMethodEnum.getDesc(apply.getRepayMethod()));
		String loanTerm = null;// 借款期限
		if (apply.getRepayMethod() == RepayMethodEnum.ONE_TIME.getValue()) {
			loanTerm = apply.getApproveTerm() + "天";
		} else {
			loanTerm = apply.getTerm() + "期";
		}
		map.put("loanTerm", loanTerm);
		// 还款总计划
		LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(apply.getId());
		if (null == repayPlan) {
			return map;
		}
		// 订单查还款计划明细
		List<Map<String, Object>> repayList = new ArrayList<Map<String, Object>>();

		List<RepayPlanItem> list = repayPlanItemManager.getByApplyIdForApp(apply.getId());
		if (list != null && list.size() > 0) {
			for (RepayPlanItem vo : list) {
				Map<String, Object> repayMap = new HashMap<String, Object>();
				if (vo.getThisTerm().equals(repayPlan.getCurrentTerm())
						&& ApplyStatusEnum.UNFINISH.getValue().equals(vo.getStatus())) {
					map.put("currRepayDate", DateUtils.formatDate(vo.getRepayDate()));
					map.put("currRepayAmt", vo.getTotalAmount());
					map.put("currTerm", vo.getThisTerm());
				}
				repayMap.put("term", vo.getThisTerm());
				repayMap.put("repayDate", DateUtils.formatDate(vo.getRepayDate()));
				repayMap.put("status", vo.getStatus());
				int overdueDays = DateUtils.daysBetween(vo.getRepayDate(), new Date());
				if (ApplyStatusEnum.UNFINISH.getValue().equals(vo.getStatus()) && overdueDays > 0) {
					repayMap.put("status", 5);// 逾期未结清
				}
				repayMap.put("repayAmt", vo.getTotalAmount());
				repayMap.put("preferential", LoanApplySimpleVO.NO);
				repayList.add(repayMap);
				// if (LoanProductEnum.XJD.getId().equals(apply.getProductId())
				// && apply.getTerm().intValue() > 1
				// && (vo.getThisTerm() == vo.getTotalTerm())) {
				// repayMap.put("preferential", LoanApplySimpleVO.YES);
				// }
			}
		}
		// 现金贷小额分期奇葩分期90天
		if (LoanProductEnum.XJD.getId().equals(apply.getProductId()) && apply.getTerm().intValue() > 1
				&& apply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_90) {
			int month = 1;
			for (int i = apply.getTerm() + 1; i <= apply.getTerm() + 2; i++) {
				Map<String, Object> repayMap = new HashMap<String, Object>();
				Date repyaDate = DateUtils.addMonth(DateUtils.addDay(repayPlan.getLoanStartDate(), -1), ++month);
				repayMap.put("term", i);
				repayMap.put("repayDate", DateUtils.formatDate(repyaDate));
				if (repayPlan.getStatus().equals(Global.REPAY_PLAN_STATUS_OVER)) {
					repayMap.put("status", 1);
				} else {
					repayMap.put("status", 0);
					int overdueDays = DateUtils.daysBetween(repyaDate, new Date());
					if (overdueDays > 0) {
						repayMap.put("status", 5);// 逾期未结清
					}
				}
				repayMap.put("repayAmt", PrincipalInterestDayUtils.getInterestCount(apply.getApproveAmt(),
						apply.getActualRate(), apply.getRepayUnit().intValue(), apply.getTerm()));
				repayMap.put("preferential", LoanApplySimpleVO.YES);
				repayList.add(repayMap);
			}
			map.put("loanTerm", repayList.size() + "期");
			map.put("repayMethod", RepayMethodEnum.INTEREST.getDesc());
		}

		map.put("unPayTotalAmt", repayPlan.getUnpayPrincipal().add(repayPlan.getUnpayInterest()));
		map.put("totalAmt", repayPlan.getTotalAmount());
		map.put("loanTime", DateUtils.formatDateTime(repayPlan.getLoanStartDate()));
		map.put("repayList", repayList);
		return map;
	}

	@Override
	public Map<String, Object> getRepayDetailByContNo(String contNo) {
		Map<String, Object> mapdata = loanApplyManager.findContractInfo(contNo);
		if (null != mapdata) {
			// 获取分期数据
			List<Map<String, Object>> repayDetailData = repayPlanItemManager.getRepayDetailByContNo(contNo);
			// 获取地区列表
			Map<String, String> areaMap = areaManager.getAreaCodeAndName();
			if (null != repayDetailData && repayDetailData.size() != 0) {
				mapdata.put("repayDetailData", repayDetailData);
				for (Map<String, Object> map : repayDetailData) {
					mapdata.put("loanStartDate", DateUtils.formatDate((Date) map.get("loanStartDate"), "yyyy年MM月dd日"));
					mapdata.put("loanEndDate", DateUtils.formatDate((Date) map.get("loanEndDate"), "yyyy年MM月dd日"));
					break;
				}
			} else {
				mapdata.put("loanStartDate", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
				Date loanEndDate = RepayPlanHelper.getCCDRepayDate(DateUtils.parse(DateUtils.getDate(), "yyyy-MM-dd"),
						(Integer) mapdata.get("totalTerm"), (BigDecimal) mapdata.get("repayUnit"));
				mapdata.put("loanEndDate", DateUtils.formatDate(loanEndDate, "yyyy年MM月dd日"));
				// 计算还款明细
				List<Map<String, Object>> repayListData = new ArrayList<Map<String, Object>>();
				for (int i = 1; i <= (Integer) mapdata.get("totalTerm"); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("thisTerm", i);
					Date ccdRepayDate = RepayPlanHelper.getCCDRepayDate(
							DateUtils.parse(DateUtils.getDate(), "yyyy-MM-dd"), i,
							(BigDecimal) mapdata.get("repayUnit"));
					map.put("repayDate", DateUtils.formatDate(ccdRepayDate, "yyyy年MM月dd日"));
					// 月还款本息
					BigDecimal monthPrincipalInterest = AverageCapitalPlusInterestUtils.getMonthPrincipalInterest(
							(BigDecimal) mapdata.get("principal"), (BigDecimal) mapdata.get("actualRate"),
							(Integer) mapdata.get("totalTerm") / 2).divide(new BigDecimal(2), 0,
							BigDecimal.ROUND_HALF_UP);
					// 月还款利息
					BigDecimal currTerm = new BigDecimal(i).divide(new BigDecimal(2), 0, BigDecimal.ROUND_HALF_UP);
					BigDecimal monthInterest = AverageCapitalPlusInterestUtils.getMonthInterest(
							(BigDecimal) mapdata.get("principal"), (BigDecimal) mapdata.get("actualRate"),
							(Integer) mapdata.get("totalTerm") / 2).get(currTerm.intValue());
					monthInterest = monthInterest.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
					BigDecimal principal = monthPrincipalInterest.subtract(monthInterest);
					BigDecimal servFee = ((BigDecimal) mapdata.get("servFeeRate")).multiply(
							(BigDecimal) mapdata.get("principal")).setScale(0, BigDecimal.ROUND_UP);
					map.put("thisAmount", principal.add(monthInterest).add(servFee));
					repayListData.add(map);
				}
				mapdata.put("repayDetailData", repayListData);
			}
			mapdata.put("totalTerm", (Integer) mapdata.get("totalTerm") / 2);
			String address = areaMap.get(mapdata.get("resideProvince")) + areaMap.get(mapdata.get("resideCity"))
					+ areaMap.get(mapdata.get("resideDistrict")) + mapdata.get("resideAddr");
			mapdata.put("resideAddr", address);
			if (StringUtils.isNotBlank((String) mapdata.get("qq"))) {
				mapdata.put("qq", mapdata.get("qq") + "@qq.com");
			}
			if (StringUtils.isNotBlank((String) mapdata.get("purpose"))) {
				mapdata.put("purpose", LoanPurposeEnum.getDesc((String) mapdata.get("purpose")));
			}
			mapdata.put("principalRMB", MoneyUtils.convert(mapdata.get("principal").toString()));
			mapdata.put("basicRate", BigDecimal.valueOf(100).multiply((BigDecimal) mapdata.get("basicRate")));
			mapdata.put("servFeeRate", BigDecimal.valueOf(100).multiply((BigDecimal) mapdata.get("servFeeRate")));
			mapdata.put("bankCode", BankCodeEnum.getName((String) mapdata.get("bankCode")));

		}
		return mapdata;
	}

	@Override
	public String getApplyIdByRepayPlanItemId(String repayPlanItemId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("id", repayPlanItemId));
		RepayPlanItem RepayPlanItem = repayPlanItemManager.getByCriteria(criteria);
		if (RepayPlanItem == null) {
			return null;
		} else {
			return RepayPlanItem.getApplyId();
		}
	}

	@Override
	@Transactional
	public int deductionRepayDetail(String repayPlanItemId, String deductionDetailAmt, String deductionReason,
			String approverId, String approverName) {
		RepayPlanItem repayPlanItem = repayPlanItemManager.getById(repayPlanItemId);
		if (null != repayPlanItem) {
			if (repayPlanItem.getStatus() == 1) {
				logger.error("还款计划明细的状态不正确, repayPlanItemId = " + repayPlanItemId + " ,status ="
						+ repayPlanItem.getStatus());
				return 0;
			}
			if (repayPlanItem.getTotalAmount().compareTo(new BigDecimal(deductionDetailAmt)) < 0) {
				logger.error("减免金额大于应还金额 repayPlanItemId = " + repayPlanItemId + " ,deductionDetailAmt ="
						+ deductionDetailAmt + " ,totalAmount =" + repayPlanItem.getTotalAmount());
				return 0;
			}
			// 更新还款明细表
			BigDecimal thisTotalAmount = repayPlanItem.getTotalAmount().add(repayPlanItem.getDeduction())
					.subtract(new BigDecimal(deductionDetailAmt));
			repayPlanItem.setTotalAmount(thisTotalAmount);
			repayPlanItem.setDeduction(new BigDecimal(deductionDetailAmt));
			int num = repayPlanItemManager.update(repayPlanItem);
			// 更新还款总账表
			List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(repayPlanItem.getApplyId());
			BigDecimal totalAmount = BigDecimal.ZERO;
			BigDecimal deduction = BigDecimal.ZERO;
			for (RepayPlanItem item : itemList) {
				totalAmount = totalAmount.add(item.getTotalAmount());
				deduction = deduction.add(item.getDeduction());
			}
			LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(repayPlanItem.getApplyId());
			repayPlan.setTotalAmount(totalAmount);
			repayPlan.setDeduction(deduction);
			int num2 = loanRepayPlanManager.update(repayPlan);
			if (num > 0 && num2 > 0) {
				// 记录减免操作日志
				DeductionLog entity = new DeductionLog();
				entity.setUserId(repayPlan.getUserId());
				entity.setApplyId(repayPlan.getApplyId());
				entity.setRepayPlanItemId(repayPlanItemId);
				entity.setDeduction(new BigDecimal(deductionDetailAmt));
				entity.setRemark(deductionReason);
				entity.setApproverId(approverId);
				entity.setApproverName(approverName);
				entity.setApproveTime(new Date());
				entity.setStatus(1);
				entity.setSource(5);
				deductionLogManager.insert(entity);
				return 1;
			}
		}
		logger.error("还款计划明细ID不存在, repayPlanItemId = " + repayPlanItemId);
		return 0;
	}

	/**
	 * 进入还款列表
	 */
	@Override
	public Page<TodayRepayListVO> todayRepayList(RepayDetailListOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<TodayRepayListVO> voList = repayPlanItemManager.todayRepayList(voPage, op);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public Page<TodayRepayListCalloutVO> todayRepayListCallout(RepayDetailListOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<TodayRepayListCalloutVO> voList = repayPlanItemManager.todayRepayListCallout(voPage, op);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public boolean updateForPartWithhold(String repayPlanItemId, String amount, String tradeDate) throws ParseException {
		boolean result = false;
		RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
		BigDecimal currActualRepayAmt = CostUtils.add(item.getActualRepayAmt(),new BigDecimal(amount));
		Date repayTime = DateUtils.parseDate(tradeDate);
		if (repayTime == null){
			repayTime = new Date();
		}
		String repayDate = DateUtils.formatDate(repayTime, "yyyy-MM-dd");
		item.setActualRepayAmt(currActualRepayAmt);
		item.setActualRepayDate(repayDate);
		item.setActualRepayTime(repayTime);
		item.setStatus(ApplyStatusEnum.UNFINISH.getValue());//部分代扣-未完结
		int rz = repayPlanItemManager.update(item);
		if (rz == 1) {
			result = true;
		}
		return result;
	}

}