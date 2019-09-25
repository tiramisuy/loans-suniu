package com.rongdu.loans.api.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.loan.option.LoanTripProductDetailOP;
import com.rongdu.loans.loan.service.LoanTripProductService;
import com.rongdu.loans.loan.vo.LoanTripProductDetailVO;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * 
* @Description:  旅游分期产品
* @author: 饶文彪
* @date 2018年7月12日
 */
@Controller
@RequestMapping(value = "${adminPath}/trip/product")
public class TripProductController extends BaseController {

	@Autowired
	private LoanTripProductService loanTripProductService;

	/**
	 * 查询所有有效的旅游产品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAllProduct", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult findAllProduct(HttpServletRequest request) throws Exception {

		// 构建返回对象
		ApiResult result = new ApiResult();

		result.setData(loanTripProductService.findAllProduct());

		return result;
	}

	/**
	 * 
	* @Title: findCustProduct
	* @Description: 询用户的旅游产品
	* @param op
	* @param request
	* @throws
	 */
	@RequestMapping(value = "/findCustProduct", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult findCustProduct(LoanTripProductDetailOP op, HttpServletRequest request) throws Exception {

		// 构建返回对象
		ApiResult result = new ApiResult();
		
		String userId = request.getHeader("userId");
		op.setCustId(userId);
		
		//loanTripProductService.saveCustProduct(op);
		
		result.setData(loanTripProductService.findCustProduct(op));

		return result;
	}

	/**
	 * 
	* @Title: findCustProduct
	* @Description: 询借款的旅游产品
	* @param op
	* @param request
	* @throws
	 */
	@RequestMapping(value = "/findApplyProduct", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult findApplyProduct(LoanTripProductDetailOP op, HttpServletRequest request) throws Exception {

		// 构建返回对象
		ApiResult result = new ApiResult();
		// 参数验证结果判断
		if (StringUtils.isBlank(op.getApplyId())) {
			logger.error("借款id不能为空！");
			return result.set(ErrInfo.BAD_REQUEST);
		}
		
		op.setCustId(request.getHeader("userId"));
						

		List<LoanTripProductDetailVO> list = loanTripProductService.findCustProduct(op);
		result.setData(list.size() == 0 ? null : list.get(0));

		return result;
	}

}
