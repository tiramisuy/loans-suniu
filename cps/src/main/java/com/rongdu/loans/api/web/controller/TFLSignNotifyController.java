package com.rongdu.loans.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.rongdu.common.config.Global;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.api.common.Md5Utils;
import com.rongdu.loans.cust.service.CustUserService;

/**
 * Created by zcb on 2017/10/18.
 */
@Controller
@RequestMapping(value = "auth")
public class TFLSignNotifyController extends BaseController {

	@Autowired
	CustUserService custUserService;

	@RequestMapping(value = "/signNotify", method = RequestMethod.POST)
	@ResponseBody
	public String signNotify(String uid, Integer result, HttpServletRequest request, HttpServletResponse response) {
		logger.info("签约通知：{},{}", uid, result);
		String sign = request.getParameter("sign");
		String ret = "OK";
		if (StringUtils.isBlank(sign) || StringUtils.isBlank(uid) || result == null) {
			ret = "NOK";
		}
		boolean md5Flag = Md5Utils.checkStrMD5(sign, result + uid, Global.getConfig("toufuli.loan.key"));
		if (!md5Flag) {
			logger.error("签约通知sign错误：{},{}", uid, sign);
			ret = "NOK";
		}
		if ("OK".equals(ret)) {
			try {
				synchronized (TFLSignNotifyController.class) {
					if (result.intValue() == 1) {
						// custUserService.saveDoSignOperator(uid);
					}
				}
			} catch (Exception e) {
				logger.error("签约通知系统异常：{},{}", uid, e.getMessage());
				ret = "NOK";
			}
		}
		logger.info("签约通知响应：{}={}", uid, ret);
		return ret;
	}

	@RequestMapping(value = "/depositNotify", method = RequestMethod.POST)
	@ResponseBody
	public String depositNotify(String uid, Integer result, Integer type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("投复利懒猫开户通知：uid={},result={},type={}", uid, result, type);
		String sign = request.getParameter("sign");
		String ret = "OK";
		if (StringUtils.isBlank(sign) || StringUtils.isBlank(uid) || result == null) {
			ret = "NOK";
		}
		boolean md5Flag = Md5Utils.checkStrMD5(sign, result + "" + type + "" + uid,
				Global.getConfig("toufuli.loan.key"));
		if (!md5Flag) {
			logger.error("投复利开户通知sign错误：{},{}", uid, sign);
			ret = "NOK";
		}
		if ("OK".equals(ret)) {
			try {
				synchronized (TFLSignNotifyController.class) {
					if (result.intValue() == 1) {
						custUserService.saveDoDepositOperator(uid);
					}
				}
			} catch (Exception e) {
				logger.error("投复利开户通知系统异常：{},{}", uid, e.getMessage());
				ret = "NOK";
			}
		}
		logger.info("投复利开户通知响应：{}={}", uid, ret);
		return ret;
	}

	// @RequestMapping(value = "/sign")
	// @ResponseBody
	// public String contentTable(String uid, HttpServletRequest request,
	// HttpServletResponse response) {
	// custUserService.saveDoSignOperator(uid);
	// return "OK";
	// }
}
