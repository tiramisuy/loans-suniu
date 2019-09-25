/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanGoodsBag;
import com.rongdu.loans.loan.vo.LoanGoodsVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 商品礼包表-数据访问接口
 * @author Lee
 * @version 2018-05-08
 */
@MyBatisDao
public interface LoanGoodsBagDao extends BaseDao<LoanGoodsBag,String> {

    List<LoanGoodsVO> getLoanGoods(@Param(value ="loanAmt") String loanAmt, @Param(value ="remark")String remark);
}