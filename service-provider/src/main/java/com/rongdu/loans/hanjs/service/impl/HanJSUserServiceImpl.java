package com.rongdu.loans.hanjs.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.hanjs.api.service.HanJSApiUserService;
import com.rongdu.loans.hanjs.op.HanJSOrderOP;
import com.rongdu.loans.hanjs.op.HanJSUserOP;
import com.rongdu.loans.hanjs.op.HanJSWithdrawOP;
import com.rongdu.loans.hanjs.op.QueryOrderStateOP;
import com.rongdu.loans.hanjs.service.HanJSUserService;
import com.rongdu.loans.hanjs.vo.HanJSResultVO;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.CancelLog;
import com.rongdu.loans.loan.service.CancelLogService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.loan.vo.PayLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("hanJSUserService")
public class HanJSUserServiceImpl implements HanJSUserService {
	public static final Logger logger = LoggerFactory.getLogger(HanJSUserServiceImpl.class);
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private ContractManager contractManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private CustCouponManager custCouponManager;
	@Autowired
	private PayLogService payLogService;

	@Autowired
	private CancelLogService cancelLogService;

	@Autowired
	private HanJSApiUserService hanJSApiUserService;
	@Autowired
	private BorrowInfoManager borrowInfoManager;

	@Override
	public HanJSResultVO openAccount(HanJSUserOP op) {
		return hanJSApiUserService.openAccount(op);
	}

	@Override
	public HanJSResultVO withdraw(HanJSWithdrawOP op) {
		return hanJSApiUserService.withdraw(op);
	}

	@Override
	public HanJSResultVO pushBid(HanJSOrderOP op) {
		return hanJSApiUserService.pushBid(op);
	}

	public HanJSResultVO queryOrderState(String applyId) {
		QueryOrderStateOP op = new QueryOrderStateOP();
		op.setOrderId(applyId);
		return hanJSApiUserService.queryOrderState(op);
	}

	public AdminWebResult cancelOrder(String payLogId, String applyId) {
		PayLogVO log = payLogService.get(payLogId);
		if (log != null && applyId.equals(log.getApplyId())
				&& Integer.parseInt(log.getStatus()) == ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue()) {
			LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
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
			// NOORDER 订单不存在
			// NOLENDPAY 未放款
			// LENDPAYFAIL 放款失败
			// LENDPAYING 放款中
			// WITHDRAWSUCCESS 提现成功
			// NOWITHDRAW 未提现
			// WITHDRAWING 提现处理中
			// WITHDRAWFAIL 提现失败
			// EARLYSETTLE 放款后24小时未提现，提前兑付
			HanJSResultVO vo = queryOrderState(applyId);
			if ("EARLYSETTLE".equals(vo.getData())) {
				contractManager.delByApplyId(applyId);
				loanRepayPlanManager.delByApplyId(applyId);
				repayPlanItemManager.delByApplyId(applyId);

				Criteria criteria1 = new Criteria();
				criteria1.add(Criterion.eq("apply_id", applyId));
				custCouponManager.deleteTruelyByCriteria(criteria1);// 删除卡券

				loanApply.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
				loanApply.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
				loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
				loanApplyManager.updateLoanApplyInfo(loanApply);

				log.setStatus(String.valueOf(ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL.getValue()));
				log.setRemark(vo.getMessage());
				payLogService.update(log);
				CancelLog cancelLog = new CancelLog();
				cancelLog.preInsert();
				cancelLog.setUserId(log.getUserId());
				cancelLog.setUserName(log.getUserName());
				cancelLog.setMobile(log.getToMobile());
				cancelLog.setIdNo(log.getToIdno());
				cancelLog.setCardNo(log.getToAccNo());
				cancelLog.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue());
				cancelLog.setDel(0);
				cancelLogService.saveCancelLog(cancelLog);

				return new AdminWebResult("1", "取消成功");
			} else {
				return new AdminWebResult("99", "取消失败," + vo.getMessage());
			}
		} else {
			return new AdminWebResult("99", "取消失败,订单状态异常");
		}
	}

	@Override
	public AdminWebResult changeOrder(String payLogId, String applyId, String paychannel) {
		PayLogVO log = payLogService.get(payLogId);
		if (log != null && applyId.equals(log.getApplyId())
				&& Integer.parseInt(log.getStatus()) == ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue()) {
			// NOORDER 订单不存在
			// NOLENDPAY 未放款
			// LENDPAYFAIL 放款失败
			// LENDPAYING 放款中
			// WITHDRAWSUCCESS 提现成功
			// NOWITHDRAW 未提现
			// WITHDRAWING 提现处理中
			// WITHDRAWFAIL 提现失败
			// EARLYSETTLE 放款后24小时未提现，提前兑付
			LoanApply loanApply = loanApplyManager.getLoanApplyById(log.getApplyId());

			if (loanApply == null) {
				logger.error("修改放款渠道，贷款申请单不存在，applyId = {}", log.getApplyId());
				throw new IllegalArgumentException("贷款申请单不存在，applyId = " + log.getApplyId());
			}
			if (loanApply.getStatus().intValue() >= ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue()) {
				logger.error("修改放款渠道，贷款申请单状态不正确，applyId = {}", log.getApplyId());
				throw new IllegalArgumentException("贷款申请单状态不正确，applyId = " + log.getApplyId());
			}
			if ("4".equals(loanApply.getSource())
					&& ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
					&& JedisUtils.get(Global.RONG_CREATE_ACCOUNT + log.getApplyId()) == null) {
				logger.error("修改放款渠道，融360未确认借款,不能修改渠道,只能取消，applyId = {}", log.getApplyId());
				throw new IllegalArgumentException("融360未确认借款,不能修改渠道,只能取消，applyId = " + log.getApplyId());
			}
			HanJSResultVO vo = queryOrderState(applyId);
           // vo.setData("EARLYSETTLE");
			if ("EARLYSETTLE".equals(vo.getData())) {
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

				BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(log.getApplyId());
				if (borrowInfo != null) {
					borrowInfo.setPayChannel(Integer.parseInt(paychannel));
					borrowInfo.setPushStatus(loanApply.getStatus());
					borrowInfo.preUpdate();
					borrowInfoManager.update(borrowInfo);
				}
				// log.setRemark(oldPayChannel + "_TO_" + paychannel);
				log.setStatus("515");
				log.setRemark("未提现,转乐视放款");
				payLogService.update(log);

				// 修改放款渠道时，在loan_cancel_log插入数据
				CancelLog cancelLog = new CancelLog();
				cancelLog.preInsert();
				cancelLog.setUserId(log.getUserId());
				cancelLog.setUserName(log.getUserName());
				cancelLog.setMobile(log.getToMobile());
				cancelLog.setCardNo(log.getToAccNo());
				cancelLog.setIdNo(log.getToIdno());
				cancelLog.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue());
				cancelLogService.saveCancelLog(cancelLog);

				return new AdminWebResult("1", "修改放款渠道成功");
			} else {
				return new AdminWebResult("99", "修改放款渠道失败," + vo.getMessage());
			}
		} else {
			return new AdminWebResult("99", "修改放款渠道失败,订单状态异常");
		}
	}

}
