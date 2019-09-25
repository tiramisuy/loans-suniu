package com.rongdu.loans.koudai.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JRandomUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.KDBankEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.koudai.api.service.KDCreateApiService;
import com.rongdu.loans.koudai.api.service.KDDepositOrderApiService;
import com.rongdu.loans.koudai.api.service.KDPayApiService;
import com.rongdu.loans.koudai.entity.PayLog;
import com.rongdu.loans.koudai.manager.PayLogManager;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;
import com.rongdu.loans.koudai.op.pay.KDPayOP;
import com.rongdu.loans.koudai.op.pay.KDPayQueryOP;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.koudai.vo.create.KDCreateResultVO;
import com.rongdu.loans.koudai.vo.pay.KDPayQueryVO;
import com.rongdu.loans.koudai.vo.pay.KDPayVO;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.CancelLog;
import com.rongdu.loans.loan.service.CancelLogService;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.RongPointCutService;
import com.rongdu.loans.loan.vo.AdminWebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("kdPayService")
public class KDPayServiceImpl implements KDPayService {
	public static final Logger logger = LoggerFactory.getLogger(KDPayServiceImpl.class);
	@Autowired
	private KDPayApiService kdPayApiService;
	@Autowired
	private KDCreateApiService kdCreateApiService;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private ContractService contractService;
	@Autowired
	private PayLogManager payLogManager;
	@Autowired
	private KDDepositOrderApiService kDDepositOrderApiService;
	@Autowired
	private BorrowInfoManager borrowInfoManager;
	@Autowired
	private RongPointCutService rongPointCutService;

	@Autowired
	private ContractManager contractManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private CustCouponManager custCouponManager;

	@Autowired
	private CancelLogService cancelLogService;

	@Override
	public KDPayVO pay(String applyId) {
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser user = custUserManager.getById(loanApply.getUserId());
		if (StringUtils.isBlank(user.getBankCode())) {
			throw new RuntimeException("口袋放款失败，未绑定银行卡," + applyId);
		}
		if (payLogManager.countByApplyId(applyId) > 0) {
			throw new RuntimeException("已存在放款记录," + applyId);
		}
		KDPayOP op = new KDPayOP();
		op.setYur_ref(applyId + JRandomUtils.getRandomNumStr(30 - applyId.length()));
		op.setUser_id(JRandomUtils.getRandomNumStr(9));
		op.setReal_name(user.getRealName());
		op.setBank_id(KDBankEnum.getId(user.getBankCode()));
		op.setCard_no(user.getCardNo());
		op.setMoney(MoneyUtils.yuan2fen(loanApply.getApproveAmt().subtract(loanApply.getServFee()).toString()));
		op.setPay_summary("聚宝钱包放款");
		KDPayVO vo = kdPayApiService.pay(op);

		savePayLog(loanApply, user, op, vo);
		return vo;
	}

