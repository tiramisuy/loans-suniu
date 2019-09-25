/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.CustCoupon;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户卡券表-数据访问接口
 * @author raowb
 * @version 2018-08-27
 */
@MyBatisDao
public interface CustCouponDao extends BaseDao<CustCoupon,String> {

    String sumCouponAmtByIds (@Param("couponIds") List<String> couponIds);
	
}