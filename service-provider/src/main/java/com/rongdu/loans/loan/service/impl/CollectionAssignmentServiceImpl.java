/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.enums.ReturnTypeEnum;
import com.rongdu.loans.loan.entity.CollectionAssignment;
import com.rongdu.loans.loan.manager.CollectionAssignmentManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.OverdueManager;
import com.rongdu.loans.loan.option.CollectionAssignmentOP;
import com.rongdu.loans.loan.service.CollectionAssignmentService;
import com.rongdu.loans.loan.vo.CollectionAssignmentVO;

/**
 * 催收分配记录-业务逻辑实现类
 * @author zhangxiaolong
 * @version 2017-09-28
 */
@Service("collectionAssignmentService")
public class CollectionAssignmentServiceImpl extends BaseService implements CollectionAssignmentService {

	/**
	 * 催收分配记录-实体管理接口
	 */
	@Autowired
	private CollectionAssignmentManager collectionAssignmentManager;
	@Autowired
	private OverdueManager overdueManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	
	
	@Override
	public List<CollectionAssignmentVO> getAllByRepayPlanItemId( String repayPlanItemId) {
		List<CollectionAssignment> collectionAssignmentList = collectionAssignmentManager.getAllByRepayPlanItemId(repayPlanItemId);
		if (CollectionUtils.isEmpty(collectionAssignmentList)){
			return Collections.EMPTY_LIST;
		}
		return BeanMapper.mapList(collectionAssignmentList, CollectionAssignmentVO.class);
	}

	@Override
	public void doAllotment( CollectionAssignmentOP collectionAssignmentOP) {
		List<String> idList = collectionAssignmentOP.getIdList();
		
		List<String[]> listUser = getUsers(collectionAssignmentOP.getId());		//获取拼装的催收人id和name
		int total  = listUser.size();
		
		List<CollectionAssignment> list = collectionAssignmentManager.getAllByItemIdAndDel(idList, 0);
		List<CollectionAssignment> destinationList = new ArrayList<>();
		/*for (CollectionAssignment c : list)*/
		int m =0;
		for(int i=0;i<list.size();i++){
			CollectionAssignment c = list.get(i);
			CollectionAssignment destination = new CollectionAssignment();
			destination.setRepayPlanItemId(c.getRepayPlanItemId());
			destination.setApplyId(c.getApplyId());
			destination.setContNo(c.getContNo());
			destination.setUserId(c.getUserId());
			destination.setUserName(c.getUserName());
			destination.setFromOperatorId(c.getToOperatorId());
			destination.setFromOperatorName(c.getToOperatorName());
			/*destination.setToOperatorId(collectionAssignmentOP.getId());
			destination.setToOperatorName(collectionAssignmentOP.getName());*/
			String uId ="";
			String uName="";
			
			//借款开始时间为9月26号之前申请的单期14天/15天产品逾期分配直接分配给“催收分配账号”
			//其他的平均分配催收员
			int applyCount = loanApplyManager.getCountByCondition(c.getApplyId()); 	//
				if(applyCount > 0){
					uId = "b15917d7b6044b4cae8d8a26512b841f";
					uName="催收分配账号";
					destination.setToOperatorId("b15917d7b6044b4cae8d8a26512b841f");
					destination.setToOperatorName("催收分配账号");
				}else{
					uId = listUser.get(m % total)[0];
					uName=listUser.get(m % total)[1];
					destination.setToOperatorId(uId);
					destination.setToOperatorName(uName);
					m++;
				}
			
			destination.setReturnType(collectionAssignmentOP.getReturnType());
			destination.setReturnTime(collectionAssignmentOP.getReturnTime());
			destination.setCreateBy(collectionAssignmentOP.getOperatorName());
			destination.setUpdateBy(collectionAssignmentOP.getOperatorName());
			destinationList.add(destination);
			
			overdueManager.collectionAssignmentByid(uId,uName, collectionAssignmentOP.getOperatorName(), c.getRepayPlanItemId());
		}
		collectionAssignmentManager.doAllotment(destinationList);
		
		/*for(int a=0;a<idList.size();a++){
			overdueManager.collectionAssignmentByid(listUser.get(a % total)[0], listUser.get(a % total)[1], collectionAssignmentOP.getOperatorName(), idList.get(a));
		}*/
		/*
		overdueManager.collectionAssignment(collectionAssignmentOP.getId(), collectionAssignmentOP.getName(),
				collectionAssignmentOP.getOperatorName(), idList);*/
	}
	
	/**
	 * 将组装的催收人ID 和 name 拆分
	 * eg:  id-name|id-name|……
	 * @param operateId
	 * @return
	 */
	public static List<String[]> getUsers(String operateId){
		String[] idAndName = operateId.split("\\|");
		List<String[]> list = new ArrayList<>();
		String [] idName ={};
		for(int i=0;i<idAndName.length;i++){
			idName = idAndName[i].split("-");
			list.add(idName);
		}
		return list;
	}
	
	/**
	 * 催收分配退回任务
	 *
	 * 查询所有需要退回的数据，将催收任务分配给原先的操作员。
	 * 失效当前数据，插入新的分配数据
	 *
	 * @return
	 */
	@Override
	@Transactional
	public TaskResult returnBack() {
		logger.info("开始执行催收分配退回任务。");
		long starTime = System.currentTimeMillis();

		List<CollectionAssignment> list = collectionAssignmentManager.getForReturnBack();
		if (CollectionUtils.isEmpty(list)){
			long endTime = System.currentTimeMillis();
			logger.info("催收分配退回任务结束，当日暂无逾期数据。执行耗时{}", endTime - starTime);
			return new TaskResult(0, 0);
		}
		logger.info("催收分配退回任务处理数据：{}", JsonMapper.getInstance().toJson(list));
		for (CollectionAssignment item : list){
			//退回给原先的催收员
			String toOperatorId = item.getFromOperatorId();
			String toOperatorName = item.getFromOperatorName();
			item.setFromOperatorId(item.getToOperatorId());
			item.setFromOperatorName(item.getToOperatorName());
			item.setToOperatorId(toOperatorId);
			item.setToOperatorName(toOperatorName);
			item.setReturnTime(null);
			item.setReturnType(ReturnTypeEnum.NOT.getValue());
			item.setRemark("催收分配退回");
			item.setCreateBy("system");
			item.setUpdateBy("system");
			item.setIsNewRecord(false);
		}
		collectionAssignmentManager.doAllotment(list);
		return new TaskResult(1, 0);
	}


}