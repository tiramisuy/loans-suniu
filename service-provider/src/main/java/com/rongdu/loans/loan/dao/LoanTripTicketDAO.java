package com.rongdu.loans.loan.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanTripTicket;

/**
 * 
* @Description:  旅游产品信息DAO接口
* @author: 饶文彪
* @date 2018年7月11日
 */
@MyBatisDao
public interface LoanTripTicketDAO extends BaseDao<LoanTripTicket, String> {

	/**
	 * 
	* @Title: findAvailableByProductId
	* @Description: 根据产品id查询未发放旅游券 
	* @param productId
	* @return    设定文件
	* @return List<LoanTripTicket>    返回类型
	* @throws
	 */
	List<LoanTripTicket> findAvailableByProductId(@Param("productId") String productId,@Param("pageSize") Integer pageSize);

}
