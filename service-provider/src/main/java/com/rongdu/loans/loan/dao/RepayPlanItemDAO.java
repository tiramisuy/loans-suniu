/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rongdu.loans.loan.vo.TodayRepayListCalloutVO;
import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.dto.OverdueItemCalcDTO;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.entity.RepayPlanItemDetail;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.RepayWarnListOP;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.loan.vo.RepayWarnListVO;
import com.rongdu.loans.loan.vo.TodayRepayListVO;

/**
 * 还款计划明细-数据访问接口
 *
 * @author zhangxiaolong
 * @version 2017-07-08
 */
@MyBatisDao
public interface RepayPlanItemDAO extends BaseDao<RepayPlanItem, String> {

	List<RepayDetailListVO> repayDetailList(@Param(value = "page") Page page,
			@Param(value = "op") RepayDetailListOP op);

	List<RepayDetailListVO> repayDetailListExecludeDelay(@Param(value = "page") Page page,
			@Param(value = "op") RepayDetailListOP op);

	List<RepayWarnListVO> repayWarnList(@Param(value = "page") Page page, @Param(value = "op") RepayWarnListOP op);

	/**
	 * 更新还款结果
	 *
	 * @param entity
	 * @return
	 */
	int updatePayResult(RepayPlanItem entity);

	/**
	 * 更新未结清还款结果
	 *
	 * @param entity
	 * @return
	 */
	int updateUnPayResult(RepayPlanItem entity);

	List<OverdueItemCalcDTO> findOverdueItem();

	List<OverdueItemCalcDTO> findOverdueItemForTFL();

	List<RepayPlanItem> getByApplyId(String applyId);

	/**
	 * code y0601
	 *
	 * @Title: getByApplyIdForApp
	 * @Description: 根据订单ID获取还款计划明细（APP上展示不包括已延期的还款记录）
	 * @param @param
	 *            applyId
	 * @param @return
	 *            参数
	 * @return List<RepayPlanItem> 返回类型
	 */
	List<RepayPlanItem> getByApplyIdForApp(String applyId);

	List<RepayPlanItem> getByUserId(String userId);

	/**
	 * 查询当日到期的还款计划明细
	 *
	 * @return
	 */
	List<RepayPlanItem> curdateRepayList(@Param(value = "termType") Integer termType);

	List<RepayPlanItem> afterCurdateRepayList(@Param(value = "termType") Integer termType,
			@Param(value = "withholdDate") String withholdDate, @Param(value = "num") Integer num);

	/**
	 * 查询明日到期的还款计划明细
	 *
	 * @return
	 */
	List<RepayPlanItem> tomorrowRepayList();

	List<RepayPlanItemDetail> noticeRepayList();

	/**
	 * 查询某日的未还款记录
	 *
	 * @param date
	 * @return
	 */
	List<RepayPlanItem> somedayUnrepayList(@Param(value = "date") Date date);

	/**
	 * 更新为处理中状态
	 *
	 * @param id
	 * @return
	 */
	int processing(@Param(value = "id") String id);

	/**
	 * 更新为未还款状态
	 *
	 * @param id
	 * @return
	 */
	int unfinish(@Param(value = "id") String id);

	int updateForDelay(RepayPlanItem item);

	/**
	 * 根据期数查询未完结的还款计划明细
	 *
	 * @param applyId
	 * @param term
	 * @return
	 */
	RepayPlanItem getUnoverItemByTerm(@Param(value = "applyId") String applyId, @Param(value = "term") int term);

	List<Map<String, Object>> getRepayDetailByContNo(@Param(value = "contNo") String contNo);

	public List<TodayRepayListVO> todayRepayList(@Param(value = "page") Page page,
			@Param(value = "op") RepayDetailListOP op);

	public List<TodayRepayListCalloutVO> todayRepayListCallout(@Param(value = "page") Page page,
			@Param(value = "op") RepayDetailListOP op);

	public int updateRepayTime(RepayPlanItem entity);

	/**
	 * 统计用户二三期逾期的订单数
	 *
	 * @param userId
	 * @return
	 */
	int countOverdueInTwoOrThreeTerm(@Param(value = "userId") String userId, @Param(value = "applyId") String applyId);

	public int delByApplyId(String applyId);
}