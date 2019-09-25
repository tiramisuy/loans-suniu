/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.GoodsAddress;
import org.apache.ibatis.annotations.Param;

/**
 * 商品收货地址-数据访问接口
 *
 * @author Lee
 * @version 2018-05-09
 */
@MyBatisDao
public interface GoodsAddressDao extends BaseDao<GoodsAddress, String> {

    GoodsAddress getNewAddress(@Param(value = "userId") String userId);
}