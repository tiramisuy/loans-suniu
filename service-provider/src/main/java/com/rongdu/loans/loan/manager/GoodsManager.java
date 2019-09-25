/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.GoodsDao;
import com.rongdu.loans.loan.entity.Goods;
import org.springframework.stereotype.Service;



/**
 * 商品-实体管理实现类
 * @author Lee
 * @version 2018-07-04
 */
@Service("goodsManager")
public class GoodsManager extends BaseManager<GoodsDao, Goods, String> {
	
}