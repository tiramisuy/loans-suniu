package com.rongdu.loans.api.web.controller;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.web.option.HanJSOpenAccountOP;
import com.rongdu.loans.api.web.option.HanJSPayNotiftOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.hanjs.op.HanJSUserOP;
import com.rongdu.loans.hanjs.util.MD5Util;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.RongPointCutService;
import com.rongdu.loans.loan.vo.PayLogVO;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "hjs/loanNotify")
public class HanJSNotifyController extends BaseController {

	@Autowired
	private CustUserService custUserService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private PayLogService payLogService;
	@Autowired
	private LoanApplyService loanApplyService;
    @Autowired
    private RongPointCutService rongPointCutService;

	/**
	 * @return String 返回类型
	 * @throws @Title:
	 *             accountOpenRetNotify
	 * @Description: 开户同步页面
	 */
	@RequestMapping(value = "/openSuccess")
	public String openSuccess(HttpServletRequest request, HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		logger.info("{}-{}:{}", "汉金所", "存管开户同步回跳,开户成功,手机号:", mobile);
		try {
			if (StringUtils.isNotBlank(mobile)) {
				CustUserVO userVO = custUserService.getCustUserByMobile(mobile);
				if (userVO != null) {
					userVO.setHjsAccountId(1);
					custUserService.updateCustUser(userVO);
					// 插入borrwinfo 更新loanapply
					String applyId = loanApplyService.getCurApplyIdByUserId(userVO.getId());
					if (StringUtils.isNotBlank(applyId)) {
						loanApplyService.updateHanJSPayChannel(applyId);
						// SLL汉金所存管开户成功标识
						JedisUtils.set(Global.HJS_OPEN3 + applyId, "lock", Global.THREE_DAY_CACHESECONDS);
						rongPointCutService.creatAccount(applyId, "绑卡成功", true);// SLL汉金所开户时，切面通知的切入点标记
					}
					// 清除用户信息缓存以便重新加载
					LoginUtils.cleanCustUserInfoCache(userVO.getId());
				}
			}
		} catch (Exception e) {
			logger.error("更新汉金所开户状态失败,mobile:" + mobile, e);
		}
		return "redirect:http://api.jubaoqiandai.com/#/successPage";
	}

	/**
	 * @return String 返回类型
	 * @throws @Title:
	 *             accountOpenRetNotify
	 * @Description: 开户同步页面
	 */
	@RequestMapping(value = "/openFail")
	public String openFail(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String mobile = request.getParameter("mobile");
		String msg = request.getParameter("msg");
		logger.info("{}-{}:{}-{}:{}", "汉金所", "存管开户同步回跳,开户失败,手机号", mobile, "失败原因", msg);
		if (StringUtils.isBlank(msg)) {
			msg = "开户失败";
		}
		try {
			if (StringUtils.isNotBlank(mobile)) {
				CustUserVO userVO = custUserService.getCustUserByMobile(mobile);
				if (userVO != null) {
					userVO.setHjsAccountId(0);
					custUserService.updateCustUser(userVO);
					// 清除用户信息缓存以便重新加载
					LoginUtils.cleanCustUserInfoCache(userVO.getId());
				}
			}
		} catch (Exception e) {
			logger.error("更新汉金所开户状态失败,mobile:" + mobile, e);
		}
		return "redirect:http://api.jubaoqiandai.com/#/errorPage?msg=" + URLEncoder.encode(msg, "utf-8");
	}

