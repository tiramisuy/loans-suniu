/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.option.FlowListOP;
import com.rongdu.loans.loan.vo.RepayLogListVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.entity.RepayLog;

/**
 * 充值/还款-数据访问接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-11
 */
@MyBatisDao
public interface RepayLogDAO extends BaseDao<RepayLog, String> {

	List<RepayLogListVO> getRepayLogList(@Param(value = "page") Page page, @Param(value = "op") FlowListOP op);

	Long countPayingByRepayPlanItemId(String repayPlanItemId);
	
	Long countPayingByIdNo(String idNo);

	Long countPayingByApplyId(String applyId);

	public List<RepayLogVO> findUnsolvedOrders();
	
	RepayLog findByRepayPlanItemId(String repayPlanItemId);
}