	@Override
	public TaskResult processPayingTask() {
		logger.info("开始口袋放款处理中订单任务。");
		long starTime = System.currentTimeMillis();
		/** 查询所有处理中的数据 */
		List<PayLog> list = payLogManager.findPayingList();
		int success = 0;
		int fail = 0;
		for (PayLog l : list) {
			try {
				Thread.sleep(500);

				KDPayQueryOP op = new KDPayQueryOP();
				op.setYur_ref(l.getPayOrderId());
				KDPayQueryVO vo = kdPayApiService.query(op);
				if (vo.isIng()) {
					success++;
					continue;
				}
				if (vo.isSuccess()) {
					l.setPayStatus(0);
					l.setPaySuccTime(new Date());
					// 生成还款计划
					contractService.processKoudaiLendPay(l.getApplyId(), new Date());
				} else {
					l.setPayStatus(1);
					int failCount = l.getPayFailCount();
					l.setPayFailCount(++failCount);
				}
				l.setKdPayCode(vo.getCode());
				l.setKdPayMsg(vo.getMsg());
				payLogManager.update(l);
				success++;
			} catch (Exception e) {
				fail++;
				logger.error("放款处理失败，参数： " + JsonMapper.getInstance().toJson(l), e);
			}
		}

		long endTime = System.currentTimeMillis();
		logger.info("口袋放款处理中订单任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

	private int savePayLog(LoanApply loanApply, CustUser user, KDPayOP op, KDPayVO vo) {
		PayLog log = new PayLog();
		log.setApplyId(loanApply.getId());
		log.setUserId(user.getId());
		log.setUserName(user.getRealName());
		log.setMobile(user.getMobile());
		log.setIdNo(user.getIdNo());
		log.setBankCode(user.getBankCode());
		log.setBankName(KDBankEnum.getName(user.getBankCode()));
		log.setCardNo(user.getCardNo());
		log.setPayAmt(new BigDecimal(MoneyUtils.fen2yuan(op.getMoney())));
		log.setPayTime(new Date());
		log.setPayOrderId(op.getYur_ref());// 聚宝放款订单号，固定30位
		log.setPayFailCount(vo.isSuccess() ? 0 : 1);// 放款失败次数
		log.setPayStatus(vo.isSuccess() ? 2 : 1);// 0=成功,1=失败,2=处理中
		log.setKdPayUserId(Integer.parseInt(op.getUser_id()));// 口袋用户id(最长9位)
		log.setKdPayCode(vo.getCode());
		log.setKdPayMsg(vo.getMsg());
		if (vo.isSuccess()) {
			log.setKdPayThirdPlatform(vo.getThird_platform());// 第三方通道编号
			log.setKdPayOrderId(vo.getPay_order_id());// 口袋订单号
		}
		return payLogManager.insert(log);
	}

	@Override
	public AdminWebResult adminPay(String payLogId) {
		PayLog log = payLogManager.get(payLogId);
		if (log.getPayStatus().intValue() != 1) {
			return new AdminWebResult("99", "放款订单状态异常");
		}
		int failCount = log.getPayFailCount();
		if (failCount >= 2) {
			return new AdminWebResult("99", "失败次数超限，请次日对账看放款结果");
		}
		long pastMinute = DateUtils.pastMinutes(log.getPayTime());
		if (pastMinute < 30) {
			return new AdminWebResult("99", "半个小时后重新发起放款操作");
		}
		LoanApply loanApply = loanApplyManager.getLoanApplyById(log.getApplyId());
		if (loanApply != null && loanApply.getStatus() != XjdLifeCycle.LC_RAISE_1) {
			return new AdminWebResult("99", "申请单状态异常");
		}

		KDPayOP op = new KDPayOP();
		op.setYur_ref(log.getPayOrderId());
		op.setUser_id(String.valueOf(log.getKdPayUserId()));
		op.setReal_name(log.getUserName());
		op.setBank_id(KDBankEnum.getId(log.getBankCode()));
		op.setCard_no(log.getCardNo());
		op.setMoney(MoneyUtils.yuan2fen(log.getPayAmt().toString()));
		op.setPay_summary(log.getUserName() + "放款");
		KDPayVO vo = kdPayApiService.pay(op);

		log.setPayTime(new Date());
		log.setPayFailCount(vo.isSuccess() ? failCount : ++failCount);// 放款失败次数
		log.setPayStatus(vo.isSuccess() ? 2 : 1);// 0=成功,1=失败,2=处理中
		log.setKdPayCode(vo.getCode());
		log.setKdPayMsg(vo.getMsg());

		if (vo.isSuccess()) {
			log.setKdPayThirdPlatform(vo.getThird_platform());// 第三方通道编号
			log.setKdPayOrderId(vo.getPay_order_id());// 口袋订单号
		}
		int rz = payLogManager.update(log);
		return new AdminWebResult(String.valueOf(rz), rz == 1 ? "提交成功" : "提交失败");
	}

	@Override
	public AdminWebResult adminCreate(String payLogId) {
		PayLog log = payLogManager.get(payLogId);

		// 创建订单
		KDCreateResultVO vo = kdCreateApiService.createOrder(log.getApplyId());

		log.setKdCreateCode(Integer.parseInt(vo.getCode()));
		if (vo.isSuccess()) {
			log.setKdCreateOrderId(vo.getOrder_id());
		} else {
			log.setKdCreateMsg(vo.getMessage());
		}

		int rz = payLogManager.update(log);
		return new AdminWebResult(String.valueOf(rz), rz == 1 ? "提交成功" : "提交失败");
	}

	@Transactional
	@Override
	public AdminWebResult adminCancel(String payLogId) {
		PayLog log = payLogManager.get(payLogId);

		LoanApply loanApply = loanApplyManager.getLoanApplyById(log.getApplyId());
		if (loanApply == null) {
			logger.error("取消借款，贷款申请单不存在，applyId = {}", log.getApplyId());
			return new AdminWebResult("0", "贷款申请单不存在");
		}
		if (loanApply.getStatus().intValue() >= ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue()) {
			logger.error("取消借款，贷款申请单状态不正确，applyId = {}", log.getApplyId());
			return new AdminWebResult("0", "贷款申请单状态不正确");
		}
		if ("4".equals(loanApply.getSource())
				&& ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
				&& JedisUtils.get(Global.RONG_CREATE_ACCOUNT + loanApply.getId()) != null) {
			logger.error("融360已经确认借款，不能取消，applyId = {}", loanApply.getId());
			return new AdminWebResult("0", "融360已经确认借款，不能取消");
		}
		int rz = 0;
		if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(loanApply.getPayChannel())) {
			Map<String, Object> result = queryStatus(log.getApplyId());
			if (0 == (Integer) result.get("retCode")) {
				Map<String, Object> data = (Map<String, Object>) result.get("retData");
				if (null != data) {
					Integer status = (Integer) data.get("status");
					if (status != null) {
						if (status.intValue() == 8 || status.intValue() == 11) {// 订单不存在、订单取消
							log.setKdPayMsg(getKdStatus(status));
							rz = _adminCancel(log, loanApply);
						} else {
							return new AdminWebResult("0", "取消失败,订单状态：" + getKdStatus(status));
						}
					}

				}
			} else if (70002 == (Integer) result.get("retCode")) {// 该笔订单不存在
				log.setKdPayMsg(String.valueOf(result.get("retMsg")));
				rz = _adminCancel(log, loanApply);
			}
		}
		return new AdminWebResult(String.valueOf(rz), rz == 1 ? "提交成功" : "提交失败");
	}

	private int _adminCancel(PayLog log, LoanApply loanApply) {
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

		log.setPayStatus(3);// 取消

		int rz = payLogManager.update(log);

		// 取消订单时，在loan_cancel_log插入数据
		CancelLog cancelLog = new CancelLog();
		cancelLog.preInsert();
		cancelLog.setUserId(log.getUserId());
		cancelLog.setUserName(log.getUserName());
		cancelLog.setMobile(log.getMobile());
		cancelLog.setCardNo(log.getCardNo());
		cancelLog.setIdNo(log.getIdNo());
		cancelLog.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue());
		cancelLogService.saveCancelLog(cancelLog);
		return rz;
	}

