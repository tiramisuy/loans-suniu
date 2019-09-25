/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.test.dao;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.test.entity.Test;

/**
 * 测试DAO接口
 * @author sunda
 * @version 2013-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {

}
