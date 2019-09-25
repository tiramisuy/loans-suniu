/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.GoodsList;
import com.rongdu.loans.loan.option.GoodsListOp;
import com.rongdu.loans.loan.vo.GoodsListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品信息表-数据访问接口
 * @author Lee
 * @version 2018-08-28
 */
@MyBatisDao
public interface GoodsListDao extends BaseDao<GoodsList,String> {

    List<GoodsListVO> queryGoodsList(@Param(value = "page") Page page, @Param(value = "op")GoodsListOp op);

    int updateGoods(@Param(value = "entity")GoodsListVO entity);

    int insertGoods(GoodsListVO entity);
}