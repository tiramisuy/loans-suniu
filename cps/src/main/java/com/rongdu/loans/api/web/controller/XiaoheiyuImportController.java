package com.rongdu.loans.api.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.RSAUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.web.option.XiaoheiyuOP;
import com.rongdu.loans.api.web.vo.XiaoheiyuDataVO;
import com.rongdu.loans.api.web.vo.XiaoheiyuVO;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.enums.MsgTypeEnum;
import com.rongdu.loans.enums.SourceEnum;

/**
 * 
 * @Description: 小黑鱼导流
 * @author: 饶文彪
 * @date 2018年7月4日 上午11:44:08
 */
@Controller
@RequestMapping(value = "imp/xhy")
public class XiaoheiyuImportController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(XiaoheiyuImportController.class);
	@Autowired
	private CustUserService custUserService;

	@Autowired
	private ShortMsgService shortMsgService;

	// static String pubKey =
	// "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJl7oPHAGoUHxaR2BntyvYB7Ab4Zw1ZX0fkpH1UPKSxRHkBlruwtMSizYM5ASj/APcvY/maMr9Yqz0Sb2rIEd7sCAwEAAQ==";
	// static String privateKey =
	// "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAmXug8cAahQfFpHYGe3K9gHsBvhnDVlfR+SkfVQ8pLFEeQGWu7C0xKLNgzkBKP8A9y9j+Zoyv1irPRJvasgR3uwIDAQABAkAN6AezH8bHWubrec4ojULiS0LjKI5sWlSqELHIETGX1DXPrkx61AojZGFdO+4rINkXgix5sQAkeExlWml8EMphAiEAx4gOPVfATGBm7AWS74geXFaA0ONegSJy1i5oUJnHm/MCIQDE62Gyi1lzmCnC63S7EgmvbtK0BzZhgs95k3NPLtEPGQIhAJQJ7ga1RIdmPvZ+bDYr19rKk2hoSYWl+W3PoLWsYtzhAiAWwGtlSZxoMqiAkNvH0Wm1D0Tg8ARkd8yo61RjTbFx4QIgAzEzc/MYJubgOqjGB91Bo/GIWyx1NEmBstdA3G5Wf08=";

	static final String downloadPage = "/WEB-INF/views/modules/xiaoheiyu/apply.jsp";

	private static final long TIME = 15;// 时间段，单位秒
	private static final long COUNT = 5;// 允许访问的次数
	private static long firstTime = 0;
	private static long accessCount = 0;

	private static synchronized XiaoheiyuVO accessLock(Long start) {// 并发控制
		if (System.currentTimeMillis() - firstTime <= TIME * 1000L) {
			if (accessCount < COUNT) {
				accessCount++;
			} else {
				XiaoheiyuVO vo = new XiaoheiyuVO();
				vo.setSuccess(false);
				vo.setCode("ERROR");
				vo.setCost_time(Long.valueOf((System.currentTimeMillis() - start)).intValue());
				vo.setMsg("系统繁忙，请稍后重试");
				vo.setData(new XiaoheiyuDataVO());
				vo.getData().setStatus("1");
				return vo;
			}
		} else {
			firstTime = System.currentTimeMillis();
			accessCount = 1;
		}
		return null;
	}

	/**
	 * 验证用户是否注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyRegister")
	@ResponseBody
	public XiaoheiyuVO verifyRegister(HttpServletRequest request) {
		Long start = System.currentTimeMillis();
		try {
			// 访问频率控制
			XiaoheiyuVO lockVO = accessLock(start);
			if (lockVO != null) {
				logger.info("访问频率限制--->小黑鱼--->验证用户注册");
				return lockVO;
			}

			XiaoheiyuOP op = readXiaoheiyuOPFromReq(request);

			if (op == null) {
				return getErrorRtn(ErrInfo.BAD_REQUEST, start);
			}

			logger.info("接收参数：name=&phoneNo=" + op.getPhoneNo() + "&timestamp=" + op.getTimestamp() + "&sign="
					+ op.getSign());
			op.setSign(op.getSign().replaceAll("[\\t\\n\\r]", ""));
			boolean verify = RSAUtils.mrVerify("name=&phoneNo=" + op.getPhoneNo() + "&timestamp=" + op.getTimestamp(),
					Global.getConfig("xiaoheiyu_pulicKey"), op.getSign());

			if (!verify) {
				return getErrorRtn(ErrInfo.SIGN_ERROR, start);
			}

			if (custUserService.isRegisterByMobileMD5(op.getPhoneNo())) {
				return getSuccessRtn("1", ErrInfo.REG_USER_EXISTS, start);
			} else {
				return getSuccessRtn("0", ErrInfo.REG_USER_UNEXISTS, start);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRtn(ErrInfo.ERROR, start);
		}
	}

	/**
	 * 用户注册，并跳转到下载app页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register")
	public String register(XiaoheiyuOP op, HttpServletRequest request) throws Exception {

		if (op == null || op.getPhoneNo() == null) {
			return null;
		}

		logger.info("接收参数：name=" + op.getName() + "&phoneNo=" + op.getPhoneNo() + "&timestamp=" + op.getTimestamp()
				+ "&sign=" + op.getSign());

		op.setSign(op.getSign().replaceAll("[\\t\\n\\r]", ""));

		boolean verify = RSAUtils.mrVerify(
				"name=" + op.getName() + "&phoneNo=" + op.getPhoneNo() + "&timestamp=" + op.getTimestamp(),
				Global.getConfig("xiaoheiyu_pulicKey"), op.getSign());

		if (!verify) {// 验证签名
			logger.info("验证签名失败");
			return null;
		}
		String password = "jubao" + op.getPhoneNo().substring(op.getPhoneNo().length() - 4);

		if (custUserService.isRegister(op.getPhoneNo())) {// 帐号已注册
			request.setAttribute("password", password);
			request.setAttribute("phoneNo", op.getPhoneNo());
			logger.info("帐号已注册");
			return downloadPage;
		}
		// 截取ip
		String ip = Servlets.getIpAddress(request);

		// 注册账户
		RegisterOP registerOP = new RegisterOP();
		// 手机号
		registerOP.setAccount(op.getPhoneNo());
		// 渠道
		registerOP.setChannel("xiaoheiyu");
		// 验证码
		registerOP.setMsgVerCode("123456");
		// 来源 1-ios,2-android,3-h5,4-api,5-后台网址,6-系统
		registerOP.setSource("3");

		registerOP.setIp(ip);

		// 密码加密
		registerOP.setPassword(LoginUtils.pwdToSHA1(password));

		registerOP.setRealName(op.getName());

		String userId = custUserService.saveRegister(registerOP);
		if (StringUtils.equals(userId, "0")) {// 注册失败
			logger.info("注册失败");
			return null;
		}

		request.setAttribute("password", password);
		request.setAttribute("phoneNo", op.getPhoneNo());

		// 发送短信
		SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
		sendShortMsgOP.setIp(ip);
		sendShortMsgOP.setMobile(op.getPhoneNo());
		sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD, password));
		sendShortMsgOP.setUserId(userId);
		sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
		sendShortMsgOP.setSource(SourceEnum.H5.getCode());
		sendShortMsgOP.setChannelId("xiaoheiyu");
		shortMsgService.sendMsg(sendShortMsgOP);

		logger.info("注册成功");
		return downloadPage;
	}

	private XiaoheiyuVO getErrorRtn(ErrInfo errInfo, Long start) {
		XiaoheiyuVO vo = new XiaoheiyuVO();
		vo.setSuccess(false);
		vo.setCode(errInfo.getCode());
		vo.setCost_time(Long.valueOf((System.currentTimeMillis() - start)).intValue());
		vo.setMsg(errInfo.getMsg());
		vo.setData(new XiaoheiyuDataVO());
		vo.getData().setStatus("1");
		logger.info("返回数据：" + JSONObject.toJSONString(vo));
		return vo;
	}

	private XiaoheiyuVO getSuccessRtn(String status, ErrInfo errInfo, Long start) {
		XiaoheiyuVO vo = new XiaoheiyuVO();
		vo.setSuccess(true);
		vo.setCode(errInfo.getCode());
		vo.setCost_time(Long.valueOf((System.currentTimeMillis() - start)).intValue());
		vo.setMsg(errInfo.getMsg());
		vo.setData(new XiaoheiyuDataVO());
		vo.getData().setStatus(status);

		logger.info("返回数据：" + JSONObject.toJSONString(vo));
		return vo;
	}

	/**
	 * 
	 * @Title: readXiaoheiyuOPFromReq @Description: 读取请求body内容 @param request @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	private static XiaoheiyuOP readXiaoheiyuOPFromReq(HttpServletRequest request) {
		BufferedReader reader = null;
		XiaoheiyuOP op = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String rtn = IOUtils.read(reader);
			op = (XiaoheiyuOP) JsonMapper.fromJsonString(rtn, XiaoheiyuOP.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return op;
	}
}
