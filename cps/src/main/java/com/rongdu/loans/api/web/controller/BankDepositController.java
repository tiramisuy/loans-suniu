package com.rongdu.loans.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.loans.api.web.option.BankDepositOP;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.sys.web.ApiResult;
/**
 * 
* @Description: 存管接口
* @author: RaoWenbiao
* @date 2018年9月10日
 */
@Controller
@RequestMapping(value = "bankDeposit/anon")
public class BankDepositController {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustUserService custUserService;

	/**
	 * 存管回调
	 */
	@RequestMapping(value = "/lanmao", name = "存管回调")
	@ResponseBody
	public ApiResult callback(@Valid BankDepositOP param, HttpServletRequest request) {
		ApiResult result = new ApiResult();
		CustUserVO custUserVO = new CustUserVO();
		
		if(!param.getStatus().equals("0000")){
			 result.setCode("400");
	         result.setMsg(param.getStatus().equals("1001")?"审核中":param.getStatus().equals("1002")?"审核退回":
	        	 param.getStatus().equals("1003")?"审核拒绝":param.getStatus().equals("1004")?"审核失效":param.getStatus());
	         return result;
		}

		CustUserVO custUser = custUserService.getCustUserByMobile(param.getMobile());
		
		if(custUser == null){
			 result.setCode("400");
	         result.setMsg("用户不存在");
	         return result;
		}
				
		custUserVO.setId(custUser.getId());
		custUserVO.setAccountId(param.getAccountId());

		int rtn = custUserService.updateCustUser(custUserVO);
		
		if(rtn == 0){
			 result.setCode("400");
	         result.setMsg("更新用户信息失败");
	         return result;
		}
		
		return result;
	}
}
