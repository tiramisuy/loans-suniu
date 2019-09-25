/**
 * Copyright &copy; 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.web;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.basic.service.AlarmService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.service.BaofooWithholdService;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *宝付支付-支付结果回调
 * @author sunda
 * @version 2017-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/")
public class BaofooCallbackController1 extends BaseController {

	@Autowired
	public BaofooWithholdService baofooWithholdService;
	@Autowired
	private CustUserService custUserService;

	/**
	 * 测试代扣
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/withhold/test.html")
	public String withrawReturnUrl(HttpServletRequest request,HttpServletResponse response) throws Exception {
		WithholdOP op = new WithholdOP();
//		alarmService.sendAlarmSms("13912345678","你还款了吗？");
		return null;
	}

	
}