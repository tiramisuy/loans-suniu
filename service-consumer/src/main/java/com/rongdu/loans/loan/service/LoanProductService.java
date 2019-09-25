package com.rongdu.loans.loan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.option.PromotionOP;
import com.rongdu.loans.loan.vo.LoanProductVO;
import com.rongdu.loans.loan.vo.PromotionCaseVO;

/**
 * 产品信息Service接口
 * @author likang
 * @version 2017-06-21
 */
@Service
//@Transactional(readOnly = true)
public interface LoanProductService {

	/**
	 * 获取产品信息明细
	 * @return
	 */
	LoanProductVO getLoanProductDetail(String productId);
	
	/**
	 * 获取现金贷产品信息明细
	 * @return
	 */
	List<LoanProductVO> getXJDLoanProductDetail(String productId);
	
	public Page<PromotionCaseVO> getLoanProductDetail(PromotionOP op);
	
	public int updatePromotion(String id);
}
