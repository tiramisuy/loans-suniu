/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.loans.loan.vo.LoanGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.LoanGoodsBag;
import com.rongdu.loans.loan.dao.LoanGoodsBagDao;

import java.util.List;

/**
 * 商品礼包表-实体管理实现类
 *
 * @author Lee
 * @version 2018-05-08
 */
@Service("loanGoodsBagManager")
public class LoanGoodsBagManager extends BaseManager<LoanGoodsBagDao, LoanGoodsBag, String> {

    @Autowired
    private LoanGoodsBagDao loanGoodsBagDao;

    public List<LoanGoodsVO> getLoanGoods(String loanAmt, String remark) {
        List<LoanGoodsVO> loanGoodsVO = loanGoodsBagDao.getLoanGoods(loanAmt, remark);
        return loanGoodsVO;
    }
}