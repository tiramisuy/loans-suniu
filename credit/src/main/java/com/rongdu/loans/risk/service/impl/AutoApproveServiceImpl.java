/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.LoanCheckOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.ApplyListVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.mq.MessageProductor;
import com.rongdu.loans.risk.common.*;
import com.rongdu.loans.risk.common.chain.ExecutorChainFactory;
import com.rongdu.loans.risk.entity.AutoApprove;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.manager.AutoApproveManager;
import com.rongdu.loans.risk.manager.RiskWhitelistManager;
import com.rongdu.loans.risk.service.AutoApproveService;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.risk.service.CreditDataPersistenceService;
import com.rongdu.loans.risk.service.HitRuleService;
import com.rongdu.loans.risk.vo.AutoApproveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 自动审批结果-业务逻辑实现类
 *
 * @author sunda
 * @version 2017-08-14
 */
@Service("autoApproveService")
public class AutoApproveServiceImpl extends BaseService implements AutoApproveService {

	/**
	 * 自动审批结果-实体管理接口
	 */
	@Autowired
	private AutoApproveManager autoApproveManager;

	@Autowired
	private LoanApplyService loanApplyService;

	@Autowired
	private CreditDataInvokeService creditDataInvokeService;

	@Autowired
	private CreditDataPersistenceService creditDataPersistenceService;
	@Autowired
	private MessageProductor messageProductor;

	@Autowired
	private HitRuleService hitRuleService;
	@Autowired
	public OverdueService overdueService;
	@Autowired
	private ApplyTripartiteService applyTripartiteService;
	@Autowired
	private ApplyTripartiteRong360Service applyTripartiteRong360Service;
	@Autowired
	private JDQService jdqService;
	@Autowired
	private DWDService dwdService;
	@Autowired
	private SLLService sllService;
	@Autowired
	private RiskWhitelistManager riskWhitelistManager;
	@Autowired
	private ConfigService configService;
	@Autowired
	private CancelLogService cancelLogService;

	private static ExecutorService executorService = Executors.newFixedThreadPool(10);

	/**
	 * 现金贷预备审批定时任务，查询待审批的数据，放入预备审批的消息队列
	 *
	 * @return
	 */
	public TaskResult prepareApproveXjdTask() {
		TaskResult taskResult = new TaskResult();
		Page<ApplyListVO> page = new Page<ApplyListVO>();
		page.setPageSize(200);
		page.setPageNo(1);
		page.setOrderBy("apply_time asc");
		ApplyListOP op = new ApplyListOP();
		Date now = new Date();
		// 查询5分钟之前——7天之内的贷款申请
		Date startTime = DateUtils.addDay(new Date(), -7);// 精确到天
		Date endTime = new Date(now.getTime() - 5 * 60 * 1000);// 精确到分钟
		op.setApplyTimeStart(startTime);
		op.setApplyTimeEnd(endTime);
		op.setStatus(XjdLifeCycle.LC_APPLY_1);
		page = loanApplyService.getLoanApplyList(page, op);
		List<ApplyListVO> list = page.getList();
		logger.debug("预备审批定时任务-将{}个贷款申请推送到消息队列", list.size());
		int succNum = 0;
		int fail = 0;
		List<String> mobileList = new ArrayList<String>();
		String cachePrefix = "prepare_approving_apply_id_";
		for (ApplyListVO vo : list) {
			String exceptionCacheKey = "prepare_approve_excepion_" + vo.getId();
			if (JedisUtils.get(exceptionCacheKey) != null) {
				continue;
			}

			succNum++;

			if (mobileList.contains(vo.getMobile()))
				continue;
			mobileList.add(vo.getMobile());

			try {
				// 控制接口调用频率
				Thread.sleep(2000);

				logger.info("预审批：{}", vo.getId());
				loanApplyService.updateApplyStatus(vo.getId(),
						ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue());
			} catch (Exception e) {
				logger.error("预审批异常,info = " + JsonMapper.toJsonString(vo), e);
				fail++;
				JedisUtils.set(exceptionCacheKey, "1", Global.TWO_HOURS_CACHESECONDS);
			}
		}
		taskResult.setSuccNum(succNum);
		taskResult.setFailNum(fail);
		return taskResult;
	}

