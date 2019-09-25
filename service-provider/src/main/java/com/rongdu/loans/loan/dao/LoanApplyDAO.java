package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.CountApplyOP;
import com.rongdu.loans.loan.option.LoanApplyCustOP;
import com.rongdu.loans.loan.option.xjbk.ApplyThirdOP;
import com.rongdu.loans.loan.option.xjbk.ApproveFeedbackOP;
import com.rongdu.loans.loan.vo.ApplyListVO;
import com.rongdu.loans.loan.vo.LoanApplyStatusVO;
import com.rongdu.loans.loan.vo.LoansApplySummaryVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 贷款申请DAO接口
 * 
 * @author likang
 * @version 2017-06-22
 */
@MyBatisDao
public interface LoanApplyDAO extends BaseDao<LoanApply, String> {

	/**
	 * 根据用户基本信息查询贷款申请
	 * 
	 * @param op
	 * @return
	 */
	List<LoanApply> getLoanApplyFromUser(LoanApplyCustOP op);

	/**
	 * 根据用户id查询初始与未完结的申请记录
	 * 
	 * @param userId
	 * @return
	 */
	LoanApply getInitUnFinishLoanApplyByUserId(String userId);

	/**
	 * 根据用户id查询未完结的申请记录
	 * 
	 * @param userId
	 * @return
	 */
	LoanApply getUnFinishLoanApplyByUserId(String userId);

	/**
	 * 获取审核中订单
	 * 
	 * @param userId
	 * @return
	 */
	LoanApply getAuditLoanApply(String userId);

	/**
	 * 根据用户基本信息查询贷款申请
	 * 
	 * @param userId
	 * @return
	 */
	List<LoanApply> getRejectLoanApplyByUserId(String userId);

	/**
	 * 更新申请表的阶段与状态
	 * 
	 * @param loanApply
	 * @return
	 */
	int updateStageOrStatus(LoanApply loanApply);

	/**
	 * 根据用户基本信息查询贷款申请概要列表
	 * 
	 * @param op
	 * @return
	 */
	List<LoansApplySummaryVO> getLoanApplySummaryFromUser(LoanApplyCustOP op);

	/**
	 * 跟进申请编号获取申请单当前阶段与状态
	 * 
	 * @param applyId
	 * @return
	 */
	LoanApplyStatusVO getCurrentApplyStatus(String applyId);

	/**
	 * 根据用户id获取当前（未完结）申请的申请编号
	 * 
	 * @param userId
	 * @return
	 */
	String getCurApplyIdByUserId(String userId);

	List<LoanApply> getLoanApplyByUserId(String userId);

	List<ApplyListVO> getLoanApplyList1(@Param(value = "applyListOP")  ApplyListOP applyListOP);

	List<ApplyListVO> getLoanApplyList(@Param(value = "page") Page<ApplyListOP> page,
			@Param(value = "applyListOP") ApplyListOP applyListOP);

	int countApply(String userId);

	/**
	 * 根据id统计当前记录
	 * 
	 * @param id
	 * @return
	 */
	int countById(String id);

	/**
	 * 根据id查询申请单列表
	 * 
	 * @param idList
	 * @return
	 */
	List<LoanApply> findByIdList(@Param("idList") List<String> idList);

	/**
	 * 更新为逾期状态
	 * 
	 * @param idList
	 * @return
	 */
	int updateOverdue(@Param("idList") List<String> idList);

	/**
	 * 根据用户id统计最近一月申请次数
	 * 
	 * @param countApplyOP
	 * @return
	 */
	int countApplyNearMonth(CountApplyOP countApplyOP);

	/**
	 * 根据用户id查询最后一次已完结贷款申请
	 * 
	 * @param userId
	 * @return
	 */
	LoanApply getLastFinishApplyByUserId(String userId);

	/**
	 * 根据用户id统计正常还款完结的次数
	 * 
	 * @param userId
	 * @return
	 */
	int countOverLoanByRepay(String userId);

