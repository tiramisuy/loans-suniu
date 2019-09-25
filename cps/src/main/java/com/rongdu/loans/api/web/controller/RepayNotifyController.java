//package com.rongdu.loans.api.web.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.dubbo.common.utils.StringUtils;
//import com.rongdu.common.config.Global;
//import com.rongdu.common.mapper.JsonMapper;
//import com.rongdu.common.utils.DateUtils;
//import com.rongdu.common.web.BaseController;
//import com.rongdu.loans.api.common.Md5Utils;
//import com.rongdu.loans.api.web.option.RepayNotifyOP;
//import com.rongdu.loans.pay.service.WithholdService;
//
///**
// * Created by zcb on 2017/10/18.
// */
//@Controller
//@RequestMapping(value = "loan/repayNotify")
//public class RepayNotifyController extends BaseController {
//
//	@Autowired
//	private WithholdService withholdService;
//
//	@RequestMapping(value = "send", method = RequestMethod.POST)
//	@ResponseBody
//	public String contentTable(@Valid RepayNotifyOP notify, HttpServletRequest request, HttpServletResponse response) {
//		logger.info("还款通知：{}", JsonMapper.getInstance().toJson(notify));
//		String sign = request.getParameter("sign");
//		String ret = "OK";
//		if (StringUtils.isBlank(sign)) {
//			ret = "NOK";
//		}
//		boolean md5Flag = Md5Utils.checkStrMD5(sign, notify.getProductId() + String.valueOf(notify.getRepayAmt())
//				+ notify.getRepayTerm() + notify.getRepayDate(), Global.getConfig("toufuli.loan.key"));
//		if (!md5Flag) {
//			ret = "NOK";
//		}
//		if ("OK".equals(ret)) {
//			try {
//				withholdService.processP2pWithhold(notify.getProductId(), notify.getRepayAmt(), notify.getRepayTerm(),
//						notify.getRepayDate());
//			} catch (Exception e) {
//				ret = "NOK";
//			}
//		}
//		logger.info("还款通知响应：{}={}", notify.getProductId(), ret);
//		return ret;
//	}
//
//}