	/**
	 * @return String 返回类型
	 * @throws @Title:
	 *             accountOpenRetNotify
	 * @Description: 放款回调
	 */
	@RequestMapping(value = "/payment")
	@ResponseBody
	public String payment(HanJSPayNotiftOP op, HttpServletRequest request, HttpServletResponse response) {
		logger.info("放款通知：{},{}", JsonMapper.getInstance().toJson(op));
		String ret = "SUCCESS";
		if (StringUtils.isBlank(op.getSign())) {
			ret = "FAIL";
		}
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("key", Global.getConfig("hanjs.common.key"));
		reqMap.put("orderId", op.getOrderId());
		reqMap.put("datetime", op.getDatetime());
		reqMap.put("msg", op.getMsg());
		reqMap.put("code", op.getCode());
		reqMap.put("amount", op.getAmount());
		Boolean flag = MD5Util.checkSign(op.getSign(), reqMap);
		if (!flag) {
			logger.error("放款通知sign错误：{},{}", op.getOrderId(), op.getSign());
			ret = "FAIL";
		}
		if ("SUCCESS".equals(ret)) {
			String cacheKey = "hanjs_pay_callback_" + op.getOrderId() + "_" + op.getDatetime();
			if (JedisUtils.get(cacheKey) != null) {
				logger.info("放款通知回调结果：{},{}", op.getOrderId(), ret);
				return ret;
			}
			JedisUtils.set(cacheKey, "1", 60);
			try {
				if ("SUCCESS".equals(op.getCode())) {
					synchronized (HanJSNotifyController.class) {
						// 更改订单状态 生成还款计划 保存日志
						contractService.processHanJSLendPay(op.getOrderId(), DateUtils.parseDatetime(op.getDatetime()),
								op.getAmount());
						//rongPointCutService.sllLendPoint(op.getOrderId(), 1);
						rongPointCutService.lendPoint(op.getOrderId(), 1);// SLL汉金所存管放款时，切面通知的切入点标记
					}
				}
			} catch (Exception e) {
				logger.error("放款通知系统异常：{},{}", op.getOrderId(), e.getMessage());
				ret = "FAIL";
			}
		}
		logger.info("放款通知回调结果：{},{}", op.getOrderId(), ret);
		return ret;
	}

	/**
	 * @return String 返回类型
	 * @throws @Description:
	 *             提现同步页面
	 */
	@RequestMapping(value = "/withdrawResult")
	public String withdrawResult(HttpServletRequest request, HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		logger.info("{}-{}:{}", "汉金所", "存管账户提现同步回跳,mobile=", mobile);
		/*
		 * try { if (StringUtils.isNotBlank(mobile)) { CustUserVO user =
		 * custUserService.getCustUserByMobile(mobile); PayLogVO entity =
		 * payLogService.findWithdrawAmount(user.getId()); if (null != entity) {
		 * entity.setStatus(ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL.getValue().
		 * toString()); // 更新paylog payLogService.updatePayResult(entity); //
		 * 更新loanapply loanApplyService.updateApplyStatus(entity.getApplyId(),
		 * ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL.getValue()); } } } catch
		 * (Exception e) { logger.error("汉金所更新提现状态失败,mobile:" + mobile, e); }
		 */
		return "redirect:http://api.jubaoqiandai.com/#/successPage";
	}

