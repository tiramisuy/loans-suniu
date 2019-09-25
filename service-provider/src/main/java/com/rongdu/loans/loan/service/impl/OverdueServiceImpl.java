/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.loan.dto.OverdueDTO;
import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.OverdueOP;
import com.rongdu.loans.loan.option.OverduePushBackOP;
import com.rongdu.loans.loan.service.OverdueService;
import com.rongdu.loans.loan.service.RepayWarnService;
import com.rongdu.loans.loan.service.RongPointCutService;
import com.rongdu.loans.loan.vo.OverduePushBackVO;
import com.rongdu.loans.loan.vo.OverdueVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 逾期还款列表-业务逻辑实现类
 * 
 * @author zhangxiaolong
 * @version 2017-09-26
 */
@Service("overdueService")
public class OverdueServiceImpl extends BaseService implements OverdueService {

	/**
	 * 逾期还款列表-实体管理接口
	 */
	@Autowired
	private OverdueManager overdueManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private DeductionLogManager deductionLogManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private CollectionAssignmentManager collectionAssignmentManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private OperationLogManager operationLogManager;
	@Autowired
	private RongPointCutService rongPointCutService;
	@Autowired
	private RepayWarnManager repayWarnManager;

	/*
	 * type = 1 聚宝钱包 type = 2 其他产品
	 */
	@Override
	@Transactional
	public TaskResult batchInsertOverdue(int type) {
		logger.info("开始执行逾期&催收分配任务。");
		long starTime = System.currentTimeMillis();
		int success = 0;
		int fail = 0;

		/* 查询当日逾期的还款明细 */
		List<Overdue> overdueList = null;
		if (type == 1)
			overdueList = overdueManager.getOverdueOfCurdate();
		else
			overdueList = overdueManager.getOverdueOfCurdateForTFL();
		if (CollectionUtils.isEmpty(overdueList)) {
			long endTime = System.currentTimeMillis();
			logger.info("执行逾期&催收分配任务结束，当日暂无逾期数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}

		/* 更新贷款单逾期状态 */
		List<String> applyIdList = new ArrayList<String>();
		List<OverdueVO> overdueVOS = new ArrayList<>();
		for (Overdue overdue : overdueList) {
			applyIdList.add(overdue.getApplyId());
			overdueVOS.add(BeanMapper.map(overdue, OverdueVO.class));
		}
		if (applyIdList.size() > 0) {
			loanApplyManager.updateOverdue(applyIdList);
			/* 保存逾期日志 */
			// saveOverdueLog(overdueList);
		}

		/* 查询产品对应的催收员 */
		List<User> userList = null;
		userList = userManager.getUserByRole("csy");
/*		if (type == 1) { // 聚宝袋催收员
			userList = userManager.getUserByRole("jbcsy");
		} else {
			userList = userManager.getUserByRole("csy");
		}*/
		/* 按渠道排序overdueList */
		overdueList = getOverdueListChannelOrder(overdueList);
		/* 当日的逾期催收数据自动分配个催收员 */
		List<CollectionAssignment> collectionAssignmentList = allotment(overdueList, userList, type);
		/* 保存数据 */
		success = overdueManager.insertBatch(overdueList);
		if (CollectionUtils.isNotEmpty(collectionAssignmentList)) {
			collectionAssignmentManager.insertBatch(collectionAssignmentList);
		}
		long endTime = System.currentTimeMillis();
		logger.info("执行逾期&催收任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		rongPointCutService.overduePointForAnRong(overdueVOS);// 用作订单逾期，安融共享新增逾期切面通知的切入点标记
		return new TaskResult(success, fail);
	}

	/**
	 * 逾期15天分配给M2 人员修改分配表催收人
	 * 
	 * @return
	 */
	@Override
	public TaskResult batchOverdueOfFiveTeen() {
		logger.info("开始执行逾期15天&催收分配任务。");
		long starTime = System.currentTimeMillis();
		int success = 0;
		int fail = 0;
		/** 查询逾期15天的还款明细 */
		List<Overdue> overdueList = overdueManager.getOverdueOf15();
		if (CollectionUtils.isEmpty(overdueList)) {
			long endTime = System.currentTimeMillis();
			logger.info("执行逾期15天&催收分配任务结束，当日暂无逾期数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 获取特别催收组用户信息
		List<User> specialUserList = userManager.getUserByRole("csy_m2");
		if (CollectionUtils.isEmpty(specialUserList)) {
			long endTime = System.currentTimeMillis();
			logger.info("执行逾期15天&催收分配任务结束，当日暂无M2角色用户。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 平均分配
		int m = 0;// 平均分配给特别催收组
		int totalM = specialUserList.size();
		for (int i = 0; i < overdueList.size(); i++) {
			Overdue overdue = overdueList.get(i);
			// 分配给特别组，同时修改上期催收人
			String userId = specialUserList.get(m % totalM).getId();
			String userName = specialUserList.get(m % totalM).getName();
			overdue.setOperatorId(userId);
			overdue.setOperatorName(userName);
			overdueManager.updateOperator(overdue.getId(), userId, userName);
			m++;
			success++;
		}
		long endTime = System.currentTimeMillis();
		logger.info("执行逾期&催收任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

	/**
	 * 按照渠道分组排序
	 * 
	 * @param overdueList
	 * @return
	 */
	private static List<Overdue> getOverdueListChannelOrder(List<Overdue> overdueList) {
		List<Overdue> result = new ArrayList<Overdue>();
		Map<String, List<Overdue>> map = new LinkedHashMap<String, List<Overdue>>();
		for (Overdue o : overdueList) {
			String channelId = o.getChannelId();
			List<Overdue> list = map.get(channelId);
			if (list == null) {
				list = new ArrayList<Overdue>();
				map.put(channelId, list);
			}
			list.add(o);
		}
		for (Entry<String, List<Overdue>> entry : map.entrySet()) {
			for (Overdue o : entry.getValue()) {
				result.add(o);
			}
		}
		return result;
	}

	/**
	 * 保存逾期日志
	 * 
	 * @param overdueList
	 */
	private void saveOverdueLog(List<Overdue> overdueList) {
		Date now = new Date();
		List<OperationLog> list = new ArrayList<>();
		for (Overdue overdue : overdueList) {
			OperationLog operationLog = new OperationLog();
			operationLog.setPreviousStage(ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getStage());
			operationLog.setPreviousStatus(ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue());
			operationLog.setApplyId(overdue.getApplyId());
			operationLog.setUserId(overdue.getUserId());
			operationLog.setStatus(ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY.getValue());
			operationLog.setStage(ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY.getStage());
			operationLog.setTime(now);
			operationLog.setOperatorId(Global.DEFAULT_OPERATOR_ID);
			operationLog.setOperatorName(Global.DEFAULT_OPERATOR_NAME);
			operationLog.setSource(Global.SOURCE_SYSTEM);
			operationLog.preInsert();
			list.add(operationLog);
		}
		operationLogManager.insertBatch(list);

	}

	/**
	 * 自动分配
	 * 
	 * @param overdueList
	 * @param userList
	 * @return 返回催收分配记录
	 */
	private List<CollectionAssignment> allotment(List<Overdue> overdueList, List<User> userList, int type) {
		List<CollectionAssignment> collectionAssignmentList = new ArrayList<>();
		Date today = new Date();
		// 没有人员进行分配，就不分配
		if (CollectionUtils.isEmpty(userList)) {
			for (Overdue overdue : overdueList) {
				// 不自动生成主键
				overdue.setIsNewRecord(true);
				overdue.preInsert();
				overdue.setOverdueStartDate(today);
				/* 逾期天数 */
				int overdueDays = DateUtils.daysBetween(overdue.getRepayDate(), today);
				if (type == 2)
					overdueDays++;
				overdue.setOverdueDays(overdueDays);
			}
			return collectionAssignmentList;
		}
		// 获取特别催收组用户信息
		List<User> specialUserList = userManager.getUserByRole("csy_m2");

		// 平均分配
		int total = userList.size();
		int n = 0; // 平均分配给催收人
		int m = 0;// 平均分配给特别催收组
		int totalM = specialUserList.size();

		for (Overdue overdue : overdueList) {
			// 查询此订单之前是否被分配，及被分配的催收员信息
			Overdue coll = overdueManager.getLastUnoverByApplyId(overdue.getApplyId());// 查询出逾期表中未结清的
			if (coll == null) { // 未查到之前被分配催收记录
				Criteria criteria = new Criteria();
				criteria.add(Criterion.eq("repay_id",overdue.getId()));
				RepayWarn repayWarn = repayWarnManager.getByCriteria(criteria);
				if (repayWarn != null){
					// 做过预提醒，先分配给预提醒人员
					overdue.setOperatorId(repayWarn.getSysUserId());
					overdue.setOperatorName(repayWarn.getSysUserName());
				}else {
					// 未做过预提醒，平均分配
					overdue.setOperatorId(userList.get(n % total).getId());
					overdue.setOperatorName(userList.get(n % total).getName());
					n++;
				}
			} else {
				// 上期暂停催收客户，继续暂停
				if (coll.getResult() != null && coll.getResult() == 99) {
					overdue.setResult(99);
				}
				if (totalM == 0 || compareList(specialUserList, coll.getOperatorId())) {
					overdue.setOperatorId(coll.getOperatorId());
					overdue.setOperatorName(coll.getOperatorName());
				} else {
					// 分配给特别组，同时修改上期催收人
					String userId = specialUserList.get(m % totalM).getId();
					String userName = specialUserList.get(m % totalM).getName();
					overdue.setOperatorId(userId);
					overdue.setOperatorName(userName);
					overdueManager.updateOperator(coll.getId(), userId, userName);
					m++;
				}
			}
			collectionAssignmentList.add(createAllotmentLog(overdue));
			// 不自动生成主键
			overdue.setIsNewRecord(true);
			overdue.preInsert();
			overdue.setOverdueStartDate(today);
			/* 逾期天数 */
			int overdueDays = DateUtils.daysBetween(overdue.getRepayDate(), today);
			if (type == 2)
				overdueDays++;
			overdue.setOverdueDays(overdueDays);
		}
		return collectionAssignmentList;
	}

	private static boolean compareDate(String date1Str, Date date2) {
		String format = "yyyy-MM-dd HH:mm:ss";
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat(format).parse(date1Str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long da1 = date1.getTime();
		long da2 = date2.getTime();
		if (da1 - da2 > 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean compareList(List<User> list, String userId) {
		boolean flag = false;
		if (list != null) {
			for (User user : list) {
				if (user.getId().equals(userId)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	private CollectionAssignment createAllotmentLog(Overdue overdue) {
		CollectionAssignment collectionAssignment = BeanMapper.map(overdue, CollectionAssignment.class);
		collectionAssignment.setRepayPlanItemId(overdue.getId());
		collectionAssignment.setToOperatorId(overdue.getOperatorId());
		collectionAssignment.setToOperatorName(overdue.getOperatorName());
		collectionAssignment.setRemark("系统自动分配");
		return collectionAssignment;
	}

	public Page<OverdueVO> overdueList(OverdueOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<OverdueVO> voList = overdueManager.overdueList(voPage, op);
		setData(voList);
		voPage.setList(voList);
		return voPage;

	}

	private void setData(List<OverdueVO> voList) {
		Set<String> custIdSet = new HashSet<>();
		for (OverdueVO vo : voList) {
			vo.setEverydayInterest(
					CostUtils.calDayInterest(vo.getApproveAmt(), vo.getBasicRate(), vo.getDiscountRate()));
			vo.setCurrentInterest(vo.getEverydayInterest().multiply(BigDecimal.valueOf(vo.getBorrow())));
			// vo.setDeductionStatus(deductionLogManager.applyCheck(vo.getId()));
			custIdSet.add(vo.getUserId());
		}

		/*
		 * if (CollectionUtils.isNotEmpty(voList)) {
		 *//** 查询用户逾期次数 */
		/*
		 * List<OverdueDTO> countOfRepayList =
		 * overdueManager.countOverdue(custIdSet); if
		 * (CollectionUtils.isEmpty(countOfRepayList)) { return; }
		 *//** 查询用户最长逾期天数 */
		/*
		 * List<OverdueDTO> maxOfRepayList =
		 * overdueManager.maxOverdueDays(custIdSet);
		 *//** 组装数据 */
		/*
		 * for (OverdueVO vo : voList) { setOverdueTimes(vo, countOfRepayList);
		 * setMaxOverdueDays(vo, maxOfRepayList); } }
		 */
	}

	private void setOverdueTimes(OverdueVO vo, List<OverdueDTO> countOfRepayList) {
		for (OverdueDTO dto : countOfRepayList) {
			if (StringUtils.equals(dto.getUserId(), vo.getUserId())) {
				vo.setOverdueTimes(dto.getNumber());
				return;
			}
		}

	}

	private void setMaxOverdueDays(OverdueVO vo, List<OverdueDTO> maxOfRepayList) {
		for (OverdueDTO dto : maxOfRepayList) {
			if (StringUtils.equals(dto.getUserId(), vo.getUserId())) {
				vo.setMaxOverdueDays(dto.getNumber());
				return;
			}
		}

	}

	@Override
	public int getMaxOverdueDays(String userId) {
		Set<String> custIdSet = new HashSet<>();
		custIdSet.add(userId);
		List<OverdueDTO> maxOfRepayList = overdueManager.maxOverdueDays(custIdSet);
		for (OverdueDTO dto : maxOfRepayList) {
			if (StringUtils.equals(dto.getUserId(), userId)) {
				return dto.getNumber();
			}
		}
		return 0;
	}

	@Override
	public int getCountOverdueDays(String userId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("user_id", userId));
		List<Overdue> overDueList = overdueManager.findAllByCriteria(criteria);
		int overdueCount = 0;
		for (Overdue dto : overDueList) {
			overdueCount += dto.getOverdueDays();
		}
		return overdueCount;
	}

	/**
	 * 更新停催信息，result 改为99 ，停催时间为当前系统时间
	 * 
	 * @param overdueId
	 * @return
	 */
	@Override
	public int updateStopOverdue(String overdueId, Integer resultType, String opertorName) {
		return overdueManager.updateStopOverdue(overdueId, resultType, opertorName);
	}

	/**
	 * 催收回单统计
	 * 
	 * @param productId
	 * @return
	 */
	public List<OverduePushBackVO> getPushBackOverdue(OverduePushBackOP op) {
		return overdueManager.getPushBackOverdue(op);
	}

	@Override
	public int updateLastLoginTimeByUserId(String userId, Date loginTime) {
		return overdueManager.updateLastLoginTimeByUserId(userId, loginTime);
	}
}