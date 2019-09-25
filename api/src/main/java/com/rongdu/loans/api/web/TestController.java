package com.rongdu.loans.api.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.web.BaseController;
import com.rongdu.loans.loan.service.LoanTripProductService;
import com.rongdu.loans.sys.web.ApiResult;
@Controller
@RequestMapping(value = "${adminPath}/anon")
public class TestController extends BaseController{
	@Autowired
	private LoanTripProductService loanTripProductService;
	
	/**
	 * 查询所有有效的旅游产品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult findAllProduct(HttpServletRequest request) throws Exception {

		// 构建返回对象
		ApiResult result = new ApiResult();

		//result.setData(loanTripProductService.updateCustProductAndTicket("456"));

		return result;
	}
}