	/**
	 * code y0516 统计审核管理列表
	 * 
	 * @return
	 */
	public Map<String, Object> getCheckAllotListTotal(@Param("startDate") String startDate,
			@Param("endDate") String endDate, @Param("auditorId") List<String> auditorId, @Param("type") String type);

	/*
	 * 更新商户id
	 */
	void updateCompanyId(@Param("companyId") String companyId, @Param("id") String id);

	/*
	 * 根据订单id查询用户id
	 */
	void updateUserCompanyId(@Param("companyId") String companyId, @Param("id") String id);

	int updateForDelay(LoanApply loanApply);

	List<LoansApplySummaryVO> getLoanApplyListByUserId(String userId);

	LoanApply getLoanApplyByApplyId(String applyId);

	/**
	 * 查询未结清数量
	 * 
	 * @param userId
	 * @return
	 */
	int countUnFinishLoanApplyByUserId(String userId);
	
	/**
	 * code y1030
	 * 查询同以身份证号不同手机号用户未结清数量
	 */
	int countUnFinishByMobileAndIdNo(@Param("idNo") String idNo, @Param("mobile") String mobile);

	/**
	 * 查询所有未结清订单
	 * 
	 * @param userId
	 * @return
	 */
	List<LoanApply> findUnFinishListByUserId(String userId);

	/**
	 * 查询审核中数量
	 * 
	 * @param userId
	 * @return
	 */
	int countApprovingByUserId(String userId);

	/**
	 * 查询未结清金额
	 * 
	 * @param userId
	 * @return
	 */
	BigDecimal getUnFinishLoanAmtByUserId(String userId);

	/**
	 * 查询累计被拒次数
	 * 
	 * @param userId
	 * @return
	 */
	public int countRejectByUserId(String userId);

	/**
	 * 查询门店订单数
	 * 
	 * @return
	 */
	public int countApproveByCompanyId(@Param("companyId") String companyId, @Param("approveMonth") String approveMonth);

	/**
	 * 查询合同导出所需信息
	 */
	Map<String, Object> findContractInfo(@Param("contNo") String contNo);

	/**
	 * 根据客户手机号查询客户订单
	 * 
	 * @param mobile
	 * @return
	 */
	public List<Map<String, Object>> custApplyList(@Param("id") String id);

	List<ApproveFeedbackOP> getLoanApplyThirdList(@Param(value = "page") Page<ApplyThirdOP> page,
			@Param(value = "applyThirdOP") ApplyThirdOP applyThirdOP);

	int countUnFinishLoanApplyByUserInfo(@Param("userName") String userName, @Param("userPhone") String userPhone,
			@Param("userIdCard") String userIdCard);

	/**
	 * 查询最近一次还款的申请
	 * 
	 * @param userId
	 * @param productId
	 * @return
	 */
	LoanApply getLastRepayedByUserId(@Param("userId") String userId, @Param("productId") String productId);

	/**
	 * 获取状态为带推送 status = 410 的申请数据用于 营销分配
	 * 
	 * @return
	 */
	public List<LoanApply> getLoanApplyForMarket();

	List<Map<String, Object>> getReturnRate(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("auditorId") String auditorId, @Param("channel") String channel,
			@Param("productId") String productId, @Param("termType") String termType);

	/**
	 * 查询未完结订单
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	int countUnFinishApplyByMobile(String mobile);
	
	public int updateNoAnswer(@Param("applyId")String applyId,@Param("callNum")Integer callNum,@Param("remark")String remark);
	
	
	public int getCountByCondition(@Param("applyId")String applyId);
	/**
	 * 
	* @Title: updatePayChannel
	* @Description: 更新借款渠道
	* @return int    返回类型
	* @throws
	 */
	int updatePayChannel(LoanApply loanApply);


	List<ApplyListVO> getLoanApplyByApi(@Param(value = "page") Page<ApplyListOP> page,
										   @Param(value = "applyListOP") ApplyListOP applyListOP);

	/**
	 * 查询终审通过订单但未绑卡
	 * @return
	 */
	List<LoanApply> getPassNoBindCardList();
}
