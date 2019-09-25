/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ApplyAllot;
import com.rongdu.loans.loan.option.ApplyAllotOP;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.vo.ApplyAllotVO;

/**
 * 贷款订单分配表-数据访问接口
 * @author liuliang
 * @version 2018-07-12
 */
@MyBatisDao
public interface ApplyAllotDao extends BaseDao<ApplyAllot,String> {
	List<ApplyAllotVO> getAllotApplyList(@Param(value = "page") Page<ApplyListOP> page,
			@Param(value = "applyListOP") ApplyListOP applyListOP);
	
	public int insertAllot(@Param(value = "allot")ApplyAllotVO allot);
	
	public int updateAllot(@Param(value = "op")ApplyAllotOP op);
}