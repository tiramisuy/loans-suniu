/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;


import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.Goods;

/**
 * 商品-数据访问接口
 * @author Lee
 * @version 2018-07-04
 */
@MyBatisDao
public interface GoodsDao extends BaseDao<Goods,String> {
	
}