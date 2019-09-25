/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.Date;
import java.util.List;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.OverdueOP;
import com.rongdu.loans.loan.option.OverduePushBackOP;
import com.rongdu.loans.loan.vo.OverduePushBackVO;
import com.rongdu.loans.loan.vo.OverdueVO;

/**
 * 逾期还款列表-业务逻辑接口
 * 
 * @author zhangxiaolong
 * @version 2017-09-26
 */
public interface OverdueService {

	/**
	 * 逾期分配
	 * 
	 * @param type
	 *            1=XJD 2=TFL
	 * @return
	 */
	TaskResult batchInsertOverdue(int type);
	
	
	/**
	 * 逾期15天分配给M2 人员修改分配表催收人
	 * @return
	 */
	TaskResult batchOverdueOfFiveTeen();

	/**
	 * 逾期催收，催收已还
	 * 
	 * @param op
	 * @return
	 */
	Page<OverdueVO> overdueList(OverdueOP op);
	
	/**
	 * 查询最长逾期天数
	 * 
	 * @param userId
	 * @return
	 */
	int getMaxOverdueDays(String userId);

	/**
	 * 查询累计逾期天数
	 *
	 * @param userId
	 * @return
	 */
	int getCountOverdueDays(String userId);
	
	
	/**
	 * 更新停催信息，result 改为99 ，停催时间为当前系统时间
	 * @param overdueId
	 * @return
	 */
	int updateStopOverdue(String overdueId,Integer resultType,String opertorName);
	
	
	/**
	 * 催收回单统计
	 * @param productId
	 * @return
	 */
	public List<OverduePushBackVO> getPushBackOverdue(OverduePushBackOP op);

	/**
	 * 修改催收人员的登陆时间
	 * @param userId
	 * @param loginDate
	 */
	int updateLastLoginTimeByUserId(String userId, Date loginTime);

}