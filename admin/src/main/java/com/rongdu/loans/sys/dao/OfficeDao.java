/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.TreeDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.sys.entity.Office;

/**
 * 机构DAO接口
 * @author sunda
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	/*
	 * 获取所有remark为1的商户列表
	 */
	public List<Office> getAllCompany(@Param("remark")String remark);
}
