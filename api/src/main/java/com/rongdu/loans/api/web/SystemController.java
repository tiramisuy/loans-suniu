package com.rongdu.loans.api.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.Encodes;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.SendMSGUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.XianJinCardUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.AuthenticationType;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.CommonUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.vo.CustVO;
import com.rongdu.loans.api.vo.SendMessageOP;
import com.rongdu.loans.basic.option.BasicBlacklistOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.cust.option.ForgetPwdOP;
import com.rongdu.loans.cust.option.LoginOP;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.option.UpdatePwdOP;
import com.rongdu.loans.cust.service.CustUserGroupService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.ExtensionPlatformEnums;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.option.xjbk.EnSureAgreement;
import com.rongdu.loans.loan.service.ApplyTripartiteService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanProductService;
import com.rongdu.loans.loan.service.LoanTrafficService;
import com.rongdu.loans.loan.service.PromotionCaseService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.HomeLoanVO;
import com.rongdu.loans.loan.vo.LoanProductVO;
import com.rongdu.loans.loan.vo.PromotionCaseVO;
import com.rongdu.loans.pay.service.XianFengAgreementPayService;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * 登录Controller
 * 
 * @author likang
 * @version 2017-6-12
 */
@Controller
public class SystemController extends BaseController {

	@Autowired
	private CustUserService custUserService;

	@Autowired
	private LoanTrafficService loanTrafficService;

	@Autowired
	private LoanProductService loanProductService;

	@Autowired
	private LoanApplyService loanApplyService;

	@Autowired
	private ShortMsgService shortMsgService;

	@Autowired
	private CustUserGroupService custUserGroupService;

	@Autowired
	private PromotionCaseService promotionCaseService;
	@Autowired
	private ApplyTripartiteService applyTripartiteService;
	@Autowired
	private XianFengAgreementPayService xianFengAgreementPayService;

