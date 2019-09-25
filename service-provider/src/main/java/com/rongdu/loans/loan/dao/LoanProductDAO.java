package com.rongdu.loans.loan.dao;


import java.util.List;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanProduct;

/**
 * 产品信息DAO接口
 * @author likang
 * @version 2017-06-21
 */
@MyBatisDao
public interface LoanProductDAO extends BaseDao<LoanProduct, String> {

	List<LoanProduct> getLoanProductLikeById(String id);

}
