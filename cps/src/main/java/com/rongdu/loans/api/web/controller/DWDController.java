package com.rongdu.loans.api.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.rongdu.loans.pay.exception.OrderProcessingException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.api.common.AuthenticationType;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.common.dwd.DwdUtil;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.option.dwd.BankVerifyOP;
import com.rongdu.loans.loan.option.dwd.CertAuthOP;
import com.rongdu.loans.loan.option.dwd.DWDInfo;
import com.rongdu.loans.loan.option.dwd.DWDResp;
import com.rongdu.loans.loan.option.dwd.DealContractOP;
import com.rongdu.loans.loan.option.dwd.PaymentReqOP;
import com.rongdu.loans.loan.option.dwd.PaymentResultOP;
import com.rongdu.loans.loan.option.dwd.WithdrawReqOP;
import com.rongdu.loans.loan.option.dwd.WithdrawTrialOP;
import com.rongdu.loans.loan.option.jdq.JDQResp;
import com.rongdu.loans.loan.service.DWDService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.dwd.AuditResultVO;
import com.rongdu.loans.loan.vo.dwd.DealContractVO;
import com.rongdu.loans.loan.vo.dwd.OrderStatusVO;
import com.rongdu.loans.loan.vo.dwd.PaymentStatusVO;
import com.rongdu.loans.loan.vo.dwd.RepaymentPlanVO;
import com.rongdu.loans.loan.vo.dwd.WithdrawReqVO;
import com.rongdu.loans.loan.vo.dwd.WithdrawTrialVO;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lee on 2018/10/29.
 */
@Slf4j
@Controller
@RequestMapping(value = "dwd")
public class DWDController {

	@Autowired
	private DWDService dwdService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private CustUserService custUserService;

	private static final long TIME = 15;// 时间段，单位秒
	private static final long COUNT = 5;// 允许访问的次数
	private static long firstTime = 0;
	private static long accessCount = 0;

	private static synchronized DWDResp accessLock() {// 并发控制
		if (System.currentTimeMillis() - firstTime <= TIME * 1000L) {
			if (accessCount < COUNT) {
				accessCount++;
			} else {
				DWDResp resp = new DWDResp();
				resp.setCode(DWDResp.FAILURE);
				resp.setMsg("系统繁忙，请稍后重试");
				return resp;
			}
		} else {
			firstTime = System.currentTimeMillis();
			accessCount = 1;
		}
		return null;
	}

	private DWDResp pushBaseInfo(String body) {
		DWDResp dwdResp = dwdService.pushBaseInfo(body);
		return dwdResp;
	}

	private DWDResp pushAdditionalInfo(String body) {
		DWDResp dwdResp = dwdService.pushAdditionalInfo(body);
		return dwdResp;
	}

	@ResponseBody
	@RequestMapping(value = "/dwdGateWay", method = RequestMethod.POST)
	public DWDResp dwdGateWay(@RequestBody String body) {
		DWDResp dwdResp = new DWDResp();
		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String method = dwdInfo.getMethod();
		log.info("--->大王贷请求接口--->" + method);
		if ("fund.userinfo.base".equals(method)) {
			// 推送用户借款基本信息
			dwdResp = pushBaseInfo(body);
		} else if ("fund.userinfo.addit".equals(method)) {
			// 推送用户借款补充信息
			dwdResp = pushAdditionalInfo(body);
		} else if ("fund.cert.auth".equals(method)) {
			// 查询复贷和黑名单信息
			dwdResp = certAuth(body);
		} else if ("fund.bank.bind".equals(method)) {
			// 推送用户绑定银行卡
			dwdResp = bankBind(body);
		} else if ("fund.bank.verify".equals(method)) {
			// 推送用户验证银行卡
			dwdResp = bankVerify(body);
		} else if ("fund.audit.result".equals(method)) {
			// 查询审批结论
			/*DWDResp lockResp = accessLock();
			if (lockResp != null) {
				log.info("访问频率限制--->大王贷--->查询审批结论");
				return lockResp;
			}*/
			dwdResp = audiResult(body);
		} else if ("fund.withdraw.trial".equals(method)) {
			// 试算接口
			dwdResp = withdrawTrial(body);
		} else if ("fund.payment.plan".equals(method)) {
			// 查询还款计划
			/*DWDResp lockResp = accessLock();
			if (lockResp != null) {
				log.info("访问频率限制--->大王贷--->查询还款计划");
				return lockResp;
			}*/
			dwdResp = repaymentPlan(body);
		} else if ("fund.payment.req".equals(method)) {
			// 推送用户还款信息
			dwdResp = payment(body);
		} else if ("fund.payment.result".equals(method)) {
			// 查询还款状态
			/*DWDResp lockResp = accessLock();
			if (lockResp != null) {
				log.info("访问频率限制--->大王贷--->查询还款状态");
				return lockResp;
			}*/
			dwdResp = paymentStatus(body);
		} else if ("fund.order.status".equals(method)) {
			// 查询订单状态
			/*DWDResp lockResp = accessLock();
			if (lockResp != null) {
				log.info("访问频率限制--->大王贷--->查询订单状态");
				return lockResp;
			}*/
			dwdResp = orderStatus(body);
		} else if ("fund.withdraw.req".equals(method)) {
			// 推送用户确认收款信息
			dwdResp = withdrawReq(body);
		} else if ("fund.deal.contract".equals(method)) {
			// 查询借款合同
			dwdResp = dealContract(body);
		} else {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("未知接口异常");
			log.error("【大王贷-接口调用】异常-未找到匹配接口{}", method);
		}
		return dwdResp;
	}

