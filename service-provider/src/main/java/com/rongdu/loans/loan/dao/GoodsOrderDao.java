/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.loans.cust.option.ShopOrderOP;
import com.rongdu.loans.loan.entity.GoodsOrder;
import com.rongdu.loans.loan.vo.GoodsOrderVO;

/**
 * 购物订单表-数据访问接口
 * @author Lee
 * @version 2018-08-30
 */
@MyBatisDao
public interface GoodsOrderDao extends BaseDao<GoodsOrder,String> {

	List<GoodsOrderVO> goodsOrderList(@Param(value = "page") Page page, @Param(value = "op") ShopOrderOP op);
	/**
	 * 根据一组过滤条件查询所有符合要求的数据VO列表
	 * @param criteriaList
	 * @return
	 */
	public List<GoodsOrderVO> findAllVOByCriteriaList(@Param(value="criteriaList")List<Criteria> criteriaList);
	
	
}