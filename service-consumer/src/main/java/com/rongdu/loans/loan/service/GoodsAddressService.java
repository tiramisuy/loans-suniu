/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.GoodsAddressOP;

/**
 * 商品收货地址-业务逻辑接口
 * @author Lee
 * @version 2018-05-09
 */
public interface GoodsAddressService {

    int save(GoodsAddressOP goodsAddressOP);
}