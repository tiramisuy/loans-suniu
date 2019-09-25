/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.manager.AreaManager;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserInfoVO;
import com.rongdu.loans.loan.entity.GoodsAddress;
import com.rongdu.loans.loan.manager.GoodsAddressManager;
import com.rongdu.loans.loan.manager.LoanGoodsBagManager;
import com.rongdu.loans.loan.option.GoodsOP;
import com.rongdu.loans.loan.service.LoanGoodsService;
import com.rongdu.loans.loan.vo.LoanGoodsResultVO;
import com.rongdu.loans.loan.vo.LoanGoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品信息表-业务逻辑实现类
 *
 * @author Lee
 * @version 2018-05-08
 */
@Service("loanGoodsService")
public class LoanGoodsServiceImpl extends BaseService implements LoanGoodsService {

    /**
     * 商品信息表-实体管理接口
     */

    @Autowired
    private LoanGoodsBagManager loanGoodsBagManager;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private GoodsAddressManager goodsAddressManager;
    @Autowired
    private AreaManager areaManager;


    public static Logger logger = LoggerFactory.getLogger(LoanGoodsServiceImpl.class);


    @Override
    public LoanGoodsResultVO getGoods(GoodsOP goodsOP) {
        LoanGoodsResultVO loanGoodsResultVO = new LoanGoodsResultVO();
        GoodsAddress goodsAddress = goodsAddressManager.getNewAddress(goodsOP.getUserId());
        if (goodsAddress == null) {
            CustUserInfoVO vo = custUserService.getSimpleUserInfo(goodsOP.getUserId());
            loanGoodsResultVO.setUserName(vo.getUserName());
            loanGoodsResultVO.setMobile(vo.getMobile());
            loanGoodsResultVO.setResideProvince(vo.getResideProvince());
            loanGoodsResultVO.setResideProvinceName(vo.getResideProvinceName());
            loanGoodsResultVO.setResideCity(vo.getRegCity());
            loanGoodsResultVO.setResideCityName(vo.getResideCityName());
            loanGoodsResultVO.setResideDistrict(vo.getResideDistrict());
            loanGoodsResultVO.setResideDistrictName(vo.getResideDistrictName());
            loanGoodsResultVO.setResideAddr(vo.getResideAddr());
        } else {
            Map<String, String> areaMap = areaManager.getAreaCodeAndName();
            loanGoodsResultVO.setUserName(goodsAddress.getUserName());
            loanGoodsResultVO.setMobile(goodsAddress.getMobile());
            loanGoodsResultVO.setResideProvince(goodsAddress.getProvince());
            if (StringUtils.isNotBlank(goodsAddress.getProvince())) {
                loanGoodsResultVO.setResideProvinceName(areaMap.get(goodsAddress.getProvince()));
            }
            loanGoodsResultVO.setResideCity(goodsAddress.getCity());
            if (StringUtils.isNotBlank(goodsAddress.getCity())) {
                loanGoodsResultVO.setResideCityName(areaMap.get(goodsAddress.getCity()));
            }
            loanGoodsResultVO.setResideDistrict(goodsAddress.getDistrict());
            if (StringUtils.isNotBlank(goodsAddress.getDistrict())) {
                loanGoodsResultVO.setResideDistrictName(areaMap.get(goodsAddress.getDistrict()));
            }
            loanGoodsResultVO.setResideAddr(goodsAddress.getAddress());
        }
        List<LoanGoodsVO> loanGoodsVO = getLoanGoods(String.valueOf(goodsOP.getApproveAmt()));
        if (loanGoodsVO == null) {
            loanGoodsVO = loanGoodsBagManager.getLoanGoods(String.valueOf(goodsOP.getApproveAmt()), goodsOP.getLoanStatus());
            cacheLoanGoods(loanGoodsVO);
        }
        loanGoodsResultVO.setDetails(loanGoodsVO);
        loanGoodsResultVO.setBagAmt(loanGoodsVO.size() > 0 ? loanGoodsVO.get(0).getBagAmt() : null);
        loanGoodsResultVO.setRemark(Global.FIRST_FEE_DESC);
        return loanGoodsResultVO;
    }


    public static List<LoanGoodsVO> getLoanGoods(String approveAmt) {
        if (StringUtils.isNotBlank(approveAmt)) {
            return (List<LoanGoodsVO>) JedisUtils.getObject(
                    "goods:goods_" + approveAmt);
        }
        return null;
    }

    public static List<LoanGoodsVO> cacheLoanGoods(List<LoanGoodsVO> info) {
        if (null != info && info.size() > 0) {
            JedisUtils.setObject(
                    "goods:goods_" + info.get(0).getLoanAmt(),
                    info, 60 * 60 * 24 * 30);
        } else {
            logger.error("商品信息为空");
        }
        return info;
    }
}