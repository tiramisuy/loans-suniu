/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.ProductDAO;
import com.rongdu.loans.loan.entity.Product;
import org.springframework.stereotype.Service;

/**
 * 贷款产品-实体管理实现类
 * @author zhangxiaolong
 * @version 2017-07-13
 */
@Service("productManager")
public class ProductManager extends BaseManager<ProductDAO, Product, String> {

}