package com.rongdu.loans.api.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.SendMSGUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.VerifyCodeUtil;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.CommonUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.vo.CustVO;
import com.rongdu.loans.api.vo.SendMessageOP;
import com.rongdu.loans.api.vo.WapRegisterOP;
import com.rongdu.loans.basic.option.BasicBlacklistOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.CustUserGroupService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanProductService;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * 登录Controller
 * 
 * @author zcb
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/wap")
public class WapController extends BaseController {

	@Autowired
	private SessionDAO sessionDAO;

	@Autowired
	private CustUserService custUserService;

	@Autowired
	private LoanProductService loanProductService;

	@Autowired
	private LoanApplyService loanApplyService;

	@Autowired
	private ShortMsgService shortMsgService;

	@Autowired
	private CustUserGroupService custUserGroupService;

	/**
	 * H5用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, name = "H5用户注册")
	@ResponseBody
	public ApiResult register(@Valid WapRegisterOP param, Errors errors, HttpServletRequest request) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		String userRegisterLockCacheKey = "user_register_lock_" + param.getAccount();
		synchronized (WapController.class) {
			String userRegisterLock = JedisUtils.get(userRegisterLockCacheKey);
			if (userRegisterLock == null) {
				// 加锁，防止并发
				JedisUtils.set(userRegisterLockCacheKey, "locked", 120);
			} else {
				logger.warn("注册接口调用中，account= {}", param.getAccount());
				// 处理中
				result.set(ErrInfo.WAITING);
				return result;
			}
		}
		try {
			String userId = "";
			if (null != param) {
				// 短信验证码认证
				String msgVerCode = param.getMsgVerCode();
				// 获取缓存中的短信验证码
				String cache = JedisUtils.get(param.getAccount() + Global.REG_MCODE_SUFFIX);
				if (StringUtils.equals(msgVerCode, cache)) {
					// 手机号处理
					String mob = StringUtils.delSpace(param.getAccount());
					if (!StringUtils.startsWithIgnoreCase(mob, "1") || mob.length() != 11) {
						// 手机号异常
						result.set(ErrInfo.MOB_ERROR);
						return result;
					}
					param.setAccount(mob);
					// 账户是否已经注册
					boolean rz = custUserService.isRegister(param.getAccount());
					if (rz) {
						// 账户已经存在
						result.set(ErrInfo.REG_USER_EXISTS);
						return result;
					}
					// 注册账户
					// 截取ip
					String ip = Servlets.getIpAddress(request);
					param.setIp(ip);
					// 密码加密
					param.setPassword("a123456");
					param.setPassword(LoginUtils.pwdToSHA1(param.getPassword()));
					RegisterOP op = BeanMapper.map(param, RegisterOP.class);
					userId = custUserService.saveRegister(op);
					if (StringUtils.equals(userId, "0")) {
						result.set(ErrInfo.REG_ERROR);
						return result;
					}
				} else {
					result.set(ErrInfo.MSG_ERROR);
					return result;
				}
				// 初始化返回值
				CustVO vo = new CustVO();
				// 判断tokenId是否为空，为空则生成
				String tokenId = LoginUtils.generateTokenId(userId);
				// 判断appkey是否为空，为空则生成
				String appKey = LoginUtils.generateAppKey(userId);

				vo.setAppKey(appKey);
				vo.setTokenId(tokenId);
				vo.setAccount(param.getAccount());
				vo.setUserId(userId);
				result.setData(vo);
			}
		} finally {
			// 移除锁
			JedisUtils.del(userRegisterLockCacheKey);
		}

		return result;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendMsgCode", method = RequestMethod.POST, name = "H5发送短信验证码(未登录)")
	@ResponseBody
	public ApiResult sendMsgVerCodeH5(@Valid SendMessageOP param, Errors errors, HttpServletRequest request)
			throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 构建返回对象
		ApiResult result = new ApiResult();
		
	/*		
	 * 图片验证码
	 * 
 	if (StringUtils.isBlank(param.getImageCode())) {
			result.setCode("FAIL");
			result.setMsg("图片验证码不能为空");
			return result;
		}
		String cacheKey = "captcha_" + param.getAccount();
		String verifyCode = JedisUtils.get(cacheKey);
		if (StringUtils.isBlank(verifyCode) || !verifyCode.equals(param.getImageCode())) {
			result.setCode("FAIL");
			result.setMsg("图片验证码验证失败");
			return result;
		}*/
		
