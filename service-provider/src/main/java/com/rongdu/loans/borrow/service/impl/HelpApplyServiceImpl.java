/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.borrow.entity.HelpAllot;
import com.rongdu.loans.borrow.entity.HelpApply;
import com.rongdu.loans.borrow.manager.HelpAllotManager;
import com.rongdu.loans.borrow.manager.HelpApplyManager;
import com.rongdu.loans.borrow.option.HelpApplyForListOP;
import com.rongdu.loans.borrow.option.HelpApplyOP;
import com.rongdu.loans.borrow.service.HelpApplyService;
import com.rongdu.loans.borrow.vo.HelpApplyVO;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.enums.MsgTypeEnum;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.loan.entity.User;
import com.rongdu.loans.loan.manager.UserManager;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.pay.op.XfAgreementAdminPayOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.XianFengAgreementPayService;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

/**
 * 助贷申请表-业务逻辑实现类
 * 
 * @author liuliang
 * @version 2018-08-28
 */
@Service("helpApplyService")
public class HelpApplyServiceImpl extends BaseService implements HelpApplyService {

	/**
	 * 助贷申请表-实体管理接口
	 */
	@Autowired
	private HelpApplyManager helpApplyManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private HelpAllotManager helpAllotManager;
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private BaofooAgreementPayService baofooAgreementPayService;
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private XianFengAgreementPayService xianFengAgreementPayService;
	@Autowired
	private ShortMsgService shortMsgService;
	@Autowired
	private RepayLogService repayLogService;
	@Resource(name = "myexecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	public List<HelpApplyVO> getHelpApplyList() {
		return helpApplyManager.getHelpApplyList();
	}

