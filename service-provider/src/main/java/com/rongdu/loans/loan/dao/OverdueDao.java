/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.dto.OverdueDTO;
import com.rongdu.loans.loan.entity.Overdue;
import com.rongdu.loans.loan.option.OverdueOP;
import com.rongdu.loans.loan.option.OverduePushBackOP;
import com.rongdu.loans.loan.vo.OverduePushBackVO;
import com.rongdu.loans.loan.vo.OverdueVO;

/**
 * 逾期还款列表-数据访问接口
 * 
 * @author zhangxiaolong
 * @version 2017-09-26
 */
@MyBatisDao
public interface OverdueDao extends BaseDao<Overdue, String> {

	/**
	 * 查询当日逾期的还款明细
	 * 
	 * @return
	 */
	List<Overdue> getOverdueOfCurdate();
	
	
	/**
	 * 查询逾期15天的还款明细
	 * 
	 * @return
	 */
	List<Overdue> getOverdueOf15();

	/**
	 * 查询当日逾期的还款明细 for tfl
	 * 
	 * @return
	 */
	List<Overdue> getOverdueOfCurdateForTFL();

	int collectionAssignment(@Param(value = "operatorId") String operatorId,
			@Param(value = "operatorName") String operatorName, @Param(value = "updateBy") String updateBy,
			@Param(value = "ids") List<String> ids);

	int collectionAssignmentByid(@Param(value = "operatorId") String operatorId,
			@Param(value = "operatorName") String operatorName, @Param(value = "updateBy") String updateBy,
			@Param(value = "id") String id);

	List<OverdueVO> overdueList(@Param(value = "page") Page page, @Param(value = "op") OverdueOP op);

	/**
	 * 完结合同时更新逾期还款表
	 * 
	 * @param overdue
	 * @return
	 */
	int updateOverCont(Overdue overdue);

	int updateOverdueDays(@Param(value = "id") String id, @Param(value = "overdueDays") int overdueDays);

	List<OverdueDTO> countOverdue(@Param(value = "custIdSet") Set<String> custIdSet);

	List<OverdueDTO> maxOverdueDays(@Param(value = "custIdSet") Set<String> custIdSet);

	List<OverdueDTO> maxOverdueNumber(@Param(value = "applyIdList") List<String> applyIdList);

	public Overdue getLastUnoverByApplyId(@Param(value = "applyId") String applyId);

	/**
	 * 更新停催信息，result 改为99 ，停催时间为当前系统时间
	 * 
	 * @param overdueId
	 * @return
	 */
	public int updateStopOverdue(@Param(value = "overdueId") String overdueId,@Param(value = "resultType") Integer resultType
			,@Param(value = "opertorName")String opertorName);

	/**
	 * 催收回单统计
	 * 
	 * @param productId
	 * @return
	 */
	public List<OverduePushBackVO> getPushBackOverdue(@Param(value = "op") OverduePushBackOP op);

	/**
	 * 根据id修改逾期表的催收人信息
	 * 
	 * @param id
	 * @param operatorId
	 * @param operatorName
	 * @return
	 */
	public int updateOperate(@Param(value = "id") String id, @Param(value = "operatorId") String operatorId,
			@Param(value = "operatorName") String operatorName);
	
	public int deleteOverdueByApplyId(@Param(value = "applyId")String applyId);

	/**
	 * 根据userId修改逾期还款用户最后登陆时间
	 * @param userId
	 * @param loginDate
	 * @return
	 */
	int updateLastLoginTimeByUserId(@Param(value = "userId") String userId, @Param(value = "loginTime") Date loginTime);
}