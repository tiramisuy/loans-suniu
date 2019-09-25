/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.Product;

/**
 * 贷款产品-数据访问接口
 * @author zhangxiaolong
 * @version 2017-07-13
 */
@MyBatisDao
public interface ProductDAO extends BaseDao<Product,String> {

}