	/**
	 * 助贷产品分配定时任务---无效，分配表不用 直接插入申请表分配用户信息
	 * 
	 * @return
	 */
	public TaskResult borrowHelpAllot() {
		logger.info("开始执行助贷产品分配任务。");
		long starTime = System.currentTimeMillis();
		int success = 0;
		int fail = 0;

		/** 查询助贷申请表中待推送的数据用于分配 */
		List<HelpApplyVO> allotList = helpApplyManager.getHelpApplyList();
		if (CollectionUtils.isEmpty(allotList)) {
			long endTime = System.currentTimeMillis();
			logger.info("执行助贷产品分配任务结束，暂无可分配数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}

		/** 查询可被分配的用户 */
		List<User> userList = userManager.getUserByRole("help_borrow");
		if (CollectionUtils.isEmpty(userList)) {
			long endTime = System.currentTimeMillis();
			logger.info("没有可供分配的用户。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 平均分配
		int total = userList.size();
		int m = 0;
		List<HelpAllot> helpAllot = new ArrayList<HelpAllot>();
		for (HelpApplyVO allot : allotList) {
			HelpAllot borrowAllot = new HelpAllot();
			borrowAllot.setBorrowId(allot.getId());
			borrowAllot.setUserId(userList.get(m % total).getId());
			borrowAllot.setUserName(userList.get(m % total).getName());
			helpAllot.add(borrowAllot);
			m++;
		}
		if (!CollectionUtils.isEmpty(helpAllot)) {
			success = helpAllotManager.insertBatch(helpAllot);
		}
		long endTime = System.currentTimeMillis();
		logger.info("执行营销管理分配任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

	/**
	 * 查询被分配的助贷列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<HelpApplyVO> getBorrowHelpList(@NotNull(message = "分页参数不能为空") Page page, HelpApplyForListOP op) {
		List<HelpApplyVO> voList = helpApplyManager.getBorrowHelpList(page, op);
		if (CollectionUtils.isEmpty(voList)) {
			page.setList(Collections.emptyList());
			return page;
		}
		page.setList(voList);
		return page;
	}

	public HelpApplyVO getHelpById(String id) {
		return BeanMapper.map(helpApplyManager.getById(id), HelpApplyVO.class);
	}

	@Override
	public String borrowHelpApply(HelpApplyOP op) {
		String exclusiveSalesman = null;
		String requestId = String.valueOf(System.nanoTime());// 请求标识
		// 第一步：插入助贷申请表
		// 用户注册-添加redis同步锁
		String userBorrowHelpLockCacheKey = "user_borrowhelp_lock_" + op.getMobile();
		boolean borrowHelplock = JedisUtils.setLock(userBorrowHelpLockCacheKey, requestId, 2 * 60);
		if (!borrowHelplock) {
			logger.warn("助贷服务请求正在提交中，mobile = {}", op.getMobile());
			throw new RuntimeException("助贷服务请求正在提交中，请勿重复提交，mobile =" + op.getMobile());
		}

		HelpApply helpApply = new HelpApply();
		try {
			Long payCount = repayLogService.countPayingByIdNo(op.getIdNo());
			if (payCount != 0) {
				logger.warn("助贷服务正在处理中，mobile = {}", op.getMobile());
				throw new RuntimeException("助贷服务正在处理中，请稍后再试，mobile =" + op.getMobile());
			}
			HelpApply applyAccept = helpApplyManager.getBorrowHelpByIdNo(op.getIdNo());
			if (null != applyAccept) {
				logger.warn("助贷服务已支付成功，请24小时后再行申请，mobile = {}", op.getMobile());
				throw new RuntimeException("助贷服务已支付成功，请联系专属业务员QQ：" + applyAccept.getRemark());
			}
			helpApply.setApplyTime(new Date());
			helpApply.setMobile(op.getMobile());
			helpApply.setStatus(0);
			helpApply.setSource(op.getSource());
			helpApply.setUserName(op.getUserName());
			helpApply.setServiceAmt(new BigDecimal(op.getServiceAmt()));
			helpApply.setCardNo(op.getCardNo());
			helpApply.setIdNo(op.getIdNo());
			helpApply.setPayTime(new Date());
			helpApply.setPayDate(DateUtils.getYYYYMMDD2Int());
			int ha = helpApplyManager.insert(helpApply);
			if (ha != 1) {
				logger.warn("助贷申请表插入异常，mobile = {}", op.getMobile());
				return "ERROR";
			}

			/*
			 * // 第二步：查询是否已注册用户 boolean register =
			 * custUserService.isRegister(op.getMobile()); String userId = "";
			 * if (!register) { //用户注册-添加redis同步锁 String
			 * userRegisterLockCacheKey =
			 * RedisLockCacheKeyEnum.USER_REGISTER_LOCK.getKey()
			 * +op.getMobile(); boolean lock =
			 * JedisUtils.setLock(userRegisterLockCacheKey, requestId, 2*60); if
			 * (!lock) { logger.warn("此手机号正在注册处理中，mobile = {}", op.getMobile());
			 * throw new
			 * RuntimeException("此手机号正在注册处理中，请稍后再试，mobile ="+op.getMobile()); }
			 * try { // 未注册用户先进行注册 RegisterOP registerOP = new RegisterOP();
			 * registerOP.setAccount(op.getMobile()); int pwd = new
			 * Random().nextInt(999999);
			 * registerOP.setPassword(LoginUtils.pwdToSHA1
			 * (String.valueOf(pwd))); registerOP.setIp(op.getIp());
			 * registerOP.setRealName(op.getUserName());
			 * registerOP.setChannel("helpborrow");
			 * registerOP.setSource(op.getSource()); userId =
			 * custUserService.saveRegister(registerOP); if
			 * (StringUtils.equals(userId, "0")) {
			 * logger.warn("用户注册异常，mobile = {}",op.getMobile()); return false; }
			 * // 注册成功发送短信(异步执行) final SendShortMsgOP sendShortMsgOP = new
			 * SendShortMsgOP(); sendShortMsgOP.setIp(op.getIp());
			 * sendShortMsgOP.setMobile(op.getMobile());
			 * sendShortMsgOP.setMessage
			 * (String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD, pwd));
			 * sendShortMsgOP.setUserId(userId);
			 * sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
			 * sendShortMsgOP.setSource(SourceEnum.H5.getCode());
			 * sendShortMsgOP.setChannelId("helpborrow"); Runnable runnable =
			 * new Runnable() {
			 * 
			 * @Override public void run() {
			 * shortMsgService.sendMsg(sendShortMsgOP); } };
			 * threadPoolTaskExecutor.execute(runnable); } catch (Exception e) {
			 * logger.error("用户注册异常，mobile = {}",op.getMobile(), e); return
			 * false; }finally { // 移除锁
			 * JedisUtils.releaseLock(userRegisterLockCacheKey, requestId); }
			 * }else { CustUserVO custUserVO =
			 * custUserService.getCustUserByMobile(op.getMobile()); userId =
			 * custUserVO.getId(); }
			 * 
			 * 
			 * // 第三步：未绑卡用户先进行绑卡 if (!StringUtils.isBlank(op.getVerifyCode())) {
			 * //确认绑卡-添加redis同步锁 String userConfirmBindCardCacheKey =
			 * RedisLockCacheKeyEnum.USER_CONFIRM_BINDCARD_LOCK.getKey()
			 * +op.getCardNo(); boolean lock =
			 * JedisUtils.setLock(userConfirmBindCardCacheKey, requestId, 2*60);
			 * if (!lock) { logger.warn("此银行卡正在绑定处理中，mobile = {}，cardNo = {}",
			 * op.getMobile(),op.getCardNo()); throw new
			 * RuntimeException("此银行卡正在绑定处理中，请稍后再试，mobile = "
			 * +op.getMobile()+"，cardNo = "+op .getCardNo()); } try {
			 * ConfirmBindCardOP confirmBindCardOP = new ConfirmBindCardOP();
			 * confirmBindCardOP.setUserId(userId);
			 * confirmBindCardOP.setMsgVerCode(op.getVerifyCode()); String input
			 * = op.getIdNo() + op.getCardNo() + op.getMobile(); String
			 * bindCardId = Digests.md5(input); BindCardVO bindInfo =
			 * bindCardService.get(bindCardId); if (bindInfo == null) {
			 * logger.warn("绑卡信息为空，mobile = {}，cardNo = {}", op.getMobile(),
			 * op.getCardNo()); return false; }
			 * confirmBindCardOP.setBindId(bindInfo.getBindId());
			 * confirmBindCardOP.setOrderNo(bindInfo.getChlOrderNo());
			 * confirmBindCardOP.setSource(op.getSource());
			 * confirmBindCardOP.setType(1); BindCardResultVO bindCardResult =
			 * baofooAgreementPayService
			 * .agreementConfirmBind(confirmBindCardOP); if
			 * (!bindCardResult.isSuccess() ||
			 * StringUtils.isBlank(bindCardResult.getBindId())) {
			 * logger.warn("确认绑卡失败：{}，mobile = {}，cardNo = {}",
			 * bindCardResult.getMsg(),op.getMobile(),op.getCardNo()); throw new
			 * RuntimeException("确认绑卡失败："+bindCardResult.getMsg()); }
			 * bindInfo.setBindId(bindCardResult.getBindId());
			 * bindInfo.setStatus(bindCardResult.getCode());
			 * bindInfo.setRemark(bindCardResult.getMsg());
			 * bindInfo.setChlName("宝付确认绑卡");
			 * 
			 * int saveBc = bindCardService.update(bindInfo); if (saveBc != 1) {
			 * logger.warn("绑卡信息更新异常，mobile = {}，cardNo = {}", op.getMobile(),
			 * op.getCardNo()); return false; } if (bindCardResult.isSuccess())
			 * { IdentityInfoOP identityInfoOP = new IdentityInfoOP();
			 * identityInfoOP.setTrueName(bindInfo.getName());
			 * identityInfoOP.setCardNo(bindInfo.getCardNo());
			 * identityInfoOP.setIdNo(bindInfo.getIdNo());
			 * identityInfoOP.setProtocolNo(bindCardResult.getBindId());
			 * identityInfoOP.setUserId(userId);
			 * identityInfoOP.setSource(op.getSource());
			 * identityInfoOP.setBankCode(bindCardResult.getBankCode());
			 * identityInfoOP.setProductId("XJD");
			 * identityInfoOP.setAccount(bindInfo.getMobile());
			 * identityInfoOP.setBankMobile(op.getMobile()); int saveRz =
			 * custUserService.saveIdentityInfo(identityInfoOP); if (saveRz !=
			 * 1) { logger.warn("确认绑卡信息插入异常，mobile = {}，cardNo = {}",
			 * op.getMobile(), op.getCardNo()); return false; } } } catch
			 * (Exception e) {
			 * logger.error("确认绑卡异常，mobile = {}，cardNo = {}",op.getMobile
			 * (),op.getCardNo(),e ); return false; }finally { // 移除锁
			 * JedisUtils.releaseLock(userConfirmBindCardCacheKey, requestId); }
			 * // 更新缓存中的用户信息 CustUserVO custUserVO =
			 * custUserService.getCustUserByMobile(op.getMobile());
			 * LoginUtils.cacheCustUserInfo(custUserVO); }
			 */

			// 第四步：支付服务费-先锋支付
			XfAgreementAdminPayOP xfAgreementAdminPayOP = new XfAgreementAdminPayOP();
			xfAgreementAdminPayOP.setAmount(op.getServiceAmt());
			xfAgreementAdminPayOP.setCardNo(op.getCardNo());
			xfAgreementAdminPayOP.setIdNo(op.getIdNo());
			xfAgreementAdminPayOP.setMobile(op.getMobile());
			xfAgreementAdminPayOP.setUserName(op.getUserName());
			xfAgreementAdminPayOP.setPayType(PayTypeEnum.BORROWHELP.getId());
			xfAgreementAdminPayOP.setRemark("助贷服务费");
			xfAgreementAdminPayOP.setTxType("AUTO_PAY");
			xfAgreementAdminPayOP.setApplyId(helpApply.getId());

			XfAgreementPayResultVO xfAgreementPayResultVO = xianFengAgreementPayService
					.agreementAdminPay(xfAgreementAdminPayOP);
			helpApply.setRetCode(xfAgreementPayResultVO.getResCode());
			helpApply.setRetMsg(xfAgreementPayResultVO.getResMessage());
			if ("I".equals(xfAgreementPayResultVO.getStatus())) {
				Map<String, Object> allotHelp = allotHelp(helpApply.getId(),helpApply.getIdNo());
				if (null == allotHelp) {
					return "ERROR";
				}
				exclusiveSalesman = String.valueOf(allotHelp.get("qq"));
				helpApply.setAllotUserId(String.valueOf(allotHelp.get("userId")));
				helpApply.setAllotUserName(String.valueOf(allotHelp.get("userName")));
				helpApply.setRemark(exclusiveSalesman);// 记录专属业务员qq
				helpApply.setStatus(2);
			}
			// helpApply.setUserId(userId);
			int updateResult = helpApplyManager.updateConcurent(helpApply);
			if (updateResult != 1) {
				helpApply.setRetCode(null);
				helpApply.setRetMsg(null);
				helpApply.setStatus(null);
				updateResult = helpApplyManager.updateByIdSelective(helpApply);
				if (updateResult != 1) {
					logger.warn("助贷申请表更新异常，mobile = {}", op.getMobile());
					return "ERROR";
				}
			}
			if ("F".equals(xfAgreementPayResultVO.getStatus())) {
				// 服务费支付失败
				logger.warn("助贷服务费支付失败：{},mobile = {}", xfAgreementPayResultVO.getResMessage(), op.getMobile());
				throw new RuntimeException("助贷服务费支付失败：" + xfAgreementPayResultVO.getResMessage());
			}
		} finally {
			// 移除锁
			JedisUtils.releaseLock(userBorrowHelpLockCacheKey, requestId);
		}
		return exclusiveSalesman;
	}

	@Override
	public void borrowHelpPaid(HelpApplyOP op) {
		HelpApply helpApply = helpApplyManager.getById(op.getId());
		if (null != helpApply) {
			helpApply.setRetCode(op.getRetCode());
			helpApply.setRetMsg(op.getRetMsg());
			helpApply.setStatus(op.getStatus());
			helpApply.setPayTime(op.getPayTime());
			helpApply.setPayDate(op.getPayDate());
			helpApplyManager.update(helpApply);
			if (op.getStatus() == 1) {
				//支付成功发送短信
				SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
				sendShortMsgOP.setMobile(helpApply.getMobile());
				sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.HELP_APPLY_SUCCESS,helpApply.getRemark()));
				sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
				sendShortMsgOP.setSource(helpApply.getSource());
				sendShortMsgOP.setChannelId("helpborrow");
				shortMsgService.sendMsg(sendShortMsgOP);
			}
		}
	}

	/**
	 * 申请了之后直接随机分配用户，返回营销员QQ
	 * 
	 * @param borrowId
	 * @return
	 */
	public Map<String, Object> allotHelp(String borrowId, String idNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HelpApply helpApply = helpApplyManager.getHelpApplyByIdNo(idNo);
			if (helpApply != null) {
				map.put("userId", helpApply.getAllotUserId());
				map.put("userName", helpApply.getAllotUserName());
				map.put("qq", helpApply.getRemark());
				return map;
				
			} else {
				/** 查询可被分配的用户 */
				List<User> userList = userManager.getUserByRole("help_borrow");
				if (CollectionUtils.isEmpty(userList)) {
					logger.info("没有可供分配的用户。");
					return null;
				}
				Random random = new Random();
				int total = userList.size();
				int m = random.nextInt(total);

				map.put("userId", userList.get(m).getId());
				map.put("userName", userList.get(m).getName());
				map.put("qq", userList.get(m).getMobile());
				return map;
				

			}
		} catch (Exception e) {
			logger.info("系统异常");
			return null;
		}
	}

	/**
	 * 更新助贷申请表，分配的用户信息
	 * 
	 * @param borrowId
	 * @param userId
	 * @param userName
	 * @return
	 */
	public int updateAllotApply(String borrowId, String userId, String userName,String updateBy) {
		return helpApplyManager.updateAllotApply(borrowId, userId, userName, updateBy);
	}

}