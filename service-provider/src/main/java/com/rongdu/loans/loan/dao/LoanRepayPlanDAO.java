package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.vo.LoanRepayDetailVO;
import com.rongdu.loans.loan.vo.RepayTotalListVO;
import com.rongdu.loans.loan.vo.StatementVO;

/**
 * 还款计划DAO接口
 * 
 * @author likang
 * @version 2017-06-22
 */
@MyBatisDao
public interface LoanRepayPlanDAO extends BaseDao<LoanRepayPlan, String> {

	/**
	 * 根据申请编号获取还款计划信息
	 * 
	 * @param applyId
	 * @return
	 */
	LoanRepayPlan getByApplyId(String applyId);

	/**
	 * 根据申请编号获取还款计划明细
	 * 
	 * @param applyId
	 * @return
	 */
	LoanRepayDetailVO getLoanRepayDetail(@Param("applyId") String applyId, @Param("status") Integer status);

	/**
	 * 根据明细id获取当前期账单
	 * 
	 * @param repayPlanItemId
	 * @return
	 */
	StatementVO getStatementByItemId(@Param("repayPlanItemId") String repayPlanItemId);

	/**
	 * 更新还款结果
	 * 
	 * @param loanRepayPlan
	 * @return
	 */
	int updatePayResult(LoanRepayPlan loanRepayPlan);

	List<LoanRepayPlan> getByApplyIdList(@Param("applyIdList") List<String> applyIdList);

	List<LoanRepayPlan> findAllByUserId(String userId);

	int updateForDelay(LoanRepayPlan loanRepayPlan);

	List<RepayTotalListVO> repayTotalList(@Param(value = "page") Page page, @Param(value = "op") RepayDetailListOP op);
	
	public int updateRepayTime(LoanRepayPlan entity);
	
	public int delByApplyId(@Param("applyId")String applyId);
}
