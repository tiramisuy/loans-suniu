package com.rongdu.loans.api.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.RSAUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.web.vo.LiangHuaPaiResultVO;
import com.rongdu.loans.api.web.vo.LiangHuaPaiVO;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.MsgTypeEnum;
import com.rongdu.loans.enums.SourceEnum;

/**
 * 
* @Description:  量化派导流
* @author: 饶文彪
* @date 2018年7月4日 上午11:44:08
 */
@Controller
@RequestMapping(value = "imp/lhp")
public class LiangHuaPaiImportController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(LiangHuaPaiImportController.class);
	@Autowired
	private CustUserService custUserService;

	@Autowired
	private ShortMsgService shortMsgService;

	static final String downloadPage = "/WEB-INF/views/modules/liangHuaPai/apply.jsp";
		  
	

	/**
	 * 验证用户是否注册
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyRegister")
	@ResponseBody
	public LiangHuaPaiVO verifyRegister(HttpServletRequest request) {
		try {
			String channel = request.getParameter("channel");
			String phone = request.getParameter("phone");
			String sign = request.getParameter("sign");

			if (StringUtils.isBlank(channel) || StringUtils.isBlank(phone) || StringUtils.isBlank(sign)) {
				return getErrorRtn(ErrInfo.BAD_REQUEST);
			}

			String paramJson = "{\"channel\":\""+channel+"\",\"phone\":\""+phone+"\"}";
			
			 logger.info("接收参数：" + paramJson);

			 boolean verify = RSAUtils.mrVerify(paramJson, Global.getConfig("lianghuapai_pulicKey"),sign);

			 if (!verify) {
				 return getErrorRtn(ErrInfo.SIGN_ERROR);
			 }

			CustUserVO userVO = custUserService.getCustUserByMobile(phone);

			if (userVO != null) {
				return getSuccessRtn("1", "/imp/lhp/register?phone=" + phone);
			} else {

				String password = "jubao" + phone.substring(phone.length() - 4);

				// 截取ip
				String ip = Servlets.getIpAddress(request);

				// 注册账户
				RegisterOP registerOP = new RegisterOP();
				// 手机号
				registerOP.setAccount(phone);
				// 渠道
				registerOP.setChannel("lianghuapai");
				// 验证码
				registerOP.setMsgVerCode("123456");
				// 来源 1-ios,2-android,3-h5,4-api,5-后台网址,6-系统
				registerOP.setSource("3");

				registerOP.setIp(ip);

				// 密码加密
				registerOP.setPassword(LoginUtils.pwdToSHA1(password));

				// registerOP.setRealName(op.getName());

				String userId = custUserService.saveRegister(registerOP);
				if (StringUtils.equals(userId, "0")) {// 注册失败
					logger.info("注册失败");
					return null;
				}

				// request.setAttribute("password", password);
				// request.setAttribute("phoneNo", phone);

				// 发送短信
				SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
				sendShortMsgOP.setIp(ip);
				sendShortMsgOP.setMobile(phone);
				sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD, password));
				sendShortMsgOP.setUserId(userId);
				sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
				sendShortMsgOP.setSource(SourceEnum.H5.getCode());
				sendShortMsgOP.setChannelId("lianghuapai");
				shortMsgService.sendMsg(sendShortMsgOP);

				return getSuccessRtn("0", "/imp/lhp/register?phone=" + phone + "&password=" + password);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRtn(ErrInfo.ERROR);
		}
	}

	/**
	 * 用户注册，并跳转到下载app页面        
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register")
	public String register(HttpServletRequest request) throws Exception {
//		String phone = request.getParameter("");
//		String password = request.getParameter("password");
		return downloadPage;
	}

	private LiangHuaPaiVO getErrorRtn(ErrInfo errInfo) {
		LiangHuaPaiVO vo = new LiangHuaPaiVO();
		vo.setCode(errInfo.getCode());
		vo.setMessage(errInfo.getMsg());
		vo.setResult(new LiangHuaPaiResultVO());
		logger.info("返回数据：" + JSONObject.toJSONString(vo));
		return vo;
	}

	private LiangHuaPaiVO getSuccessRtn(String userType, String returnUrl) {
		LiangHuaPaiVO vo = new LiangHuaPaiVO();
		vo.setCode("200");
		vo.setMessage("请求成功");
		//vo.setResult(new LiangHuaPaiResultVO(userType, Global.getConfig("server.url") + returnUrl));
		vo.setResult(new LiangHuaPaiResultVO(userType, Global.getConfig("server.url") + returnUrl));
		logger.info("返回数据：" + JSONObject.toJSONString(vo));
		return vo;
	}

}
