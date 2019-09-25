package com.rongdu.loans.api.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.rongdu.common.config.Global;
import com.rongdu.common.security.SignUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.MD5Util;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.option.rong360Model.*;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJCallbackResp;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJReportReq;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJResp;
import com.rongdu.loans.loan.service.ApplyTripartiteRong360Service;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.RongService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.mq.MessageProductorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 2018/5/22.
 */
@Slf4j
@Controller
@RequestMapping(value = "rong360")
public class Rong360Controller {

	@Autowired
	private MessageProductorService messageProductorService;
	@Autowired
	private RongService rongService;
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private ApplyTripartiteRong360Service applyTripartiteRong360Service;

	private static final long TIME = 15;// 时间段，单位秒
	private static final long COUNT = 5;// 允许访问的次数
	private static long firstTime = 0;
	private static long accessCount = 0;

	private static final long TIME_2 = 15;// 时间段，单位秒
	private static final long COUNT_2 = 45;// 允许访问的次数
	private static long firstTime_2 = 0;
	private static long accessCount_2 = 0;

	private static final long TIME_3 = 15;// 时间段，单位秒
	private static final long COUNT_3 = 45;// 允许访问的次数
	private static long firstTime_3 = 0;
	private static long accessCount_3 = 0;

	private static synchronized Rong360Resp accessLock() {// 并发控制
		if (System.currentTimeMillis() - firstTime <= TIME * 1000L) {
			if (accessCount < COUNT) {
				accessCount++;
			} else {
				Rong360Resp resp = new Rong360Resp();
				resp.setCode(Rong360Resp.FAILURE);
				resp.setMsg("系统繁忙，请稍后重试");
				return resp;
			}
		} else {
			firstTime = System.currentTimeMillis();
			accessCount = 1;
		}
		return null;
	}

	private static synchronized Rong360Resp accessLock_2() {// 并发控制
		if (System.currentTimeMillis() - firstTime_2 <= TIME_2 * 1000L) {
			if (accessCount_2 < COUNT_2) {
				accessCount_2++;
			} else {
				Rong360Resp resp = new Rong360Resp();
				resp.setCode(Rong360Resp.FAILURE);
				resp.setMsg("系统繁忙，请稍后重试");
				return resp;
			}
		} else {
			firstTime_2 = System.currentTimeMillis();
			accessCount_2 = 1;
		}
		return null;
	}

