/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.test.dao;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.test.entity.SysEmp;

/**
 * 员工信息DAO接口
 * @author sunda
 * @version 2016-11-20
 */
@MyBatisDao
public interface SysEmpDao extends CrudDao<SysEmp> {
	
}