	/**
	 * 现金贷自动审批定时任务，查询待审批的数据，放入自动审批的消息队列
	 *
	 * @return
	 */
	public TaskResult approveXjdTask() {
		final AtomicInteger successNum = new AtomicInteger();
		final AtomicInteger failNum = new AtomicInteger();
		TaskResult taskResult = new TaskResult();
		Page<ApplyListVO> page = new Page<ApplyListVO>();
		page.setPageSize(250);
		page.setPageNo(1);
		page.setOrderBy("apply_time asc");
		ApplyListOP op = new ApplyListOP();
		op.setStatus(XjdLifeCycle.LC_AUTO_AUDIT_0);
		long starTime = System.currentTimeMillis();
		try {
			page = loanApplyService.getLoanApplyList(page, op);
			List<ApplyListVO> list = page.getList();
			final Queue<ApplyListVO> queue = new ConcurrentLinkedDeque<>(list);

			final CountDownLatch countDownLatch = new CountDownLatch(list.size());
			logger.debug("【自动审批】定时任务-将{}个贷款申请推送到消息队列", list.size());
			for (int i = 0; i < list.size() ; i++) {
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						ApplyListVO poll = queue.poll();
						logger.debug(Thread.currentThread().getName()+":"+poll.getId()+"-"+poll.getUserName());
						try {
							Thread.sleep(500);
							AutoApproveVO decision = null;
							decision = approveXjd(poll.getId());
							if (StringUtils.equals(ErrInfo.SUCCESS.getCode(), decision.getStatus())) {
								successNum.incrementAndGet();
								// 需要人工审批
								ApplyListVO applyListVO = loanApplyService.getBaseLoanApplyById(poll.getId());
								if (applyListVO.getStatus() == 220) {
									JedisUtils.set("approve_apply_" + poll.getId(), poll.getUserId(), Global.THREE_DAY_CACHESECONDS);// 加入审批列表,缓存7天
								}
							} else {
								failNum.incrementAndGet();
							}
						} catch (Exception e) {
							logger.error("自动审批异常,info = " + JsonMapper.toJsonString(poll), e);
							failNum.incrementAndGet();
						} finally {
							countDownLatch.countDown();
						}
					}
				});
			}
			countDownLatch.await();
		} catch (Exception e) {
			logger.error("----------执行【自动审批】定时任务异常----------", e);
		}
		long endTime = System.currentTimeMillis();
		logger.info("执行【自动审批】任务结束,执行耗时{}", endTime - starTime);

		taskResult.setSuccNum(successNum.intValue());
		taskResult.setFailNum(failNum.intValue());
		return taskResult;
	}

	/**
	 * 准备对现金贷贷款申请进行自动审批
	 */
	public void prepareApproveXjd(String applyId, String source) {
		logger.debug("准备自动审批：{},{}", applyId, source);
		creditDataPersistenceService.preLoadCreditData(applyId, source);
	}

	/**
	 * 对现金贷贷款申请进行自动审批
	 */
	public AutoApproveVO approveXjd(String applyId) {
		logger.debug("自动审批开始：{}", applyId);
		LoanApplySimpleVO applyInfo = loanApplyService.getLoanApplyById(applyId);
		AutoApprove decision = new AutoApprove();
		AutoApproveContext context = null;
		if (applyInfo != null) {
			int status = applyInfo.getProcessStatus();
			if (XjdLifeCycle.LC_AUTO_AUDIT_0 == status || XjdLifeCycle.LC_APPLY_1 == status) {
				ExecutorChain chains = null;
				try {
					context = creditDataInvokeService.createAutoApproveContext(applyId);
					if (jdqService.isExistApplyId(applyId, ChannelEnum.JIEDIANQIAN2.getCode())) {
						context.put("channel", ChannelEnum.JIEDIANQIAN2.getCode());
						context.setModelId(RiskContants.JDQ_RISK_MODEL_ID_B);
					}else if (jdqService.isExistApplyId(applyId)) {
						context.put("channel", ChannelEnum.JIEDIANQIAN.getCode());
						context.setModelId(RiskContants.JDQ_RISK_MODEL_ID_B);
					}else if (dwdService.isExistApplyId(applyId, ChannelEnum._51JDQ.getCode())) {
						context.put("channel", ChannelEnum._51JDQ.getCode());
						context.setModelId(RiskContants.JDQ_RISK_MODEL_ID_B);
					}else if (dwdService.isExistApplyId(applyId, ChannelEnum.YBQB.getCode())) {
						context.put("channel", ChannelEnum.YBQB.getCode());
						context.setModelId(RiskContants.JDQ_RISK_MODEL_ID_B);
					}else if (dwdService.isExistApplyId(applyId, ChannelEnum.CYQB.getCode())) {
						context.put("channel", ChannelEnum.CYQB.getCode());
						context.setModelId(RiskContants.JDQ_RISK_MODEL_ID_B);
					}else if (dwdService.isExistApplyId(applyId, ChannelEnum.CYQBIOS.getCode())) {
						context.put("channel", ChannelEnum.CYQBIOS.getCode());
						context.setModelId(RiskContants.JDQ_RISK_MODEL_ID_B);
					}else if (dwdService.isExistApplyId(applyId)) {
						context.put("channel", ChannelEnum.DAWANGDAI.getCode());
						context.setModelId(RiskContants.JDQ_RISK_MODEL_ID_B);
					}

					chains = ExecutorChainFactory.createExecutorChain(context);
					if (chains == null)
						throw new RuntimeException("规则执行链为空");
					chains.doExecute(context);
					decision = doDecision(context);
					decision.setStatus(ErrInfo.SUCCESS.getCode());

					//保存命中的风控规则
					saveHitRules(context.getHitRules());
					//自动审核完毕，更新贷款申请状态
					updateApplyStatus(applyInfo, decision);
				} catch (Exception e) {
					decision = doDecision(context);
					decision.setStatus(ErrInfo.ERROR.getCode());
					String remark = StringUtils.substring(e.getMessage(), 0, 100);
					decision.setRemark(remark);
					logger.error("自动审批-出现异常：{}",e);
				} finally {
					autoApproveManager.insert(decision);
				}
			} else {
				String msg = String.format("自动审批-贷款状态不符，不能进行审核：%s,当前状态：%s", applyId, status);
				decision.setRemark(msg);
				logger.error(msg);
			}
		} else {
			String msg = String.format("自动审批-未能加载到贷款申请信息：%s", applyId);
			decision.setRemark(msg);
		}
		AutoApproveVO vo = BeanMapper.map(decision, AutoApproveVO.class);
		logger.debug("自动审批结束：{}", JsonMapper.toJsonString(vo));
		return vo;
	}

	/**
	 * 保存命中的风控规则
	 *
	 * @param hitRules
	 */
	private void saveHitRules(List<HitRule> hitRules) {
		if (hitRules != null && hitRules.size() > 0) {
			hitRuleService.insertBatch(hitRules);
		}
	}

	/**
	 * 自动审核完毕，更新贷款申请状态
	 *
	 * @param applyInfo
	 * @param decision
	 */
	private void updateApplyStatus(LoanApplySimpleVO applyInfo, AutoApprove decision) {
		LoanCheckOP op = new LoanCheckOP();
		if (decision != null) {
			op.setRemark(decision.getRemark());
			op.setAutoApproveStatus2(decision.getAutoApproveStatus2());
		}
		op.setApplyId(applyInfo.getApplyId());
		op.setApproveAmt(applyInfo.getApproveAmt());
		op.setApproveTerm(applyInfo.getApproveTerm());
		op.setRepayTerm(applyInfo.getTerm());
		op.setBorrowType(Global.DEFAULT_BORROW_TYPE);
		op.setServFeeRate(applyInfo.getServFeeRate());
		op.setProductId(applyInfo.getProductId());
		op.setOperatorId("system");
		op.setOperatorName("自动审批系统");
		// 默认需要人工审批
		op.setCheckResult(3);
		if (StringUtils.equals(RiskDecision.REVIEW, decision.getDecision())) {
			op.setCheckResult(3);
		}
		if (StringUtils.equals(RiskDecision.REJECT, decision.getDecision())) {
			op.setCheckResult(2);
		}
		if (StringUtils.equals(RiskDecision.ACCEPT, decision.getDecision())) {
			op.setCheckResult(1);
		}


		// TODO 7.5日 复贷提额2000 停掉
		// 复贷提额
		/*if (op.getCheckResult() != 2 && LoanProductEnum.JNFQ.getId().equals(applyInfo.getProductId())
				&& (ChannelEnum.DAWANGDAI.getCode().equals(applyInfo.getChannelId())
				||ChannelEnum.CYQB.getCode().equals(applyInfo.getChannelId())
				|| ChannelEnum.CYQBIOS.getCode().equals(applyInfo.getChannelId()))) {
			UserInfoVO userInfoVO = creditDataInvokeService.getUserInfo(applyInfo.getUserId(), applyInfo.getApplyId(),
					true);
			int succCount = userInfoVO.getLoanSuccCount();
			if (succCount > 0){
				BigDecimal startAmt = new BigDecimal(3000);
				op.setApproveAmt(startAmt);
			}
		}*/


		// 现金贷复贷自动通过
		// 复贷2次，额度自动+100，但借款金额不超过1800。逾期超过3天以上不增加额度
/*		if (LoanProductEnum.XJD.getId().equals(applyInfo.getProductId()) && op.getCheckResult().intValue() != 2
				&& !"XJBK".equals(applyInfo.getChannelId())) {
			UserInfoVO userInfoVO = creditDataInvokeService.getUserInfo(applyInfo.getUserId(), applyInfo.getApplyId(),
					true);
			int term = applyInfo.getTerm().intValue();
			int approveTerm = applyInfo.getApproveTerm().intValue();
			int succCount = userInfoVO.getLoanSuccCount().intValue();
			int maxOverdueDays = overdueService.getMaxOverdueDays(applyInfo.getUserId());
			if (succCount > 0 && maxOverdueDays <= 5) {
				int n = (succCount % 2 == 1) ? (succCount - 1) / 2 : succCount / 2;
				BigDecimal startAmt = new BigDecimal(1500);
				BigDecimal addAmt = new BigDecimal(0).multiply(new BigDecimal(n));
				BigDecimal maxAddAmt = new BigDecimal(0);
				if (term > 1 && approveTerm == Global.XJD_AUTO_FQ_DAY_90) {
					startAmt = new BigDecimal(2000);
					addAmt = new BigDecimal(0).multiply(new BigDecimal(n));
					maxAddAmt = new BigDecimal(0);
				} else if (term > 1 && approveTerm == Global.XJD_AUTO_FQ_DAY_28) {
					startAmt = new BigDecimal(3000);
					if ("DWDAPI".equals(applyInfo.getChannelId()) && "4".equals(applyInfo.getSource())) {
						// 大王贷特殊处理为2000
						startAmt = new BigDecimal(2000);
					}
					addAmt = new BigDecimal(0).multiply(new BigDecimal(n));
					maxAddAmt = new BigDecimal(0);
				}
				if (addAmt.compareTo(maxAddAmt) > 0) {
					addAmt = maxAddAmt;
				}
				if (maxOverdueDays <= 3) {
					op.setApproveAmt(startAmt.add(addAmt));
				} else {
					op.setApproveAmt(startAmt);
				}
				if (applyTripartiteRong360Service.isExistApplyId(applyInfo.getApplyId())) {
					// 融360订单复贷审批金额：1500
					op.setApproveAmt(new BigDecimal(1500));
				}
				if (approveTerm == Global.XJD_DQ_DAY_15 && !"RONG".equals(applyInfo.getChannelId())
						&& ("1".equals(applyInfo.getSource()) || "2".equals(applyInfo.getSource()))) {
					// 15天复贷用户。非融360渠道。来源为ios或者安卓。审批金额:1800
					op.setApproveAmt(new BigDecimal(1800));
				}
				// ytodo 复贷减免

				  String flag =
				 configService.getValue(Global.DAY_15_DERATE);//15天产品减免活动标识0：
				 *关闭，1：启动 if ("1".equals(flag) && term == 1 && approveTerm ==
				  Global.XJD_DQ_DAY_15 && maxOverdueDays == 0){ long count =
				 *riskWhitelistManager.countByUserId(applyInfo.getUserId()); if
				  (count > 0) { op.setDerate(1); } else if
				  (DerateCounter.get().addByDay() <= DerateCounter.LIMIT) {
				  //单期1500产品复贷用户，每天最多新增20个名额享受减免并加入白名单，服务费为0，审批金额1200，年化利率0.24
				  op.setDerate(1); RiskWhitelist riskWhitelist = new
				 RiskWhitelist(); riskWhitelist.setIdNo(applyInfo.getIdNo());
				 riskWhitelist.setUserId(applyInfo.getUserId());
				 riskWhitelist.setMobile(applyInfo.getMobile());
				  riskWhitelist.setName(applyInfo.getUserName());
				  riskWhitelist.setSourceChannel(applyInfo.getSource());
				  riskWhitelist.setSourceType(1); riskWhitelist.setTime(new
				  Date()); riskWhitelist.setStatus(1);
				  riskWhitelistManager.insert(riskWhitelist); } }

				op.setCheckResult(1);
			}
		}*/

		int payChannel = WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue();// 默认通联放款
		/*
		 * String source = applyInfo.getSource();// 来源 int PAY_CHANNEL = 0; //
		 * 只要count >0 就不分配汉金所或者口袋了 int depositCancelCount = (int)
		 * cancelLogService.countByUserId(applyInfo.getUserId()); // 乐视放款（存管取消过）
		 * if (depositCancelCount > 0) { payChannel =
		 * WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue(); } else if
		 * ("2".equals(source)) { // android PAY_CHANNEL =
		 * Integer.parseInt(configService.getValue("day_15_pay_channel")); int
		 * currHour = DateUtils.getHour(DateUtils.getDateTime()); if (currHour
		 * >= 7 && currHour <= 14 && (applyInfo.getApproveTerm() ==
		 * Global.XJD_DQ_DAY_15 || applyInfo.getApproveTerm() ==
		 * Global.XJD_AUTO_FQ_DAY_28)) { payChannel =
		 * WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue(); } else if
		 * (PAY_CHANNEL == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()
		 * && applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15) { payChannel =
		 * WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue(); } else if
		 * (PAY_CHANNEL == WithdrawalSourceEnum.WITHDRAWAL_TONGRONG.getValue()
		 * && applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15) { payChannel =
		 * WithdrawalSourceEnum.WITHDRAWAL_TONGRONG.getValue(); } } else if
		 * ("4".equals(source) && ("RONG".equals(applyInfo.getChannelId()) ||
		 * "RONGJHH".equals(applyInfo.getChannelId()))) { // api 融360
		 * PAY_CHANNEL =
		 * Integer.parseInt(configService.getValue("day_15_pay_channel_rong"));
		 * if (applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15) { payChannel
		 * = PAY_CHANNEL; } } else if ("4".equals(source) &&
		 * ("SLLAPI".equals(applyInfo.getChannelId()) ||
		 * "SLLAPIJHH".equals(applyInfo.getChannelId()))) { // api 奇虎360
		 * PAY_CHANNEL =
		 * Integer.parseInt(configService.getValue("day_15_pay_channel_sll"));
		 * if (applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15) { payChannel
		 * = PAY_CHANNEL; } }
		 */

/*		int currHour = DateUtils.getHour(DateUtils.getDateTime());
		int PAY_CHANNEL = Integer.parseInt(configService.getValue("day_15_pay_channel"));
		// 只要count >0 就不分配汉金所或者口袋了
		int depositCancelCount = (int) cancelLogService.countByUserId(applyInfo.getUserId());
		// 乐视放款（存管取消过）
		if (depositCancelCount > 0) {
			payChannel = WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue();
		}
		// 汉金所放款(安卓客户,1-15点)
		else if ("2".equals(applyInfo.getSource()) && currHour >= 7 && currHour <= 14
				&& (applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15
						|| applyInfo.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28)) {
			payChannel = WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue();
		}
		// 口袋存管放款(安卓客户)
		else if ("2".equals(applyInfo.getSource())
				&& PAY_CHANNEL == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()
				&& applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15) {
			payChannel = WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue();
		}
		// 口袋存管放款(融360)
		else if ("4".equals(applyInfo.getSource())
				&& ("RONG".equals(applyInfo.getChannelId()) || "RONGJHH".equals(applyInfo.getChannelId()))
				&& PAY_CHANNEL == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()
				&& applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15) {
			payChannel = WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue();
		}
		// 通融放款
		else if (PAY_CHANNEL == WithdrawalSourceEnum.WITHDRAWAL_TONGRONG.getValue()
				&& applyInfo.getApproveTerm() == Global.XJD_DQ_DAY_15) {
			payChannel = WithdrawalSourceEnum.WITHDRAWAL_TONGRONG.getValue();
		}*/
		loanApplyService.updatePayChannel(applyInfo.getApplyId(), payChannel);
		loanApplyService.approve(op);
	}

	/**
	 * 汇总的命中规则，进行最终决策
	 */
	public AutoApprove doDecision(AutoApproveContext context) {
		AutoApprove decision = new AutoApprove();

		List<HitRule> list = context.getHitRules();
		for (HitRule rule : list) {
			if (rule.getScore() != null) {
				context.setScore(context.getScore() + rule.getScore());
			}
		}
		Map<Boolean, List<HitRule>> collect =
				list.stream().collect(Collectors.partitioningBy(rule -> "AT".equals(rule.getParentRuleId())));
		String riskDecision1 = getRiskDecision(collect.get(false), RiskDecision.REVIEW);// 一次机审规则
		String riskDecision2 = riskDecision1;
		UserInfoVO userInfo = context.getUserInfo();
		if (!(userInfo != null && userInfo.getLoanSuccCount() != null && userInfo.getLoanSuccCount() > 0)
				&& RiskDecision.REVIEW.equals(riskDecision1)) {
			// 非复贷且一次机审规则为“REVIEW”  才看二次机审规则
			riskDecision2 = getRiskDecision(collect.get(true), RiskDecision.REVIEW);// 二次机审规则
			if (collect.get(true) == null || collect.get(true).isEmpty()){
				// 二次机审通过
				decision.setAutoApproveStatus2(1);
			}else {
				// 二次机审拒绝
				decision.setAutoApproveStatus2(0);
			}
		}
		LoanProductEnum product = LoanProductEnum.get(context.getApplyInfo().getProductId());
		String riskDecision;
		switch (product) {
			case JDQ:
			case JNFQ:
			case JN:
			case JN2:
				if (RiskDecision.REJECT.equals(riskDecision1)) {
					riskDecision = RiskDecision.REJECT;
				} else {
					riskDecision = riskDecision2;
				}
				break;
			default:
				riskDecision = RiskDecision.REVIEW;
				break;
		}
		context.setDecision(riskDecision);

		decision.setStrategyId(product.getId());
		decision.setStrategyName(product.getName() + "自动审核风控策略集");
		decision.setApplyId(context.getApplyId());
		decision.setName(context.getUserName());
		decision.setUserId(context.getUserId());
		decision.setDecision(context.getDecision());
		decision.setScore(context.getScore());
		decision.setHitNum(context.getHitRules().size());
		decision.setCostTime(context.getCostTime());
		return decision;
	}

	/**
	 * 根据命中的规则进行风险决策
	 *
	 * @param list
	 * @return
	 */
	private String getRiskDecision(List<HitRule> list, String defaultDecision) {
		if (StringUtils.isBlank(defaultDecision)){
			defaultDecision = RiskDecision.REVIEW;
		}
		String riskDecision;
		for (HitRule rule : list) {
			if (RiskRank.A.equals(rule.getRiskRank()) || RiskRank.B.equals(rule.getRiskRank())) {
				riskDecision = RiskDecision.REJECT;
				return riskDecision;
			}
		}
		for (HitRule rule : list) {
			if (Executor.isInnerRule(rule) && RiskRank.C.equals(rule.getRiskRank())) {
				riskDecision = RiskDecision.REVIEW;
				return riskDecision;
			}
		}
		for (HitRule rule : list) {
			if (RiskRank.P.equals(rule.getRiskRank())) {
				riskDecision = RiskDecision.ACCEPT;
				return riskDecision;
			}
		}
		return defaultDecision;
	}
}