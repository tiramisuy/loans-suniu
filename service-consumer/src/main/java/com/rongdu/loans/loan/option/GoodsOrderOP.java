/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品信息表Entity
 *
 * @author Lee
 * @version 2018-08-28
 */
@Data
public class GoodsOrderOP implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 下单账户id
     */
    private String accountId;
    /**
     * 收货人手机
     */
    private String phone;
    /**
     * 收货姓名
     */
    private String name;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 结算价格
     */
    private BigDecimal price;
    /**
     * invoice
     */
    private String invoice;

    private String couponId;

    private BigDecimal coupon;

    private String bankName;
    /**
	  *0（未付款） 1(已付款)
	  */
    private String status;

    private String userPhone;

    private String userName;
    
    /**
     * 商品详情
     */
    private String remark;

}