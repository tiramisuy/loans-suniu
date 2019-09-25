package com.rongdu.loans.loan.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanTripProductDetail;
import com.rongdu.loans.loan.option.LoanTripProductDetailOP;
import com.rongdu.loans.loan.option.LoanTripProductListOP;
import com.rongdu.loans.loan.vo.LoanTripProductDetailVO;
import com.rongdu.loans.loan.vo.LoanTripProductListVO;

/**
 * 
* @Description:  旅游产品信息DAO接口
* @author: 饶文彪
* @date 2018年7月11日
 */
@MyBatisDao
public interface LoanTripProductDetailDAO extends BaseDao<LoanTripProductDetail, String> {

	/**
	 * 
	* @Title: findCustProduct
	* @Description: 查询用户的旅游产品
	* @param op
	* @return    设定文件
	* @return List<LoanTripProductDetailVO>    返回类型
	* @throws
	 */
	List<LoanTripProductDetailVO> findCustProduct(LoanTripProductDetailOP op);
	/**
	 * 
	* @Title: findByApplyId
	* @Description: 
	* @param applyId
	* @return    设定文件
	* @return LoanTripProductDetail    返回类型
	* @throws
	 */
	LoanTripProductDetail findByApplyId(String applyId);
	
	List<LoanTripProductListVO> getLoanTripList(@Param(value = "page")Page<LoanTripProductListVO> page, @Param(value = "op")LoanTripProductListOP op);
}
