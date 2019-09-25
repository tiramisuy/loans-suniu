package com.rongdu.loans.loan.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.option.ShoppedCouponOP;
import com.rongdu.loans.loan.vo.ShoppedCouponVO;

/**
 * 
* @Description:  购物券DAO接口
* @author:fy
* @date 18-10-22
 */
@MyBatisDao
public interface ShoppedCouponDAO extends BaseDao<ShoppedCouponVO, String> {

	List<ShoppedCouponVO> getShoppedCouponList(@Param(value = "page") Page page, @Param(value = "op") ShoppedCouponOP op);

	int countShoppedCoupon(@Param(value = "op") ShoppedCouponOP op);

}