	/**
	 * @return String 返回类型
	 * @throws @Description:
	 *             提现回调
	 */
	@RequestMapping(value = "/withdrawNotify")
	@ResponseBody
	public String withdrawNotify(HanJSPayNotiftOP op, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		logger.info("提现回调通知：{},{}", JsonMapper.getInstance().toJson(op));
		String ret = "SUCCESS";
		if (StringUtils.isBlank(op.getSign())) {
			ret = "FAIL";
		}
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("key", Global.getConfig("hanjs.common.key"));
		reqMap.put("orderId", op.getOrderId());
		reqMap.put("datetime", op.getDatetime());
		reqMap.put("msg", URLDecoder.decode(op.getMsg(), "utf-8"));
		reqMap.put("code", op.getCode());
		reqMap.put("amount", op.getAmount());
		Boolean flag = MD5Util.checkSign(op.getSign(), reqMap);
		if (!flag) {
			logger.error("提现回调通知sign错误：{},{}", op.getOrderId(), op.getSign());
			ret = "FAIL";
		}
		if ("SUCCESS".equals(ret)) {
			// String cacheKey = "hanjs_withdraw_callback_" + op.getOrderId() +
			// "_" + op.getDatetime();
			// if (JedisUtils.get(cacheKey) != null) {
			// logger.info("提现回调通知结果：{},{}", op.getOrderId(), ret);
			// return ret;
			// }
			// JedisUtils.set(cacheKey, "1", 60 * 30);
			try {
				if ("0000".equals(op.getCode())) {
					synchronized (HanJSNotifyController.class) {
						PayLogVO entity = payLogService.findWithdrawLogByApplyId(op.getOrderId());
						if (null != entity && XjdLifeCycle.LC_CASH_2 == Integer.parseInt(entity.getStatus())) {
							entity.setStatus(ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue().toString());
							// 更新paylog
							payLogService.updatePayResult(entity);
							// 更新loanapply
							loanApplyService.updateApplyStatus(entity.getApplyId(),
									ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue());
							rongPointCutService.lendPoint(op.getOrderId(), 2);// SLL汉金所存管提现时，切面通知的切入点标记
						}
					}
				}
				// 提现冲正
				else if ("0001".equals(op.getCode())) {
					synchronized (HanJSNotifyController.class) {
						PayLogVO entity = payLogService.findWithdrawLogByApplyId(op.getOrderId());
						if (null != entity) {
							entity.setStatus(ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue().toString());
							entity.setRemark("提现冲正");
							// 更新paylog
							payLogService.updatePayResult(entity);
							// 更新loanapply
							loanApplyService.updateApplyStatus(entity.getApplyId(),
									ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue());
						}
					}
				}
			} catch (Exception e) {
				logger.error("提现回调通知结果系统异常：{},{}", op.getOrderId(), e.getMessage());
				ret = "FAIL";
			}
		}
		logger.info("提现回调通知结果：{},{}", op.getOrderId(), ret);
		return ret;
	}

	/**
	 * 成功跳转页面
	 */
	@RequestMapping(value = "/retSuccessUrl")
	public String retSuccessUrl(HttpServletRequest request, HttpServletResponse response) {
		logger.info("{}-{}:{}", "汉金所", "重置密码跳转页面", JsonMapper.toJsonString(request.getParameterMap()));
		return "redirect:http://api.jubaoqiandai.com/#/successPage";
	}

	/**
	 * 测试汉金所url转发页面
	 */
	@RequestMapping(value = "/testSignUrl")
	public void testSignUrl(HttpServletRequest request, HttpServletResponse response) {
		String key = Global.getConfig("hanjs.common.key");
		// String url = Global.getConfig("hanjs.openAccount.url");
		String url = "http://iweb-stg.hanxinbank.com:28080/hjs/open/openAccount.do";

		String datetime = "201902251122" + RandomUtils.nextInt(1, 100);

		HanJSUserOP op = new HanJSUserOP();
		op.setGender("M");
		op.setMobile("159027628" + RandomUtils.nextInt(10, 99));
		op.setName("ceshi04" + RandomUtils.nextInt(1, 1000));
		// 获取sign
		op.setChannelId("jbqb");
		op.setDatetime(datetime);
		op.setSign_success_url("1234");
		op.setSign_fail_url("1234");
		Map<String, Object> map = BeanMapper.map(op, Map.class);
		map.put("key", key);
		String sign = MD5Util.getMd5Sign(map);
		System.out.println(sign);

		// 配置请求参数
		HanJSOpenAccountOP param = new HanJSOpenAccountOP();
		BeanMapper.copy(op, param);
		param.setSign(sign);
		Map<String, String> params = BeanMapper.map(param, Map.class);
		// String responseString = HttpUtils.getForJson(url, params);
		String responseString = RestTemplateUtils.getInstance().postForJson(url, params);
		System.out.println(url);
		System.out.println(params);
		JsonMapper.toJsonString(param);
		if (responseString != null) {
			System.out.println(responseString);
			try {
				// response.setCharacterEncoding("utf-8");
				// response.setContentType("text/html;charset=utf-8");
				response.setContentType("text/html;");
				PrintWriter out = response.getWriter();
				out.print(responseString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// out.print();
		// return "redirect:http://api.jubaoqiandai.com/#/successPage";
	}

}
