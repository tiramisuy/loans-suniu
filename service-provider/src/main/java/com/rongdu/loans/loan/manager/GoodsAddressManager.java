/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.GoodsAddress;
import com.rongdu.loans.loan.dao.GoodsAddressDao;

/**
 * 商品收货地址-实体管理实现类
 * @author Lee
 * @version 2018-05-09
 */
@Service("goodsAddressManager")
public class GoodsAddressManager extends BaseManager<GoodsAddressDao, GoodsAddress, String> {

    public GoodsAddress getNewAddress(String userId) {
        return dao.getNewAddress(userId);
    }
}