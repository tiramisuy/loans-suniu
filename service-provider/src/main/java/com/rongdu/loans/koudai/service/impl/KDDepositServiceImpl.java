package com.rongdu.loans.koudai.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.KDBankEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.koudai.api.service.KDDepositOrderApiService;
import com.rongdu.loans.koudai.api.vo.deposit.KDComprehensiveResultVO;
import com.rongdu.loans.koudai.api.vo.deposit.KDDepositResultVO;
import com.rongdu.loans.koudai.api.vo.deposit.KDQueryComprehensiveVO;
import com.rongdu.loans.koudai.api.vo.deposit.KDWithdrawResultVO;
import com.rongdu.loans.koudai.entity.PayLog;
import com.rongdu.loans.koudai.manager.PayLogManager;
import com.rongdu.loans.koudai.op.deposit.KDDepositBorrowBaseOP;
import com.rongdu.loans.koudai.op.deposit.KDDepositComprehensiveOP;
import com.rongdu.loans.koudai.op.deposit.KDDepositOpenBaseVO;
import com.rongdu.loans.koudai.op.deposit.KDDepositRepayAuthBaseOP;
import com.rongdu.loans.koudai.op.deposit.KDDepositWithdrawOP;
import com.rongdu.loans.koudai.op.deposit.KDOrderBaseOP;
import com.rongdu.loans.koudai.op.deposit.KDOtherOP;
import com.rongdu.loans.koudai.op.deposit.KDPeriodBaseOP;
import com.rongdu.loans.koudai.op.deposit.KDPushAssetRepaymentPeriodOP;
import com.rongdu.loans.koudai.op.deposit.KDRepaymentBaseOP;
import com.rongdu.loans.koudai.service.KDDepositService;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.koudai.vo.KDwithdrawRecodeVO;
import com.rongdu.loans.loan.entity.ContactHistory;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.manager.ContactHistoryManager;
import com.rongdu.loans.loan.manager.ContractManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanRepayPlanManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.service.RongPointCutService;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.risk.service.RiskBlacklistService;

@Service("kDDepositService")
public class KDDepositServiceImpl implements KDDepositService {
	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private KDDepositOrderApiService kdDepositOrderApiService;
	@Autowired
	private PayLogManager payLogManager;
	@Autowired
	private ContactHistoryManager contactHistoryManager;
	@Autowired
	private BorrowInfoManager borrowInfoManager;

	@Autowired
	private ContractManager contractManager;
	@Autowired
	private CustCouponManager custCouponManager;

	@Autowired
	private RiskBlacklistService riskBlacklistService;
	@Autowired
	private RongPointCutService rongPointCutService;
	@Autowired
	private KDPayService kdPayService;