	private static synchronized Rong360Resp accessLock_3() {// 并发控制
		if (System.currentTimeMillis() - firstTime_3 <= TIME_3 * 1000L) {
			if (accessCount_3 < COUNT_3) {
				accessCount_3++;
			} else {
				Rong360Resp resp = new Rong360Resp();
				resp.setCode(Rong360Resp.FAILURE);
				resp.setMsg("系统繁忙，请稍后重试");
				return resp;
			}
		} else {
			firstTime_3 = System.currentTimeMillis();
			accessCount_3 = 1;
		}
		return null;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "pushBaseInfo", method = RequestMethod.POST)
	public Rong360Resp pushBaseInfo(@RequestBody Rong360Req req) {
		log.info("融360开始推送基础数据");
		Rong360Resp resp = new Rong360Resp();
		String biz_data = req.getBiz_data();
		log.info("==========融360基础数据==========");

		// long a = System.currentTimeMillis();
		// File file = new File("/tmp/" + String.valueOf(a));
		// try {
		// file.createNewFile();
		// BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		// bw.write(biz_data);
		// bw.flush();
		// bw.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		OrderBaseInfo orderBaseInfo = JSONObject.parseObject(biz_data, OrderBaseInfo.class);
		String userPhone = null;
		try {
			userPhone = orderBaseInfo.getOrderinfo().getUserMobile().trim();
			String mobile = userPhone;
			String idNo = orderBaseInfo.getApplydetail().getUserId().trim();
			String mobileAndIdNo = mobile + idNo;
			String md5 = MD5Util.string2MD5(mobileAndIdNo).toUpperCase();
			JedisUtils.set("RONG:APPLY_LOCK_"+ md5, "lock", Global.ONE_DAY_CACHESECONDS);
		} catch (Exception e) {
			log.info("==========融360基础数据pushBaseInfo异常==========");
		}
		if (userPhone == null || userPhone.length() > 11) {
			resp.setCode("102");
			resp.setMsg("数据缺失");
			return resp;
		}
		messageProductorService.sendRongBaseInfoQuene(orderBaseInfo);
		JedisUtils.set("ORDER:Locked_" + orderBaseInfo.getApplydetail().getPhoneNumberHouse(), "", 60 * 60 * 4);
		resp.setCode("200");
		resp.setMsg("成功");
		return resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "pushAdditionalInfo", method = RequestMethod.POST)
	public Rong360Resp pushAdditionalInfo(@RequestBody Rong360Req req) {
		log.info("融360开始推送补充数据");
		Rong360Resp resp = new Rong360Resp();
		String biz_data = req.getBiz_data();
		log.info("==========融360补充数据==========");
		OrderAppendInfo orderAppendInfo = JSONObject.parseObject(biz_data, OrderAppendInfo.class);
		if (orderAppendInfo == null) {
			resp.setCode("102");
			resp.setMsg("数据缺失");
			return resp;
		}
		messageProductorService.sendRongAdditionalInfoQuene(orderAppendInfo);
		resp.setCode("200");
		resp.setMsg("成功");
		return resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "bindCard", method = RequestMethod.POST)
	public Rong360Resp bindCard(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		BindCardOP bindCardOP = JSONObject.parseObject(biz_data, BindCardOP.class);
		Rong360Resp rong360Resp = rongService.bindCard(bindCardOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "confirmBindCard", method = RequestMethod.POST)
	public Rong360Resp confirmBindCard(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		BindCardOP op = JSONObject.parseObject(biz_data, BindCardOP.class);
		// 确认绑卡
		Rong360Resp rong360Resp = rongService.confirmBindCard(op);
		if (!"200".equals(rong360Resp.getCode())) {
			return rong360Resp;
		}
		String orderNo = op.getOrderNo();
		String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
		if (StringUtils.isBlank(applyId)) {
			rong360Resp.setCode(Rong360Resp.FAILURE);
			rong360Resp.setMsg("数据异常，用户工单尚未生成！");
			log.error("【融360-绑卡开户接口】异常-用户工单尚未生成！orderNo={}", orderNo);
			return rong360Resp;
		}
		ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
		if (!WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(applyAllotVO.getPayChannel())) {
			// 放款渠道不是口袋存管，不去开户
			return rong360Resp;
		}
		// 绑卡成功，请求口袋存管，返回开户页
		try {
			CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
			if (custUserVO == null) {
				rong360Resp.setCode(Rong360Resp.FAILURE);
				rong360Resp.setMsg("数据异常，用户尚未注册！");
				log.error("【融360-绑卡开户接口】异常-用户尚未注册orderNo={}", orderNo);
				return rong360Resp;
			}

			// 模拟用户登陆
			Map<String, String> headerMap = LoginUtils.loginMock(custUserVO);
			Map<String, String> params = new HashMap<>();
			params.put("userId", headerMap.get("userId"));
			params.put("cityId", "1");
			params.put("email", "1");
			params.put("source", "4");// 来源api

			// api接口签名
			Map<String, Object> signMap = JSONObject.parseObject(JSONObject.toJSONString(params),
					new TypeReference<Map<String, Object>>() {
					});
			String openReturnUrl = URLEncoder.encode(op.getReturnUrl(), "UTF-8");
			signMap.put("appKey", headerMap.remove("appKey"));
			signMap.put("orderNo", orderNo);
			signMap.put("openReturnUrl", openReturnUrl);
			headerMap.put("sign", SignUtils.createSign(signMap, true));

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("orderNo", op.getOrderNo());
			queryParams.add("openReturnUrl", openReturnUrl);
			// String url =
			// UriComponentsBuilder.newInstance().scheme("http").host("127.0.0.1").port(8080).path("/api").path(
			// "/api").path("/loan").path("/creatJxbAccount").queryParams(queryParams).build().toString();

			String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/api")
					.path("/loan").path("/creatJxbAccount").queryParams(queryParams).build().toString();

			// 请求api开户接口
			CreateAccountVO vo = rongService.createAccount(url, params, headerMap);
			rong360Resp.setMsg("SUCCESS");
			rong360Resp.setData(vo);
			if (vo == null || StringUtils.isBlank(vo.getBindCardUrl())) {
				rong360Resp.setCode(Rong360Resp.FAILURE);
				rong360Resp.setMsg("系统异常");
			}
		} catch (Exception e) {
			rong360Resp.setCode(Rong360Resp.FAILURE);
			rong360Resp.setMsg("系统异常");
			log.error("【融360-绑卡开户接口】异常orderNo={}", orderNo, e);
		}
		log.debug("【融360-绑卡开户接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(rong360Resp));
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "calculation", method = RequestMethod.POST)
	public Rong360Resp calculation(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		Calculation calculation = JSONObject.parseObject(biz_data, Calculation.class);
		Rong360Resp rong360Resp = rongService.calculation(calculation);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "getApproval", method = RequestMethod.POST)
	public Rong360Resp getApproval(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		GetOP getOP = JSONObject.parseObject(biz_data, GetOP.class);

		Rong360Resp lockResp = accessLock();
		if (lockResp != null) {
			log.info("访问频率限制--->融360--->拉取审批状态--->{}", getOP.getOrderNo());
			return lockResp;
		}

		Rong360Resp rong360Resp = rongService.getApproval(getOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "getRepaymentPlan", method = RequestMethod.POST)
	public Rong360Resp getRepaymentPlan(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		GetOP getOP = JSONObject.parseObject(biz_data, GetOP.class);

		Rong360Resp lockResp = accessLock();
		if (lockResp != null) {
			log.info("访问频率限制--->融360--->拉取还款计划--->{}", getOP.getOrderNo());
			return lockResp;
		}

		Rong360Resp rong360Resp = rongService.getRepaymentPlan(getOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "getOrderStatus", method = RequestMethod.POST)
	public Rong360Resp getOrderStatus(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		GetOP getOP = JSONObject.parseObject(biz_data, GetOP.class);

		Rong360Resp lockResp = accessLock_3();
		if (lockResp != null) {
			log.info("访问频率限制--->融360--->拉取订单状态--->{}", getOP.getOrderNo());
			return lockResp;
		}

		Rong360Resp rong360Resp = rongService.getOrderStatus(getOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "confirmLoan", method = RequestMethod.POST)
	public Rong360Resp confirmLoan(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		ApproveOP approveOP = JSONObject.parseObject(biz_data, ApproveOP.class);
		Rong360Resp rong360Resp = rongService.confirmLoan(approveOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "getRepaymentDetails", method = RequestMethod.POST)
	public Rong360Resp getRepaymentDetails(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		RepaymentDetailsOP repaymentDetailsOP = JSONObject.parseObject(biz_data, RepaymentDetailsOP.class);
		Rong360Resp rong360Resp = rongService.getRepaymentDetails(repaymentDetailsOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "applyRepay", method = RequestMethod.POST)
	public Rong360Resp applyRepay(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		ApplyRepayOP applyRepayOP = JSONObject.parseObject(biz_data, ApplyRepayOP.class);
		Rong360Resp rong360Resp = rongService.applyRepay(applyRepayOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "delayDetails", method = RequestMethod.POST)
	public Rong360Resp delayDetails(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		DelayDetailsOP delayDetailsOP = JSONObject.parseObject(biz_data, DelayDetailsOP.class);
		Rong360Resp rong360Resp = rongService.delayDetails(delayDetailsOP);
		return rong360Resp;
	}

	// @CheckSign
	@ResponseBody
	@RequestMapping(value = "applyDelay", method = RequestMethod.POST)
	public Rong360Resp applyDelay(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		ApplyDelayOP applyRepayOP = JSONObject.parseObject(biz_data, ApplyDelayOP.class);
		Rong360Resp rong360Resp = rongService.applyDelay(applyRepayOP);
		return rong360Resp;
	}

	@ResponseBody
	// @CheckSign
	@RequestMapping(value = "getBindBankCard", method = RequestMethod.POST)
	public Rong360Resp getBindBankCard(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		GetCardOP getCardOP = JSONObject.parseObject(biz_data, GetCardOP.class);
		Rong360Resp rong360Resp = rongService.getBindBankCard(getCardOP);
		return rong360Resp;
	}

	@ResponseBody
	// @CheckSign
	@RequestMapping(value = "getContract", method = RequestMethod.POST)
	public Rong360Resp getContract(@RequestBody Rong360Req req) {
		String biz_data = req.getBiz_data();
		ContractOP contractOP = JSONObject.parseObject(biz_data, ContractOP.class);
		Rong360Resp rong360Resp = rongService.getContract(contractOP);
		return rong360Resp;
	}

	@ResponseBody
	// @CheckSign
	@RequestMapping(value = "isUserAccept", method = RequestMethod.POST)
	public Rong360Resp isUserAccept(@RequestBody Rong360Req req) {
		Rong360Resp lockResp = accessLock_2();
		if (lockResp != null) {
			log.info("访问频率限制--->融360--->isUserAccept");
			return lockResp;
		}
		String biz_data = req.getBiz_data();
		AcceptOP acceptOP = JSONObject.parseObject(biz_data, AcceptOP.class);
		Rong360Resp rong360Resp = rongService.isUserAccept(acceptOP);
		return rong360Resp;
	}

	@ResponseBody
	@RequestMapping(value = "/tianJiReportCallback", method = RequestMethod.POST)
	public RongTJCallbackResp tianJiReportCallback(RongTJReportReq req) {
		log.info("------------接收【融天机运营商报告异步回调】state={},searchId={}", req.getState(), req.getSearch_id());
		RongTJCallbackResp resp = new RongTJCallbackResp();
		resp.setCode(200);
		resp.setMsg("success");
		/*
		 * if (null == req || StringUtils.isBlank(req.getOut_unique_id()) ||
		 * StringUtils.isBlank(req.getSearch_id()) ||
		 * StringUtils.isBlank(req.getState())) { resp.setCode("104");
		 * resp.setMsg("数据不完整"); return resp; } String cacheKey =
		 * "rong_tj_report_"+req.getSearch_id(); Map<String, String> map =
		 * JedisUtils.getMap(cacheKey); //缓存过期异常 if (map == null ||
		 * map.isEmpty()) { resp.setCode("103"); resp.setMsg("系统异常");
		 * log.warn("系统内部异常，cacheKey={}已过期",cacheKey); return resp; }
		 * req.setOrderNo(map.get("orderNo"));
		 * req.setApplyId(map.get("applyId")); req.setUserId(map.get("userId"));
		 * return resp;
		 */
		String cacheKey = "rong_tj_report_" + req.getSearch_id();
		Map<String, String> map = JedisUtils.getMap(cacheKey);
		// 缓存过期异常
		if (map == null || map.isEmpty()) {
			resp.setCode(200);
			resp.setMsg("success");
			log.warn("重复回调，cacheKey={}已过期", cacheKey);
			return resp;
		}
		String searchId = map.get("searchId");
		String orderNo = map.get("orderNo");
		String applyId = map.get("applyId");
		String userId = map.get("userId");
		String lockKey = "rong_tj_report_savelock_" + orderNo;
		String requestId = String.valueOf(System.nanoTime());// 请求标识
		// 根据orderNo防并发加锁
		boolean lock = JedisUtils.setLock(lockKey, requestId, 60 * 6);
		if (!lock) {
			return resp;
		}
		try {
			// 处理运营商报告，返回true流程继续
			boolean result = rongService.rongReportDetail(orderNo, searchId, userId, applyId, req.getState());
			if (result) {
				JedisUtils.del(cacheKey);
			} else {
				resp.setCode(102);
				resp.setMsg("未知异常");
			}
		} finally {
			// 解除orderNo并发锁
			JedisUtils.releaseLock(lockKey, requestId);
		}
		return resp;
	}

	@RequestMapping(value = "/testTianJiReport")
	@ResponseBody
	public Rong360Resp testTianJiReport(String userId, String applyId, String orderNo, String type, String version)
			throws Exception {
		Rong360Resp resp = new Rong360Resp();
		String notifyUrl = "http://l.viphk.ngrok.org/cps/rong360/tianJiReportCallback";
		RongTJResp crawlGenerateReport = rongService.crawlGenerateReport(orderNo, type, notifyUrl, version);
		String searchId = crawlGenerateReport.getTianjiApiTaojinyunreportGeneratereportResponse().getSearchId();
		if (StringUtils.isBlank(searchId)) {
			log.warn("融天机运营商报告生成异常，查询ID为空！！！orderNo={}", orderNo);
			resp.setCode("500");
			resp.setMsg("fail");
			return resp;
		}
		String cacheKey = "rong_tj_report_" + searchId;
		Map<String, String> map = new HashMap<>();
		map.put("searchId", searchId);
		map.put("orderNo", orderNo);
		map.put("applyId", applyId);
		map.put("userId", userId);
		JedisUtils.setMap(cacheKey, map, 60 * 60);
		resp.setCode("200");
		resp.setMsg("success");
		resp.setData(crawlGenerateReport);
		// log.info("------------【tianJiGeneratereport】crawlGenerateReport={}",crawlGenerateReport);
		return resp;
	}

	@RequestMapping(value = "/resetImage")
	@ResponseBody
	public void resetImage(String orderNo, String key) throws Exception {
		if (!"123qweQWE+".equals(key)) {
			return;
		}
		rongService.resetImage(orderNo);
	}

	@RequestMapping(value = "/handPush")
	@ResponseBody
	public void handPush(String start, String end, int size) throws Exception {
		rongService.handPush(start, end, size);

	}

	@RequestMapping(value = "/handPush1")
	@ResponseBody
	public void handPush1(String applyid) throws Exception {
		rongService.handPush1(applyid);
	}

	@ResponseBody
	@RequestMapping(value = "confirmLoanHand", method = RequestMethod.POST)
	public Rong360Resp confirmLoan(String OrderNo, String key) {
		if (!"123qweQWE".equals(key)) {
			return null;
		}
		ApproveOP approveOP = new ApproveOP();
		approveOP.setOrderNo(OrderNo);
		Rong360Resp rong360Resp = rongService.confirmLoan(approveOP);
		return rong360Resp;
	}

	@RequestMapping(value = "/handleData")
	@ResponseBody
	public boolean handleData(String key) throws Exception {
		if (!"123qweQWE".equals(key)) {
			return false;
		}
		rongService.HandleData();
		return true;
	}

	@RequestMapping(value = "/handRefuse")
	@ResponseBody
	public String handRefuse(String key, String orderNo) throws Exception {
		if (!"123qweQWE".equals(key)) {
			return "密码错误";
		}
		String result = rongService.handRefuse(orderNo);
		return result;
	}

	/**
	 * 存管开户
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	 * public Rong360Resp createAccount(@RequestBody Rong360Req req) {
	 * Rong360Resp rong360Resp = new Rong360Resp(); String orderNo = "";
	 * 
	 * try { // 入参 String biz_data = req.getBiz_data(); CreateAccountOP op =
	 * JSONObject.parseObject(biz_data, CreateAccountOP.class); orderNo =
	 * op.getOrderNo();
	 * 
	 * String applyId =
	 * applyTripartiteRong360Service.getApplyIdByThirdId(orderNo); if
	 * (StringUtils.isBlank(applyId)) {
	 * rong360Resp.setCode(Rong360Resp.FAILURE);
	 * rong360Resp.setMsg("数据异常，用户工单尚未生成！");
	 * log.error("【融360-开户执行接口】异常-用户工单尚未生成！orderNo={}", orderNo); return
	 * rong360Resp; } ApplyAllotVO applyAllotVO =
	 * loanApplyService.getApplyById(applyId); CustUserVO custUserVO =
	 * custUserService.getCustUserByMobile(applyAllotVO.getMobile()); if
	 * (custUserVO == null) { rong360Resp.setCode(Rong360Resp.FAILURE);
	 * rong360Resp.setMsg("数据异常，用户尚未注册！");
	 * log.error("【融360-开户执行接口】异常-用户尚未注册orderNo={}", orderNo); return
	 * rong360Resp; }
	 * 
	 * // 模拟用户登陆 Map<String, String> headerMap =
	 * LoginUtils.loginMock(custUserVO); Map<String, String> params = new
	 * HashMap<>(); params.put("userId", headerMap.get("userId"));
	 * params.put("cityId", "1"); params.put("email", "1"); params.put("source",
	 * "4");// 来源api
	 * 
	 * // api接口签名 Map<String, Object> signMap =
	 * JSONObject.parseObject(JSONObject.toJSONString(params), new
	 * TypeReference<Map<String, Object>>() { }); String openReturnUrl =
	 * URLEncoder.encode(op.getOpenReturnUrl(), "UTF-8"); signMap.put("appKey",
	 * headerMap.remove("appKey")); signMap.put("orderNo", op.getOrderNo());
	 * signMap.put("openReturnUrl", openReturnUrl); headerMap.put("sign",
	 * SignUtils.createSign(signMap, true));
	 * 
	 * MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
	 * queryParams.add("orderNo", op.getOrderNo());
	 * queryParams.add("openReturnUrl", openReturnUrl); // String url = //
	 * UriComponentsBuilder.newInstance().scheme("http").host("127.0.0.1").port(
	 * 8080).path("/api").path( //
	 * "/api").path("/loan").path("/creatJxbAccount").queryParams(queryParams).
	 * build().toString();
	 * 
	 * String url = UriComponentsBuilder.newInstance().scheme("https").host(
	 * "api.jubaoqiandai.com").path("/api")
	 * .path("/loan").path("/creatJxbAccount").queryParams(queryParams).build().
	 * toString();
	 * 
	 * // 请求api开户接口 CreateAccountVO vo = rongService.createAccount(url, params,
	 * headerMap); rong360Resp.setCode(Rong360Resp.SUCCESS);
	 * rong360Resp.setMsg("SUCCESS"); rong360Resp.setData(vo); if (vo == null ||
	 * StringUtils.isBlank(vo.getOpenUrl())) {
	 * rong360Resp.setCode(Rong360Resp.FAILURE); rong360Resp.setMsg("系统异常"); } }
	 * catch (Exception e) { rong360Resp.setCode(Rong360Resp.FAILURE);
	 * rong360Resp.setMsg("系统异常"); log.error("【融360-开户执行接口】异常orderNo={}",
	 * orderNo, e); } log.debug("【融360-开户执行接口】orderNo={},响应结果={}", orderNo,
	 * JSONObject.toJSONString(rong360Resp)); return rong360Resp; }
	 */

	/**
	 * 确认提现
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmWithdraw", method = RequestMethod.POST)
	public Rong360Resp confirmWithdraw(@RequestBody Rong360Req req) {
		Rong360Resp rong360Resp = new Rong360Resp();
		String orderNo = "";

		try {
			// 入参
			String biz_data = req.getBiz_data();
			ConfirmWithdrawOP op = JSONObject.parseObject(biz_data, ConfirmWithdrawOP.class);
			orderNo = op.getOrderNo();

			String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
			if (StringUtils.isBlank(applyId)) {
				rong360Resp.setCode(Rong360Resp.FAILURE);
				rong360Resp.setMsg("数据异常，用户工单尚未生成！");
				log.error("【融360-提现执行接口】异常-用户工单尚未生成！orderNo={}", orderNo);
				return rong360Resp;
			}
			ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
			CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
			if (custUserVO == null) {
				rong360Resp.setCode(Rong360Resp.FAILURE);
				rong360Resp.setMsg("数据异常，用户尚未注册！");
				log.error("【融360-提现执行接口】异常-用户尚未注册orderNo={}", orderNo);
				return rong360Resp;
			}

			// 模拟用户登陆
			Map<String, String> headerMap = LoginUtils.loginMock(custUserVO);
			Map<String, String> params = new HashMap<>();
			params.put("applyId", applyId);

			// api接口签名
			Map<String, Object> signMap = JSONObject.parseObject(JSONObject.toJSONString(params),
					new TypeReference<Map<String, Object>>() {
					});
			String cashReturnUrl = URLEncoder.encode(op.getCashReturnUrl(), "UTF-8");
			signMap.put("appKey", headerMap.remove("appKey"));
			signMap.put("orderNo", op.getOrderNo());
			signMap.put("cashReturnUrl", cashReturnUrl);
			headerMap.put("sign", SignUtils.createSign(signMap, true));

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("orderNo", op.getOrderNo());
			queryParams.add("cashReturnUrl", cashReturnUrl);
			// String url =
			// UriComponentsBuilder.newInstance().scheme("http").host("127.0.0.1").port(8080).path("/api").path(
			// "/api").path("/loan").path("/enSureKdWithdraw").queryParams(queryParams).build().toString();

			String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/api")
					.path("/loan").path("/enSureKdWithdraw").queryParams(queryParams).build().toString();

			// 请求api提现接口
			ConfirmWithdrawVO vo = rongService.confirmWithdraw(url, params, headerMap);
			rong360Resp.setCode(Rong360Resp.SUCCESS);
			rong360Resp.setMsg("SUCCESS");
			rong360Resp.setData(vo);
			if (vo == null || StringUtils.isBlank(vo.getCashUrl())) {
				rong360Resp.setCode(Rong360Resp.FAILURE);
				rong360Resp.setMsg("系统异常");
			}
		} catch (Exception e) {
			rong360Resp.setCode(Rong360Resp.FAILURE);
			rong360Resp.setMsg("系统异常");
			log.error("【融360-提现执行接口】异常orderNo={}", orderNo, e);
		}
		log.debug("【融360-提现执行接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(rong360Resp));
		return rong360Resp;
	}

}
