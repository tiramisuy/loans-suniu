/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.cust.option.ShopOrderOP;
import com.rongdu.loans.loan.dao.GoodsOrderDao;
import com.rongdu.loans.loan.entity.GoodsOrder;
import com.rongdu.loans.loan.vo.GoodsOrderVO;

/**
 * 购物订单表-实体管理实现类
 * @author Lee
 * @version 2018-08-30
 */
@Service("goodsOrderManager")
public class GoodsOrderManager extends BaseManager<GoodsOrderDao, GoodsOrder, String> {

	public List<GoodsOrderVO> goodsOrderList(Page page, ShopOrderOP op) {
		return dao.goodsOrderList(page, op);
	}
	
	/**
	 * 根据一组过滤条件查询所有符合要求的数据VO列表
	 * @param criteriaList
	 * @return
	 */
	public List<GoodsOrderVO> findAllVOByCriteriaList(List<Criteria> criteriaList){
		return dao.findAllVOByCriteriaList(criteriaList);
	}
	
}