/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.web;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageOP;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.pay.common.RespInfo;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付限额Controller
 * @author sunda
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/pay/")
public class BankLimitController extends BaseController {
		
	/**
 	* 清空特定的缓存
 	* @author sunda
 	*/
	@RequestMapping(value = "clear-cache")
	@ResponseBody
	public RespInfo clearCache(HttpServletRequest request, HttpServletResponse response) {
		BankLimitUtils.clearCache();
		RespInfo respInfo = new RespInfo();
		
//		ZhimaService zhimaService = SpringContextHolder.getBean("zhimaService");
//		AuthorizeOP op  = new AuthorizeOP();
//		op.setIdNo("430104198601151945 ");
//		op.setName("张三");
//		op.setUserId("123456");
//		op.setApplyId("123456");
//		op.setIp("127.0.0.1");
//		op.setSource("1");
//		AuthorizeVO vo = zhimaService.authorize(op);
//		respInfo.put("data", vo);
		return respInfo;
	}
	
	/**
 	* 查询支付限额
 	* @author sunda
 	*/
	@RequestMapping(value = "bank-limit")
	@ResponseBody
	public RespInfo bankLimit(HttpServletRequest request, HttpServletResponse response) {
		RespInfo respInfo = new RespInfo();
		respInfo.put("data", BankLimitUtils.getBankLimits());
		return respInfo;
	}
}