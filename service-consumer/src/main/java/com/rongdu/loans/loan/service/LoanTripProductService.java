package com.rongdu.loans.loan.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.option.LoanTripProductDetailOP;
import com.rongdu.loans.loan.option.LoanTripProductListOP;
import com.rongdu.loans.loan.vo.LoanTripProductDetailVO;
import com.rongdu.loans.loan.vo.LoanTripProductListVO;
import com.rongdu.loans.loan.vo.LoanTripProductVO;

/**
 * 
* @Description:  旅游产品信息Service接口
* @author: 饶文彪
* @date 2018年7月11日
 */
@Service
//@Transactional(readOnly = true)
public interface LoanTripProductService{

	/**
	 * 
	* @Title: findAllProduct
	* @Description: 查询所有旅游产品
	* @return    设定文件
	* @return List<LoanTripProductVO>    返回类型
	* @throws
	 */
	List<LoanTripProductVO> findAllProduct();
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
	* @Title: saveCustProduct
	* @Description: 保存用户旅游分期产品
	* @param op
	* @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	String saveCustProduct(LoanTripProductDetailOP op);
	
	/**
	 * 
	* @Title: updateCustProductAndTicket
	* @Description: 更新用户产品信息和旅游券状态
	* @param applyId 借款申请id
	* @return    设定文件
	* @return Boolean    返回类型
	* @throws
	 */
	Boolean updateCustProductAndTicket(String applyId,String updateBy);
	
	
	
	Page<LoanTripProductListVO> getLoanTripList(@NotNull(message = "参数不能为空") LoanTripProductListOP op);
	
	
}
