package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.security.MD5Utils;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.CityService;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.*;
import com.rongdu.loans.common.dwd.DwdUtil;
import com.rongdu.loans.cust.option.BaseInfoOP;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.option.OcrOP;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.entity.ApplyTripartiteDwd;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.OperationLog;
import com.rongdu.loans.loan.entity.PromotionCase;
import com.rongdu.loans.loan.manager.ApplyTripartiteDwdManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.PromotionCaseManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.LoanApplyOP;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.option.RepayPlanOP;
import com.rongdu.loans.loan.option.dwd.*;
import com.rongdu.loans.loan.option.dwd.charge.*;
import com.rongdu.loans.loan.option.dwd.report.ReportInfo;
import com.rongdu.loans.loan.option.jdq.Count;
import com.rongdu.loans.loan.option.jdq.JDQUrgentContact;
import com.rongdu.loans.loan.option.share.JCUserInfo;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.FileUploadResult;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.CostingResultVO;
import com.rongdu.loans.loan.vo.FileVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.SaveApplyResultVO;
import com.rongdu.loans.loan.vo.dwd.*;
import com.rongdu.loans.mq.dwd.DWDMessageService;
import com.rongdu.loans.mq.share.SharedMessageService;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @version V1.0
 * @Title: DWDServiceImpl.java
 * @Package com.rongdu.loans.loan.service.impl
 * @author: yuanxianchu
 * @date 2018年10月29日
 */
@Slf4j
@Service("dwdService")
public class DWDServiceImpl implements DWDService {

	@Autowired
	private DWDMessageService dwdMessageService;
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private RiskBlacklistService riskBlacklistService;
	@Autowired
	private OverdueService overdueService;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private BaofooAgreementPayService baofooAgreementPayService;
	@Autowired
	private AppBankLimitService appBankLimitService;
	@Autowired
	private DWDStatusFeedBackService dwdStatusFeedBackService;
	@Autowired
	private WithholdService withholdService;
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private FileInfoManager fileInfoManager;
	@Autowired
	private ApplyTripartiteDwdManager applyTripartiteDwdManager;
	@Autowired
	private PromotionCaseService promotionCaseService;
	@Autowired
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired
	private PromotionCaseManager promotionCaseManager;
	@Autowired
	private RepayPlanItemService repayPlanItemService;
	@Autowired
	private ShortMsgService shortMsgService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private TltAgreementPayService tltAgreementPayService;
	@Autowired
	private CityService cityService;
	@Autowired
	private SharedMessageService sharedMessageService;

	private FileServerClient fileServerClient = new FileServerClient();

	@Override
	public DWDResp certAuth(CertAuthOP certAuthOP) {
		DWDResp dwdResp = new DWDResp();
		String userName = certAuthOP.getUserName();
		String userMobile = certAuthOP.getUserMobile();
		String userIdCard = certAuthOP.getIdCard();
		userIdCard = userIdCard.replace("*", "_");
		userMobile = userMobile.replace("*", "_");
		String key = MD5Utils.MD5(userMobile + userIdCard);
		DWDResp dwdRespCache = getIsUserAcceptCache(key);
		if (dwdRespCache != null) {
			return dwdRespCache;
		}

		// String jdqfq_limit_day = configService.getValue("jdqfq_limit_day");
		String dwdfq_limit_day = "";
		int dwdfqLimitDay = StringUtils.isBlank(dwdfq_limit_day) ? 60 : Integer.parseInt(dwdfq_limit_day);
		CustUserVO custUserVO = custUserService.isRegister(userName, userMobile, userIdCard);
		if (custUserVO != null) {
			String userId = custUserVO.getId();
			// 聚宝自有黑名单
			boolean isBlackUser = riskBlacklistService.inBlackList(userName, userMobile, userIdCard);
			if (isBlackUser) {
				dwdResp.setCode(DWDResp.FAILURE);// code=400 代表不可申请
				dwdResp.setMsg("C002");// C002：在对方有不良贷款记录
				setIsUserAcceptCache(dwdResp, key);
				return dwdResp;
			}

			// 未完成工单
			boolean isExist = loanApplyService.isExistUnFinishLoanApply(userId);
			if (isExist) {
				dwdResp.setCode(DWDResp.FAILURE);
				dwdResp.setMsg("C001");// C001：已经在对方有进行中的贷款
				setIsUserAcceptCache(dwdResp, key);
				return dwdResp;
			}

			// 在本平台有15天以上逾期记录
			int maxOverdueDays = overdueService.getMaxOverdueDays(userId);
			if (maxOverdueDays > 15) {
				dwdResp.setCode(DWDResp.FAILURE);
				dwdResp.setMsg("C002");// C002：在对方有不良贷款记录
				setIsUserAcceptCache(dwdResp, key);
				return dwdResp;
			}

			// 被拒日期距今不满60天
			/*LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
			if (lastApply != null
					&& (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(lastApply.getStatus()) || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS
							.getValue().equals(lastApply.getStatus()))) {
				Date lastUpdateTime = lastApply.getUpdateTime();
				long pastDays = DateUtils.pastDays(lastUpdateTime);
				if (pastDays < dwdfqLimitDay) {
					dwdResp.setCode(DWDResp.FAILURE);
					dwdResp.setMsg("C003");// C003：30 天内被机构审批拒绝过
					setIsUserAcceptCache(dwdResp, key);
					return dwdResp;
				}
			}*/
		}
		CertAuthVO vo = new CertAuthVO();
		vo.setIsReloan(0);
		dwdResp.setData(vo);
		setIsUserAcceptCache(dwdResp, key);
		return dwdResp;
	}

	@Override
	public DWDResp bankBind(BankVerifyOP bankVerifyOP) {
		DWDResp dwdResp = new DWDResp();
		BankVerifyVO bankVerifyVO = new BankVerifyVO();
		String applyId = this.getApplyId(bankVerifyOP.getOrderNo());
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		if (loanApply == null || StringUtils.isBlank(loanApply.getUserId())) {
			dwdResp.setCode(DWDResp.SUCCESS);
			dwdResp.setMsg("用户数据异常");
			bankVerifyVO.setNeedConfirm("0");// 0=不发送验证码
			bankVerifyVO.setDealResult("0");// 0-绑卡失败
			dwdResp.setData(bankVerifyVO);
			log.error("【大王贷-用户绑定银行卡接口】异常-用户订单不存在orderNo={}", bankVerifyOP.getOrderNo());
			return dwdResp;
		}
		String userId = loanApply.getUserId();
		try {
			// 验证银行是否开通
			boolean isOpen = appBankLimitService.isOpenByBankNo(bankVerifyOP.getOpenBank());
			//boolean isOpen = appBankLimitService.isOpen(bankVerifyOP.getOpenBank());
			if (!isOpen) {
				// log.error("银行卡暂不支持绑定:{},{}", userId,
				// custUserVO.getBankCode());
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("银行卡暂不支持绑定，请更换其他银行卡");
				bankVerifyVO.setNeedConfirm("0");// 0=不发送验证码
				bankVerifyVO.setDealResult("0");// 0-绑卡失败
				dwdResp.setData(bankVerifyVO);
				return dwdResp;
			}

			DirectBindCardOP bindCardOp = new DirectBindCardOP();
			bindCardOp.setCardNo(bankVerifyOP.getBankCard());
			bindCardOp.setIdNo(bankVerifyOP.getIdNumber());
			bindCardOp.setMobile(bankVerifyOP.getUserMobile());
			bindCardOp.setRealName(bankVerifyOP.getUserName());
			bindCardOp.setUserId(userId);
			bindCardOp.setBankCode(BankCodeEnum.getEnumByNo(bankVerifyOP.getOpenBank()).getbCode());
			bindCardOp.setBankNo(bankVerifyOP.getOpenBank());
			bindCardOp.setSource("4");
			bindCardOp.setIpAddr("127.0.0.1");
			// 宝付协议支付预绑卡银行发验证码
			//BindCardResultVO bindCardResult = baofooAgreementPayService.agreementPreBind(bindCardOp);
			BindCardResultVO bindCardResult = tltAgreementPayService.agreementPayMsgSend(bindCardOp, HandlerTypeEnum.SANS_HANDLER);
			if (!bindCardResult.isSuccess() || StringUtils.isBlank(bindCardResult.getBindId())) {
				// logger.error("[{}]协议支付预绑卡失败", custUserVO.getMobile());
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg(bindCardResult.getMsg());
				bankVerifyVO.setNeedConfirm("0");// 0=不发送验证码
				bankVerifyVO.setDealResult("0");// 0-绑卡失败
				dwdResp.setData(bankVerifyVO);
			} else {
				Map<String, String> map = new HashMap<>();
				map.put("bindId", bindCardResult.getBindId());
				map.put("orderNo", bindCardResult.getOrderNo());
				JedisUtils.setMap("DWD:bankVerify_" + userId, map, 60 * 3);
				bankVerifyVO.setNeedConfirm("1");// 1=发送验证码
				dwdResp.setData(bankVerifyVO);
			}
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("绑卡异常");
			bankVerifyVO.setNeedConfirm("0");// 0=不发送验证码
			bankVerifyVO.setDealResult("0");// 0-绑卡失败
			dwdResp.setData(bankVerifyVO);
			log.error("【大王贷-用户绑定银行卡接口】异常orderNo={}", bankVerifyOP.getOrderNo(), e);
		}
		return dwdResp;
	}