	private DWDResp certAuth(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			CertAuthOP op = JSONObject.parseObject(bizData, CertAuthOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			dwdResp = dwdService.certAuth(op);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-查询复贷接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-查询复贷接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp bankBind(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			BankVerifyOP op = JSONObject.parseObject(bizData, BankVerifyOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			dwdResp = dwdService.bankBind(op);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-用户绑定银行卡接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-用户绑定银行卡接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo,
				JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp bankVerify(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			BankVerifyOP op = JSONObject.parseObject(bizData, BankVerifyOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			dwdResp = dwdService.bankVerify(op);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-用户验证银行卡接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-用户验证银行卡接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp audiResult(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			JSONObject object = JSONObject.parseObject(bizData);
			orderNo = object.getString("order_no");

			// 业务服务
			AuditResultVO vo = dwdService.audiResult(orderNo);
			dwdResp.setData(vo);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-查询审批结论接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-查询审批结论接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp withdrawTrial(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			WithdrawTrialOP op = JSONObject.parseObject(bizData, WithdrawTrialOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			WithdrawTrialVO vo = dwdService.withdrawTrial(op);
			dwdResp.setData(vo);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-试算接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-试算接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp repaymentPlan(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			JSONObject object = JSONObject.parseObject(bizData);
			orderNo = object.getString("order_no");

			// 业务服务
			RepaymentPlanVO vo = dwdService.repaymentPlan(orderNo);
			dwdResp.setData(vo);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-查询还款计划接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-查询还款计划接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp payment(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			PaymentReqOP op = JSONObject.parseObject(bizData, PaymentReqOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			dwdResp = dwdService.payment(op);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-用户还款接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-用户还款接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp paymentStatus(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";
		String transactionid = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			PaymentResultOP op = JSONObject.parseObject(bizData, PaymentResultOP.class);
			orderNo = op.getOrderNo();
			transactionid = op.getTransactionid();

			// 业务服务
			PaymentStatusVO vo = dwdService.paymentStatus(op);
			dwdResp.setData(vo);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-查询还款状态接口】异常orderNo={},repayPlanItemId={}", channelParse.getApp(), orderNo, transactionid, e);
		}
		log.debug("【大王贷-{}-查询还款状态接口】orderNo={},repayPlanItemId={},响应结果={}", channelParse.getApp(), orderNo, transactionid,
				JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp orderStatus(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			JSONObject object = JSONObject.parseObject(bizData);
			orderNo = object.getString("order_no");

			// 业务服务
			OrderStatusVO vo = dwdService.orderStatus(orderNo);
			dwdResp.setData(vo);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-查询订单状态接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-查询订单状态接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private DWDResp withdrawReq(String body) {
		DWDResp dwdResp = new DWDResp();
		WithdrawReqVO vo = new WithdrawReqVO();
		vo.setNeedConfirm("0");
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			WithdrawReqOP op = JSONObject.parseObject(bizData, WithdrawReqOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			dwdResp = dwdService.withdrawReq(op);

		} catch (Exception e) {
			vo.setDealResult("0");
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			dwdResp.setData(vo);
			log.error("【大王贷-确认收款接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-确认收款接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	/*private DWDResp withdrawReq(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(body);
			WithdrawReqOP op = JSONObject.parseObject(bizData, WithdrawReqOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			String applyId = dwdService.getApplyId(orderNo);
			if (StringUtils.isBlank(applyId)) {
				dwdResp.setCode(DWDResp.FAILURE);
				dwdResp.setMsg("数据异常，用户工单尚未生成！");
				log.error("【大王贷-确认收款接口】异常-用户工单尚未生成！orderNo={}", orderNo);
				return dwdResp;
			}
			ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
			CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
			if (custUserVO == null) {
				dwdResp.setCode(JDQResp.FAILURE);
				dwdResp.setMsg("数据异常，用户尚未注册！");
				log.error("【大王贷-确认收款接口】异常-用户尚未注册orderNo={}", orderNo);
				return dwdResp;
			}
			String servFee = String.valueOf(applyAllotVO.getServFee());
			String approveAmt = String.valueOf(applyAllotVO.getApproveAmt());
			String approveTerm = String.valueOf(applyAllotVO.getApproveTerm());
			// 模拟用户登陆
			Map<String, String> map = loginMock(custUserVO);
			String returnUrl = op.getReturnUrl();
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.setAll(map);
			params.add("servFee", servFee);
			params.add("approveAmt", approveAmt);
			params.add("approveTerm", approveTerm);
			params.add("returnUrl", returnUrl);
			String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/#")
					.path("/loanInfo").queryParams(params).build().toString();
			*//*
			 * String url =
			 * UriComponentsBuilder.newInstance().scheme("http").host
			 * ("47.100.113.196").path("/#")
			 * .path("/loanInfo").queryParams(params).build().toString();
			 *//*
			WithdrawReqVO vo = new WithdrawReqVO();
			vo.setWithdrawReqUrl(url);
			dwdResp.setCode(DWDResp.SUCCESS);
			dwdResp.setMsg("SUCCESS");
			dwdResp.setData(vo);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-确认收款接口】异常orderNo={}", orderNo, e);
		}
		log.debug("【大王贷-确认收款接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}*/

	private DWDResp dealContract(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";
		DealContractVO vo = new DealContractVO();

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			DealContractOP op = JSONObject.parseObject(bizData, DealContractOP.class);
			orderNo = op.getOrderNo();

			// 业务服务
			/*String applyId = dwdService.getApplyId(orderNo);
			if (StringUtils.isBlank(applyId)) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("数据异常，用户工单尚未生成！");
				log.error("【大王贷-查询借款合同接口】异常-用户工单尚未生成！orderNo={}", orderNo);
				return dwdResp;
			}
			ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
			CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
			if (custUserVO == null) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("数据异常，用户尚未注册！");
				log.error("【大王贷-查询借款合同接口】异常-用户尚未注册orderNo={}", orderNo);
				return dwdResp;
			}
			// 模拟用户登陆
			Map<String, String> map = loginMock(custUserVO);
			String returnUrl = op.getContractReturnUrl();
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.setAll(map);
			params.add("returnUrl", returnUrl);
			String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/#")
					.path("/agreement14").queryParams(params).build().toString();*/
			String url =
					UriComponentsBuilder.newInstance().scheme("http").host
							(Global.getConfig("h5.server.url")).path("/#").path("/dwdAgreementList").build().toString();
			vo.setContractUrl(url);
			dwdResp.setData(vo);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			log.error("【大王贷-{}-查询借款合同接口】异常orderNo={}", channelParse.getApp(), orderNo, e);
		}
		log.debug("【大王贷-{}-查询借款合同接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	private Map<String, String> loginMock(CustUserVO custUserVO) {
		Map<String, String> map = new HashMap<>();
		UsernamePasswordToken token = new UsernamePasswordToken(custUserVO.getMobile(), custUserVO.getPassword());
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		SecurityUtils.getSubject().getSession().setTimeout(3600000L);
		AuthenticationType authenticationType = AuthenticationType.LOGIN;
		LoginUtils.authenticationType.set(authenticationType);
		subject.login(token);
		CustUserVO shiroUser = (CustUserVO) subject.getPrincipal();
		String userId = shiroUser.getId();
		String tokenId = LoginUtils.generateTokenId(userId);
		String appKey = LoginUtils.generateAppKey(userId);
		LoginUtils.cleanCustUserInfoCache(userId);
		JedisUtils.delObject(Global.USER_AUTH_PREFIX + userId);

		map.put("userId", userId);
		map.put("appKey", appKey);
		map.put("tokenId", tokenId);
		return map;
	}
}
