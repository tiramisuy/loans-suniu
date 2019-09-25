/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.loan.option.GoodsListOp;
import com.rongdu.loans.loan.vo.GoodsListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.GoodsList;
import com.rongdu.loans.loan.dao.GoodsListDao;

import java.util.Date;
import java.util.List;

/**
 * 商品信息表-实体管理实现类
 *
 * @author Lee
 * @version 2018-08-28
 */
@Service("goodsListManager")
public class GoodsListManager extends BaseManager<GoodsListDao, GoodsList, String> {

    public List<GoodsListVO> queryGoodsList(Page page, GoodsListOp op) {
        return dao.queryGoodsList(page, op);
    }

    public Integer saveOrUpdate(GoodsListVO entity) {
        int rz = 0;
        if (null != entity) {
            if (StringUtils.isNotBlank(entity.getId())) {
                entity.setUpdateTime(new Date());
                rz = dao.updateGoods(entity);
            } else {
                entity.setId(IdGen.uuid());
                entity.setCreateTime(new Date());
                entity.setUpdateTime(new Date());
                entity.setSalesVolume("0");
                rz = dao.insertGoods(entity);
            }
        }
        return rz;

    }
}