/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.service.CustCouponService;
import com.rongdu.loans.cust.vo.CustCouponVO;
import com.rongdu.loans.loan.dao.LoanTripProductDetailDAO;
/**
 * 客户卡券表-业务逻辑实现类
 * @author raowb
 * @version 2018-08-28
 */
@Service("custCouponService")
public class CustCouponServiceImpl  extends BaseService implements  CustCouponService{
	
	/**
 	* 客户卡券表-实体管理接口
 	*/
	@Autowired
	private CustCouponManager custCouponManager;
	@Autowired
	private LoanTripProductDetailDAO loanTripProductDetailDAO;

	@Override
	public List<CustCouponVO> findCustCouponByUserId(String userId) {
		// TODO Auto-generated method stub
		
		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("user_id", userId));
		criteria1.and(Criterion.eq("del", 0));
		criteriaList.add(criteria1);

		return BeanMapper.mapList(custCouponManager.findAllByCriteriaList(criteriaList),CustCouponVO.class);
	}
	
	@Override
	public List<CustCouponVO> findAvailableCoupon(String userId,Float amount) {
		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("user_id", userId));
		criteria1.and(Criterion.eq("del", 0));
		criteria1.and(Criterion.eq("status", 0));
		criteria1.and(Criterion.ge("end_time", DateUtils.formatDate(new Date())));
		criteria1.and(Criterion.le("amount", amount));
		
		criteriaList.add(criteria1);

		return BeanMapper.mapList(custCouponManager.findAllByCriteriaList(criteriaList),CustCouponVO.class);
	}

	@Override
	public Long findUnusedCouponCount(String userId) {
		// TODO Auto-generated method stub
		
		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("user_id", userId));
		criteria1.and(Criterion.eq("del", 0));
		criteria1.and(Criterion.eq("status", 0));
		criteria1.and(Criterion.ge("end_time", DateUtils.formatDate(new Date())));
		criteriaList.add(criteria1);
		
		return custCouponManager.countByCriteriaList(criteriaList);
	}

	@Override
	@Transactional
	public Integer cancelCoupon(String applyId) {
		// TODO Auto-generated method stub
		
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("apply_id", applyId));
		
		int rtn = 0;
		//旅游券
		rtn = loanTripProductDetailDAO.deleteTruelyByCriteria(criteria1);
		
		criteria1.and(Criterion.eq("status", 0));
		
		//购物券
		rtn+=custCouponManager.deleteTruelyByCriteria(criteria1);
		
		return rtn;
	}


	
	
}