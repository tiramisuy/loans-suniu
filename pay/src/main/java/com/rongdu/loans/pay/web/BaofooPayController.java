/**
 * Copyright &copy; 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.web;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.pay.op.ConfirmAuthPayOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.op.PreAuthPayOP;
import com.rongdu.loans.pay.service.BaofooAuthPayService;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.service.impl.BaofooAuthPayServiceImpl;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.PreAuthPayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 *宝付支付-支付结果回调
 * @author sunda
 * @version 2017-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/")
public class BaofooPayController extends BaseController {
    
	@Autowired
	private BaofooAuthPayService baofooPayService;
	
	@Autowired
	private RepayLogService repayLogService;
	
	@Autowired
	private BaofooWithdrawService baofooWithdrawService;
	
	private static List<String> ipWhiteList = null;
	
	@RequestMapping(value = "/partner/directBindCard")
	@ResponseBody
	public BindCardResultVO directBindCard(HttpServletRequest request,DirectBindCardOP param) throws Exception {
		checkIpAddress("宝付支付-直接绑卡",request);
		BindCardResultVO vo = baofooPayService.directBindCard(param);
		return vo;
	}
	
	@RequestMapping(value = "/partner/preAuthPay")
	@ResponseBody
	public PreAuthPayVO preAuthPay(HttpServletRequest request,PreAuthPayOP param) throws Exception {
		checkIpAddress("宝付支付-预支付",request);
		PreAuthPayVO vo = baofooPayService.preAuthPay(param);
		return vo;
	}
	
	@RequestMapping(value = "/partner/confirmAuthPay")
	@ResponseBody
	public ConfirmAuthPayVO confirmAuthPay(HttpServletRequest request,ConfirmAuthPayOP form) throws Exception {
		checkIpAddress("宝付支付-确认支付",request);
		ConfirmAuthPayVO vo = baofooPayService.confirmAuthPay(form);
		BaofooAuthPayServiceImpl service = SpringContextHolder.getBean("baofooAuthPayService");
		DirectBindCardOP param = new DirectBindCardOP();
//		param.setUserId("0196deb830fa40bfa290b2ddeca63665");
		param.setSource("1");
		param.setIpAddr("127.0.0.1");
//		RepayLogVO repayLog = repayLogService.get("04494c511c6b40de9278fd837e5daaf5");
//		baofooWithdrawService.withraw(repayLog);
		return vo;
	}
	
	/**
	 * 只能接受白名单IP的请求
	 * @param msg
	 * @param request
	 */
	private void checkIpAddress(String msg, HttpServletRequest request) {
		String ip = Servlets.getIpAddress(request);
		Servlets.printAllParameters(request);
		logger.info("{}：{}",msg,ip);
		if (!getIpWhiteList().contains(ip)) {
			logger.info("{}：非法的IP访问-{}",msg,ip);
			//throw new BizException("FOBIDDEN");
		}
	}

	/**
	 * 获取IP白名单
	 * 
	 * @return
	 */
	private static List<String> getIpWhiteList() {
		if (null == ipWhiteList) {
			String[] temp = StringUtils.split(BaofooConfig.baofoo_ip_whitelist, ',');
			ipWhiteList=  Arrays.asList(temp);
		}
		return ipWhiteList;
	}
	
}