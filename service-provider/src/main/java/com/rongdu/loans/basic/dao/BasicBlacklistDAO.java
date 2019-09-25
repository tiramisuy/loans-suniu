/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.BasicBlacklist;

/**
 * 基础黑名单（手机号、ip等）-数据访问接口
 * @author likang
 * @version 2017-10-10
 */
@MyBatisDao
public interface BasicBlacklistDAO extends BaseDao<BasicBlacklist,String> {

	/**
	 * 根据基础黑名单值（手机号或ip）统计数据条数
	 * @param blValue
	 * @return
	 */
	int countByBlValue(@Param("blValue")String blValue);
}