		// 手机号处理
		String mob = StringUtils.delSpace(param.getAccount());
		if (!StringUtils.startsWithIgnoreCase(mob, "1") || mob.length() != 11) {
			// 手机号异常
			result.set(ErrInfo.MOB_ERROR);
			return result;
		}
		param.setAccount(mob);
		// 短信类型处理
		int msgType;
		try {
			msgType = Integer.parseInt(param.getMsgType());
		} catch (NumberFormatException e) {
			logger.warn("请输入有效的短信验证类型");
			return result.set(ErrInfo.MSG_TYPE_ERROR);
		}
		if ("sudai".equals(param.getChannel())) {
			logger.error("拦截速贷之家渠道注册");
			return result.set(ErrInfo.MSG_FREQUENTLY);
		}

		// User-Agent过滤
		// if(CommonUtils.filterUserAgent(
		// request.getHeader("User-Agent"))) {
		// logger.error("sendMsgVerCode User-Agent limit!");
		// return result;
		// }
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		// 请求过滤
		if (msgType != Global.MSG_TYPE_REG) {
			// 判断账户是否已经注册
			if (!custUserService.isRegister(param.getAccount())) {
				// 未注册账户提示
				result.set(ErrInfo.REG_USER_UNEXISTS);
				return result;
			}
		} else {
			// 判断账户是否已经注册
			if (custUserService.isRegister(param.getAccount())) {
				// 注册账户提示
				result.set(ErrInfo.REG_USER_EXISTS);
				return result;
			}
			// 注册短信时--ip黑名单过滤
			if (shortMsgService.isInBlackListTab(ip, param.getAccount())) {
				// if(shortMsgService.isRegBlackList(ip)) {
				// result.set(ErrInfo.MSG_FREQUENTLY);
				logger.error("sendMsgVerCode ip BlackList limit!");
				return result;
			}
		}
		// 渠道
		String channel = param.getChannel();
		if (StringUtils.isBlank(channel)) {
			channel = ChannelEnum.JUQIANBAO.getCode();
			param.setChannel(channel);
		}
		// ip、手机号次数过滤
		String filterRz = SendMSGUtils.filterMsgSend(param.getAccount(), msgType, ip);
		if (StringUtils.equals(filterRz, SendMSGUtils.LR_YES)) {
			// result.set(ErrInfo.MSG_FREQUENTLY);
			logger.error(ErrInfo.MSG_FREQUENTLY.getMsg());
			return result;
		} else if (StringUtils.equals(filterRz, SendMSGUtils.LR_BL_MOB)) {
			// 添加黑名单
			saveBlackList(SendMSGUtils.BLACKLIST_TYPE_MOB, param.getAccount(), ChannelEnum.JUQIANBAO.getCode());
			// result.set(ErrInfo.MSG_FREQUENTLY);
			logger.error(ErrInfo.MSG_FREQUENTLY.getMsg());
			return result;
		} else if (StringUtils.equals(filterRz, SendMSGUtils.LR_BL_IP)) {
			// 添加黑名单
			saveBlackList(SendMSGUtils.BLACKLIST_TYPE_IP, ip, ChannelEnum.JUQIANBAO.getCode());
			// result.set(ErrInfo.MSG_FREQUENTLY);
			logger.error(ErrInfo.MSG_FREQUENTLY.getMsg());
			return result;
		}
		// 发送验证码
		// 调短息验证码服务获取短信验证码
		String msgVerCode = CommonUtils.sendMessage(param.getAccount(), msgType, ip, param.getSource(), null, channel,
				shortMsgService);
		if (StringUtils.equals(msgVerCode, Global.FALSE)) {
			return result.set(ErrInfo.MSG_TYPE_ERROR);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/validImage", name = "H5验证图片验证码")
	public void ValidateCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String phone = request.getParameter("phone");
		if (StringUtils.isBlank(phone)) {
			return;
		}
		String cacheKey = "captcha_" + phone;
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
		JedisUtils.set(cacheKey, verifyCode, 4 * 60);

		// 输出图象到页面
		VerifyCodeUtil.outputImage(60, 30, response.getOutputStream(), verifyCode);
	}

	/**
	 * 添加黑名单
	 * 
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
