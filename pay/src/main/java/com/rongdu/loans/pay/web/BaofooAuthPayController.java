/**
 * Copyright &copy; 2015-2017 聚宝钱包 All rights reserved.
 *//*
package com.rongdu.loans.pay.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.exception.BizException;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.pay.common.RespInfo;
import com.rongdu.loans.pay.service.BaofooAuthPayService;
import com.rongdu.loans.pay.service.PayOrderService;
import com.rabbitmq.http.client.domain.UserInfo;
*//**
 *宝付支付-认证支付
 * @author sunda
 * @version 2017-02-27
 *//*
@Controller
@RequestMapping(value = "${adminPath}/auth-pay/")
public class BaofooAuthPayController extends BaseController {
	
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private BaofooAuthPayService authPayService;
	
	*//**
 	* 访问网关支付demo
 	* @author sunda
 	*//*
	@RequestMapping(value = "demo")
	public String demo() {
		return "modules/pay/pc-pay-demo";
	}
	
	*//**
 	* 11-预绑卡类交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "pre-bind-card")
	@ResponseBody
	public RespInfo preBindCard(HttpServletRequest request, HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		String ipAddr = Servlets.getIpAddress(request);
		UserInfo user = findUser(request);
		if (StringUtils.isBlank(channel)) {
			throw new BizException("渠道代码不能为空");
		}
		if (StringUtils.isBlank(mobile)) {
			throw new BizException("手机号码不能为空");
		}
		
		RespInfo resp = authPayService.preBindCard(user, ipAddr, channel ,mobile);
		//包装错误信息
		if ("BF00101".equals(resp.getCode())) {
			resp.setMsg("预留手机号与银行卡信息、身份信息不匹配");
		}
		return resp;
	}

	*//**
 	* 12-确认绑卡交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "confirm-bind-card")
	@ResponseBody
	public RespInfo confirmBindCard(HttpServletRequest request, HttpServletResponse response) {
		String smsCode = request.getParameter("smsCode");
		String orderNo = request.getParameter("orderNo");
		UserInfo user = findUser(request);
		if (StringUtils.isBlank(smsCode)) {
			throw new BizException("短信验证码不能为空");
		}
		if (StringUtils.isBlank(orderNo)) {
			throw new BizException("交易订单号不能为空");
		}
		return authPayService.confirmBindCard(user,smsCode, orderNo);
	}
	
	*//**
 	* 01-直接绑卡交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "direct-bind-card")
	@ResponseBody
	public RespInfo directBindCard(HttpServletRequest request, HttpServletResponse response) {
		String ipAddr = Servlets.getIpAddress(request);
		String channel = request.getParameter("channel");
//		UserInfo user = findUser(request,false);
//		String mobile =  user.getPhonenumber();
//		String accNo =  user.getBankcardno1();
//		if (StringUtils.isBlank(mobile)) {
//			throw new BizException("手机号码不能为空");
//		}
//		if (StringUtils.isBlank(accNo)) {
//			throw new BizException("银行卡号不能为空");
//		}	
//		return authPayService.directBindCard(user, ipAddr, channel);
		return new RespInfo();
	}
	
	*//**
 	* 02-解除绑定关系交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "cancel-bind")
	@ResponseBody
	public RespInfo cancelBind(HttpServletRequest request, HttpServletResponse response) {
		String bindId = request.getParameter("bindId");
		if (StringUtils.isBlank(bindId)) {
			throw new BizException("绑定关系号不能为空");
		}
		UserInfo user  = findUser(request);
		return authPayService.cancelBind(user,bindId);
	}
	
	
	*//**
 	* 03-查询绑定关系交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "query-bind")
	@ResponseBody
	public RespInfo queryBind(HttpServletRequest request, HttpServletResponse response) {
		String accNo = request.getParameter("accNo");
		if (StringUtils.isBlank(accNo)) {
			throw new BizException("银行卡号不能为空");
		}	
		return authPayService.queryBind(accNo);
	}
	
	*//**
 	* 15-认证支付类预支付交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "pre-auth-pay")
	@ResponseBody
	public RespInfo preAuthPay(HttpServletRequest request, HttpServletResponse response) {
		String channel = request.getParameter("channel");
		String txnAmt = request.getParameter("txnAmt");
		UserInfo user = findUser(request,false);
		if (StringUtils.isBlank(user.getBindid())) {
			throw new BizException("该用户尚未绑定银行卡");
		}
		if (StringUtils.isBlank(channel)) {
			throw new BizException("渠道不能为空");
		}
		if (StringUtils.isBlank(txnAmt)) {
			throw new BizException("充值金额不能为空");
		}	
		String ipAddr = Servlets.getIpAddress(request);
		return authPayService.preAuthPay(user,channel, txnAmt, ipAddr);
	}
	
	*//**
 	* 16-认证支付类支付确认交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "confirm-auth-pay")
	@ResponseBody
	public RespInfo confirmAuthPay(HttpServletRequest request, HttpServletResponse response) {
		String smsCode = request.getParameter("smsCode");
		String payOrderNo = request.getParameter("payOrderNo");
		if (StringUtils.isBlank(smsCode)) {
			throw new BizException("短信验证码不能为空");
		}
		if (StringUtils.isBlank(payOrderNo)) {
			throw new BizException("业务流水号不能为空");
		}	
		return authPayService.confirmAuthPay(smsCode, payOrderNo);
	}
	
	*//**
 	* 06/31-交易状态查询交易
 	* @author sunda
 	*//*
	@RequestMapping(value = "query-order-status")
	@ResponseBody
	public RespInfo queryOrderStatus(HttpServletRequest request, HttpServletResponse response) {
		String orderNo = request.getParameter("orderNo");
		if (StringUtils.isBlank(orderNo)) {
			throw new BizException("交易订单号不能为空");
		}
		return authPayService.queryAuthPayStatus(orderNo);
	}
	
	
}*/