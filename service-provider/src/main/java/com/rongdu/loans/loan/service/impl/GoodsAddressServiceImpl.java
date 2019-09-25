/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.utils.MyBeanUtils;
import com.rongdu.loans.loan.option.GoodsAddressOP;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.entity.GoodsAddress;
import com.rongdu.loans.loan.service.GoodsAddressService;
import com.rongdu.loans.loan.manager.GoodsAddressManager;

/**
 * 商品收货地址-业务逻辑实现类
 *
 * @author Lee
 * @version 2018-05-09
 */
@Service("goodsAddressService")
public class GoodsAddressServiceImpl extends BaseService implements GoodsAddressService {

    /**
     * 商品收货地址-实体管理接口
     */
    @Autowired
    private GoodsAddressManager goodsAddressManager;

    @Override
    public int save(GoodsAddressOP goodsAddressOP) {
        GoodsAddress goodsAddress = new GoodsAddress();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(goodsAddressOP, goodsAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int saveBack = goodsAddressManager.insert(goodsAddress);
        return saveBack;
    }
}