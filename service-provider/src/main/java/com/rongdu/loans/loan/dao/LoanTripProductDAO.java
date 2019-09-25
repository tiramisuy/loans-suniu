package com.rongdu.loans.loan.dao;


import java.util.List;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanTripProduct;

/**
 * 
* @Description:  旅游产品信息DAO接口
* @author: 饶文彪
* @date 2018年7月11日
 */
@MyBatisDao
public interface LoanTripProductDAO extends BaseDao<LoanTripProduct, String> {

	List<LoanTripProduct> findAllList();

}
