package com.rongdu.loans.baiqishi.web;

import com.rongdu.common.web.BaseController;
import com.rongdu.loans.baiqishi.common.BaiqishiConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 芝麻信用回调
 * 
 * @author sunda
 * @version 2013-5-31
 */
@Controller
public class CallbackController extends BaseController {
	public static Logger logger = LoggerFactory
			.getLogger(CallbackController.class);

	@RequestMapping(value = "/anon/zhima/callback", name = "芝麻信用回调")
	public String callback(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = BaiqishiConfig.zhima_h5_callback_url;
		String queryString = request.getQueryString();
		if (0 != queryString.length()) {
			String linkOperator = url.contains("?") ? "&" : "?";
			url += linkOperator + queryString;
		}
		logger.debug("芝麻信用回调：{}", url);
		return "redirect:" + url;
	}

}
