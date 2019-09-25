package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.CrudService;
import com.rongdu.loans.loan.dao.LoanTripProductDetailDAO;
import com.rongdu.loans.loan.entity.LoanTripProductDetail;
import com.rongdu.loans.loan.option.LoanTripProductListOP;
import com.rongdu.loans.loan.vo.LoanTripProductListVO;

@Service("tripProductDetailManager")
public class LoanTripProductDetailManager extends CrudService<LoanTripProductDetailDAO, LoanTripProductDetail, String>{

	
	public List<LoanTripProductListVO> getLoanTripList(Page<LoanTripProductListVO> page, LoanTripProductListOP op){
		return dao.getLoanTripList(page, op);
	}
}