	@Override
	public DWDResp bankVerify(BankVerifyOP bankVerifyOP) {
		DWDResp dwdResp = new DWDResp();
		BankVerifyVO bankVerifyVO = new BankVerifyVO();
		String applyId = this.getApplyId(bankVerifyOP.getOrderNo());
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		String userId = loanApply.getUserId();

		try {
			Map<String, String> map = JedisUtils.getMap("DWD:bankVerify_" + userId);
			String orderNo = map == null ? "" : map.get("orderNo");
			String bindId = map == null ? "" : map.get("bindId");
			ConfirmBindCardOP bindCardOp = new ConfirmBindCardOP();
			bindCardOp.setMsgVerCode(bankVerifyOP.getVerifyCode());
			bindCardOp.setOrderNo(orderNo);
			bindCardOp.setBindId(bindId);
			bindCardOp.setUserId(userId);
			bindCardOp.setSource("4");
			bindCardOp.setType(1);
			// 宝付协议确认绑卡
			//BindCardResultVO bindCardResult = baofooAgreementPayService.agreementConfirmBind(bindCardOp);
			BindCardResultVO bindCardResult = tltAgreementPayService.agreementPaySign(bindCardOp);
			if (!bindCardResult.isSuccess()) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg(bindCardResult.getMsg());
				bankVerifyVO.setDealResult("0");// 0-绑卡失败
				dwdResp.setData(bankVerifyVO);
				return dwdResp;
			}
			if (StringUtils.isBlank(bindCardResult.getBindId())) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("银行卡信息有误，请重试或换卡");
				bankVerifyVO.setDealResult("0");// 0-绑卡失败
				dwdResp.setData(bankVerifyVO);
				return dwdResp;
			}
			BindCardVO bindInfo = bindCardService.findByOrderNo(orderNo);
			if (bindInfo == null) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("银行卡信息有误，请重试或换卡");
				bankVerifyVO.setDealResult("0");// 0-绑卡失败
				dwdResp.setData(bankVerifyVO);
				return dwdResp;
			}
			bindInfo.setBindId(bindCardResult.getBindId());
			bindInfo.setStatus(bindCardResult.getCode());
			bindInfo.setRemark(bindCardResult.getMsg());
			bindInfo.setChlName("通联确认协议支付绑卡");
			bindInfo.setSource("4");
			bindInfo.setIp("127.0.0.1");

			int saveBc = bindCardService.update(bindInfo);
			if (saveBc == 0) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("银行卡信息有误，请重试或换卡");
				bankVerifyVO.setDealResult("0");// 0-绑卡失败
				dwdResp.setData(bankVerifyVO);
				return dwdResp;
			}

			IdentityInfoOP identityInfoOP = new IdentityInfoOP();
			identityInfoOP.setTrueName(bindInfo.getName());
			identityInfoOP.setCardNo(bindInfo.getCardNo());
			identityInfoOP.setIdNo(bindInfo.getIdNo());
			identityInfoOP.setProtocolNo(bindCardResult.getBindId());
			identityInfoOP.setUserId(userId);
			identityInfoOP.setSource("4");
			identityInfoOP.setBankCode(bindInfo.getBankCode());
			identityInfoOP.setProductId("XJD");
			identityInfoOP.setAccount(bindInfo.getMobile());
			identityInfoOP.setBankMobile(bindInfo.getMobile());
			identityInfoOP.setBindId(bindInfo.getBindId());
			String bankAddress = bankVerifyOP.getBankAddress();
			if (StringUtils.isNotBlank(bankAddress)) {
				String[] split = bankAddress.split(" ");
				if (split.length == 2) {
					Integer id = cityService.getIdByName(split[1]);
					identityInfoOP.setCityId(id == null ? "" : id.toString());
				}
			}

			int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
			if (saveRz == 0) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("系统异常");
				bankVerifyVO.setDealResult("0");// 0-绑卡失败
				dwdResp.setData(bankVerifyVO);
				return dwdResp;
			}
			bankVerifyVO.setDealResult("1");
			dwdResp.setData(bankVerifyVO);
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			bankVerifyVO.setDealResult("0");// 0-绑卡失败
			dwdResp.setData(bankVerifyVO);
			log.error("【大王贷-用户验证银行卡接口】异常orderNo={}", bankVerifyOP.getOrderNo(), e);
		}
		return dwdResp;
	}

	@Override
	public DWDResp payment(PaymentReqOP paymentReqOP) {
		DWDResp dwdResp = new DWDResp();
		PaymentReqVO paymentReqVO = new PaymentReqVO();
		String orderNo = paymentReqOP.getOrderNo();
		String applyId = this.getApplyId(orderNo);
		String lockKey = Global.JBD_PAY_LOCK + applyId;
		String requestId = String.valueOf(System.nanoTime());// 请求标识
		try {
			LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
			String repayPlanItemId = loanApplySimpleVO.getRepayPlanItemId();
			// 根据orderId防并发加锁
			boolean lock = JedisUtils.setLock(lockKey, requestId, 2 * 60);
			if (!lock) {
				log.warn("【大王贷】协议直接支付接口调用中，applyId={},orderNo= {}", applyId,orderNo);
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("还款处理中，请勿重复操作");
				paymentReqVO.setNeedConfirm("0");// 是否会发送验证码 0=不发送验证码,1=发送验证码
				paymentReqVO.setDealResult("0");// 还款申请结果1-申请成功，0-申请失败
				paymentReqVO.setReason("还款处理中，请勿重复操作");
				paymentReqVO.setTransactionid(repayPlanItemId);
				dwdResp.setData(paymentReqVO);
				return dwdResp;
			}

			CustUserVO custUserVO = LoginUtils.getCustUserInfo(loanApplySimpleVO.getUserId());
			if (custUserVO == null){
				custUserVO = custUserService.getCustUserById(loanApplySimpleVO.getUserId());
			}

			RePayOP rePayOP = new RePayOP();
			rePayOP.setApplyId(applyId);
			rePayOP.setUserId(loanApplySimpleVO.getUserId());
			rePayOP.setRepayPlanItemId(repayPlanItemId);
			rePayOP.setTxAmt(loanApplySimpleVO.getCurToltalRepayAmt().toString());
			rePayOP.setPayType(1);// 还款
			rePayOP.setPrePayFlag(RePayOP.PREPAY_FLAG_NO);
			rePayOP.setSource(loanApplySimpleVO.getSource());
			rePayOP.setIp(loanApplySimpleVO.getIp());
			rePayOP.setBindId(custUserVO.getBindId());
			rePayOP.setFullName(custUserVO.getRealName());
			rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);// 主动还款
			//ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementPay(rePayOP);
			ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementPayTest(rePayOP);
			if (confirmAuthPayVO.isSuccess()) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("还款成功");
				paymentReqVO.setNeedConfirm("0");
				paymentReqVO.setDealResult("1");
				paymentReqVO.setTransactionid(repayPlanItemId);
				dwdResp.setData(paymentReqVO);
			} else if ("I".equals(confirmAuthPayVO.getStatus())) {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg("还款处理中，请稍后查询");
				paymentReqVO.setNeedConfirm("0");
				paymentReqVO.setDealResult("1");
				paymentReqVO.setReason(dwdResp.getMsg());
				paymentReqVO.setTransactionid(repayPlanItemId);
				dwdResp.setData(paymentReqVO);
			} else {
				dwdResp.setCode(DWDResp.SUCCESS);
				dwdResp.setMsg(confirmAuthPayVO.getMsg());
				paymentReqVO.setNeedConfirm("0");
				paymentReqVO.setDealResult("0");
				paymentReqVO.setReason(dwdResp.getMsg());
				paymentReqVO.setTransactionid(repayPlanItemId);
				dwdResp.setData(paymentReqVO);
			}
		} catch (Exception e) {
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("还款异常");
			dwdResp.setData("");
			log.error("【大王贷-用户还款接口】异常orderNo={}", paymentReqOP.getOrderNo(), e);
		} finally {
			// 解除orderId并发锁
			JedisUtils.releaseLock(lockKey, requestId);
		}
		return dwdResp;
	}

	@Override
	public AuditResultVO audiResult(String orderNo) {
		AuditResultVO auditResultVO = dwdStatusFeedBackService.pullAudiResult(orderNo);
		return auditResultVO;
	}

	@Override
	public PaymentStatusVO paymentStatus(PaymentResultOP paymentResultOP) {
		PaymentStatusVO vo = dwdStatusFeedBackService.pullPaymentStatus(paymentResultOP.getOrderNo(),
				paymentResultOP.getTransactionid());
		return vo;
	}

	@Override
	public WithdrawTrialVO withdrawTrial(WithdrawTrialOP withdrawTrialOP) {
		WithdrawTrialVO vo = new WithdrawTrialVO();

		String applyId = this.getApplyId(withdrawTrialOP.getOrderNo());
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		if (loanApply == null) {
			throw new RuntimeException("用户数据异常，订单尚未生成");
		}

		PromotionCase promotionCase = promotionCaseManager.getById(loanApply.getPromotionCaseId());
		PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
		promotionCaseOP.setApplyAmt(loanApply.getApproveAmt());
		promotionCaseOP.setApplyTerm(loanApply.getApproveTerm());
		promotionCaseOP.setProductId(loanApply.getProductId());
		promotionCaseOP.setChannelId(promotionCase.getChannelId());

		CostingResultVO costingResultVO = promotionCaseService.Costing(promotionCaseOP);
		vo.setDailyRate(costingResultVO.getRatePerDay());
		vo.setReceiveAmount(costingResultVO.getToAccountAmt());
		vo.setServiceFee(costingResultVO.getServFee());
		vo.setPayAmount(costingResultVO.getRealRepayAmt());

		RepayPlanOP repayPlanOP = new RepayPlanOP();
		repayPlanOP.setApplyAmt(promotionCaseOP.getApplyAmt());
		repayPlanOP.setProductId(promotionCaseOP.getProductId());
		repayPlanOP.setRepayTerm(promotionCaseOP.getApplyTerm());
		repayPlanOP.setChannelId(promotionCaseOP.getChannelId());
		repayPlanOP.setRepayMethod(loanApply.getRepayMethod());
		//Map<String, Object> repayPlanMap = loanRepayPlanService.getRepayPlan(repayPlanOP);
        repayPlanOP.setApplyId(loanApply.getId());
        Map<String, Object> repayPlanMap = loanRepayPlanService.getJDQRepayPlan(repayPlanOP);

		List<RepaymentPlanDetail> details = new ArrayList<>();
		Date repayDate = new Date();
		if (!repayPlanMap.isEmpty()) {
			List<Map<String, Object>> repayPlanDetails = (List<Map<String, Object>>) repayPlanMap.get("list");
			for (int i = 0; i < repayPlanDetails.size(); i++) {
				Map<String, Object> repayPlanDetailMap = repayPlanDetails.get(i);
				RepaymentPlanDetail detail = new RepaymentPlanDetail();
				detail.setPeriodAmount((BigDecimal) repayPlanDetailMap.get("repayAmt"));
				detail.setPeriodNo(i + 1);
				repayDate = DateUtils.addDay(repayDate, loanApply.getRepayUnit().intValue() - 1);
				detail.setCanRepayTime(String.valueOf(repayDate.getTime()));
				details.add(detail);
			}
		}
		vo.setTrialResultData(details);
		return vo;
	}

	@Override
    public DWDResp withdrawReq(WithdrawReqOP withdrawReqOP){
		DWDResp dwdResp = new DWDResp();
		WithdrawReqVO vo = new WithdrawReqVO();
		vo.setNeedConfirm("0");// 0=不发送验证码 1=发送验证码
		String applyId = this.getApplyId(withdrawReqOP.getOrderNo());
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		if (loanApply == null || StringUtils.isBlank(loanApply.getId())) {
			dwdResp.setCode(DWDResp.SUCCESS);
			dwdResp.setMsg("用户尚未进件");
			vo.setDealResult("0");
			dwdResp.setData(vo);
			return dwdResp;
		}

		CustUserVO custUserVO = custUserService.getCustUserById(loanApply.getUserId());
		if (custUserVO == null || custUserVO.getCardNo() == null) {
			dwdResp.setCode(DWDResp.SUCCESS);
			dwdResp.setMsg("用户尚未绑卡");
			vo.setDealResult("0");
			dwdResp.setData(vo);
			return dwdResp;
		}

		Integer status = loanApply.getStatus();
		if (!status.equals(XjdLifeCycle.LC_RAISE_0)) {
			dwdResp.setCode(DWDResp.SUCCESS);
			dwdResp.setMsg("用户订单状态异常");
			vo.setDealResult("0");
			dwdResp.setData(vo);
			return dwdResp;
		}

		boolean flag = loanApplyService.saveShopedBorrowInfo(applyId, LoanApplySimpleVO.APPLY_PAY_TYPE_1);
		vo.setDealResult("1");
		if (!flag) {
			dwdResp.setCode(DWDResp.SUCCESS);
			dwdResp.setMsg("系统异常");
			vo.setDealResult("0");
		}
		dwdResp.setData(vo);
		return dwdResp;
    }

	@Override
	public RepaymentPlanVO repaymentPlan(String orderNo) {
		RepaymentPlanVO repaymentPlanVO = dwdStatusFeedBackService.pullRepaymentPlan(orderNo);
		return repaymentPlanVO;
	}

	@Override
	public OrderStatusVO orderStatus(String orderNo) {
		OrderStatusVO vo = dwdStatusFeedBackService.pullOrderStatus(orderNo);
		return vo;
	}

	public static DWDResp getIsUserAcceptCache(String key) {
		DWDResp dwdResp = new DWDResp();
		if (StringUtils.isNotBlank(key)) {
			return (DWDResp) JedisUtils.getObject("DWD:IsUserAccept_" + key);
		}
		return dwdResp;
	}

	public static DWDResp setIsUserAcceptCache(DWDResp dwdResp, String key) {
		if (null != dwdResp) {
			JedisUtils.setObject("DWD:IsUserAccept_" + key, dwdResp, 30);
		}
		return dwdResp;
	}

	@Override
	public DWDResp pushBaseInfo(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			DWDBaseInfo dwdBaseInfo = JSONObject.parseObject(bizData, DWDBaseInfo.class);
			dwdBaseInfo.setChannelCode(channelParse.getChannelCode());

			boolean lock = JedisUtils.setLock(Global.JBD_ORDER_LOCK + dwdBaseInfo.getOrderinfo().getUserMobile()
					, channelParse.getChannelCode(), Global.HALF_DAY_CACHESECONDS);
			if (!lock) {
				dwdResp.setCode(DWDResp.FAILURE);
				dwdResp.setMsg("该用户已从其他渠道进件！");
				log.debug("【大王贷-{}-基础信息接口】进件失败-已从其他渠道进件！orderNo={}", channelParse.getApp(), dwdBaseInfo.getOrderinfo().getOrderNo());
				return dwdResp;
			}

			dwdMessageService.sendBaseInfo(dwdBaseInfo);
			orderNo = dwdBaseInfo.getOrderinfo().getOrderNo();
		} catch (Exception e) {
			e.printStackTrace();
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			return dwdResp;
		}
		log.debug("【大王贷-{}-基础信息接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo,
				JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	@Override
	public DWDResp pushAdditionalInfo(String body) {
		DWDResp dwdResp = new DWDResp();
		String orderNo = "";

		DWDInfo dwdInfo = JSONObject.parseObject(body, DWDInfo.class);
		String sourceId = dwdInfo.getSourceId();// 用户来源
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupBySourceId(sourceId);
		try {
			// 业务数据解密
			String bizData = DwdUtil.getBizData(dwdInfo);
			DWDAdditionInfo dwdAdditionInfo = JSONObject.parseObject(bizData, DWDAdditionInfo.class);
			dwdAdditionInfo.setChannelCode(channelParse.getChannelCode());
			dwdMessageService.sendAdditionInfo(dwdAdditionInfo);
			orderNo = dwdAdditionInfo.getOrderNo();
		} catch (Exception e) {
			e.printStackTrace();
			dwdResp.setCode(DWDResp.FAILURE);
			dwdResp.setMsg("系统异常");
			return dwdResp;
		}
		log.debug("【大王贷-{}-补充信息接口】orderNo={},响应结果={}", channelParse.getApp(), orderNo, JSONObject.toJSONString(dwdResp));
		return dwdResp;
	}

	@Override
	public boolean saveBaseInfo(DWDBaseInfo intoOrder) {
		boolean flag = false;
		try {
			String userPhone = intoOrder.getOrderinfo().getUserMobile();
			String userId = registerOrReturnUserId(userPhone, intoOrder.getChannelCode());
			FileInfoVO fileInfoVO = custUserService.getLastDWDBaseByOrderSn(intoOrder.getOrderinfo().getOrderNo());
			if (fileInfoVO == null) {
				String res = uploadBaseData(intoOrder, FileBizCode.DWD_BASE_DATA.getBizCode(), userId);
				FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
						FileUploadResult.class);
				if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
					flag = true;
				}
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private String uploadBaseData(DWDBaseInfo orderBaseInfo, String code, String userId) {
		UploadParams params = new UploadParams();
		String clientIp = "127.0.0.1";
		String source = "4";
		params.setUserId(userId);
		params.setApplyId(orderBaseInfo.getOrderinfo().getOrderNo());
		params.setIp(clientIp);
		params.setSource(source);
		params.setBizCode(code);
		params.setRemark(orderBaseInfo.getChannelCode());
		String fileBodyText = JsonMapper.toJsonString(orderBaseInfo);
		String fileExt = "txt";
		String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
		return res;
	}

	private String registerOrReturnUserId(String userPhone, String channelCode) {
		String userId = "";
		if (!custUserService.isRegister(userPhone)) {
			RegisterOP registerOP = new RegisterOP();
			registerOP.setAccount(userPhone);
			String password = XianJinCardUtils.setData(4);
			registerOP.setPassword(XianJinCardUtils.pwdToSHA1(String.valueOf(password)));
			registerOP.setChannel(channelCode);
			userId = custUserService.saveRegister(registerOP);
			//sendMsg(password, userId, userPhone);
		} else {
			CustUserVO custUserVO = custUserService.getCustUserByMobile(userPhone);
			userId = custUserVO.getId();
		}
		return userId;
	}

	private void sendMsg(String password, String userId, String mobile) {
		SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
		sendShortMsgOP.setIp("127.0.0.1");
		sendShortMsgOP.setMobile(mobile);
		sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD, password));
		sendShortMsgOP.setUserId(userId);
		sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
		sendShortMsgOP.setSource(SourceEnum.API.getCode());
		sendShortMsgOP.setChannelId(ChannelEnum.DAWANGDAI.getCode());
		shortMsgService.sendMsg(sendShortMsgOP);
	}

	@Override
	public boolean saveAdditionInfo(DWDAdditionInfo intoOrder) {
		boolean flag = false;
		try {
			String orderSn = intoOrder.getOrderNo();
			FileInfoVO fileInfoVO = custUserService.getLastDWDAdditionalByOrderSn(orderSn);
			if (fileInfoVO == null) {
				String res = uploadAppendData(intoOrder, FileBizCode.DWD_ADDITIONAL_DATA.getBizCode(), "DWDAdditional");
				FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
						FileUploadResult.class);
				if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
					flag = true;
					Map<String, String> map = Maps.newHashMap();
					map.put(orderSn, String.valueOf(System.currentTimeMillis()));
					JedisUtils.mapPut(Global.DWD_THIRD_KEY, map);
				}
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private String uploadAppendData(DWDAdditionInfo orderAppendInfo, String code, String userId) {
		UploadParams params = new UploadParams();
		String clientIp = "127.0.0.1";
		String source = "4";
		params.setUserId(userId);
		params.setApplyId(orderAppendInfo.getOrderNo());
		params.setIp(clientIp);
		params.setSource(source);
		params.setBizCode(code);
		params.setRemark(orderAppendInfo.getChannelCode());
		String fileBodyText = JsonMapper.toJsonString(orderAppendInfo);
		String fileExt = "txt";
		String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
		return res;
	}

	@Override
	public boolean saveChargeInfo(ChargeInfo chargeInfo) {
		boolean flag = false;
		try {
			String orderNo = chargeInfo.getOrderNo();
			// 缺失代码
			FileInfoVO fileInfoVO = custUserService.getLastDWDChargeInfoByOrderSn(orderNo);
			if (fileInfoVO == null) {
				String res = uploadChargeData(chargeInfo, FileBizCode.DWD_CHARGE_DATA.getBizCode(), chargeInfo.getUserId());
				FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
						FileUploadResult.class);
				if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
					flag = true;
				}
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private String uploadChargeData(ChargeInfo chargeInfo, String code, String userId) {
		UploadParams params = new UploadParams();
		String clientIp = "127.0.0.1";
		String source = "4";
		params.setUserId(userId);
		params.setApplyId(chargeInfo.getOrderNo());
		params.setIp(clientIp);
		params.setSource(source);
		params.setBizCode(code);
		params.setRemark(chargeInfo.getChannelCode());
		String fileBodyText = JsonMapper.toJsonString(chargeInfo);
		String fileExt = "txt";
		String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
		return res;
	}

	//@Transactional
	@Override
	public void saveUserAndApplyInfo(String orderNo) throws Exception{
		DWDBaseInfo base = getPushBaseData(orderNo);
		DWDAdditionInfo additional = getPushAdditionalData(orderNo);
		if (base != null && additional != null) {
			String userPhone = base.getOrderinfo().getUserMobile();
			String ip = "127.0.0.1";
			String userId = registerOrReturnUserId(userPhone, base.getChannelCode());
			if (!loanApplyService.isExistUnFinishLoanApply(userId)) {
				int data = fileInfoManager.updateUserIdByOrderSn(userId, orderNo,
						FileBizCode.DWD_ADDITIONAL_DATA.getBizCode());
				saveDoOcr(base, additional, userId);
				saveBaseInfo(base, additional, userId, base.getChannelCode());
				saveRz(userId, ip);
				ChargeInfo chargeInfo = getChargeInfo(orderNo, userId, base.getChannelCode());
				savedwdData(chargeInfo, additional, base);
				saveLoanApply(additional, base, userId);

				// 共享消息到聚财
				// sharedMessageService.shareToJuCai(orderNo);
			}
			JedisUtils.mapRemove(Global.DWD_THIRD_KEY, orderNo);
		} else {
			Map<String, String> rePush = Maps.newHashMap();
			rePush.put(orderNo, String.valueOf(System.currentTimeMillis()));
			JedisUtils.mapPut(Global.DWD_THIRD_KEY, rePush);
		}
	}

	private void saveBaseInfo(DWDBaseInfo base, DWDAdditionInfo additional, String userId, String channelId) {
		String userPhone = base.getOrderinfo().getUserMobile();
		String userName = base.getOrderinfo().getUserName();
		String userIdcard = base.getApplydetail().getUserId();
		String degree = DwdUtil.convertDegree(base.getApplydetail().getUserEducation());
		String homeAddress = additional.getAddrDetail();

		String relation = additional.getEmergencyContactPersonaRelationship();
		String mobile = additional.getEmergencyContactPersonaPhone();
		String name = filterEmoji(additional.getEmergencyContactPersonaName());
		String relation1 = DwdUtil.convertContact(relation);
		String inRelation = relation1 + "," + name + "," + mobile;

		String relationSpare = additional.getContact1aRelationship();
		String nameSpare = filterEmoji(additional.getContact1aName());
		String mobileSpare = additional.getContact1aNumber();
		String relationSpare1 = DwdUtil.convertRelationship(relationSpare);
		String inRelationSpare = relationSpare1 + "," + nameSpare + "," + mobileSpare;

		String qq = "";
		BaseInfoOP baseInfoOP = new BaseInfoOP();
		String companyName = filterEmoji(additional.getCompanyName());
		String address = filterEmoji(additional.getCompanyAddrDetail());
		if (address.length() >= 180) {
			address = address.substring(0, 180);
		}
		String tel = additional.getCompanyNumber();
		// String[] telSplit = tel.split("-");

		// 获取居住地省市区 江苏省 无锡市 北塘区  无锡五河苑156302
		String[] strings = homeAddress.split(" ");
		if (strings.length > 0){
			baseInfoOP.setResideProvince(strings[0]);
		}
		if (strings.length >= 1){
			baseInfoOP.setResideCity(strings[1]);
		}
		if (strings.length >= 2){
			baseInfoOP.setResideDistrict(strings[2]);
		}
		// 获取工作地址省市区
		String[] workAddrs = address.split(" ");
		if (workAddrs.length > 0){
			baseInfoOP.setWorkProvince(workAddrs[0]);
		}
		if (workAddrs.length >= 1){
			baseInfoOP.setWorkCity(workAddrs[1]);
		}
		if (workAddrs.length >= 2){
			baseInfoOP.setWorkDistrict(workAddrs[2]);
		}
		baseInfoOP.setUserName(userName);
		baseInfoOP.setMobile(userPhone);
		baseInfoOP.setIdNo(userIdcard);
		baseInfoOP.setIdType("0");
		baseInfoOP.setUserId(userId);
		baseInfoOP.setComName(companyName);
		baseInfoOP.setWorkAddr(address);
		// baseInfoOP.setComTelZone(telSplit.length >= 1 ? telSplit[0] :
		// "");
		// baseInfoOP.setComTelNo(telSplit.length >= 2 ? telSplit[1] : "");
		// baseInfoOP.setComTelExt(telSplit.length >= 3 ? telSplit[2] : "");

		baseInfoOP.setDegree(degree);
		baseInfoOP.setResideAddr(homeAddress);
		baseInfoOP.setContactParent(inRelation);
		baseInfoOP.setContactFriend(inRelationSpare);
		baseInfoOP.setQq(qq);
		baseInfoOP.setChannelId(channelId);
		int saveRz = custUserService.saveBaseInfo(baseInfoOP);
	}

	private void saveDoOcr(DWDBaseInfo base, DWDAdditionInfo appendInfo, String userId) throws Exception {
		String faceRecognitionPicture = appendInfo.getPhotoAssay().get(0);// 活体照片
		String zPicture = appendInfo.getIdPositive().get(0);// 身份证正面
		String fPicture = appendInfo.getIdNegative().get(0);// 身份证反面

		String ocrName = base.getOrderinfo().getUserName();
		String ocrAddress = base.getApplydetail().getFamadr();
		String ocrIdNumber = base.getApplydetail().getUserId();
		String ocrIssuedBy = base.getApplydetail().getAgency();
		String ocrSex = "";
		String idCard = base.getApplydetail().getUserId();
		if (idCard.length() == IdcardUtils.CHINA_ID_MIN_LENGTH) {
			idCard = IdcardUtils.conver15CardTo18(idCard);
		}
		String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			ocrSex = "1";// 男
		} else {
			ocrSex = "2";// 女
		}
		String ocrRace = base.getApplydetail().getNation();
		String ocrEndTime = base.getApplydetail().getValidenddate();
		Date date = DateUtils.parse(ocrEndTime, "yyyyMMdd");
		ocrEndTime = DateUtils.formatDate(date, "yyyy-MM-dd");
		String ocrStartTime = "2018-10-24";

		String faceRecognitionPictureBase64 = "";
		String zPictureBase64 = "";
		String fPictureBase64 = "";
		String orderNo = appendInfo.getOrderNo();
		String channelCode = appendInfo.getChannelCode();
		faceRecognitionPictureBase64 = getImageBase64(orderNo, faceRecognitionPicture, channelCode);
		zPictureBase64 = getImageBase64(orderNo, zPicture, channelCode);
		fPictureBase64 = getImageBase64(orderNo, fPicture, channelCode);

		UploadParams faceImage = new UploadParams();
		faceImage.setUserId(userId);
		faceImage.setIp("127.0.0.1");
		faceImage.setSource("4");
		faceImage.setRemark(base.getChannelCode());
		faceImage.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
		FileVO fileVO = XJ360Util.uploadBase64Image(faceRecognitionPictureBase64, faceImage);
		faceImage.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
		FileVO fileVO1 = XJ360Util.uploadBase64Image(zPictureBase64, faceImage);
		faceImage.setBizCode(FileBizCode.BACK_IDCARD.getBizCode());
		FileVO fileVO2 = XJ360Util.uploadBase64Image(fPictureBase64, faceImage);
		if (fileVO == null || fileVO1 == null || fileVO2 == null) {
			throw new RuntimeException("【大王贷】图片上传异常！");
		}

		OcrOP ocrOP = new OcrOP();
		ocrOP.setName(ocrName);
		ocrOP.setAddress(ocrAddress);
		ocrOP.setIdcard(ocrIdNumber);
		ocrOP.setAuthority(ocrIssuedBy);
		ocrOP.setSex(ocrSex);
		ocrOP.setNation(ocrRace);
		ocrOP.setValidDate(ocrStartTime.replace("-", ".") + "-" + ocrEndTime.replace("-", "."));
		ocrOP.setUserId(userId);
		int saveDoOcrResult = custUserService.saveDoOcr(ocrOP);
	}

	@Override
	public DWDBaseInfo getPushBaseData(String orderSn) {
		String cacheKey = "DWD:PUSH_BASE_" + orderSn;
		DWDBaseInfo vo = (DWDBaseInfo) JedisUtils.getObject(cacheKey);
		try {
			if (vo == null) {
				FileInfoVO fileInfoVO = custUserService.getLastDWDBaseByOrderSn(orderSn);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					log.info("{}-{}-请求地址：{}", "大王贷", "从文件获取用户基础信息", fileInfoVO.getUrl());
					vo = (DWDBaseInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
							DWDBaseInfo.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, 60);
					} else {
						log.info("{}-{}-应答结果：{}", "大王贷", "从文件获取用户基础信息", false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public DWDAdditionInfo getPushAdditionalData(String orderSn) {
		String cacheKey = "DWD:PUSH_ADDITIONAL_" + orderSn;
		DWDAdditionInfo vo = (DWDAdditionInfo) JedisUtils.getObject(cacheKey);
		try {
			if (vo == null) {
				FileInfoVO fileInfoVO = custUserService.getLastDWDAdditionalByOrderSn(orderSn);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					log.info("{}-{}-请求地址：{}", "大王贷", "从文件获取用户附加信息", fileInfoVO.getUrl());
					vo = (DWDAdditionInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
							DWDAdditionInfo.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, 60);
					} else {
						log.info("{}-{}-应答结果：{}", "大王贷", "从文件获取用户附加信息", false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	public static String filterEmoji(String source) {
		if (!StringUtils.isEmpty(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		} else {
			return source;
		}
	}

	private void saveRz(String userId, String ip) {
		/** 保存操作记录数据 */
		OperationLog operationLog = new OperationLog();
		operationLog.setUserId(userId);
		operationLog.setStage(XjdLifeCycle.LC_FACE);
		operationLog.setStatus(XjdLifeCycle.LC_FACE_1);
		operationLog.setIp(ip);
		operationLog.setSource(Global.DEFAULT_SOURCE);
		operationLog.defOperatorIdAndName();
		operationLog.preInsert();
		loanApplyManager.saveOperationLog(operationLog);
	}

	private SaveApplyResultVO saveLoanApply(DWDAdditionInfo additional, DWDBaseInfo base, String userId) {
		String orderSn = additional.getOrderNo();
		DwdUtil.ChannelParse channelParse = DwdUtil.ChannelParse.lookupByChannelCode(additional.getChannelCode());
		PromotionCaseOP promotionCaseOP =
				TripartitePromotionConfig.getPromotionCase(configService.getValue(channelParse.getProductFlag()));
		LoanApplyOP loanApplyOP = new LoanApplyOP();
		loanApplyOP.setProductId(promotionCaseOP.getProductId());
		loanApplyOP.setUserId(userId);
		loanApplyOP.setApplyAmt(promotionCaseOP.getApplyAmt());
		loanApplyOP.setApplyTerm(promotionCaseOP.getApplyTerm());
		loanApplyOP.setSource("4");
		loanApplyOP.setProductType(LoanProductEnum.get(loanApplyOP.getProductId()).getType());// 产品类型
		loanApplyOP.setTerm(promotionCaseOP.getTerm());// 还款期数
		loanApplyOP.setChannelId(channelParse.getChannelCode());
		loanApplyOP.setPurpose("10");
		XJ360Util.cleanCustUserInfoCache(userId);
		SaveApplyResultVO rz = loanApplyService.saveLoanApply(loanApplyOP);
		if (!isExistApplyId(rz.getApplyId())) {
			Criteria criteria = new Criteria();
			criteria.add(Criterion.eq("id", rz.getApplyId()));
			LoanApply loanApply = new LoanApply();
			loanApply.setChannelId(channelParse.getChannelCode());
			loanApplyManager.updateByCriteriaSelective(loanApply, criteria);
			int saveTripartiteOrderResult = insertTripartiteOrder(rz.getApplyId(), orderSn, channelParse.getChannelCode());
		}
		return rz;
	}

	@Override
	public boolean isExistApplyId(String applyId){
		return isExistApplyId(applyId, null);
	}

	@Override
	public boolean isExistApplyId(String applyId, String status) {
		if (applyId == null) {
			applyId = "";
		}
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		if (StringUtils.isNotBlank(status)) {
			criteria.and(Criterion.eq("status", status));
		}
		long count = applyTripartiteDwdManager.countByCriteria(criteria);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int insertTripartiteOrder(String applyId, String orderSn, String channelCode) {
		ApplyTripartiteDwd applyTripartiteJdq = new ApplyTripartiteDwd();
		applyTripartiteJdq.setApplyId(applyId);
		applyTripartiteJdq.setTripartiteNo(orderSn);
		applyTripartiteJdq.setStatus(channelCode);
		return applyTripartiteDwdManager.insert(applyTripartiteJdq);
	}

	@Override
	public String getOrderNo(String applyId) {
		if (applyId == null) {
			applyId = "";
		}
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		ApplyTripartiteDwd applyTripartiteJdq = applyTripartiteDwdManager.getByCriteria(criteria);
		return applyTripartiteJdq.getTripartiteNo();
	}

	@Override
	public String getApplyId(String orderNo) {
		String applyId = "";
		if (orderNo == null) {
			orderNo = "";
		}
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("tripartite_no", orderNo));
		ApplyTripartiteDwd applyTripartiteDwd = applyTripartiteDwdManager.getByCriteria(criteria);
		if (applyTripartiteDwd != null) {
			applyId = applyTripartiteDwd.getApplyId();
		}
		return applyId;
	}

	@Override
	public List<String> findThirdIdsByApplyIds(List<String> applyIds) {
		return applyTripartiteDwdManager.findThirdIdsByApplyIds(applyIds);
	}

	@Override
	public TaskResult approveFeedbackOfRedis() {
		TaskResult taskResult = new TaskResult();
		int succNum = 0;
		int count = 0;
		try {
			Map<String, String> dwdCallBackMap = JedisUtils.getMap(Global.DWD_APPROVE_FEEDBACK);
			if (dwdCallBackMap != null) {
				List<Map.Entry<String, String>> callBackList = new ArrayList<>(dwdCallBackMap.entrySet());
				Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
					@Override
					public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
						return map1.getValue().compareTo(map2.getValue());
					}
				});
				for (Map.Entry<String, String> map : callBackList) {
					if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
						Thread.sleep(2000);
						if (count >= 200) {
							break;
						}
						count++;
						String applyId = map.getKey();
						String orderNo = this.getOrderNo(applyId);
						if (orderNo != null) {
							log.info("----------【大王贷-审批结论，订单状态推送】定时任务applyId={},orderNo={}----------", applyId, orderNo);
							boolean result = dwdStatusFeedBackService.approveFeedBack(applyId);
							if (result) {
								JedisUtils.mapRemove(Global.DWD_APPROVE_FEEDBACK, applyId);
								succNum++;
							}
						} else {
							JedisUtils.mapRemove(Global.DWD_APPROVE_FEEDBACK, applyId);
							succNum++;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("----------【大王贷-审批结论，订单状态推送】定时任务异常----------", e);
		}
		taskResult.setSuccNum(succNum);
		taskResult.setFailNum(count - succNum);
		return taskResult;
	}

	@Override
	public TaskResult lendFeedbackOfRedis() {
		TaskResult taskResult = new TaskResult();
		int succNum = 0;
		int count = 0;
		try {
			Map<String, String> dwdCallBackMap = JedisUtils.getMap(Global.DWD_LEND_FEEDBACK);
			if (dwdCallBackMap != null) {
				List<Map.Entry<String, String>> callBackList = new ArrayList<>(dwdCallBackMap.entrySet());
				Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
					@Override
					public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
						return map1.getValue().compareTo(map2.getValue());
					}
				});
				for (Map.Entry<String, String> map : callBackList) {
					if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
						Thread.sleep(2000);
						if (count >= 200) {
							break;
						}
						count++;
						String applyId = map.getKey();
						String orderNo = this.getOrderNo(applyId);
						if (orderNo != null) {
							log.info("----------【大王贷-订单状态，还款计划推送】定时任务applyId={},orderNo={}----------", applyId, orderNo);
							boolean result = dwdStatusFeedBackService.lendFeedBack(applyId);
							if (result) {
								JedisUtils.mapRemove(Global.DWD_LEND_FEEDBACK, applyId);
								succNum++;
							}
						} else {
							JedisUtils.mapRemove(Global.DWD_LEND_FEEDBACK, applyId);
							succNum++;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("----------【大王贷-订单状态，还款计划推送】定时任务异常----------", e);
		}
		taskResult.setSuccNum(succNum);
		taskResult.setFailNum(count - succNum);
		return taskResult;
	}

	@Override
	public TaskResult settlementFeedbackOfRedis() {
		TaskResult taskResult = new TaskResult();
		int succNum = 0;
		int count = 0;
		try {
			Map<String, String> dwdCallBackMap = JedisUtils.getMap(Global.DWD_SETTLEMENT_FEEDBACK);
			if (dwdCallBackMap != null) {
				List<Map.Entry<String, String>> callBackList = new ArrayList<>(dwdCallBackMap.entrySet());
				Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
					@Override
					public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
						return map1.getValue().compareTo(map2.getValue());
					}
				});
				for (Map.Entry<String, String> map : callBackList) {
					if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
						Thread.sleep(2000);
						if (count >= 200) {
							break;
						}
						count++;
						String repayPlanItemId = map.getKey();
						String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
						String orderNo = this.getOrderNo(applyId);
						if (orderNo != null) {
							log.info(
									"----------【大王贷-订单状态，还款状态，还款计划推送】定时任务applyId={},orderNo={},repayPlanItemId={}----------",
									applyId, orderNo, repayPlanItemId);
							boolean result = dwdStatusFeedBackService.settlementFeedBack(applyId, repayPlanItemId);
							if (result) {
								JedisUtils.mapRemove(Global.DWD_SETTLEMENT_FEEDBACK, applyId);
								succNum++;
							}
						} else {
							JedisUtils.mapRemove(Global.DWD_SETTLEMENT_FEEDBACK, applyId);
							succNum++;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("----------【大王贷-订单状态，还款状态，还款计划推送】定时任务异常----------", e);
		}
		taskResult.setSuccNum(succNum);
		taskResult.setFailNum(count - succNum);
		return taskResult;
	}

	public String getImageBase64(String var1, String var2, String channelCode) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		map.put("order_no", var1);
		map.put("fileid", var2);

		ThirdApiDTO thirdApiDTO = new ThirdApiDTO(DwdUtil.queryWay, JsonMapper.toJsonString(map),
				DWDServiceEnums.DWD_FINDFILE.getMethod());
		thirdApiDTO.setChannelCode(channelCode);
		JSONObject jsonObject = dwdStatusFeedBackService.requestHandler(thirdApiDTO, JSONObject.class);
		String result = "";
		if (jsonObject.getString("code").equals("200")) {
			result = jsonObject.getJSONObject("data").getString("filestr");
		} else {
			throw new Exception("文件为空异常" + var1 + "," + var2);
		}

		return result;
	}

	public ChargeInfo getChargeInfo(String orderNo, String userId, String channelCode) throws Exception {
		// 获取运营商原始数据
		Map<String, String> chargeMap = Maps.newHashMap();
		chargeMap.put("order_no", orderNo);

		ThirdApiDTO thirdApiDTO = new ThirdApiDTO(DwdUtil.queryWay, JsonMapper.toJsonString(chargeMap),
				DWDServiceEnums.DWD_CHARGE.getMethod());
		thirdApiDTO.setChannelCode(channelCode);
		JSONObject chargeObject = dwdStatusFeedBackService.requestHandler(thirdApiDTO, JSONObject.class);
		String chargeString = chargeObject.getString("data");
		Charge charge = JSONObject.parseObject(chargeString, Charge.class);

		// 获取运营商报告
		Map<String, String> reportMap = Maps.newHashMap();
		reportMap.put("order_no", orderNo);
		reportMap.put("type", "2");

		ThirdApiDTO thirdApiDTO2 = new ThirdApiDTO(DwdUtil.queryWay, JsonMapper.toJsonString(reportMap),
				DWDServiceEnums.DWD_CHARGE.getMethod());
		thirdApiDTO2.setChannelCode(channelCode);
		JSONObject reportObject = dwdStatusFeedBackService.requestHandler(thirdApiDTO2, JSONObject.class);
		String reportString = reportObject.getString("data");
		ReportInfo reportInfo = JSONObject.parseObject(reportString, ReportInfo.class);
		if ("200".equals(chargeObject.getString("code")) && "200".equals(reportObject.getString("code"))) {
			// 保存大王贷运营商数据
			ChargeInfo chargeInfo = new ChargeInfo();
			chargeInfo.setCharge(charge);
			chargeInfo.setReportInfo(reportInfo);

			chargeInfo.setOrderNo(orderNo);
			chargeInfo.setUserId(userId);
			chargeInfo.setChannelCode(channelCode);
			dwdMessageService.sendChargeInfo(chargeInfo);
			return chargeInfo;
		} else {
			throw new RuntimeException("【大王贷】charge数据为空异常orderNo=" + orderNo);
		}
	}

	public void savedwdData(ChargeInfo chargeInfo, DWDAdditionInfo dwdAdditionInfo, DWDBaseInfo baseInfo) {
		// 运营商-用户通讯信息   transactions
		//basic			dict	基本信息
		//calls	 		list	通话信息
		//smses			list	短信信息
		//nets			list	流量使用信息
		//transactions	list	账单信息
		Transactions info = chargeInfo.getCharge().getData().getReport().getMembers().getTransactions().get(0);// 运营商-用户通讯信息

		Map<String, Object> result = new HashMap<>();
		// String data = JsonMapper.toJsonString(charge);

		Basic basic = info.getBasic();// 运营商-用户基本信息
		List<Transaction> transactions = info.getTransactions();// 运营商-用户账单信息
		if (transactions != null) {
			// 账单总和
			Transaction sum = new Transaction();
			for (Transaction t : transactions) {
				sum.setPayAmt(t.getPayAmt() + sum.getPayAmt());
				sum.setPlanAmt(t.getPlanAmt() + sum.getPlanAmt());
				sum.setTotalAmt(t.getTotalAmt() + sum.getTotalAmt());
			}
			// 账单平均
			Transaction avg = new Transaction();
			int size = transactions.size() == 0 ? 1 : transactions.size();
			avg.setPayAmt(sum.getPayAmt() / size);
			avg.setTotalAmt(sum.getTotalAmt() / size);
			avg.setPlanAmt(sum.getPlanAmt() / size);
			avg.setBillCycle("平均");
			transactions.add(avg);
			Collections.sort(transactions, new Comparator<Transaction>() {
				@Override
				public int compare(Transaction t1, Transaction t2) {
					return t1.getBillCycle().compareTo(t2.getBillCycle());
				}
			});
		}
		List<PhoneList> addressBookList = dwdAdditionInfo.getContacts().getPhoneList();// 用户通讯录
		if (addressBookList == null){
			addressBookList = new ArrayList<>();
		}
		List<Calls> callsList = info.getCalls();// 运营商-用户通话信息
		Map<String, Count> zj = Maps.newHashMap();// 主叫
		Map<String, Count> bj = Maps.newHashMap();// 被叫
		int callAtNight = 0;// 夜间通话次数
		int callAtDay = 0;// 白天通话次数
		int call110 = 0;// 110通话次数
		List<String> stringList = Lists.newArrayList();
		// 近三个月通话记录时间集合
		List<String> stringList3 = Lists.newArrayList();
		for (Calls calls : callsList) {
			// 获取通话记录时间 加入集合
			String startTime = calls.getStartTime();
			stringList.add(calls.getStartTime());
			// 聚信立报告生成时间
			String createTime = chargeInfo.getReportInfo().getData().getReport().getReport().getUpdateTime();
			// 3个月内通话记录集合
			int days = DateUtils.daysBetween(DateUtils.parse(startTime), DateUtils.parseDate(DateUtils.dealDateFormat(createTime))) - 1;
			if (days < 90){
				stringList3.add(calls.getStartTime());
			}
			int hour = Integer.parseInt(calls.getStartTime().substring(11, 13));
			if (hour >= 23 || hour < 6) {
				callAtNight++;
			}
			if (hour < 23 && hour > 6) {
				callAtDay++;
			}
			if (calls.getOtherCellPhone().equals("110")) {
				call110++;
			}
			bj = CallCount("被叫", calls, bj);
			zj = CallCount("主叫", calls, zj);
		}
		BigDecimal count = new BigDecimal(callAtNight + callAtDay);
		BigDecimal bl = new BigDecimal(callAtNight).divide(count, 2, BigDecimal.ROUND_HALF_UP).multiply(
				new BigDecimal(100));
		Set<String> bjSet = bj.keySet();
		Set<String> zjSet = zj.keySet();
		Set<String> countSet = Sets.newHashSet();
		countSet.addAll(bjSet);
		countSet.retainAll(zjSet);// 交集
		int countCall = countSet.size();
		countSet.clear();
		countSet.addAll(bjSet);
		countSet.addAll(zjSet);// 并集
		int countAll = countSet.size();
		BigDecimal bigDecimal = new BigDecimal(countCall).divide(new BigDecimal(countAll), 2,
				BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
		// 无通话记录天数
		int days1 = 0;
		// 无通话记录天数
		int month3Days = 0;
		// 连续3天及以上无通话记录天数
		int days3 = 0;
		// 通话记录时间记录去重排序
		List<String> listNew = new ArrayList<>(new TreeSet<>(stringList));
		List<String> listNew3 = new ArrayList<>(new TreeSet<>(stringList3));
		for (int i = 0; i < listNew3.size() - 1; i++) {
			Date start = DateUtils.parse(listNew.get(i));
			Date end = DateUtils.parse(listNew.get(i + 1));
			int days = DateUtils.daysBetween(start, end) - 1;
			if (days >= 1) {
				month3Days+=days;
			}
		}

		String recentDays3Time = null;
		for (int i = 0; i < listNew.size() - 1; i++) {
			Date start = DateUtils.parse(listNew.get(i));
			Date end = DateUtils.parse(listNew.get(i + 1));
			int days = DateUtils.daysBetween(start, end) - 1;
			if (days >= 1) {
				days1+=days;
			}
			if (days > 2) {
				days3++;
				recentDays3Time = listNew.get(i + 1);
			}
		}
		Date start = DateUtils.parse(listNew.get(0));
		Date end = DateUtils.parse(listNew.get(listNew.size() - 1));
		int days = DateUtils.daysBetween(start, end);
		List<ContactList> listbj = Lists.newArrayList();
		for (String k : bj.keySet()) {
			ContactList contactList = new ContactList();
			contactList.setCallInCnt(bj.get(k).getCount());
			contactList.setCallInLen(bj.get(k).getSum());
			contactList.setPhoneNum(k);
			contactList.setPhoneNumLoc(bj.get(k).getGsd());
			contactList.setLastCall(bj.get(k).getLastCall());
			contactList.setFirstCall(bj.get(k).getFirstCall());
			listbj.add(contactList);
		}
		List<ContactList> listzj = Lists.newArrayList();
		for (String k : zj.keySet()) {
			ContactList contactList = new ContactList();
			contactList.setCallOutCnt(zj.get(k).getCount());
			contactList.setCallOutLen(zj.get(k).getSum());
			contactList.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(zj.get(k).getSum()).longValue()));
			contactList.setPhoneNum(k);
			contactList.setPhoneNumLoc(zj.get(k).getGsd());
			contactList.setLastCall(zj.get(k).getLastCall());
			contactList.setFirstCall(zj.get(k).getFirstCall());
			listzj.add(contactList);
		}
		List<ContactList> calInCntList = getTopCalInCntList(listbj, 50);// 呼入前50
		List<ContactList> callOutCntList = getTopCallOutCntList(listzj, 50);// 呼出前50
		List calInCntListV = jdqContactMatch(calInCntList, addressBookList);// 呼入前50匹配通讯录姓名
		List callOutCntListV = jdqContactMatch(callOutCntList, addressBookList);// 呼出前50匹配通讯录姓名

		List<JDQUrgentContact> urgentContactArrayList = new ArrayList<>();
		String contact1aNumber = dwdAdditionInfo.getContact1aNumber();
		JDQUrgentContact urgentContact = new JDQUrgentContact();
		urgentContact.setMobile(contact1aNumber);
		urgentContact.setName(dwdAdditionInfo.getContact1aName());
		urgentContact.setRelation(DwdUtil.convertShip(dwdAdditionInfo.getContact1aRelationship()));
		if (StringUtils.isNotBlank(contact1aNumber)){
			for (ContactList contactList1 : listbj) {
				String phoneNum = contactList1.getPhoneNum().trim();
				if (contact1aNumber.trim().contains(phoneNum) || phoneNum.contains(contact1aNumber.trim())) {
					urgentContact.setFirstCallIn(contactList1.getFirstCall());
					urgentContact.setCallInLen(new Double(contactList1.getCallInLen()).intValue());
					urgentContact.setLastCallIn(contactList1.getLastCall());
					urgentContact.setCallInCnt(contactList1.getCallInCnt());
					urgentContact.setPhoneNumLoc(contactList1.getPhoneNumLoc());
				}
			}
			for (ContactList contactList1 : listzj) {
				String phoneNum = contactList1.getPhoneNum().trim();
				if (contact1aNumber.trim().contains(phoneNum) || phoneNum.contains(contact1aNumber.trim())) {
					urgentContact.setFirstCallOut(contactList1.getFirstCall());
					urgentContact.setCallOutLen(new Double(contactList1.getCallOutLen()).intValue());
					urgentContact.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(contactList1.getCallOutLen()).longValue()));
					urgentContact.setLastCallOut(contactList1.getLastCall());
					urgentContact.setCallOutCnt(contactList1.getCallOutCnt());
					urgentContact.setPhoneNumLoc(contactList1.getPhoneNumLoc());
				}
			}
		}
		urgentContactArrayList.add(urgentContact);
		JDQUrgentContact urgentContact1 = new JDQUrgentContact();
		String emergencyContactPersonaPhone = dwdAdditionInfo.getEmergencyContactPersonaPhone().trim();
		urgentContact1.setMobile(emergencyContactPersonaPhone);
		urgentContact1.setName(dwdAdditionInfo.getEmergencyContactPersonaName());
		urgentContact1.setRelation(DwdUtil.convertShip(dwdAdditionInfo.getEmergencyContactPersonaRelationship()));
		if (StringUtils.isNotBlank(emergencyContactPersonaPhone)){
			for (ContactList contactList1 : listbj) {
				String phoneNum = contactList1.getPhoneNum().trim();
				if (emergencyContactPersonaPhone.trim().contains(phoneNum) || phoneNum.contains(emergencyContactPersonaPhone.trim())) {
					urgentContact1.setFirstCallIn(contactList1.getFirstCall());
					urgentContact1.setCallInLen(new Double(contactList1.getCallInLen()).intValue());
					urgentContact1.setLastCallIn(contactList1.getLastCall());
					urgentContact1.setCallInCnt(contactList1.getCallInCnt());
					urgentContact1.setPhoneNumLoc(contactList1.getPhoneNumLoc());
				}
			}
			for (ContactList contactList1 : listzj) {
				String phoneNum = contactList1.getPhoneNum().trim();
				if (emergencyContactPersonaPhone.trim().contains(phoneNum) || phoneNum.contains(emergencyContactPersonaPhone.trim())) {
					urgentContact1.setFirstCallOut(contactList1.getFirstCall());
					urgentContact1.setCallOutLen(new Double(contactList1.getCallOutLen()).intValue());
					urgentContact1.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(contactList1.getCallOutLen()).longValue()));
					urgentContact1.setLastCallOut(contactList1.getLastCall());
					urgentContact1.setCallOutCnt(contactList1.getCallOutCnt());
					urgentContact1.setPhoneNumLoc(contactList1.getPhoneNumLoc());
				}
			}
		}
		urgentContactArrayList.add(urgentContact1);
		String regTime = basic.getRegTime();
		Date date = new Date();
		if (regTime != null) {
			date = DateUtils.parse(regTime);
		} else {
			regTime = DateUtils.formatDateTime(date);
		}
		int month = DateUtils.getMonth(date, new Date());

		List<ContactCheck> contactChecks = new ArrayList<>();
		Map<String, Count> countMap = Maps.newHashMap();
		for (Calls calls : callsList) {
			if (StringUtils.isBlank(calls.getOtherCellPhone())){
				continue;
			}
			Count object = countMap.get(calls.getOtherCellPhone());
			if (object == null) {
				Count count1 = new Count();
				count1.setCount(1);
				count1.setSum(calls.getUseTime());
				countMap.put(calls.getOtherCellPhone(), count1);
			} else {
				object.setCount(object.getCount() + 1);
				object.setSum(object.getSum() + calls.getUseTime());
				countMap.put(calls.getOtherCellPhone(), object);
			}
		}
		for (PhoneList addressBook : addressBookList) {
			ContactCheck contactCheck = new ContactCheck();
			String contactMobile = addressBook.getPhone();
			if (StringUtils.isBlank(contactMobile) || contactMobile.length() < 5){
				continue;
			}
			String contactName = addressBook.getName();
			contactCheck.setMobile(contactMobile);
			contactCheck.setName(contactName);
			contactCheck.setCallCnt(0);
			contactCheck.setCallLen(0);

			for (String k : countMap.keySet()) {
				if (contactMobile.trim().contains(k.trim()) || k.trim().contains(contactMobile.trim())) {
					contactCheck.setCallCnt(countMap.get(k).getCount());
					contactCheck.setCallLen(countMap.get(k).getSum());
				}
			}
			contactChecks.add(contactCheck);
		}
		Collections.sort(contactChecks);

		DWDReport dwdReport = new DWDReport();
		dwdReport.setCallatnight(callAtNight);// 夜间通话次数
		dwdReport.setBl(bl);// 夜间通话占比
		dwdReport.setDays(days);
		dwdReport.setDays1(days1);// 无通话天数
		dwdReport.setDays3(days3);// 连续三天无通话次数
		dwdReport.setRecentDays3Time(recentDays3Time);
		dwdReport.setMonth3Days(month3Days);
		dwdReport.setCall110(call110);// 110通话次数
		dwdReport.setCountcall(countCall);// 互通话数
		dwdReport.setBigdecimal(bigDecimal);// 互通话占比
		dwdReport.setBjsize(listbj.size());// 被叫
		dwdReport.setZjsize(listzj.size());// 主叫
		dwdReport.setMonth(month);
		dwdReport.setCalincntlistv(calInCntListV);// 被叫前50匹配通讯录
		dwdReport.setCalloutcntlistv(callOutCntListV);// 主叫前50匹配通讯录
		dwdReport.setUrgentcontact(urgentContactArrayList);// 紧急联系人
		dwdReport.setTransactions(transactions);// 运营商-用户账单信息
		dwdReport.setBasic(basic);// 运营商-用户基本信息
		dwdReport.setContactCheckList(contactChecks);// 通讯录通话匹配
		dwdReport.setChannelCode(baseInfo.getChannelCode());
		saveReport(dwdReport, baseInfo);
	}

	public boolean saveReport(DWDReport dwdReport, DWDBaseInfo baseInfo) {
		boolean flag = false;
		try {
			String userPhone = baseInfo.getOrderinfo().getUserMobile();
			String userId = registerOrReturnUserId(userPhone, baseInfo.getChannelCode());
			FileInfoVO fileInfoVO = custUserService.getLastDWDReportByOrderSn(baseInfo.getOrderinfo().getOrderNo());
			if (fileInfoVO == null) {
				String res = uploadBaseData(dwdReport, FileBizCode.DWD_REPORT_DATA.getBizCode(), userId, baseInfo
						.getOrderinfo().getOrderNo());
				FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
						FileUploadResult.class);
				if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
					flag = true;
				}
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private String uploadBaseData(DWDReport dwdReport, String code, String userId, String orderSn) {
		UploadParams params = new UploadParams();
		String clientIp = "127.0.0.1";
		String source = "4";
		params.setUserId(userId);
		params.setApplyId(orderSn);
		params.setIp(clientIp);
		params.setSource(source);
		params.setBizCode(code);
		params.setRemark(dwdReport.getChannelCode());
		String fileBodyText = JsonMapper.toJsonString(dwdReport);
		String fileExt = "txt";
		String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
		return res;
	}

	public Map<String, Count> CallCount(String type, Calls calls, Map<String, Count> call) {
		if (StringUtils.isBlank(calls.getOtherCellPhone()) || StringUtils.isBlank(calls.getInitType())){
			return call;
		}
		if (calls.getInitType().contains(type)) {
			Count object = call.get(calls.getOtherCellPhone());
			if (object == null) {
				Count count1 = new Count();
				count1.setCount(1);
				count1.setSum(calls.getUseTime());
				count1.setGsd(calls.getPlace());
				count1.setFirstCall(calls.getStartTime());
				count1.setLastCall(calls.getStartTime());
				call.put(calls.getOtherCellPhone(), count1);
			} else {
				object.setCount(object.getCount() + 1);
				object.setSum(object.getSum() + calls.getUseTime());
				if (DateUtils.compareDate(object.getFirstCall(), calls.getStartTime()) == 1) {
					object.setFirstCall(calls.getStartTime());
				}
				if (DateUtils.compareDate(object.getLastCall(), calls.getStartTime()) == -1) {
					object.setLastCall(calls.getStartTime());
				}
				call.put(calls.getOtherCellPhone(), object);
			}
		}
		return call;
	}

	public static List<ContactList> getTopCalInCntList(List<ContactList> ccmList, int top) {
		if (ccmList != null) {
			Collections.sort(ccmList, new Comparator<ContactList>() {
				@Override
				public int compare(ContactList o1, ContactList o2) {
					ContactList ccm1 = o1;
					ContactList ccm2 = o2;
					if (ccm1.getCallInCnt() > ccm2.getCallInCnt()) {
						return -1;
					}
					if (ccm1.getCallInCnt() < ccm2.getCallInCnt()) {
						return 1;
					}
					return 0;
				}
			});
			top = (ccmList.size() < top) ? ccmList.size() : top;
			List<ContactList> list = ccmList.subList(0, top);
			return list;
		} else {
			List<ContactList> list = new ArrayList<ContactList>();
			return list;
		}
	}

	public static List<ContactList> getTopCallOutCntList(List<ContactList> ccmList, int top) {
		if (ccmList != null) {
			Collections.sort(ccmList, new Comparator<ContactList>() {
				@Override
				public int compare(ContactList o1, ContactList o2) {
					ContactList ccm1 = o1;
					ContactList ccm2 = o2;
					if (ccm1.getCallOutCnt() > ccm2.getCallOutCnt()) {
						return -1;
					}
					if (ccm1.getCallOutCnt() < ccm2.getCallOutCnt()) {
						return 1;
					}
					return 0;
				}
			});
			top = (ccmList.size() < top) ? ccmList.size() : top;
			List<ContactList> list = ccmList.subList(0, top);
			return list;
		} else {
			List<ContactList> list = new ArrayList<ContactList>();
			return list;
		}
	}

	private List jdqContactMatch(List<ContactList> contactLists, List<PhoneList> phoneList) {
		List ccmList2 = new ArrayList();
		for (ContactList contactList : contactLists) {
			String phoneNum = contactList.getPhoneNum().trim();
			String contactName = "";
			if (phoneList != null) {
				for (PhoneList phone : phoneList) {
					if (StringUtils.isBlank(phone.getPhone()) || phone.getPhone().length() < 5){
						continue;
					}
					String contactMobile = phone.getPhone().trim();
                    if (StringUtils.contains(phoneNum, contactMobile) || StringUtils.contains(contactMobile,
                            phoneNum)) {
                        contactName = phone.getName();
                        break;
                    }
				}
			}
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("mobile", phoneNum);
			m.put("belongTo", contactList.getPhoneNumLoc());
			m.put("contact_1w", contactList.getContact1w());
			m.put("contact_1m", contactList.getContact1m());
			m.put("contact_3m", contactList.getContact3m());
			m.put("contact_3m_plus", contactList.getContact3mPlus());
			m.put("call_cnt", contactList.getCallCnt());
			m.put("call_len", new Double(contactList.getCallLen()).intValue());
			m.put("terminatingCallCount", contactList.getCallInCnt());
			m.put("terminatingTime", new Double(contactList.getCallInLen()).intValue());
			m.put("originatingCallCount", contactList.getCallOutCnt());
			m.put("originatingTime", new Double(contactList.getCallOutLen()).intValue());
			m.put("contactName", contactName);
			m.put("lastCall", contactList.getLastCall());
			m.put("firstCall", contactList.getFirstCall());
			ccmList2.add(m);
		}
		return ccmList2;
	}

	public Map<String, Count> CallCount(String type, com.rongdu.loans.loan.option.jdq.Calls calls,
			Map<String, Count> call) {
		if (calls.getInitType().equals(type)) {
			Count object = call.get(calls.getOtherCellPhone());
			if (object == null) {
				Count count1 = new Count();
				count1.setCount(1);
				count1.setSum(calls.getUseTime());
				count1.setGsd(calls.getPlace());
				count1.setFirstCall(calls.getStartTime());
				count1.setLastCall(calls.getStartTime());
				call.put(calls.getOtherCellPhone(), count1);
			} else {
				object.setCount(object.getCount() + 1);
				object.setSum(object.getSum() + calls.getUseTime());
				if (DateUtils.compareDate(object.getFirstCall(), calls.getStartTime()) == 1) {
					object.setFirstCall(calls.getStartTime());
				}
				if (DateUtils.compareDate(object.getLastCall(), calls.getStartTime()) == -1) {
					object.setLastCall(calls.getStartTime());
				}
				call.put(calls.getOtherCellPhone(), object);
			}
		}
		return call;
	}

	@Override
	public DWDReport getReportData(String orderSn) {
		String cacheKey = "DWD:PUSH_REPORT_" + orderSn;
		DWDReport vo = (DWDReport) JedisUtils.getObject(cacheKey);
		try {
			if (vo == null) {
				FileInfoVO fileInfoVO = custUserService.getLastDWDReportByOrderSn(orderSn);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					log.info("{}-{}-请求地址：{}", "大王贷", "从文件获取用户报告信息", fileInfoVO.getUrl());
					vo = (DWDReport) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), DWDReport.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, 60);
					} else {
						log.info("{}-{}-应答结果：{}", "大王贷", "从文件获取用户报告信息", false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public ChargeInfo getdwdChargeInfo(String orderSn) {
		String cacheKey = "DWD:PUSH_CHARGEINFO_" + orderSn;
		ChargeInfo vo = (ChargeInfo) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			try {
				FileInfoVO fileInfoVO = custUserService.getLastDWDChargeInfoByOrderSn(orderSn);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					log.info("{}-{}-请求地址：{}", "【大王贷】", "从文件获取用户运营商信息", fileInfoVO.getUrl());
					vo = (ChargeInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), ChargeInfo.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, 60*60);
					} else {
						log.info("{}-{}-应答结果：{}", "大王贷", "从文件获取用户运营商信息", false);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
}
