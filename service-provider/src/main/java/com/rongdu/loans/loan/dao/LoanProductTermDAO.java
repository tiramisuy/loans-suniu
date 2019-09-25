package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanProductTerm;
import com.rongdu.loans.loan.vo.LoanProductTermVO;

import java.util.List;

/**
 * 产品周期信息DAO接口
 * @author likang
 * @version 2017-06-21
 */
@MyBatisDao
public interface LoanProductTermDAO extends BaseDao<LoanProductTerm, String> {

	/**
	 * 通过产品代码获取产品周期列表
	 * @param productId
	 * @return
	 */
	List<LoanProductTermVO> getByProductId(String productId);
}
