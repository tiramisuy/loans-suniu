/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.GoodsOP;
import com.rongdu.loans.loan.vo.LoanGoodsResultVO;
import com.rongdu.loans.loan.vo.LoanGoodsVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品信息表-业务逻辑接口
 * @author Lee
 * @version 2018-05-08
 */
public interface LoanGoodsService {
    LoanGoodsResultVO getGoods(GoodsOP goodsOP);
}