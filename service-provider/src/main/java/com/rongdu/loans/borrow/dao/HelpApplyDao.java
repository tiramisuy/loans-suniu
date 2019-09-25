/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.borrow.entity.HelpApply;
import com.rongdu.loans.borrow.option.HelpApplyForListOP;
import com.rongdu.loans.borrow.vo.HelpApplyVO;

/**
 * 助贷申请表-数据访问接口
 * @author liuliang
 * @version 2018-08-28
 */
@MyBatisDao
public interface HelpApplyDao extends BaseDao<HelpApply,String> {
	public List<HelpApplyVO> getHelpApplyList();
	public List<HelpApplyVO> getBorrowHelpList(@Param(value = "page") Page<HelpApplyVO>  page,@Param(value = "op") HelpApplyForListOP op);
	public int updateAllotApply(@Param(value = "borrowId")String borrowId,@Param(value = "userId")String userId,@Param(value = "userName")String userName,@Param(value = "updateBy")String updateBy);
	public HelpApply getHelpApplyByIdNo(@Param(value = "idNo")String idNo);

	int updateConcurent(@Param(value="entity")HelpApply entity);
	
	HelpApply getBorrowHelpByIdNo(@Param(value="idNo")String idNo,@Param(value="date")String date);
}