	@Override
	public ApiResultVO comprehensive(String applyId, String returnUrl) {

		ApiResultVO rtnVo = new ApiResultVO();

		if (StringUtils.isBlank(applyId)) {
			logger.error("申请id不能为空");
			rtnVo.setCode(ErrInfo.ERROR.getCode());
			rtnVo.setMsg("申请id不能为空");
			return rtnVo;
		}
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser custUser = custUserManager.getById(loanApply.getUserId());

		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositComprehensiveOP pack = new KDDepositComprehensiveOP();

		KDDepositOpenBaseVO openBaseVO = new KDDepositOpenBaseVO();
		openBaseVO.setName(custUser.getRealName());
		openBaseVO.setMobile(custUser.getMobile());
		openBaseVO.setGender(
				custUser.getSex() == null ? "" : custUser.getSex() == 1 ? "M" : custUser.getSex() == 2 ? "F" : "");

		// ytodo 0217 开户异步回调
		openBaseVO.setRetUrl(Global.getConfig("kd.deposit.accountOpenRetURL"));
		if (StringUtils.isNotBlank(returnUrl)) {
			try {
				returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			openBaseVO.setRetUrl(openBaseVO.getRetUrl() + "?returnUrl=" + returnUrl);
		}
		openBaseVO.setNotifyUrl(Global.getConfig("kd.deposit.accountOpenCallbackURL"));

		pack.setOpenBase(openBaseVO);

		KDDepositRepayAuthBaseOP repayAuthBase = new KDDepositRepayAuthBaseOP();
		repayAuthBase.setRepayMaxAmt(1000000 * 100);// 100万
		repayAuthBase.setPaymentMaxAmt(250000 * 100);
		repayAuthBase.setPaymentDeadline(
				Integer.parseInt(DateUtils.formatDate(DateUtils.addDay(new Date(), 1800), "yyyyMMdd")));// 授权1800天
		repayAuthBase.setRepayDeadline(repayAuthBase.getPaymentDeadline());
		// ytodo 0217 授权异步回调
		repayAuthBase.setRetUrl(Global.getConfig("kd.deposit.repayAuthRetURL"));
		if (StringUtils.isNotBlank(returnUrl)) {
			repayAuthBase.setRetUrl(repayAuthBase.getRetUrl() + "?returnUrl=" + returnUrl);
		}
		repayAuthBase.setNotifyUrl("");

		pack.setRepayAuthBase(repayAuthBase);
		// 贷款金额，单位：分 审批金额-服务费
		int moneyAmount = loanApply.getApproveAmt().subtract(loanApply.getServFee()).multiply(BigDecimal.valueOf(100))
				.intValue();

		if ("S".equals(loanApply.getUrgentPayed())) {
			// ytodo 0303 加急券
			//moneyAmount = loanApply.getApproveAmt().multiply(BigDecimal.valueOf(100)).intValue();
		}
		KDOrderBaseOP kdOrderBaseOP = new KDOrderBaseOP();
		kdOrderBaseOP.setOutTradeNo(applyId);
		// 贷款金额，单位：分
		kdOrderBaseOP.setMoneyAmount(moneyAmount);
		// 贷款方式(0:按天,1:按月,2:按年) M-月、Q-季、Y-年、D-天
		kdOrderBaseOP.setLoanMethod(loanApply.getRepayFreq().equals("D") ? 0
				: loanApply.getRepayFreq().equals("M") ? 1
						: loanApply.getRepayFreq().equals("Q") ? 1 : loanApply.getRepayFreq().equals("Y") ? 2 : -1);
		// kdOrderBaseOP.setLoanTerm(loanApply.getRepayFreq().equals("Q") ? (3 *
		// loanApply.getTerm()) : loanApply.getTerm());
		// kdOrderBaseOP.setLoanTerm(loanApply.getApproveTerm());
		// 固定14
		kdOrderBaseOP.setLoanTerm(14);
		// kdOrderBaseOP.setApr(loanApply.getActualRate().multiply(BigDecimal.valueOf(100)).floatValue());
		kdOrderBaseOP.setApr(Float.valueOf(18));
		// kdOrderBaseOP.setLoanInterests(loanApply.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
		// 利息 1200*18%/360*14
		kdOrderBaseOP.setLoanInterests(moneyAmount * 18 / 100 / 360 * 14);
		kdOrderBaseOP.setOrderTime(currentTime);
		// kdOrderBaseOP.setCounterFee(loanApply.getServFee().multiply(BigDecimal.valueOf(100)).intValue());
		kdOrderBaseOP.setCounterFee(0);

		kdOrderBaseOP.setLendPayType(1);
		kdOrderBaseOP.setUsageOfLoan("5");

		pack.setOrderBase(kdOrderBaseOP);

		KDDepositBorrowBaseOP borrowBase = new KDDepositBorrowBaseOP();

		// borrowBase.setBorrowCost(kdOrderBaseOP.getLoanInterests());
		// ytodo 0217 借款合规页异步回调
		borrowBase.setRetUrl(Global.getConfig("kd.deposit.borrowRetURL") + "?applyId=" + applyId);
		if (StringUtils.isNotBlank(returnUrl)) {
			borrowBase.setRetUrl(borrowBase.getRetUrl() + "&returnUrl=" + returnUrl);
		}
		borrowBase.setNotifyUrl(Global.getConfig("kd.deposit.borrowNotifyUrl"));

		borrowBase.setCounterFee(0);
		int approveAmt = loanApply.getApproveAmt().subtract(loanApply.getServFee()).multiply(BigDecimal.valueOf(100))
				.intValue();
		if ("S".equals(loanApply.getUrgentPayed())) {
			// ytodo 0303 加急券
			//approveAmt = loanApply.getApproveAmt().multiply(BigDecimal.valueOf(100)).intValue();
		}
		borrowBase.setPlanRepaymentMoney(approveAmt + kdOrderBaseOP.getLoanInterests());

		// borrowBase.setBorrowCost(loanApply.getActualRate().multiply(BigDecimal.valueOf(100)).intValue());
		// borrowBase.setBorrowCost(18);
		// 综合借款成本=(资金成本算出的利息+合作方服务费)/借款本金/天数X360，

		borrowBase.setBorrowCost((float) ((kdOrderBaseOP.getLoanInterests() + borrowBase.getCounterFee()) * 360 * 100
				/ kdOrderBaseOP.getMoneyAmount() / kdOrderBaseOP.getLoanTerm()));

		borrowBase.setBorrowCost(
				new BigDecimal(borrowBase.getBorrowCost()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

		pack.setBorrowBase(borrowBase);

		KDOtherOP kdOtherOP = new KDOtherOP();
		kdOtherOP.setIndustry(0);
		kdOtherOP.setWorkNature(1);
		kdOtherOP.setIncome(1);
		kdOtherOP.setRepaySource(1);
		// 紧急联系人
		List<ContactHistory> contactHistorieList = contactHistoryManager.getContactHisByApplyNo(applyId);
		if (CollectionUtils.isNotEmpty(contactHistorieList)) {
			kdOtherOP.setExigencyName(contactHistorieList.get(0).getName());
			kdOtherOP.setExigencyPhone(contactHistorieList.get(0).getMobile());
		} else {
			kdOtherOP.setExigencyName("未知");
			kdOtherOP.setExigencyPhone("13333333333");
		}

		kdOtherOP.setStudent(0);

		pack.setOther(kdOtherOP);

		KDComprehensiveResultVO resultVO = kdDepositOrderApiService.comprehensive(pack);

		if (resultVO.getRetCode() == 0) {
			// loanApply.setStatus(ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue());
			// loanApplyManager.updateStageOrStatus(loanApply);
			rtnVo.put("url", resultVO.getRetData().getUrl());
		} else {
			rtnVo.setCode(resultVO.getRetCode() + "");
			rtnVo.setMsg(resultVO.getRetMsg());
		}

		return rtnVo;

	}

	@Override
	/**
	 * 上传还款计划
	 */
	public ApiResultVO pushAssetRepaymentPeriod(String applyId) {

		ApiResultVO rtnVo = new ApiResultVO();

		if (StringUtils.isBlank(applyId)) {
			logger.error("申请id不能为空");
			rtnVo.setCode(ErrInfo.ERROR.getCode());
			rtnVo.setMsg("申请id不能为空");
			return rtnVo;
		}
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);

		KDPushAssetRepaymentPeriodOP pack = new KDPushAssetRepaymentPeriodOP();

		pack.setOutTradeNo(applyId);

		// 贷款金额，单位：分 审批金额-服务费
		int moneyAmount = loanApply.getApproveAmt().subtract(loanApply.getServFee()).multiply(BigDecimal.valueOf(100))
				.intValue();
		
		if ("S".equals(loanApply.getUrgentPayed())) {
			// ytodo 0303 加急券
			//moneyAmount = loanApply.getApproveAmt().multiply(BigDecimal.valueOf(100)).intValue();
		}

		// 还款计划
		KDRepaymentBaseOP kdRepaymentBaseOP = new KDRepaymentBaseOP();
		// 一次性还款
		kdRepaymentBaseOP.setRepaymentType(2);

		LoanRepayPlan loanRepayPlan = loanRepayPlanManager.getByApplyId(applyId);

		kdRepaymentBaseOP.setRepaymentTime(Long.valueOf(loanRepayPlan.getLoanEndDate().getTime() / 1000).intValue());
		kdRepaymentBaseOP
				.setPeriod(loanApply.getRepayFreq().equals("Q") ? (3 * loanApply.getTerm()) : loanApply.getTerm());
		// kdRepaymentBaseOP.setRepaymentPrincipal(loanRepayPlan.getPrincipal().multiply(BigDecimal.valueOf(100)).intValue());
		kdRepaymentBaseOP.setRepaymentPrincipal(moneyAmount);
		// kdRepaymentBaseOP.setRepaymentInterest(loanRepayPlan.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
		kdRepaymentBaseOP.setRepaymentInterest(moneyAmount * 18 / 100 / 360 * 14);

		// kdRepaymentBaseOP.setRepaymentAmount(loanRepayPlan.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
		kdRepaymentBaseOP.setRepaymentAmount(
				kdRepaymentBaseOP.getRepaymentPrincipal() + kdRepaymentBaseOP.getRepaymentInterest());

		pack.setRepaymentBase(kdRepaymentBaseOP);

		List<KDPeriodBaseOP> kdPeriodBaseOPList = new ArrayList<>();

		/** 获取所有明细 */
		List<RepayPlanItem> repayPlanItemList = repayPlanItemManager.getByApplyId(applyId);
		KDPeriodBaseOP preBaseOP = null;
		for (RepayPlanItem repayPlanItem : repayPlanItemList) {
			preBaseOP = new KDPeriodBaseOP();
			preBaseOP.setPeriod(repayPlanItem.getThisTerm());
			preBaseOP.setPlanRepaymentTime(Long.valueOf(repayPlanItem.getRepayDate().getTime() / 1000).intValue());

			// preBaseOP.setPlanRepaymentPrincipal(repayPlanItem.getPrincipal().multiply(BigDecimal.valueOf(100)).intValue());
			// preBaseOP.setPlanRepaymentInterest(repayPlanItem.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
			// preBaseOP.setPlanRepaymentMoney(repayPlanItem.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
			// preBaseOP.setApr(loanApply.getActualRate().multiply(BigDecimal.valueOf(100)).floatValue());

			preBaseOP.setPlanRepaymentPrincipal(kdRepaymentBaseOP.getRepaymentPrincipal());
			preBaseOP.setPlanRepaymentInterest(kdRepaymentBaseOP.getRepaymentInterest());
			preBaseOP.setPlanRepaymentMoney(kdRepaymentBaseOP.getRepaymentAmount());
			preBaseOP.setApr(Float.valueOf(18));

			kdPeriodBaseOPList.add(preBaseOP);
		}

		pack.setPeriodBase(kdPeriodBaseOPList);

		KDDepositResultVO resultVO = kdDepositOrderApiService.pushAssetRepaymentPeriod(pack);

		if (resultVO.getRetCode() == 0) {
			logger.info("口袋存管上传还款计划成功");
		} else {
			rtnVo.setCode(resultVO.getRetCode() + "");
			rtnVo.setMsg(resultVO.getRetMsg());
		}

		return rtnVo;

	}

	@Override
	public ApiResultVO withdraw(String applyId, String returnUrl) {
		// TODO Auto-generated method stub

		ApiResultVO rtnVo = new ApiResultVO();

		KDDepositWithdrawOP pack = new KDDepositWithdrawOP();

		pack.setOutTradeNo(applyId);
		// ytodo 0217 提现同步跳转
		pack.setRetUrl(Global.getConfig("kd.deposit.withdrawRetURL") + "?applyId=" + applyId);// 跳转页面
		if (StringUtils.isNotBlank(returnUrl)) {
			try {
				returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			pack.setRetUrl(pack.getRetUrl() + "&returnUrl=" + returnUrl);
		}
		pack.setIsUrl(1);// 1 url 0 html

		KDWithdrawResultVO kdWithdrawResultVO = kdDepositOrderApiService.withdraw(pack);

		if (kdWithdrawResultVO.getRetCode() == 0) {// 成功
			// LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
			// loanApply.setStage(ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL.getStage());//提现处理中
			// loanApply.setStatus(ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL.getValue());//提现处理中
			// loanApplyManager.updateStageOrStatus(loanApply);

			rtnVo.put("url", kdWithdrawResultVO.getRetData().getUrl());// 跳转url

			// updatePayLogStatus(applyId,2);//更新为提现中

			return rtnVo;
		} else {
			rtnVo.setCode(kdWithdrawResultVO.getRetCode() + "");
			rtnVo.setMsg(kdWithdrawResultVO.getRetMsg());
			// rtnVo.put("url",kdWithdrawResultVO.getRetData().getUrl());//跳转url
			// rtnVo.put("url","https://www.baidu.com");//跳转url
		}

		return rtnVo;
	}

	@Override
	public ApiResultVO saveOrUpdatePayLogStatus(String applyId, String kdOrderId, int payStatus) {

		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser user = custUserManager.getById(loanApply.getUserId());

		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("del", 0));
		criteria1.and(Criterion.eq("apply_id", applyId));

		PayLog log = payLogManager.getByCriteria(criteria1);
		if (log == null) {
			log = new PayLog();
			log.setApplyId(loanApply.getId());
			log.setUserId(user.getId());
			log.setUserName(user.getRealName());
			log.setMobile(user.getMobile());
			log.setIdNo(user.getIdNo());
			log.setBankCode(user.getBankCode());
			log.setBankName(KDBankEnum.getName(user.getBankCode()));
			log.setCardNo(user.getCardNo());
			log.setPayAmt(loanApply.getApproveAmt().subtract(loanApply.getServFee()));
			if ("S".equals(loanApply.getUrgentPayed())) {
				// ytodo 0303 加急券
				//log.setPayAmt(loanApply.getApproveAmt());
			}
			log.setPayTime(new Date());
			log.setPayOrderId(kdOrderId);// 口袋放款订单号
			log.setPayFailCount(0);// 放款失败次数
			log.setPayStatus(payStatus);// 0=成功,1=失败,2=处理中
			if (payStatus == 0) {
				log.setPaySuccTime(new Date());
				log.setKdPayMsg("放款成功");
			}
			// log.setKdPayUserId();// 口袋用户id(最长9位)
			log.setKdPayCode(0);
			log.setKdPayThirdPlatform(7);// 第三方通道编号
			log.setKdPayOrderId(kdOrderId);// 口袋订单号

			log.setWithdrawStatus(0);
			payLogManager.insert(log);
		} else {

			if (log.getPayStatus() == 0) {// 状态为0的不允许修改状态
				payStatus = 0;
			}
			if (kdOrderId != null) {
				log.setKdPayOrderId(kdOrderId);// 口袋订单号
			}

			log.setPayStatus(payStatus);
			if (payStatus == 0) {
				log.setPaySuccTime(new Date());
				log.setKdPayMsg("放款成功");
			}
			log.setRemark("");
			payLogManager.update(log);
		}

		return null;
	}

	@Override
	public ApiResultVO updatePayLogStatus(String applyId, Integer withdrawStatus) {
		// TODO Auto-generated method stub

		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("apply_id", applyId));
		criteria1.and(Criterion.eq("del", 0));

		List<PayLog> list = payLogManager.findAllByCriteria(criteria1);
		if (CollectionUtils.isNotEmpty(list)) {
			PayLog log = list.get(0);
			log.setWithdrawStatus(withdrawStatus);
			// if(status == 0){//失败次数+1
			// if(log.getPayFailCount() == null){
			// log.setPayFailCount(1);
			// }else {
			// log.setPayFailCount(log.getPayFailCount()+1);
			// }
			// }

			payLogManager.update(log);
		}

		return new ApiResultVO();
	}

	@Override
	public List<KDwithdrawRecodeVO> findWithdrawRecode(String userId, Integer withdrawStatus) {

		// KDwithdrawRecodeVO
		PayLog payLog = new PayLog();
		payLog.setUserId(userId);
		payLog.setWithdrawStatus(withdrawStatus);
		payLog.setPayStatus(0);

		List<PayLog> list = payLogManager.findWithdrawRecode(payLog);
		List<KDwithdrawRecodeVO> retList = new ArrayList<>();
		for (PayLog log : list) {
			KDwithdrawRecodeVO recodeVO = new KDwithdrawRecodeVO();
			recodeVO.setId(log.getId());
			recodeVO.setApplyId(log.getApplyId());
			if (log.getPayAmt() != null) {
				recodeVO.setMoney(log.getPayAmt().setScale(2).toString());
			} else {
				recodeVO.setMoney("0");
			}
			recodeVO.setStatus(log.getWithdrawStatus());
			if (log.getWithdrawStatus() != null) {
				recodeVO.setStatusDesc(
						log.getWithdrawStatus() == 1 ? "已提现" : log.getWithdrawStatus() == 2 ? "提现中" : "未提现");
			}

			recodeVO.setDate(DateUtils.formatDate(log.getCreateTime(), DateUtils.FORMAT_LONG));
			retList.add(recodeVO);

		}

		return retList;
	}

	/**
	 * @return TaskResult 返回类型
	 * @throws @Title:
	 *             processKDWaitingLending
	 * @Description: 处理口袋存管待放款订单查询
	 */
	public TaskResult processKDWaitingLending() {

		logger.info("开始执行口袋存管待放款订单查询任务");
		long starTime = System.currentTimeMillis();
		int success = 0;
		int fail = 0;

		// 待放款 订单
		List<PayLog> payLogList = payLogManager.findKDWaitingLendingList();

		for (PayLog payLog : payLogList) {
			try {
				KDQueryComprehensiveVO comprehensiveVO = kdDepositOrderApiService
						.queryComprehensive(payLog.getApplyId());
				if (comprehensiveVO.getRetCode() == 0) {
					if (comprehensiveVO.getRetData().getStatus() == 8 || comprehensiveVO.getRetData().getStatus() == 11
							|| comprehensiveVO.getRetData().getStatus() == 200) {// 订单不存在、订单取消、授权问题
						payLog.setPayStatus(1);
						if (payLog.getPayFailCount() == null) {
							payLog.setPayFailCount(1);
						} else {
							payLog.setPayFailCount(payLog.getPayFailCount() + 1);
						}
						payLog.setRemark(comprehensiveVO.getRetData().getMsg());// 状态说明

						payLogManager.update(payLog);
						success++;
					} else if (comprehensiveVO.getRetData().getStatus() == 9) {// 风控拒绝，加入黑名单、取消订单
						LoanApply loanApply = loanApplyManager.getLoanApplyById(payLog.getApplyId());

						long checkInsert = riskBlacklistService.countInBlacklist(loanApply.getUserId());
						if (checkInsert > 0) {
							logger.info("userId:" + loanApply.getUserId() + "该用户，已经在黑名单中。");
						} else {
							riskBlacklistService.insertBlacklist(loanApply.getUserId(), "口袋存管风控拒绝加入黑名单", null, 1,
									"system");
						}

						contractManager.delByApplyId(loanApply.getId());
						loanRepayPlanManager.delByApplyId(loanApply.getId());
						repayPlanItemManager.delByApplyId(loanApply.getId());

						Criteria criteria1 = new Criteria();
						criteria1.add(Criterion.eq("apply_id", loanApply.getId()));
						custCouponManager.deleteTruelyByCriteria(criteria1);// 删除卡券

						loanApply.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
						loanApply.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
						loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
						loanApplyManager.updateLoanApplyInfo(loanApply);
						rongPointCutService.cancelApply(loanApply.getId(), "system");// Rong360放款时，切面通知的切入点标记

						payLog.setPayStatus(3);// 取消
						payLog.setKdPayMsg("风控拒绝订单");

						payLogManager.update(payLog);
					} /*
						 * else if (comprehensiveVO.getRetData().getStatus() ==
						 * 200) {// 授权问题 payLog.setPayStatus(1);
						 * payLog.setRemark("授权问题");
						 * 
						 * LoanApply loanApply = new LoanApply();
						 * loanApply.setId(payLog.getApplyId());
						 * loanApply.setStatus(ApplyStatusLifeCycleEnum.
						 * WAITING_PUSH.getValue());
						 * loanApply.setStage(ApplyStatusLifeCycleEnum.
						 * WAITING_PUSH.getStage());
						 * 
						 * loanApplyManager.updateStageOrStatus(loanApply);
						 * payLogManager.update(payLog);
						 * 
						 * borrowInfoManager.delByApplyId(payLog.getApplyId());
						 * success++;
						 * 
						 * }
						 */
				} else if (comprehensiveVO.getRetCode() == 70002) {// 该笔订单不存在
					payLog.setPayStatus(1);
					if (payLog.getPayFailCount() == null) {
						payLog.setPayFailCount(1);
					} else {
						payLog.setPayFailCount(payLog.getPayFailCount() + 1);
					}
					payLog.setRemark(comprehensiveVO.getRetMsg());// 状态说明

					payLogManager.update(payLog);
					success++;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				fail++;
			}

		}
		long endTime = System.currentTimeMillis();
		logger.info("口袋存管待放款订单查询,执行耗时{}", endTime - starTime);
		return new TaskResult(success, fail);
	}

	@Override
	public TaskResult processKDWithdrawOrder() {
		logger.info("开始执行口袋存管待提现订单查询任务");
		long starTime = System.currentTimeMillis();
		int success = 0;
		int fail = 0;
		// 待放款 订单
		List<PayLog> payLogList = payLogManager.findKDUnWithdrawOrderList();
		for (PayLog payLog : payLogList) {
			try {
				Thread.sleep(1000);
				try {
					// 切换乐视放款
					kdPayService.changePaychannel(payLog.getId(),
							String.valueOf(WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue()));
					success++;
				} catch (Exception e) {
					// 切换渠道失败，取消订单
					AdminWebResult result = kdPayService.adminCancel(payLog.getId());
					if ("0".equals(result.getCode())) {
						fail++;
					} else {
						success++;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				fail++;
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("口袋存管待提现订单查询,执行耗时{}", endTime - starTime);
		return new TaskResult(success, fail);
	}

}
