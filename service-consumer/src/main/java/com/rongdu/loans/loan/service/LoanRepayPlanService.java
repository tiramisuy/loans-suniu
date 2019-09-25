package com.rongdu.loans.loan.service;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.rongdu.loans.loan.option.xjbk.LoanRepayPlanVO;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.cust.vo.RepayItemDetailVO;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.RepayPlanOP;
import com.rongdu.loans.loan.vo.RepayTotalListVO;

/**
 * 贷款申请Service接口
 * 
 * @author likang
 * @version 2017-06-22
 */
@Service
public interface LoanRepayPlanService {

	/**
	 * 查询用户借款信息
	 * 
	 * @param userId
	 * @return
	 */
	List<RepayItemDetailVO> findUserRepayList(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 查询还款计划
	 * 
	 * @param op
	 * @return
	 */
	@Deprecated
	public Map<String, Object> getRepayPlan(RepayPlanOP op);

	public Map<String, Object> getRepayPlan2(RepayPlanOP op);

	/**
	 * 查询借点钱还款计划(前期固定数据,后期需作修改)
	 */
	public Map<String, Object> getJDQRepayPlan(RepayPlanOP op);

	/**
     * 后台还款总账列表接口
     * @param op
     * @return
     */
    Page<RepayTotalListVO> repayTotalList(@NotNull(message = "参数不能为空") RepayDetailListOP op);
    
    /**
     * 后台还款总账列表导出接口
     * @param op
     * @return
     */
    public List<RepayTotalListVO> repayTotalExportList(RepayDetailListOP op);

	LoanRepayPlanVO getByApplyId(String applyId);
	/**
	 * 
	* @Title: delPlanAndItemByApplyId
	* @Description: 删除还款计划和明细
	* @return void    返回类型
	* @throws
	 */
	int delPlanAndItemByApplyId(String applyId);
}
