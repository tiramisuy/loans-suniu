package com.rongdu.loans.api.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.api.common.AuthenticationType;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.CommonUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.common.XianJinBaiKaUtil;
import com.rongdu.loans.api.web.option.XianJinBaiKaRequest;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.loan.option.AgreementOP;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.Contracts;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaRepaymentPlan;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaVO;
import com.rongdu.loans.loan.service.ApplyTripartiteService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.XianJinBaiKaService;
import com.rongdu.loans.loan.vo.AgreementVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.mq.MessageProductorService;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * Created by lee on 2018/5/22.
 */
@Controller
@RequestMapping(value = "cashWhiteCardFQ")
public class XianJinBaiKaFQController {

	private Logger logger = LoggerFactory.getLogger(XianJinBaiKaFQController.class);

	@Autowired
	private MessageProductorService messageProductorService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private XianJinBaiKaService xianJinBaiKaService;
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private ApplyTripartiteService applyTripartiteService;

	private static final long TIME = 15;// 时间段，单位秒
	private static final long COUNT = 5;// 允许访问的次数
	private static long firstTime = 0;
	private static long accessCount = 0;

	private static synchronized XianJinBaiKaVO accessLock() {// 并发控制
		if (System.currentTimeMillis() - firstTime <= TIME * 1000L) {
			if (accessCount < COUNT) {
				accessCount++;
			} else {
				XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
				xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
				xianJinBaiKaResponse.setMessage("系统繁忙，请稍后重试");
				return xianJinBaiKaResponse;
			}
		} else {
			firstTime = System.currentTimeMillis();
			accessCount = 1;
		}
		return null;
	}

	@RequestMapping(value = "route")
	@ResponseBody
	public XianJinBaiKaVO xianjincard(XianJinBaiKaRequest xianJinBaiKaRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long sessionId = System.currentTimeMillis();
		XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
		try {
			logger.info(sessionId + "开始XianJinBaiKaController xianjincard method");
			// 获取业务参数
			String args = xianJinBaiKaRequest.getArgs();
			String call = xianJinBaiKaRequest.getCall();
			String sign = xianJinBaiKaRequest.getSign();
			// logger.info(sessionId + "：call=" + call + ",sign=" + sign +
			// ",args=" + args);
			logger.info(sessionId + "：call=" + call + ",sign=" + sign);
			XianJinBaiKaCommonOP xianJinBaiKaCommonRequest = (XianJinBaiKaCommonOP) JsonMapper.fromJsonString(args,
					XianJinBaiKaCommonOP.class);

			if (StringUtils.isBlank(call) || StringUtils.isBlank(sign)) {
				xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
				xianJinBaiKaResponse.setMessage("入参不合法");
				logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinBaiKaResponse));
				return xianJinBaiKaResponse;
			}

