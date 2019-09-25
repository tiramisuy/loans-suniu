/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.entity.Blacklist;
import com.rongdu.loans.cust.manager.BlacklistManager;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.entity.ApplyAllot;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.ApplyAllotManager;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.option.ApplyAllotOP;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.service.ApplyAllotService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
/**
 * 贷款订单分配表-业务逻辑实现类
 * @author liuliang
 * @version 2018-07-12
 */
@Service("applyAllotService")
public class ApplyAllotServiceImpl  extends BaseService implements  ApplyAllotService{
	
	/**
 	* 贷款订单分配表-实体管理接口
 	*/
	@Autowired
	private ApplyAllotManager applyAllotManager;
	
	@Autowired
	private BlacklistManager blacklistManager;
	
	@Autowired
	private BorrowInfoManager borrowInfoManager;
	
	
	@Override
	public int insertAllot(ApplyAllotVO vo){
		//ApplyAllot entity = BeanMapper.map(vo,ApplyAllot.class);
		//return applyAllotManager.insert(entity);
		vo.setIsNewRecord(true);
		vo.preInsert();
		return applyAllotManager.insertAllot(vo);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<ApplyAllotVO> getAllotApplyList(Page page, ApplyListOP applyListOP) {
		List<ApplyAllotVO> voList = applyAllotManager.getAllotApplyList(page, applyListOP);
		if (CollectionUtils.isEmpty(voList)) {
			page.setList(Collections.emptyList());
			return page;
		}
		setBlacklistParam(applyListOP, voList);
		page.setList(voList);
		return page;
	}
	
	public ApplyAllotVO getById(String id){
		if(applyAllotManager.get(id) !=null){
			return  BeanMapper.map(applyAllotManager.get(id),ApplyAllotVO.class);
		}else{
			return new ApplyAllotVO();
		}
	}
	
	
	
	public int updateAllot(ApplyAllotOP op){
		return applyAllotManager.updateAllot(op);
	}
	
	
	private void setBlacklistParam(ApplyListOP applyListOP, List<ApplyAllotVO> voList) {
		/** 已否决列表需要查询是否在黑名单 */
		if (applyListOP.getStage().equals(3)) {
			List<String> userIdList = new ArrayList<>();
			for (ApplyAllotVO vo : voList) {
				userIdList.add(vo.getUserId());
			}
			List<Blacklist> blacklistList = blacklistManager.getBlacklistByUserIdList(userIdList);
			if (CollectionUtils.isNotEmpty(blacklistList)) {
				for (ApplyAllotVO vo : voList) {
					for (Blacklist black : blacklistList) {
						if (StringUtils.equals(vo.getUserId(), black.getUserId())) {
							vo.setBlacklist(1);
						}
					}
				}
			}
		}
	}

	
	/**
	 * 取消订单
	 */
	public int updateCancel(String applyId) {
		ApplyAllot allot = applyAllotManager.get(applyId);
		if (XjdLifeCycle.LC_RAISE_0 != allot.getStatus().intValue()
				&& XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0 != allot.getStatus().intValue()) {
			return 0;
		}
		logger.info("取消订单: {},{},{}", applyId, allot.getStatus(), ApplyStatusLifeCycleEnum.CANCAL.getValue());
		ApplyAllotOP op = new ApplyAllotOP();
		op.setId(applyId);
		op.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
		op.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
		op.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
		return applyAllotManager.updateAllot(op);
	}
	
	
	@Override
	public int updateResetCheck(String applyId) {
		ApplyAllot allot = applyAllotManager.get(applyId);
		logger.info("撤销订单: {},{},{}", applyId, allot.getStatus(),
				ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue());
		
		ApplyAllotOP op = new ApplyAllotOP();
		op.setId(applyId);
		op.setStage(ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getStage());
		op.setStatus(ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue());
		op.setApplyStatus(ApplyStatusEnum.UNFINISH.getValue());
		op.setApproveResult(0);
		return applyAllotManager.updateAllot(op);
	}

	
}