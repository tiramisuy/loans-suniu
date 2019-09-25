/**
 * Copyright &copy; 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.web;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.baofoo.demo.base.TransContent;
import com.rongdu.loans.pay.baofoo.demo.base.response.TransRespBF0040002;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.WithdrawErrInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 宝付支付-付款
 * 
 * @author sunda
 * @version 2017-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/withdraw/")
public class BaofooWithdrawController extends BaseController {

	@Autowired
	private BaofooWithdrawService baofooWithdrawService;
	@Autowired
	private PayLogService payLogService;
	@Autowired
	private RepayLogService repayLogService;

	private static List<String> ipWhiteList = null;

	/**
	 * 重新付款的条件： 1、仅白名单IP可以调用 2、检查充值订单号origOrderNo 3、检查用户userId
	 * 4、通过宝付查证付款结果，确认该订单的确是付款失败 必须同时满足以上条件才能重新付款付款
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/re-withdraw")
	public String reWithdraw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ip = Servlets.getIpAddress(request);
		logger.info("宝付支付-代付-重新付款：{}", ip);
		if (!getIpWhiteList().contains(ip)) {
			logger.info("宝付支付-代付-重新付款：非法的IP访问-{}", ip);
			Servlets.printAllParameters(request);
			// Servlets.writeString(response,"FOBBIDEN");
			// return null;
		}
		// UserInfo user = findUser(request);
		String orderNo = request.getParameter("orderNo");
		String origOrderNo = request.getParameter("origOrderNo");
		String userId = request.getParameter("userId");

		if (StringUtils.isAnyBlank(orderNo, origOrderNo, userId)) {
			logger.info("宝付支付-代付-重新付款：参数不正确");
			Servlets.writeString(response, "FOBBIDEN");
			return null;
		}
		String lockCacheId = "RW_WITHDRAW_" + orderNo;
		Boolean lock = (Boolean) JedisUtils.getObject(lockCacheId);
		lock = lock == null ? false : true;
		if (lock) {
			logger.info("宝付支付-代付-重新付款：已经锁定订单，请稍后重试");
			// 锁定一分钟
			JedisUtils.setObject(lockCacheId, true, 1 * 60);
			Servlets.writeString(response, "已经锁定订单，请稍后重试");
			return null;
		}

		PayLogVO pw = payLogService.get(orderNo);
		if (pw == null) {
			RepayLogVO repayVO = repayLogService.get(origOrderNo);
			String succCode = ErrInfo.SUCCESS.getCode();
			if (repayVO != null && StringUtils.equals(succCode, repayVO.getStatus())
					&& !StringUtils.equals(succCode, repayVO.getPayStatus())) {
				// baofooWithdrawService.withraw(repayVO);
				Servlets.writeString(response, "SUCCESS");

			}
		} else {
			if (!origOrderNo.equals(pw.getOrigOrderNo())) {
				logger.info("宝付支付-代付-重新付款：订单信息校验未通过");
				Servlets.writeString(response, "订单信息校验未通过");
				return null;
			}
		}

		if (!userId.equals(pw.getUserId())) {
			logger.info("宝付支付-代付-重新付款：用户信息校验未通过");
			Servlets.writeString(response, "用户信息校验未通过");
			return null;
		}
		boolean isDelete = pw.getDel() == BaseEntity.DEL_DELETE;
		// 该笔订单尚未处理完成,才可重新付款
		Boolean isReWithdraw = baofooWithdrawService.isReWithdraw(orderNo, pw);
		logger.info("宝付支付-代付-重新付款：是否符合付款条件：{}，是否已经付款：{}，订单是否失效：", isReWithdraw, lock, isDelete);
		if (isReWithdraw && lock == false && !isDelete) {
			// baofooWithdrawService.reWithdraw(pw);
			// 锁定一个小时
			JedisUtils.setObject(lockCacheId, true, 60 * 60);
			Servlets.writeString(response, "SUCCESS");
			return null;
		}

		Servlets.writeString(response, "FAILURE");
		return null;
	}

	/**
	 * 获取IP白名单
	 * 
	 * @return
	 */
	private static List<String> getIpWhiteList() {
		if (null == ipWhiteList) {
			String[] temp = StringUtils.split(BaofooConfig.baofoo_ip_whitelist, ',');
			ipWhiteList = Arrays.asList(temp);
		}
		return ipWhiteList;
	}

}