	/**
	 * Shiro登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/login", method = RequestMethod.POST, name = "登录")
	@ResponseBody
	public ApiResult login(@Valid LoginOP param, Errors errors, HttpServletRequest request) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		if (null != param) {
			// 用户冻结状态判断
			CustUserVO cust = custUserService.getCustUserByMobile(param.getAccount());
			if (null != cust && cust.getStatus().equals(Global.LOCK_USER_FLAG)) {
				result.set(ErrInfo.USER_LOCK);
				return result;
			}
			// 账户是否被锁定 判断
			if (LoginUtils.isLockedAccount(param.getAccount())) {
				result.set(ErrInfo.PWDERROR_FREQUENTLY);
				return result;
			}
			// 密码加密
			param.setPassword(LoginUtils.pwdToSHA1(param.getPassword()));
			// 创建用户名和密码的令牌
			UsernamePasswordToken token = new UsernamePasswordToken(param.getAccount(), param.getPassword());
			// 记录该令牌
			token.setRememberMe(true);
			// subject权限对象,类似user
			Subject subject = SecurityUtils.getSubject();
			// 有效时间1小时
			SecurityUtils.getSubject().getSession().setTimeout(3600000L);
			// 登录方式:1-账号密码登录, 2-Token登录(没启用),3-短信密码登录
			AuthenticationType authenticationType = AuthenticationType.LOGIN;
			// 登录方式为短信密码登录
			if ("2".equals(param.getLoginType())) {
				authenticationType = AuthenticationType.MSG_PWD_LOGIN;
			}
			LoginUtils.authenticationType.set(authenticationType);
			subject.login(token);
			CustUserVO shiroUser = (CustUserVO) subject.getPrincipal();
			// 用户id
			String userId = shiroUser.getId();
			// 判断tokenId是否为空，为空则生成
			String tokenId = LoginUtils.generateTokenId(userId);
			// 判断appkey是否为空，为空则生成
			String appKey = LoginUtils.generateAppKey(userId);

			// 截取当前ip
			String currentIp = Servlets.getIpAddress(request);
			param.setCurrentIp(currentIp);
			// 上次登录ip
			param.setLastIp(shiroUser.getLoginIp());
			// 上次登录时间
			param.setLastLoginTime(shiroUser.getLoginTime());
			// 登录次数
			int loginNum = shiroUser.getLoginNum() == null ? 1 : shiroUser.getLoginNum() + 1;
			param.setLoginNum(loginNum);
			// 更新登录信息
			param.setUserId(userId);
			custUserService.updateLoginRecord(param);
			// 清理用户信息缓存，以便更新
			LoginUtils.cleanCustUserInfoCache(userId);
			// 删除缓存用户认证信息
			JedisUtils.delObject(Global.USER_AUTH_PREFIX + userId);
			// 初始化返回值
			CustVO vo = new CustVO();
			// 设置返回值
			vo.setAppKey(appKey);
			vo.setTokenId(tokenId);
			vo.setUserId(userId);
			vo.setTrueName(shiroUser.getRealName());
			vo.setAccount(shiroUser.getMobile());
			vo.setIdType(shiroUser.getIdType());
			vo.setIdNo(shiroUser.getIdNo());
			vo.setSex(shiroUser.getSex());
			vo.setAvatarUrl(shiroUser.getAvatar());
			result.setData(vo);
		}
		return result;
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/register", method = RequestMethod.POST, name = "用户注册")
	@ResponseBody
	public ApiResult register(@Valid RegisterOP param, Errors errors, HttpServletRequest request) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		randomSleep();
		ApiResult result = new ApiResult();
		String userRegisterLockCacheKey = "user_register_lock_" + param.getAccount();
		synchronized (SystemController.class) {
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
				// 诚诚贷验证门店，组
				if (ChannelEnum.CHENGDAI.getCode().equals(param.getChannel())) {
					if (StringUtils.isBlank(param.getCompanyId())) {
						logger.error("门店不能为空,account={}", param.getAccount());
						result.setCode("FAIL");
						result.setMsg("门店不能为空");
						return result;
					}
					if (StringUtils.isBlank(param.getGroupId())) {
						logger.error("组不能为空,account={}", param.getAccount());
						result.setCode("FAIL");
						result.setMsg("组不能为空");
						return result;
					}
				}
				// 旅游分期验证门店
				if (ChannelEnum.LYFQAPP.getCode().equals(param.getChannel())) {
					if (StringUtils.isBlank(param.getCompanyId())) {
						logger.error("门店不能为空,account={}", param.getAccount());
						result.setCode("FAIL");
						result.setMsg("门店不能为空");
						return result;
					}
				}
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
					param.setPassword(LoginUtils.pwdToSHA1(param.getPassword()));
					userId = custUserService.saveRegister(param);
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

				// 诚诚贷 记录门店，分组
				if (ChannelEnum.CHENGDAI.getCode().equals(param.getChannel())) {
					custUserGroupService.insert(param.getCompanyId(), param.getGroupId(), userId);
				}

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
	 * 忘记密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/forget", method = RequestMethod.POST, name = "忘记密码")
	@ResponseBody
	public ApiResult forget(@Valid ForgetPwdOP param, Errors errors) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		// 短信验证码认证
		String msgVerCode = param.getMsgVerCode();
		// 获取缓存中的短信验证码
		String cache = JedisUtils.get(param.getAccount() + Global.FORGET_MCODE_SUFFIX);
		if (!StringUtils.equals(msgVerCode, cache)) {
			result.set(ErrInfo.MSG_ERROR);
			return result;
		}
		result.setData(LoginUtils.genUpdatePwdKey(param.getAccount()));
		return result;
	}

	/**
	 * 修改交易密码的短信认证
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/checkUpdPayPwd", method = RequestMethod.POST, name = "修改交易密码的短信认证")
	@ResponseBody
	public ApiResult checkUpdPayPwd(@Valid ForgetPwdOP param, Errors errors) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		// 短信验证码认证
		String msgVerCode = param.getMsgVerCode();
		// 获取缓存中的短信验证码
		String cache = JedisUtils.get(param.getAccount() + Global.BANKDEPOSIT_SRVAUTHCODE_SUFFIX);
		if (!StringUtils.equals(msgVerCode, cache)) {
			result.set(ErrInfo.MSG_ERROR);
		}
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/updatePwdFromForget", method = RequestMethod.POST, name = "修改密码")
	@ResponseBody
	public ApiResult updatePwdFromForget(@Valid UpdatePwdOP param, Errors errors) throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		if (StringUtils.isBlank(param.getUpdateToken())) {
			result.set(ErrInfo.UPDATE_TOKEN);
			return result;
		}
		// 获取缓存中的修改密码密钥
		String cacheToken = LoginUtils.getUpdatePwdKey(param.getAccount());
		if (!StringUtils.equals(param.getUpdateToken(), cacheToken)) {
			result.set(ErrInfo.UPDATE_TOKEN);
			return result;
		}
		// 密码加密
		param.setPassword(LoginUtils.pwdToSHA1(param.getPassword()));
		int updateRz = custUserService.updatePwd(param);
		if (updateRz < 1) {
			result.set(ErrInfo.PWD_UPDATE_FAIL);
			return result;
		}
		result.setData(updateRz);
		// 密码修改成功短信提醒 TODO
		return result;
	}

	/**
	 * 用户是否已经注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/isRegister", method = RequestMethod.POST, name = "用户是否已经注册")
	@ResponseBody
	public ApiResult isRegister(String account) throws Exception {

		ApiResult result = new ApiResult();
		if (null != account) {
			boolean rz = custUserService.isRegister(account);
			result.setData(rz);
		} else {
			result.set(ErrInfo.BAD_REQUEST);
		}
		return result;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/sendMsgVerCode", method = RequestMethod.POST, name = "发送短信验证码(未登录)")
	@ResponseBody
	public ApiResult sendMsgVerCode(@Valid SendMessageOP param, Errors errors, HttpServletRequest request)
			throws Exception {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 构建返回对象
		ApiResult result = new ApiResult();
		if (StringUtils.isNotBlank(param.getSource()) && !StringUtils.equals(param.getSource(), "1")
				&& !StringUtils.equals(param.getSource(), "2")) {
			result.setCode("FAIL");
			result.setMsg("请求错误");
			return result;
		}
		// app不允许注册 20190312
		if(StringUtils.equals(param.getMsgType(), "1")) {
			return result;
		}
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

		// 注册
		if (msgType == Global.MSG_TYPE_REG) {
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
				logger.error("sendMsgVerCode ip BlackList limit!===" + param.getAccount());
				return result;
			}
		} else if (msgType == Global.MSG_TYPE_FORGET) {
			// 忘记密码时判断账户是否已经注册
			if (!custUserService.isRegister(param.getAccount())) {
				// 未注册账户提示
				result.set(ErrInfo.REG_USER_UNEXISTS);
				return result;
			}
		} else {
			// 短信登录,判断账户是否已经注册
			if (!custUserService.isRegister(param.getAccount())) {
				// 未注册账户提示
				result.set(ErrInfo.REG_USER_UNEXISTS);
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

	/**
	 * 获取产品信息 来源（1-ios, 2-android, 3-h5, 4-api）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/getLoanProduct", method = RequestMethod.POST, name = "获取产品信息")
	@ResponseBody
	public ApiResult getLoanProduct(String productId, Integer source) throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 参数验证结果判断
		if (StringUtils.isBlank(productId)) {
			logger.error("产品代码不能为空！");
			return result.set(ErrInfo.BAD_REQUEST);
		}
		LoanProductVO vo = loanProductService.getLoanProductDetail(productId);
		if (null != vo) {
			if (LoanProductEnum.XJD.getId().equals(productId)) {
				List<PromotionCaseVO> list = vo.getLoanProductRate();
				if (list != null && list.size() > 0) {
					for (PromotionCaseVO pvo : list) {
						pvo.setServFee(new BigDecimal(0));
					}
				}
				// if(source == 2) {
				//
				// }
			}
			result.setData(vo);
		} else {
			result.set(ErrInfo.NOTFIND_LOAN_PRODUCT);
		}
		return result;
	}

	/**
	 * 获取推广平台列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/anon/getExtensionPlatformList", name = "获取推广平台列表")
	@ResponseBody
	public ApiResult getExtensionPlatformList() {
		ApiResult result = new ApiResult();
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (ExtensionPlatformEnums platformEnums : ExtensionPlatformEnums.values()) {
			map = new HashMap<String, String>();
			map.put("name", platformEnums.getName());
			map.put("produce", platformEnums.getProduce());
			map.put("imageUrl", platformEnums.getImageUrl());
			map.put("platFormurl", platformEnums.getPlatFormurl());
			list.add(map);
		}
		result.setData(list);
		return result;
	}

	/**
	 * 获取推广平台列表vue
	 * 
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/anon/getExtensionPlatformListVue", name = "获取推广平台列表vue")
	@ResponseBody
	public ApiResult getExtensionPlatformListVue() {
		ApiResult result = new ApiResult();
		HashMap<String, Object> condition = new HashMap<String, Object>();// 加这个conditon为了以后mapper方法的扩展
		List<Map<String, Object>> list = loanTrafficService.getExtensionPlatformListByCondition(condition);
		result.setData(list);
		return result;
	}

	/**
	 * 给推广平台增加点击量
	 * 
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/anon/addHitLoanTraffic", name = "给推广平台增加点击量")
	@ResponseBody
	public ApiResult addHitLoanTraffic(HttpServletRequest request) {
		ApiResult result = new ApiResult();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			loanTrafficService.addHitLoanTraffic(id);
		}
		return result;
	}

	/**
	 * 获取现金贷产品信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/getXJDLoanProduct", name = "获取现金贷产品信息")
	@ResponseBody
	public ApiResult getXJDLoanProduct(HttpServletRequest request) throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		String showNine = request.getParameter("showNine");
		boolean isNewCust = true;
		List<LoanProductVO> vo = loanProductService.getXJDLoanProductDetail(LoanProductEnum.XJD.getId());
		if (null != vo) {
			String userId = request.getParameter("userId");
			if (StringUtils.isNotBlank(userId)) {
				// 判断是否新老客户 true：新客户
				String cacheKey = "user_over_loan_by_repay_count_" + userId;
				String sCount = JedisUtils.get(cacheKey);
				if (sCount == null) {
					int count = loanApplyService.countOverLoanByRepay(userId);
					sCount = String.valueOf(count);
					JedisUtils.set(cacheKey, sCount, Global.TWO_HOURS_CACHESECONDS);
				}
				isNewCust = Integer.parseInt(sCount) > 0 ? false : true;
			}
			for (LoanProductVO lpv : vo) {
				if (null != lpv.getLoanProductRate() && lpv.getLoanProductRate().size() > 0) {
					Iterator<PromotionCaseVO> iter = lpv.getLoanProductRate().iterator();
					while (iter.hasNext()) {
						PromotionCaseVO rateCase = iter.next();
						// 新客户只展示15天产品
						if (isNewCust) {
							if (rateCase.getPeriod().intValue() != Global.XJD_DQ_DAY_15) {
								iter.remove();
							}
						} else {
							if (rateCase.getPeriod().intValue() != Global.XJD_AUTO_FQ_DAY_28) {
								iter.remove();
							}
							// 老版本去除90天的
							/*
							 * if(StringUtils.isBlank(showNine)) { if
							 * (rateCase.getPeriod().intValue() == 90) {
							 * iter.remove(); } }
							 */
						}
					}
				}
			}

			result.setData(vo);
		} else {
			result.set(ErrInfo.NOTFIND_LOAN_PRODUCT);
		}
		return result;
	}

	/**
	 * 获取首页信息（2.0版本以上用）
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "${adminPath}/anon/getHomeLoanInfo", name = "获取首页信息")
	public ApiResult getHomeLoan(HttpServletRequest request) throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		String showNine = request.getParameter("showNine");
		HomeLoanVO loanVO = new HomeLoanVO();
		// loanVO.setHomePageType(2);
		// loanVO.setHomePageUrl("/homePage");
		// loanVO.setMemberPageType(2);
		// loanVO.setMemberPageUrl("/memberPage");
		// loanVO.setRepayPageType(2);
		// loanVO.setRepayPageUrl("/repayPage");
		boolean isNewCust = true;
		List<LoanProductVO> vo = loanProductService.getXJDLoanProductDetail(LoanProductEnum.XJD.getId());
		if (null != vo) {
			String userId = request.getParameter("userId");
			if (StringUtils.isNotBlank(userId)) {
				// 判断是否新老客户 true：新客户
				String cacheKey = "user_over_loan_by_repay_count_" + userId;
				String sCount = JedisUtils.get(cacheKey);
				if (sCount == null) {
					int count = loanApplyService.countOverLoanByRepay(userId);
					sCount = String.valueOf(count);
					JedisUtils.set(cacheKey, sCount, Global.TWO_HOURS_CACHESECONDS);
				}
				isNewCust = Integer.parseInt(sCount) > 0 ? false : true;

			}
			for (LoanProductVO lpv : vo) {
				if (null != lpv.getLoanProductRate() && lpv.getLoanProductRate().size() > 0) {
					Iterator<PromotionCaseVO> iter = lpv.getLoanProductRate().iterator();
					while (iter.hasNext()) {
						PromotionCaseVO rateCase = iter.next();
						// 新客户只展示15天产品，老客户展示全部产品
						if (isNewCust) {
							if (rateCase.getPeriod().intValue() != Global.XJD_DQ_DAY_15) {
								iter.remove();
							}
						} else {
							if (rateCase.getPeriod().intValue() != Global.XJD_AUTO_FQ_DAY_28) {
								iter.remove();
							}
							// 老版本去除90天的
							/*
							 * if(StringUtils.isBlank(showNine)) { if
							 * (rateCase.getPeriod().intValue() == 90) {
							 * iter.remove(); } }
							 */
						}
					}
				}
			}

			loanVO.setProductList(vo);
		} else {
			result.set(ErrInfo.NOTFIND_LOAN_PRODUCT);
		}
		result.setData(loanVO);
		return result;
	}

	private void randomSleep() {
		int min = 500, max = 2000;
		int randNumber = new Random().nextInt(max - min + 1) + min;
		try {
			Thread.sleep(randNumber);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "${adminPath}/anon/goEnSureAgreement", method = RequestMethod.GET, name = "确认借款")
	public String goEnSureAgreement(EnSureAgreement enSureAgreement) {
		logger.info("开始执行确认借款页面跳转");
		String sign = enSureAgreement.getSign();
		Map<String, String> treeMap = new TreeMap<>();
		treeMap.put("user_name", enSureAgreement.getUser_name());
		treeMap.put("user_phone", enSureAgreement.getUser_phone());
		treeMap.put("user_idcard", enSureAgreement.getUser_idcard());
		treeMap.put("return_url", enSureAgreement.getReturn_url());
		treeMap.put("order_sn", enSureAgreement.getOrder_sn());
		String newSign = XianJinCardUtils.getH5Sign(treeMap);
		if (!newSign.equals(sign)) {
			return "redirect: https://api.jubaoqiandai.com/#/loanInfo";
		}
		String user_phone = enSureAgreement.getUser_phone();
		CustUserVO cust = custUserService.getCustUserByMobile(user_phone);
		logger.info("==============================1");
		UsernamePasswordToken token = new UsernamePasswordToken(user_phone, cust.getPassword());
		logger.info("==============================2");
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		logger.info("==============================3");
		SecurityUtils.getSubject().getSession().setTimeout(3600000L);
		AuthenticationType authenticationType = AuthenticationType.LOGIN;
		LoginUtils.authenticationType.set(authenticationType);
		logger.info("==============================4");
		subject.login(token);
		logger.info("==============================5");
		CustUserVO shiroUser = (CustUserVO) subject.getPrincipal();
		logger.info("==============================6");
		String userId = shiroUser.getId();
		String tokenId = LoginUtils.generateTokenId(userId);
		String appKey = LoginUtils.generateAppKey(userId);
		LoginUtils.cleanCustUserInfoCache(userId);
		JedisUtils.delObject(Global.USER_AUTH_PREFIX + userId);
		String orderSn = enSureAgreement.getOrder_sn();
		String applyId = applyTripartiteService.getApplyIdByThirdId(orderSn);
		ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
		// String url = "http://47.100.113.196/#/loanInfo";
		String url = "https://api.jubaoqiandai.com/#/loanInfo";
		String returnUrl = Encodes.decodeBase64String(enSureAgreement.getReturn_url());
		String servFee = String.valueOf(applyAllotVO.getServFee());
		String approveAmt = String.valueOf(applyAllotVO.getApproveAmt());
		String approveTerm = String.valueOf(applyAllotVO.getApproveTerm());
		try {
			returnUrl = java.net.URLEncoder.encode(returnUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String requestUrl = url + "?tokenId=" + tokenId + "&userId=" + userId + "&appKey=" + appKey + "&servFee="
				+ servFee + "&approveAmt=" + approveAmt + "&approveTerm=" + approveTerm + "&returnUrl=" + returnUrl;
		logger.info("现金白卡请求地址============" + requestUrl);
		return "redirect:" + requestUrl;
	}

	@ResponseBody
	@RequestMapping(value = "${adminPath}/anon/restartAdmin", name = "重启后台tomcat")
	public String restartAdmin() {
		try {
			String shpath = "/home/deploy_service/restart_admin.sh";

			// 解决脚本没有执行权限
			ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755", shpath);
			Process process = builder.start();
			process.waitFor();

			process = Runtime.getRuntime().exec(shpath);
			int status = process.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String result = sb.toString();
			logger.info("重启日志" + result);
			if (status != 0) {
				return "restrat fail";
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return "restart success";
	}

	@ResponseBody
	@RequestMapping(value = "${adminPath}/anon/xf/q", name = "查询先锋支付结果")
	public Object queryXFPayResult(HttpServletRequest request) {
		String key = request.getParameter("key");
		if (!"123qweQWE+".equals(key)) {
			return "error key!";
		}
		String merchantNo = request.getParameter("merchantNo");
		return xianFengAgreementPayService.agreementPayQuery(merchantNo);
	}

	@ResponseBody
	@RequestMapping(value = "${adminPath}/anon/rong360KoudaiBuildBorrowInfo", name = "融360手动插入BorrowInfo")
	public Object rong360KoudaiBuildBorrowInfo(HttpServletRequest request) {
		String key = request.getParameter("key");
		if (!"123qweQWE+".equals(key)) {
			return "error key!";
		}
		String n = request.getParameter("n");
		int maxCount = Integer.parseInt(n);
		return loanApplyService.rong360KoudaiBuildBorrowInfo(maxCount);
	}
}
