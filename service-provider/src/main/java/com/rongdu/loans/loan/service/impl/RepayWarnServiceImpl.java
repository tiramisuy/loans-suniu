/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.entity.RepayWarn;
import com.rongdu.loans.loan.entity.User;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.manager.RepayWarnManager;
import com.rongdu.loans.loan.manager.UserManager;
import com.rongdu.loans.loan.option.RepayWarnOP;
import com.rongdu.loans.loan.service.RepayWarnService;
import com.rongdu.loans.loan.vo.RepayWarnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 还款-预提醒表-业务逻辑实现类
 * @author fy
 * @version 2019-07-23
 */
@Service("repayWarnService")
public class RepayWarnServiceImpl  extends BaseService implements  RepayWarnService{
	
	/**
 	* 还款-预提醒表-实体管理接口
 	*/
	@Autowired
	private RepayWarnManager repayWarnManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private UserManager userManager;

	@Override
	public Page<RepayWarnVO> getRepayWarnList(RepayWarnOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<RepayWarnVO> voList = repayWarnManager.getRepayWarnList(voPage, op);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public void updateWarn(String id, String content) {
		RepayWarn entity = new RepayWarn();
		entity.setId(id);
		entity.setContent(content);
		repayWarnManager.update(entity);
	}

	@Override
	@Transactional
	public TaskResult allotRepayWarn() {
		logger.info("开始执行还款预提醒分配任务。");
		long starTime = System.currentTimeMillis();
		int success = 0;
		int fail = 0;

		// 获取当前应还19:00之后未还数据
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", 0));
		criteria.and(Criterion.eq("repay_date", DateUtils.getDate()));
		List<RepayPlanItem> repayPlanItems = repayPlanItemManager.findAllByCriteria(criteria);
		if (CollectionUtils.isEmpty(repayPlanItems)) {
			long endTime = System.currentTimeMillis();
			logger.info("执行还款预提醒分配任务结束，当日暂无未还款数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 获取当前期数的上一期还款明细
		Map<Boolean, List<RepayPlanItem>> collect = repayPlanItems.stream().collect(Collectors.partitioningBy(item -> item.getThisTerm() == 1));
		List<RepayPlanItem> earlyItem = collect.get(false);
		List<RepayPlanItem> thisItem = collect.get(true);
		if (earlyItem != null && earlyItem.size() > 0){
			Criteria criteriaEarly = null;
			for (RepayPlanItem item : earlyItem) {
				criteriaEarly = new Criteria();
				criteriaEarly.add(Criterion.eq("this_term", item.getThisTerm() -1 ));
				criteriaEarly.and(Criterion.eq("apply_id", item.getApplyId()));
				RepayPlanItem repayPlanItem = repayPlanItemManager.getByCriteria(criteriaEarly);
				if (repayPlanItem.getStatus() == 1){
					thisItem.add(item);
				}
			}
		}
		// 获取预提醒人员
		List<User> users = userManager.getUserByRole("csy");

		// 执行分配
		List<RepayWarn> repayWarns = new ArrayList<>();
		RepayWarn entity = null;
		int total = users.size();
		int n = 0;
		for (int i = 0; i < thisItem.size(); i++) {
			RepayPlanItem repayPlanItem = thisItem.get(i);
			entity = new RepayWarn();
			entity.setRepayId(repayPlanItem.getId());
			entity.setApplyId(repayPlanItem.getApplyId());
			entity.setSysUserId(users.get(n % total).getId());
			entity.setSysUserName(users.get(n % total).getName());
			repayWarns.add(entity);
			n++;
		}
		success = repayWarnManager.insertBatch(repayWarns);
		long endTime = System.currentTimeMillis();
		logger.info("执行还款预提醒分配任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

}