	@Override
	public Map<String, Object> getConctract(String applyId) {
		return kDDepositOrderApiService.getContract(applyId);
	}

	@Override
	public Map<String, Object> queryStatus(String applyId) {
		return kDDepositOrderApiService.queryStatus(applyId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getPayCount(KDPayCountOP payop) {
		Page page = new Page(payop.getPageNo(), payop.getPageSize());
		List<Map<String, Object>> list = null;
		list = payLogManager.getPayCount(payop);
		page.setCount(list.size());
		page.setList(list);
		return page;
	}

	/**
	 * 修改放款渠道
	 */
	@Transactional
	@Override
	public void changePaychannel(String id, String paychannel) {
		PayLog payLogVO = payLogManager.get(id);
		Map<String, Object> result = queryStatus(payLogVO.getApplyId());
		if (0 == (Integer) result.get("retCode")) {
			Map<String, Object> data = (Map<String, Object>) result.get("retData");
			if (null != data) {
				Integer status = (Integer) data.get("status");
				if (status != null) {
					if (status.intValue() == 8 || status.intValue() == 4 || status.intValue() == 11) {// 订单不存在、提现冲正、订单取消
						_changePaychannel(payLogVO, paychannel);
						return;
					} else {
						throw new IllegalArgumentException("订单修改失败,订单状态：" + getKdStatus(status));
					}
				}
			}
		} else if (70002 == (Integer) result.get("retCode")) {// 该笔订单不存在
			_changePaychannel(payLogVO, paychannel);
		} else {
			throw new IllegalArgumentException("订单修改失败");
		}
	}

	private void _changePaychannel(PayLog payLogVO, String paychannel) {
		LoanApply loanApply = loanApplyManager.getLoanApplyById(payLogVO.getApplyId());

		if (loanApply == null) {
			logger.error("修改放款渠道，贷款申请单不存在，applyId = {}", payLogVO.getApplyId());
			throw new IllegalArgumentException("贷款申请单不存在，applyId = " + payLogVO.getApplyId());
		}
		if (loanApply.getStatus().intValue() >= ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue()) {
			logger.error("修改放款渠道，贷款申请单状态不正确，applyId = {}", payLogVO.getApplyId());
			throw new IllegalArgumentException("贷款申请单状态不正确，applyId = " + payLogVO.getApplyId());
		}
		if ("4".equals(loanApply.getSource())
				&& ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
				&& JedisUtils.get(Global.RONG_CREATE_ACCOUNT + payLogVO.getApplyId()) == null) {
			logger.error("修改放款渠道，融360未确认借款,不能修改渠道,只能取消，applyId = {}", payLogVO.getApplyId());
			throw new IllegalArgumentException("融360未确认借款,不能修改渠道,只能取消，applyId = " + payLogVO.getApplyId());
		}

		String oldPayChannel = loanApply.getPayChannel();
		// 删除还款计划
		contractManager.delByApplyId(loanApply.getId());
		loanRepayPlanManager.delByApplyId(loanApply.getId());
		repayPlanItemManager.delByApplyId(loanApply.getId());
		// 删除卡券
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("apply_id", loanApply.getId()));
		custCouponManager.deleteTruelyByCriteria(criteria1);// 删除卡券

		loanApply.setPayChannel(paychannel);
		loanApply.setStage(ApplyStatusLifeCycleEnum.WAITING_PUSH.getStage());
		loanApply.setStatus(ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue());
		loanApply.setPayTime(new Date());
		loanApply.preUpdate();
		loanApplyManager.updateLoanApplyInfo(loanApply);

		BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(payLogVO.getApplyId());
		if (borrowInfo != null) {
			borrowInfo.setPayChannel(Integer.parseInt(paychannel));
			borrowInfo.setPushStatus(loanApply.getStatus());
			borrowInfo.preUpdate();
			borrowInfoManager.update(borrowInfo);
		}
		payLogVO.setRemark(oldPayChannel + "_TO_" + paychannel);
		payLogVO.setPayStatus(1);
		payLogVO.setKdPayMsg("放款失败");
		payLogManager.update(payLogVO);

		// 修改放款渠道时，在loan_cancel_log插入数据
		CancelLog cancelLog = new CancelLog();
		cancelLog.preInsert();
		cancelLog.setUserId(payLogVO.getUserId());
		cancelLog.setUserName(payLogVO.getUserName());
		cancelLog.setMobile(payLogVO.getMobile());
		cancelLog.setCardNo(payLogVO.getCardNo());
		cancelLog.setIdNo(payLogVO.getIdNo());
		cancelLog.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue());
		cancelLogService.saveCancelLog(cancelLog);
	}

	@Override
	public Map<String, Object> queryAccount(String idNo) {
		return kDDepositOrderApiService.queryAccount(idNo);
	}

	private String getKdStatus(Integer status) {
		String resultMsg = "";

		switch (status) {
		case 1:
			resultMsg = "放款中";
			break;
		case 2:
			resultMsg = "放款成功(钱放到电子账户)";
			break;
		case 4:
			resultMsg = "提现冲正 ";
			break;
		case 5:
			resultMsg = "放款成功(受托支付)";
			break;
		case 6:
			resultMsg = "提现成功(钱到银行卡) ";
			break;
		case 7:
			resultMsg = "提现失败(可以再次发起提现) ";
			break;
		case 8:
			resultMsg = "订单不存在";
			break;
		case 9:
			resultMsg = "提现失败(不需要再次发起提现 风控拒绝订单)";
			break;
		case 10:
			resultMsg = "提现中";
			break;
		case 11:
			resultMsg = "订单取消";
			break;
		default:
			resultMsg = "查询失败";
			break;
		}

		return resultMsg;
	}

}
