/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.OverdueDao;
import com.rongdu.loans.loan.dto.OverdueDTO;
import com.rongdu.loans.loan.entity.Overdue;
import com.rongdu.loans.loan.option.OverdueOP;
import com.rongdu.loans.loan.option.OverduePushBackOP;
import com.rongdu.loans.loan.vo.OverduePushBackVO;
import com.rongdu.loans.loan.vo.OverdueVO;

/**
 * 逾期还款列表-实体管理实现类
 * 
 * @author zhangxiaolong
 * @version 2017-09-26
 */
@Service("overdueManager")
public class OverdueManager extends BaseManager<OverdueDao, Overdue, String> {

	/**
	 * 查询当日逾期的还款明细
	 * 
	 * @return
	 */
	public List<Overdue> getOverdueOfCurdate() {
		return dao.getOverdueOfCurdate();
	}

	public List<Overdue> getOverdueOf15(){
		return dao.getOverdueOf15();
	}
	/**
	 * 查询当日逾期的还款明细 for tfl
	 * 
	 * @return
	 */
	public List<Overdue> getOverdueOfCurdateForTFL() {
		return dao.getOverdueOfCurdateForTFL();
	}

	public List<OverdueVO> overdueList(Page page, OverdueOP op) {
		return dao.overdueList(page, op);
	}

	public int collectionAssignment(String operatorId, String operatorName, String updateBy, List<String> ids) {
		return dao.collectionAssignment(operatorId, operatorName, updateBy, ids);
	}

	public int collectionAssignmentByid(String operatorId, String operatorName, String updateBy, String id) {
		return dao.collectionAssignmentByid(operatorId, operatorName, updateBy, id);
	}

	/**
	 * 完结合同时更新逾期还款表
	 * 
	 * @param overdue
	 * @return
	 */
	public int updateOverCont(Overdue overdue) {
		return dao.updateOverCont(overdue);
	}

	public int updateOverdueDays(String id, int overdueDays) {
		return dao.updateOverdueDays(id, overdueDays);
	}

	public List<OverdueDTO> countOverdue(Set<String> custIdSet) {
		return dao.countOverdue(custIdSet);
	}

	public List<OverdueDTO> maxOverdueDays(Set<String> custIdSet) {
		return dao.maxOverdueDays(custIdSet);
	}

	public List<OverdueDTO> maxOverdueNumber(List<String> applyIdList) {
		return dao.maxOverdueNumber(applyIdList);
	}

	public Overdue getLastUnoverByApplyId(String applyId) {
		return dao.getLastUnoverByApplyId(applyId);
	}

	/**
	 * 更新停催信息，result 改为99 ，停催时间为当前系统时间
	 * 
	 * @param overdueId
	 * @return
	 */
	public int updateStopOverdue(String overdueId,Integer resultType,String opertorName) {
		return dao.updateStopOverdue(overdueId,resultType,opertorName);
	}

	/**
	 * 催收回单统计
	 * 
	 * @param productId
	 * @return
	 */
	public List<OverduePushBackVO> getPushBackOverdue(OverduePushBackOP op) {
		return dao.getPushBackOverdue(op);
	}

	/**
	 * 根据id修改逾期表的催收人信息
	 * @param id
	 * @param operatorId
	 * @param operatorName
	 * @return
	 */
	public int updateOperator(String id, String operatorId, String operatorName) {
		return dao.updateOperate(id, operatorId, operatorName);
	}
	
	public int deleteOverdueByApplyId(String applyId){
		return dao.deleteOverdueByApplyId(applyId);
	}

	/**
	 * 根据userId更新逾期还款用户最后登陆时间
	 * @param userId
	 * @param loginDate
	 * @return
	 */
	public int updateLastLoginTimeByUserId(String userId, Date loginTime) {
		return dao.updateLastLoginTimeByUserId(userId, loginTime);
	}
}