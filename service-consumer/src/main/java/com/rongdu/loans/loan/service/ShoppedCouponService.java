package com.rongdu.loans.loan.service;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.option.ShoppedCouponOP;
import com.rongdu.loans.loan.vo.ShoppedCouponVO;

/**
 * 
* @Description:  购物券Service接口
* @author: fy
 */
@Service
public interface ShoppedCouponService{
	
	Page<ShoppedCouponVO> getShoppedCouponList(ShoppedCouponOP op);

	void generateCoupon(String applyId,String userName);
}
