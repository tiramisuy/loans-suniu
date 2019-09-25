/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.service;

import java.util.List;

import com.rongdu.loans.cust.vo.CustCouponVO;

/**
 * 客户卡券表-业务逻辑接口
 * @author raowb
 * @version 2018-08-28
 */
public interface CustCouponService {

	/**
	 * 
	* @Title: findCustCouponByUserId
	* @Description: 查询用户卡券
	* @param userId
	* @return    设定文件
	* @return List<CustCouponVO>    返回类型
	* @throws
	 */
	List<CustCouponVO> findCustCouponByUserId(String userId);
	
	/**
	 * 
	* @Title: findAvailableCoupon
	* @Description: 查询有效用户卡券
	* @param userId, amount
	* @return    设定文件
	* @return List<CustCouponVO>    返回类型
	* @throws
	 */
	List<CustCouponVO> findAvailableCoupon(String userId,Float amount);
	
	
	/**
	 * 
	* @Title: findUnusedCouponCount
	* @Description: 查询用户卡券
	* @param userId
	* @return    设定文件
	* @return Long    返回类型
	* @throws
	 */
	Long findUnusedCouponCount(String userId);
	/**
	 * 
	* @Title: cancelCoupon
	* @Description: 取消卡券
	* @param applyId
	* @return    设定文件
	* @return Integer    返回类型
	* @throws
	 */
	Integer cancelCoupon(String applyId);
}