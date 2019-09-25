/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品收货地址Entity
 *
 * @author Lee
 * @version 2018-05-09
 */
@Data
public class GoodsAddressVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 订单id
     */
    private String applyId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;


}