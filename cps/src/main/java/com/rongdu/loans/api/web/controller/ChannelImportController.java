package com.rongdu.loans.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.SendMSGUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.CommonUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.web.option.RegOP;
import com.rongdu.loans.api.web.option.SendFnMCOP;
import com.rongdu.loans.basic.option.BasicBlacklistOP;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.enums.MsgTypeEnum;
import com.rongdu.loans.enums.SourceEnum;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * 渠道流量导入Controller
 * @author likang
 * @version 2017-10-10
 */
@Controller
@RequestMapping(value = "imp/anon")
public class ChannelImportController extends BaseController{

	@Autowired
	private CustUserService custUserService;
	
	@Autowired
	private ShortMsgService shortMsgService;
	
	/**
	 * 用户注册        
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", name="cps用户注册")
	@ResponseBody
	public ApiResult register(@Valid RegOP param,
			Errors errors, HttpServletRequest request) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		String userId = "";
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		if(null != param) {
			// 短信验证码认证
			String msgVerCode = param.getMsgVerCode();
			// 获取缓存中的短信验证码
			String cache = JedisUtils.get(
					param.getAccount()+Global.REG_MCODE_SUFFIX);
			if(StringUtils.equals(msgVerCode, cache)) {
				// 手机号判断与处理
				String mob = StringUtils.delSpace(param.getAccount());
				if(!StringUtils.startsWithIgnoreCase(mob, "1") 
						|| mob.length() != 11) {
					// 手机号异常
					result.set(ErrInfo.MOB_ERROR);
					return result;
				}
				param.setAccount(mob);
				// 账户是否已经注册
				boolean rz = custUserService.isRegister(
						param.getAccount());
				if(rz) {
					// 账户已经存在
					result.set(ErrInfo.REG_USER_EXISTS);
					return result;
				}
				// 注册账户
				RegisterOP registerOP = new RegisterOP();
				// 手机号
				registerOP.setAccount(param.getAccount());
				// 渠道
				registerOP.setChannel(param.getChannel());
				// 验证码
				registerOP.setMsgVerCode(param.getMsgVerCode());
				// 来源 1-ios,2-android,3-h5,4-api,5-后台网址,6-系统
				registerOP.setSource("3");

				registerOP.setIp(ip);
				// 密码加密
				registerOP.setPassword(
						LoginUtils.pwdToSHA1(
								param.getPassword()));
				userId = custUserService.saveRegister(registerOP);
				if(StringUtils.equals(userId, "0")) {
					result.set(ErrInfo.REG_ERROR);
					return result;
				}
			} else {
				result.set(ErrInfo.MSG_ERROR);
				return result;
			}
			// 发送短信
			if(StringUtils.equals(param.getSource(), SourceEnum.H5.getCode())) {
				SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
				sendShortMsgOP.setIp(ip);
				sendShortMsgOP.setMobile(param.getAccount());
				sendShortMsgOP.setMessage(ShortMsgTemplate.REG_SUCCESS);
				sendShortMsgOP.setUserId(userId);
				sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
				sendShortMsgOP.setSource(SourceEnum.H5.getCode());
				sendShortMsgOP.setChannelId(param.getChannel());
				shortMsgService.sendMsg(sendShortMsgOP);
			}

			// 拼装返回URL
//			StringBuilder urlbd = new StringBuilder();
//			String baseUrl = Global.getConfig("h5.reg.result.url");
//			urlbd.append(baseUrl);
//			result.setData(urlbd.toString());
		}
		return result;
	}
	
	/**
	 * 发送短信验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendFnMC", name="cps发送短信验证码1")
	@ResponseBody
	public ApiResult sendFnMC(@Valid SendFnMCOP param,
			Errors errors, HttpServletRequest request) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 构建返回对象
		ApiResult result = new ApiResult();
		
		// User-Agent过滤
		if(SendMSGUtils.filterUserAgent(
				request.getHeader("User-Agent"))) {
			logger.error("sendMsgVerCode User-Agent limit!");
			return result;
		}
		// 手机号处理
		String mob = StringUtils.delSpace(param.getAccount());
		if(!StringUtils.startsWithIgnoreCase(mob, "1")
				|| mob.length() != 11) {
			// 手机号异常
			result.set(ErrInfo.MOB_ERROR);
			return result;
		}
		param.setAccount(mob);
		// 适配短信类型
		Integer msgType = MsgTypeEnum.getCode(param.getMsgType());
		// 截取ip
		String ip = Servlets.getIpAddress(request);		
		// 请求过滤
		if(msgType != Global.MSG_TYPE_REG) {
			// 判断账户是否已经注册
			if(!custUserService.isRegister(mob)) {
				// 未注册账户提示
				result.set(ErrInfo.REG_USER_UNEXISTS);
				return result;
			}
		} else {
			// 判断账户是否已经注册
			if(custUserService.isRegister(mob)) {
				// 注册账户提示
				result.set(ErrInfo.REG_USER_EXISTS);
				return result;
			}
			// 注册短信时--ip黑名单过滤
			if(shortMsgService.isInBlackListTab(ip, mob)) {
				// result.set(ErrInfo.MSG_FREQUENTLY);
				logger.error("sendMsgVerCode ip BlackList limit!");
				return result;
			}
		}		

		// ip、手机号次数过滤
		String filterRz = 
				SendMSGUtils.filterMsgSend(param.getAccount(), msgType, ip);
		if(StringUtils.equals(filterRz, SendMSGUtils.LR_YES)) {
			// result.set(ErrInfo.MSG_FREQUENTLY);
			logger.error(ErrInfo.MSG_FREQUENTLY.getMsg());
			return result;
		} else if(StringUtils.equals(filterRz, SendMSGUtils.LR_BL_MOB)){
			// 添加黑名单
			saveBlackList(
					SendMSGUtils.BLACKLIST_TYPE_MOB,
					param.getAccount(), param.getChannel());
			// result.set(ErrInfo.MSG_FREQUENTLY);
			logger.error(ErrInfo.MSG_FREQUENTLY.getMsg());
			return result;
		} else if(StringUtils.equals(filterRz, SendMSGUtils.LR_BL_IP)){
			// 添加黑名单
			saveBlackList(
					SendMSGUtils.BLACKLIST_TYPE_IP,
					ip, param.getChannel());
			// result.set(ErrInfo.MSG_FREQUENTLY);
			logger.error(ErrInfo.MSG_FREQUENTLY.getMsg());
			return result;
		}
		// 发送验证码
		// 调短息验证码服务获取短信验证码	
		String msgVerCode = CommonUtils.sendMessage(
				param.getAccount(), msgType, ip,
				param.getSource(), null, shortMsgService);
		if(StringUtils.equals(msgVerCode, Global.FALSE)) {
			return result.set(ErrInfo.MSG_TYPE_ERROR);
		}
		return result;
	}
	
	/**
	 * 添加黑名单
	 * @param tyep
	 * @param val
	 * @param channel
	 */
	private void saveBlackList(int tyep, String val, String channel) {
		BasicBlacklistOP op = new BasicBlacklistOP();
		op.setBlType(tyep);
		op.setBlValue(val);
		op.setChannel(channel);
		shortMsgService.saveToBlackListTab(op);
	}
}
