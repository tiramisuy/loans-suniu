package com.rongdu.loans.api.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.utils.AESUtils;
import com.rongdu.common.utils.MD5Util;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.MsgTypeEnum;
import com.rongdu.loans.enums.SourceEnum;
import com.rongdu.loans.loan.option.LiangHuaPaiDataOP;
import com.rongdu.loans.loan.option.LiangHuaPaiDataVO;
import com.rongdu.loans.loan.option.LiangHuaPaiOP;
import com.rongdu.loans.loan.option.LiangHuaPaiVO;
import com.rongdu.loans.loan.option.dwd.DWDResp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lee on 2018/9/5.
 */
@Controller
@RequestMapping(value = "openapi/partnerhit")
@Slf4j
public class LiangHuaPaiController {
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private ShortMsgService shortMsgService;

	static final String downloadPage = "/WEB-INF/views/modules/liangHuaPai/apply.jsp";
	static final String key = "1mJ9HiEphbBS8baw";
	static final String url = Global.getConfig("server.url") + "/openapi/partnerhit/register";

	private static final long TIME = 15;// 时间段，单位秒
	private static final long COUNT = 5;// 允许访问的次数
	private static long firstTime = 0;
	private static long accessCount = 0;

	private static synchronized LiangHuaPaiVO accessLock() {// 并发控制
		if (System.currentTimeMillis() - firstTime <= TIME * 1000L) {
			if (accessCount < COUNT) {
				accessCount++;
			} else {
				return LiangHuaPaiVO.getInstance("F", "-1", "系统繁忙，请稍后重试", null);
			}
		} else {
			firstTime = System.currentTimeMillis();
			accessCount = 1;
		}
		return null;
	}

	@RequestMapping(value = "uCheck")
	@ResponseBody
	public LiangHuaPaiVO uCheck(LiangHuaPaiOP liangHuaPaiOP) {
		LiangHuaPaiVO lockResp = accessLock();
		if (lockResp != null) {
			log.info("访问频率限制--->量化派--->uCheck");
			return lockResp;
		}
		try {
			String requestData = liangHuaPaiOP.getRequestData();
			String requestId = liangHuaPaiOP.getRequestId();
			String channelId = liangHuaPaiOP.getChannelId();
			String partnerName = liangHuaPaiOP.getPartnerName();
			if (StringUtils.isBlank(requestData) || StringUtils.isBlank(requestId) || StringUtils.isBlank(channelId)
					|| StringUtils.isBlank(partnerName)) {
				return LiangHuaPaiVO.getInstance("F", "2", "请求参数错误", null);
			}

			log.info("requestData:" + requestData);
			String aesDecrypt = AESUtils.aesDecrypt(requestData, key);
			log.info("aesDecrypt:" + aesDecrypt);

			LiangHuaPaiDataOP liangHuaPaiDataOP = JSONObject.parseObject(aesDecrypt, LiangHuaPaiDataOP.class);
			if (liangHuaPaiDataOP.getHitType().equals("MOBILE")) {
				String mobile = liangHuaPaiDataOP.getMobileNo();
				CustUserVO userVO = custUserService.getCustUserByMobile(mobile);
				if (userVO != null) {
					if (!userVO.getChannel().equals("lianghuapai")) {
						return LiangHuaPaiVO.getInstance("F", "5", "客户已在其他渠道注册", null);
					} else {
						return LiangHuaPaiVO.getInstance("F", "3", "客户已在当前渠道注册", null);
					}
				} else {
					String uuid = MD5Util.string2MD5(mobile);
					return LiangHuaPaiVO.getInstance("S", null, null, LiangHuaPaiDataVO.getInstance(url, uuid));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LiangHuaPaiVO.getInstance("F", "-1", "内部错误", null);
	}

	@RequestMapping(value = "uRegister")
	@ResponseBody
	public LiangHuaPaiVO uRegister(LiangHuaPaiOP liangHuaPaiOP, HttpServletRequest request) {
		try {
			String requestData = liangHuaPaiOP.getRequestData();
			String requestId = liangHuaPaiOP.getRequestId();
			String channelId = liangHuaPaiOP.getChannelId();
			String partnerName = liangHuaPaiOP.getPartnerName();
			if (StringUtils.isBlank(requestData) || StringUtils.isBlank(requestId) || StringUtils.isBlank(channelId)
					|| StringUtils.isBlank(partnerName)) {
				return LiangHuaPaiVO.getInstance("F", "2", "请求参数错误", null);
			}

			log.info("requestData:" + requestData);
			String aesDecrypt = AESUtils.aesDecrypt(requestData, key);
			log.info("aesDecrypt:" + aesDecrypt);

			LiangHuaPaiDataOP liangHuaPaiDataOP = JSONObject.parseObject(aesDecrypt, LiangHuaPaiDataOP.class);
			// if (liangHuaPaiDataOP.getHitType().equals("MOBILE")) {
			String mobile = liangHuaPaiDataOP.getMobileNo();
			String uuid = MD5Util.string2MD5(mobile);
			if (!uuid.equals(liangHuaPaiDataOP.getFlowNo())) {
				return LiangHuaPaiVO.getInstance("F", "2", "请求参数错误", null);
			}
			CustUserVO userVO = custUserService.getCustUserByMobile(mobile);
			if (userVO != null) {
				if (!userVO.getChannel().equals("lianghuapai")) {
					return LiangHuaPaiVO.getInstance("F", "5", "客户已在其他渠道注册",
							LiangHuaPaiDataVO.getInstance(url + "?phone=" + mobile, null));
				} else {
					return LiangHuaPaiVO.getInstance("F", "3", "客户已在当前渠道注册",
							LiangHuaPaiDataVO.getInstance(url + "?phone=" + mobile, null));
				}
			} else {
				boolean registerResult = register(request, liangHuaPaiDataOP);
				if (registerResult == true) {
					return LiangHuaPaiVO.getInstance(
							"S",
							null,
							null,
							LiangHuaPaiDataVO.getInstance(
									url + "?phone=" + mobile + "&password=" + "jubao"
											+ mobile.substring(mobile.length() - 4), null));
				} else {
					return LiangHuaPaiVO.getInstance("F", "-1", "内部错误", null);
				}
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LiangHuaPaiVO.getInstance("F", "-1", "内部错误", null);
	}

	public boolean register(HttpServletRequest request, LiangHuaPaiDataOP liangHuaPaiDataOP) {
		String phone = liangHuaPaiDataOP.getMobileNo();
		String password = "jubao" + phone.substring(phone.length() - 4);
		String ip = Servlets.getIpAddress(request);
		RegisterOP registerOP = new RegisterOP();
		registerOP.setAccount(phone);
		registerOP.setChannel("lianghuapai");
		registerOP.setMsgVerCode("123456");
		registerOP.setSource("3");
		registerOP.setIp(ip);
		registerOP.setPassword(LoginUtils.pwdToSHA1(password));
		registerOP.setRealName(liangHuaPaiDataOP.getCustName());
		String userId = custUserService.saveRegister(registerOP);
		if (StringUtils.equals(userId, "0")) {
			return false;
		}
		SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
		sendShortMsgOP.setIp(ip);
		sendShortMsgOP.setMobile(phone);
		sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD, password));
		sendShortMsgOP.setUserId(userId);
		sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
		sendShortMsgOP.setSource(SourceEnum.H5.getCode());
		sendShortMsgOP.setChannelId("lianghuapai");
		shortMsgService.sendMsg(sendShortMsgOP);
		return true;
	}

	@RequestMapping(value = "/register")
	public String register() throws Exception {
		return downloadPage;
	}

}
