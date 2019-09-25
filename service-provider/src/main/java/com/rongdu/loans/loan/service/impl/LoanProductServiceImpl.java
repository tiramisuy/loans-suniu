package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.manager.LoanProductManager;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.PromotionOP;
import com.rongdu.loans.loan.service.LoanProductService;
import com.rongdu.loans.loan.vo.ApplyListVO;
import com.rongdu.loans.loan.vo.LoanProductVO;
import com.rongdu.loans.loan.vo.PromotionCaseVO;
import com.rongdu.loans.loan.vo.RepayWarnListVO;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品信息服务实现类
 * @author likang
 *
 */
@Service("loanProductService")
public class LoanProductServiceImpl extends BaseService implements LoanProductService {
	
	@Autowired
	private LoanProductManager loanProductManager;

	public LoanProductVO getLoanProductDetail(String productId) {
        return loanProductManager.getLoanProductDetail(productId);
	}

	@Override
	public List<LoanProductVO> getXJDLoanProductDetail(String productId) {
		return loanProductManager.getXJDLoanProductDetail(productId);
	}
	

	public Page<PromotionCaseVO> getLoanProductDetail(PromotionOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<PromotionCaseVO> voList = loanProductManager.getLoanProductDetail(voPage, op);
		voPage.setList(voList);
		return voPage;
	}
	
	public int updatePromotion(String id){
		return loanProductManager.updatePromotion(id);
	}
	
}