			// 验证签名
			boolean flag = XianJinBaiKaUtil.checkSignFQ(call, args, sign);
			if (!flag) {
				xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
				xianJinBaiKaResponse.setMessage("sign错误");
				logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinBaiKaResponse));
				return xianJinBaiKaResponse;
			}

			// 处理业务
			if ("User.isUserAccept".equals(call)) {
				xianJinBaiKaResponse = isUserAccept(sessionId, xianJinBaiKaCommonRequest);
			} else if ("User.authStatus".equals(call)) {
				xianJinBaiKaResponse = authStatus(xianJinBaiKaCommonRequest);
			} else if ("Order.pushUserBaseInfo".equals(call)) {
				xianJinBaiKaResponse = pushUserBaseInfo(sessionId, xianJinBaiKaCommonRequest);
			} else if ("Order.pushUserAdditionalInfo".equals(call)) {
				xianJinBaiKaResponse = pushUserAdditionalInfo(sessionId, xianJinBaiKaCommonRequest);
			} else if ("BindCard.getValidBankList".equals(call)) {
				xianJinBaiKaResponse = getValidBankList(sessionId);
			} else if ("BindCard.applyBindCard".equals(call)) {
				xianJinBaiKaResponse = applyBindCard(sessionId, xianJinBaiKaCommonRequest);
			} else if ("BindCard.getUserBindBankCardList".equals(call)) {
				xianJinBaiKaResponse = getUserBindBankCardList(sessionId, xianJinBaiKaCommonRequest);
			} else if ("Order.getContracts".equals(call)) {
				xianJinBaiKaResponse = getContracts(xianJinBaiKaCommonRequest);
			} else if ("Order.getRepayplan".equals(call)) {
				// 访问频率控制
				XianJinBaiKaVO lockVO = accessLock();
				if (lockVO != null) {
					logger.info("访问频率限制--->现金白卡分期--->查询还款计划--->" + call);
					return lockVO;
				}
				xianJinBaiKaResponse = getRepayplan(sessionId, xianJinBaiKaCommonRequest);
			} else if ("Order.getOrderStatus".equals(call)) {
				// 访问频率控制
				XianJinBaiKaVO lockVO = accessLock();
				if (lockVO != null) {
					logger.info("访问频率限制--->现金白卡分期--->查询订单状态--->" + call);
					return lockVO;
				}
				xianJinBaiKaResponse = getOrderStatus(xianJinBaiKaCommonRequest);
			} else if ("Order.loanCalculate".equals(call)) {
				xianJinBaiKaResponse = loanCalculate(xianJinBaiKaCommonRequest);
			} else if ("Order.applyRepay".equals(call)) {
				xianJinBaiKaResponse = applyRepay(sessionId, xianJinBaiKaCommonRequest);
			} else if ("Order.confirmLoan".equals(call)) {
				xianJinBaiKaResponse = confirmLoan(sessionId, xianJinBaiKaCommonRequest);
			} else {
				xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
				xianJinBaiKaResponse.setMessage("数据不完整");
			}
		} catch (Exception e) {
			e.printStackTrace();
			xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
			xianJinBaiKaResponse.setMessage("数据不完整");
		}
		logger.info("：结束xianjincard：" + JSON.toJSONString(xianJinBaiKaResponse));
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO getContracts(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
		xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
		xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
		List<Contracts> contractsList = new ArrayList<>();
		Contracts contracts = new Contracts();
		contracts.setName("聚宝钱包贷款合同");
		String orderSn = xianJinBaiKaCommonRequest.getOrder_sn();
		String applyId = applyTripartiteService.getApplyIdByThirdId(orderSn);
		String user_phone = null;
		// String requestUrl = "http://47.100.113.196/#/agreement3";
		String requestUrl = "https://api.jubaoqiandai.com/#/agreement3";
		if (applyId != null) {
			LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
			user_phone = loanApplySimpleVO.getMobile();
			CustUserVO cust = custUserService.getCustUserByMobile(user_phone);
			UsernamePasswordToken token = new UsernamePasswordToken(user_phone, cust.getPassword());
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
			requestUrl = requestUrl + "?tokenId=" + tokenId + "&userId=" + userId + "&appKey=" + appKey;
		}
		contracts.setLink(requestUrl);
		contractsList.add(contracts);
		xianJinBaiKaResponse.setResponse(contractsList);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO getOrderStatus(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = xianJinBaiKaService.getOrderStatus(xianJinBaiKaCommonRequest);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO applyRepay(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = xianJinBaiKaService.applyRepay(xianJinBaiKaCommonRequest);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO confirmLoan(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = xianJinBaiKaService.confirmLoan(xianJinBaiKaCommonRequest);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO getRepayplan(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
		XianJinBaiKaRepaymentPlan xianJinBaiKaRepaymentPlan = xianJinBaiKaService
				.getRepayplanFQ(xianJinBaiKaCommonRequest.getOrder_sn());
		xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
		xianJinBaiKaResponse.setMessage("success");
		xianJinBaiKaResponse.setResponse(xianJinBaiKaRepaymentPlan);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO getUserBindBankCardList(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO applyBindCard(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = xianJinBaiKaService.applyBindCard(xianJinBaiKaCommonRequest);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO pushUserAdditionalInfo(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
		String orderSn = xianJinBaiKaCommonRequest.getOrder_info().getOrderSn();
		logger.info(sessionId + ":现金白卡附加信息开始校验：{}", orderSn);
		List<String> phoneList = xianJinBaiKaCommonRequest.getUser_additional().getAddressBook().getPhoneList();
		String mobile = xianJinBaiKaCommonRequest.getUser_additional().getContactInfo().getMobile();
		String mobileSpare = xianJinBaiKaCommonRequest.getUser_additional().getContactInfo().getMobileSpare();
		if (orderSn == null || mobile == null || mobileSpare == null || phoneList.size() <= 0) {
			xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
			xianJinBaiKaResponse.setMessage("数据不完整");
			xianJinBaiKaResponse.setResponse(true);
			logger.info(sessionId + ":现金白卡附加信息数据不完整：{}", orderSn);
		} else {
			logger.info(sessionId + ":现金白卡附加信息加入队列：{}", orderSn);
			messageProductorService.sendDataToPushUserAdditionalInfoQuene(xianJinBaiKaCommonRequest);
			xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
			xianJinBaiKaResponse.setMessage("success");
			xianJinBaiKaResponse.setResponse(true);
		}
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO getValidBankList(long sessionId) throws IOException {
		XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
		logger.info(sessionId + ":开始getValidBankList method");
		try {
			List<Map<String, String>> list = new ArrayList<>();
			Map<String, String> map = null;
			for (String string : XianJinBaiKaUtil.baoFuBankCard) {
				map = new HashMap<>();
				map.put("bank_name", string);
				map.put("bank_code", XianJinBaiKaUtil.convertToBankCode(string));
				map.put("bank_title", XianJinBaiKaUtil.bfMap.get(string));
				list.add(map);
			}
			xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
			xianJinBaiKaResponse.setMessage("success");
			xianJinBaiKaResponse.setResponse(list);

		} catch (Exception e) {
			logger.error(sessionId + ":执行getValidBankList method:{}", e.getMessage());
			xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
			xianJinBaiKaResponse.setMessage("请求异常");
		}
		logger.info(sessionId + ":结束getValidBankList method:{}", JSON.toJSONString(xianJinBaiKaResponse));
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO loanCalculate(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) throws IOException {
		XianJinBaiKaVO xianJinBaiKaResponse = xianJinBaiKaService.loanCalculateFQ(xianJinBaiKaCommonRequest);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO pushUserBaseInfo(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
		String orderSn = xianJinBaiKaCommonRequest.getOrder_info().getOrderSn();
		logger.info(sessionId + ":现金白卡基础信息开始校验：{}", orderSn);
		String userPhone = xianJinBaiKaCommonRequest.getUser_info().getUserPhone();
		String userName = xianJinBaiKaCommonRequest.getUser_info().getUserName();
		String ocr_name = xianJinBaiKaCommonRequest.getUser_verify().getIdcardInfo().getIdcardInfoDetail()
				.getOcr_name();
		String face_recognition_picture = xianJinBaiKaCommonRequest.getUser_verify().getIdcardInfo()
				.getIdcardInfoDetail().getFace_recognition_picture();
		String id_number_f_picture = xianJinBaiKaCommonRequest.getUser_verify().getIdcardInfo().getIdcardInfoDetail()
				.getId_number_f_picture();
		String id_number_z_picture = xianJinBaiKaCommonRequest.getUser_verify().getIdcardInfo().getIdcardInfoDetail()
				.getId_number_z_picture();
		String idcard = xianJinBaiKaCommonRequest.getUser_verify().getOperatorVerify().getBasic().getIdcard();
		List<ContactList> contactList = xianJinBaiKaCommonRequest.getUser_verify().getOperatorReportVerify()
				.getContactList();
		if (orderSn == null || userPhone == null || userName == null || ocr_name == null
				|| face_recognition_picture == null || id_number_f_picture == null || id_number_z_picture == null
				|| idcard == null || contactList.size() <= 1) {
			xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
			xianJinBaiKaResponse.setMessage("数据不完整");
			xianJinBaiKaResponse.setResponse(true);
			logger.info(sessionId + ":现金白卡基础信息数据不完整：{}", orderSn);
		} else {
			logger.info(sessionId + ":现金白卡基础信息加入队列:{}", orderSn);
			JedisUtils.set("ORDER:Locked_" + userPhone, "", 60 * 60 * 4);
			messageProductorService.sendDataToPushUserBaseInfoQuene(xianJinBaiKaCommonRequest);
			xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
			xianJinBaiKaResponse.setMessage("success");
			xianJinBaiKaResponse.setResponse(true);
		}
		return xianJinBaiKaResponse;
	}

	public XianJinBaiKaVO isUserAccept(long sessionId, XianJinBaiKaCommonOP xianJinBaiKaCommonRequest)
			throws IOException {
		XianJinBaiKaVO xianJinBaiKaResponse = xianJinBaiKaService.isUserAcceptFQ(xianJinBaiKaCommonRequest);
		return xianJinBaiKaResponse;
	}

	private XianJinBaiKaVO authStatus(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
		XianJinBaiKaVO xianJinBaiKaResponse = xianJinBaiKaService.authStatus(xianJinBaiKaCommonRequest);
		return xianJinBaiKaResponse;
	}

	@RequestMapping(value = "/getAgreementFactor", name = "获取协议要素")
	@ResponseBody
	public ApiResult getAgreementFactor(@Valid AgreementOP param, Errors errors, HttpServletRequest request)
			throws Exception {
		// 参数异常处理
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 参数验证
		if (null == param.getApplyAmt()) {
			result.setCode("400");
			result.setMsg("申请金额不能为空");
			return result;
		}
		if (null == param.getApplyTerm()) {
			result.setCode("400");
			result.setMsg("申请期限不能为空");
			return result;
		}
		// 用户id
		param.setUserId(param.getUserId());
		param.setChannelId(ChannelEnum.JUQIANBAO.getCode());
		// 缓存中获取申请编号
		param.setApplyId(CommonUtils.getApplyNofromCache(param.getUserId()));
		// 获取协议要素信息
		param.setRepayMethod(3);
		AgreementVO vo = loanApplyService.getAgreementFactor(param);
		if (null == vo || null == vo.getIdNo()) {
			result.setCode("FAIL");
			result.setMsg("获取协议要素信息失败");
			return result;
		}
		result.setData(vo);
		logger.debug("AgreementVO:[{}]", JsonMapper.toJsonString(vo));
		return result;
	}

}
