package com.rongdu.loans.api.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.borrow.option.HelpApplyOP;
import com.rongdu.loans.borrow.service.HelpApplyService;
import com.rongdu.loans.sys.web.ApiResult;

/**  
* @Title: BorrowHelpApplyController.java  
* @Package com.rongdu.loans.api.web  
* @Description: 助贷服务 
* @author: yuanxianchu  
* @date 2018年8月29日  
* @version V1.0  
*/
@Controller
@RequestMapping(value = "${adminPath}/anon/borrowHelp")
public class BorrowHelpApplyController extends BaseController{
	
	@Autowired
	private HelpApplyService helpApplyService;
	
	
	@RequestMapping(value = "/apply",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult borrowHelpApply(@Valid HelpApplyOP op, Errors errors, HttpServletRequest request) {
		if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
		ApiResult apiResult = new ApiResult();
		String ip = Servlets.getIpAddress(request);
		op.setIp(ip);
		try {
			String result = helpApplyService.borrowHelpApply(op);
			apiResult.setData(result);
			if ("ERROR".equals(result)) {
				apiResult.setCode(ErrInfo.ERROR.getCode());
				apiResult.setMsg(ErrInfo.ERROR.getMsg());
			}
		} catch (Exception e) {
			logger.error("助贷服务申请异常", e);
			apiResult.setCode(ErrInfo.ERROR.getCode());
			apiResult.setMsg(e.getMessage());
		}
		return apiResult;
	}

}
