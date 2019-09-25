package com.rongdu.loans.loan.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.annotation.ExportCheck;
import com.rongdu.common.web.BaseController;

/**
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/export")
public class ExportCheckController extends BaseController {

	/*
	 * 导出检测
	 */
	@ResponseBody
	@RequestMapping(value = "check")
	@ExportCheck()
	public Object check(HttpServletRequest request) {
		return null;
	}

}
