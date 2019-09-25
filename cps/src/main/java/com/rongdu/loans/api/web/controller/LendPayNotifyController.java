package com.rongdu.loans.api.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.api.common.Md5Utils;
import com.rongdu.loans.api.web.option.LendPayNotifyOP;
import com.rongdu.loans.loan.service.ContractService;

/**
 * Created by zcb on 2017/10/18.
 */
@Controller
@RequestMapping(value = "loan/lendPayNotify")
public class LendPayNotifyController extends BaseController {

	@Autowired
	private ContractService contractService;

	@RequestMapping(value = "send", method = RequestMethod.POST)
	@ResponseBody
	public String contentTable(@Valid LendPayNotifyOP notify, HttpServletRequest request, HttpServletResponse response) {
		String sign = request.getParameter("sign");
		String notifyType = request.getParameter("type");
		logger.info("放款通知：{},{}", JsonMapper.getInstance().toJson(notify), notifyType);
		String ret = "OK";
		if (StringUtils.isBlank(sign)) {
			ret = "NOK";
		}
		boolean md5Flag = Md5Utils.checkStrMD5(sign, notify.getBatchLendPayDate() + notify.getProductId(),
				Global.getConfig("toufuli.loan.key"));
		if (!md5Flag) {
			logger.error("放款通知sign错误：{},{}", notify.getProductId(), sign);
			ret = "NOK";
		}
		if ("OK".equals(ret)) {
			String cacheKey = "tfl_pay_callback_" + notify.getProductId() + "_" + notifyType;
			if (JedisUtils.get(cacheKey) != null) {
				return ret;
			}
			JedisUtils.set(cacheKey, "1", 60 * 30);
			try {
				boolean payStatus = false;
				synchronized (LendPayNotifyController.class) {
					payStatus = contractService.process(notify.getProductId(),
							DateUtils.parseYYMM(notify.getBatchLendPayDate()), notifyType);
				}
				if (!payStatus) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("productId", notify.getProductId());
					params.put("batchLendPayDate", notify.getBatchLendPayDate());
					params.put("sign", sign);
					String responseStr = RestTemplateUtils.getInstance().postForJson(
							Global.getConfig("store_pay_notify"), params);
					logger.info("平台放款回调推送到门店系统：{},{},{}", notify.getProductId(), notifyType, responseStr);
				}
			} catch (Exception e) {
				logger.error("放款通知系统异常：{},{},{}", notify.getProductId(), notifyType, e.getMessage());
				ret = "NOK";
			}
		}
		logger.info("放款通知响应：{},{},{}", notify.getProductId(), notifyType, ret);
		return ret;
	}

	@RequestMapping(value = "getServFeeWithholdResult", method = RequestMethod.POST)
	@ResponseBody
	public String getServFeeWithholdResult(HttpServletRequest request, HttpServletResponse response) {
		String sign = request.getParameter("sign");
		String lid = request.getParameter("lid");
		logger.info("查询598代扣结果：lid={}", lid);
		String ret = "OK";
		if (StringUtils.isBlank(sign)) {
			ret = "NOK";
		}
		boolean md5Flag = Md5Utils.checkStrMD5(sign, lid, Global.getConfig("toufuli.loan.key"));
		if (!md5Flag) {
			logger.error("查询598代扣结果sign错误：{},{}", lid, sign);
			ret = "NOK";
		}
		if ("OK".equals(ret)) {
			try {
				int result = contractService.getServFeeWithholdResult(lid);
				ret = result == 1 ? "OK" : "NOK";
			} catch (Exception e) {
				logger.error("查询598代扣结果异常：{},{}", lid, e.getMessage());
				ret = "NOK";
			}
		}
		logger.info("查询598代扣结果响应：{}={}", lid, ret);
		return ret;
	}

	@RequestMapping(value = "test")
	@ResponseBody
	public String test(@Valid LendPayNotifyOP notify, HttpServletRequest request, HttpServletResponse response) {
		logger.info("放款通知测试：{}", JsonMapper.getInstance().toJson(notify));
		contractService.process(notify.getProductId(), DateUtils.parseYYMM(notify.getBatchLendPayDate()), "1");
		return "OK";
	}

}
