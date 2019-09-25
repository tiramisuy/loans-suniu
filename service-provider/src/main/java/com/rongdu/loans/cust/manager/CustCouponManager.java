/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.cust.entity.CustCoupon;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.cust.dao.CustCouponDao;

/**
 * 客户卡券表-实体管理实现类
 * 
 * @author raowb
 * @version 2018-08-28
 */
@Service("custCouponManager")
public class CustCouponManager extends BaseManager<CustCouponDao, CustCoupon, String> {
	/**
	 * 
	 * @Title: generateCustCoupon
	 * @Description: 生成现金抵扣券
	 * @param loanApply
	 * @return 设定文件
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer generateCustCoupon(LoanApply loanApply) {
		List<CustCoupon> couponList = new ArrayList<CustCoupon>();

		int servFee = loanApply.getServFee().intValue();
		if (servFee <= 0) {
			return 0;
		}
		int servFeeRemainder = servFee % 300;// 余数
		for (int i = 0; i < servFee / 300; i++) {
			couponList.add(createCustCoupon(loanApply, 300));// 拆分为300
		}
		if (servFeeRemainder > 0) {
			int servFeeRemainder2 = servFeeRemainder % 100;// 余数
			for (int i = 0; i < servFeeRemainder / 100; i++) {// 余数拆分为100
				couponList.add(createCustCoupon(loanApply, 100));
			}

			if (servFeeRemainder2 > 0) {
				for (int i = 0; i < servFeeRemainder2 / 50; i++) {// 余数拆分为50
					couponList.add(createCustCoupon(loanApply, 50));
				}
				if (servFeeRemainder2 % 50 > 0) {
					couponList.add(createCustCoupon(loanApply, servFeeRemainder2 % 50));
				}
			}
		}

		return dao.insertBatch(couponList);
	}

	private CustCoupon createCustCoupon(LoanApply loanApply, int amount) {
		CustCoupon custCoupon = new CustCoupon();
		custCoupon.setUserId(loanApply.getUserId());
		custCoupon.setApplyId(loanApply.getId());
		custCoupon.setType(9);
		custCoupon.setCouponName("购物券");
		custCoupon.setAmount(BigDecimal.valueOf(amount));
		custCoupon.setRate(BigDecimal.valueOf(0.01));
		custCoupon.setStartTime(DateUtils.getDayBegin(new Date()));
		custCoupon.setEndTime(DateUtils.getDayEnd(DateUtils.addYears(new Date(), 1)));
		custCoupon.setStatus(0);
		custCoupon.setSource(loanApply.getSource());

		custCoupon.setCreateBy("system");
		custCoupon.setCreateTime(new Date());
		custCoupon.setUpdateBy(loanApply.getUpdateBy());
		custCoupon.setUpdateTime(loanApply.getUpdateTime());
		return custCoupon;
	}

	public String sumCouponAmtByIds (List<String> couponIds){
		if (couponIds == null || couponIds.isEmpty()){
			return "0.00";
		}
		return dao.sumCouponAmtByIds(couponIds);
	}

	public void updateCouponStatus(List<String> couponIds,CustCoupon custCoupon){
		if (couponIds == null || couponIds.isEmpty()){
			return ;
		}
		Criteria criteria = new Criteria();
		criteria.add(Criterion.in("id", couponIds));
		if (custCoupon == null){
			custCoupon = new CustCoupon();
		}
		custCoupon.preUpdate();
		dao.updateByCriteriaSelective(custCoupon, criteria);
	}
}