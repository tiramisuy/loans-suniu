/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.borrow.entity.HelpResult;
import com.rongdu.loans.borrow.vo.HelpResultVO;

/**
 * 助贷结果表-数据访问接口
 * @author liuliang
 * @version 2018-08-28
 */
@MyBatisDao
public interface HelpResultDao extends BaseDao<HelpResult,String> {
	
	public List<HelpResultVO> getHelpResultByUserId(@Param(value = "userId")String userId);
}