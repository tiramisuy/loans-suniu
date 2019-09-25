package com.rongdu.loans.loan.manager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.rongdu.loans.enums.PayChannelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.cust.entity.CustCoupon;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.manager.MessageManager;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.MsgEnum;
import com.rongdu.loans.enums.RepayDeductionTypeEnum;
import com.rongdu.loans.external.manager.PushAssetManager;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.Overdue;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.loan.vo.OverdueRepayNoticeVO;
import com.rongdu.loans.loan.vo.RepayNoticeVO;
import com.rongdu.loans.loan.vo.StatementVO;

/**
 * 结算本地处理实现类
 * 
 * @author likang
 * 
 */
@Service("settlementManager")
public class SettlementManager {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;

	@Autowired
	private RepayPlanItemManager repayPlanItemManager;

	@Autowired
	private PromotionCaseManager promotionCaseManager;

	@Autowired
	private LoanApplyManager loanApplyManager;

	@Autowired
	private ContractManager contractManager;

	@Autowired
	private BorrowInfoManager borrowInfoManager;

	@Autowired
	private PushAssetManager pushAssetManager;

	@Autowired
	private MessageManager messageManager;

	@Autowired
	private OverdueManager overdueManager;

	@Autowired
	private CustCouponManager custCouponManager;

	/**
	 * 确认支付结果 1、 更新还款计划明细 2、 更新还款计划 3、 申请单完结情况时，更新申请状态，保存操作日志 4、 申请单完结情况时，更新合同状态
	 * 5、 发送站内信通知 6、 推送还款信息到理财端
	 */
	@Transactional
	public ConfirmPayResultVO Settlement(final RePayOP rePayOP) {
		// 构造返回对象
		ConfirmPayResultVO rzVo = new ConfirmPayResultVO();
		if (null == rePayOP) {
			logger.error("error, the param is null");
			rzVo.setResult(0);
			return rzVo;
		}
		// 提前还款标识
		rzVo.setPrePayFlag(rePayOP.getPrePayFlag());
		// 还款计划更新对象
		LoanRepayPlan updateLoanRepayPlan = new LoanRepayPlan();
		// 还款计划明细更新对象
		RepayPlanItem updateLoanRepayPlanItem = new RepayPlanItem();
		// 申请数据更新对象
		LoanApply loanApply = new LoanApply();
		// 提前还款推送通知对象设置
		RepayNoticeVO repayNoticeVO = new RepayNoticeVO();

		// 账目核对
		// 根据还款计划明细id查询还款计划
		StatementVO statementVO = loanRepayPlanManager.getStatementByItemId(rePayOP.getRepayPlanItemId());
		if (null != statementVO) {
			// 申请编号
			updateLoanRepayPlan.setApplyId(rePayOP.getApplyId());
			// 提前还款服务费
			BigDecimal prepayFee = statementVO.getCurPrepayFee();
			// 当前应还本金
			BigDecimal actualTotalAmount = statementVO.getCurTotalAmount();
			// 还款计划-已还款期数
			updateLoanRepayPlan.setPayedTerm(statementVO.getLoanPayedTerm() + 1);
			// 还款计划-待还款期数
			updateLoanRepayPlan.setUnpayTerm(statementVO.getLoanUnpayTerm() - 1);
			// 还款计划-已经还本金
			updateLoanRepayPlan.setPayedPrincipal(
					CostUtils.add(statementVO.getLoanPayedPrincipal(), statementVO.getCurPrincipal()));
			// 还款计划-待还本金
			updateLoanRepayPlan.setUnpayPrincipal(
					CostUtils.sub(statementVO.getLoanUnpayPrincipal(), statementVO.getCurPrincipal()));
			// 还款计划-已还利息
			updateLoanRepayPlan
					.setPayedInterest(CostUtils.add(statementVO.getLoanPayedInterest(), statementVO.getCurInterest()));
			// 还款计划-待还利息
			updateLoanRepayPlan
					.setUnpayInterest(CostUtils.sub(statementVO.getLoanUnpayInterest(), statementVO.getCurInterest()));
			// 还款计划-实际还款日期,格式yyyy-MM-dd HH:mm:ss
			Date txTime = null;
			if (PayChannelEnum.XIANFENG.getChannelCode().equals(rePayOP.getChlCode())) {
				txTime = DateUtils.parse(rePayOP.getPaySuccTime(), PayChannelEnum.XIANFENG.getTimePattern());
			} else if (PayChannelEnum.TONGLIAN.getChannelCode().equals(rePayOP.getChlCode())){
				txTime = DateUtils.parse(rePayOP.getPaySuccTime(), PayChannelEnum.TONGLIAN.getTimePattern());
			}
			updateLoanRepayPlan.setCurRealRepayDate(txTime);
			updateLoanRepayPlan.preUpdate();
			// 还款计划-实际还款日期,格式yyyy-MM-dd
			Date txDate = DateUtils.parse(DateUtils.formatDate(txTime, "yyyy-MM-dd"), "yyyy-MM-dd");
			// 还款计划-实际还款日期,格式yyyy-MM-dd String
			String txDateString = DateUtils.formatDate(txDate, "yyyy-MM-dd");

			// 还款计划明细id
			updateLoanRepayPlanItem.setId(rePayOP.getRepayPlanItemId());
			// 还款计划明细-已还本金
			updateLoanRepayPlanItem.setPayedPrincipal(statementVO.getCurPrincipal());
			// 还款计划明细-待还本金
			updateLoanRepayPlanItem.setUnpayPrincipal(new BigDecimal(0.00));
			// 还款计划明细-已还利息
			updateLoanRepayPlanItem.setPayedInterest(statementVO.getCurInterest());
			// 还款计划明细-待还利息
			updateLoanRepayPlanItem.setUnpayInterest(new BigDecimal(0.00));
			// 还款计划明细-实际还款时间
			updateLoanRepayPlanItem.setActualRepayTime(txTime);
			// 还款计划明细-实际还款日期
			// updateLoanRepayPlanItem.setActualRepayDate(DateUtils.getDate());
			updateLoanRepayPlanItem.setActualRepayDate(txDateString);
			// 还款计划明细-实际还款金额
			BigDecimal curActualRepayAmt = statementVO.getCurActualRepayAmt();
			BigDecimal paySuccAmt = new BigDecimal(rePayOP.getPaySuccAmt());
			BigDecimal actualRepayAmt = curActualRepayAmt == null ? paySuccAmt : curActualRepayAmt.add(paySuccAmt);
			updateLoanRepayPlanItem.setActualRepayAmt(actualRepayAmt);
			// 还款计划明细-还款方式
			updateLoanRepayPlanItem.setRepayType(rePayOP.getTxType());
			// 还款计划明细-是否已经结清
			updateLoanRepayPlanItem.setStatus(Global.REPAY_PLAN_STATUS_OVER);
			updateLoanRepayPlanItem.preUpdate();

			// 购物券抵扣
			if (RepayDeductionTypeEnum.YES.getValue().equals(rePayOP.getIsDeduction())) {
				BigDecimal curDeductionAmt = new BigDecimal(rePayOP.getDeductionAmt());
				// 更新还款计划减免金额
				updateLoanRepayPlan.setDeduction(CostUtils.add(statementVO.getLoanDeduction(), curDeductionAmt));
				updateLoanRepayPlan.setTotalAmount(CostUtils.sub(statementVO.getLoanTotalAmount(), curDeductionAmt));
				// 更新还款计划明细减免金额
				updateLoanRepayPlanItem.setDeduction(CostUtils.add(statementVO.getCurDeduction(), curDeductionAmt));
				updateLoanRepayPlanItem.setTotalAmount(CostUtils.sub(statementVO.getCurTotalAmount(), curDeductionAmt));
				updateLoanRepayPlanItem.setCouponId(rePayOP.getCouponId());
				// 更新购物券使用状态
				CustCoupon custCoupon = new CustCoupon();
				custCoupon.setStatus(1);
				custCouponManager.updateCouponStatus(Arrays.asList(rePayOP.getCouponId().split(",")), custCoupon);
			}

			// 贷款是否清算完结
			boolean isOver = false;
			// 判断当前期是否为最后一期
			if (statementVO.getCurTerm() == statementVO.getLoanTotalTerm()) {
				isOver = true;
				updateLoanRepayPlan.setStatus(Global.REPAY_PLAN_STATUS_OVER);
				updateLoanRepayPlan.setNextRepayDate(DateUtils.getDate());
				// 申请单阶段
				loanApply.setStage(XjdLifeCycle.LC_REPAY);
				// 申请单阶段状态
				loanApply.setStatus(XjdLifeCycle.LC_REPAY_2);
			} else {
				updateLoanRepayPlan.setCurrentTerm(statementVO.getCurTerm() + 1);

				RepayPlanItem nextItem = repayPlanItemManager.getUnoverItemByTerm(rePayOP.getApplyId(),
						updateLoanRepayPlan.getCurrentTerm().intValue());
				if (nextItem != null)
					updateLoanRepayPlan.setNextRepayDate(DateUtils.formatDate(nextItem.getRepayDate()));
			}

			/** 提前还款情况相关逻辑 */
			// 判断是否提前还款
			// 提前还款天数
			int prepayDays = 0;
			// 提前还款天数
			prepayDays = DateUtils.daysBetween(new Date(), statementVO.getCurRepayDate());
			// prepayDays = DateUtils.daysBetween(txDate,
			// statementVO.getCurRepayDate());
			if (logger.isDebugEnabled()) {
				logger.debug("PREPAY_FLAG_YES:[{}]; prepayDays[{}]", rePayOP.getPrePayFlag(), prepayDays);
			}
			/** 更新还款计划明细 */
			int rz = repayPlanItemManager.updatePayResult(updateLoanRepayPlanItem);
			if (rz == 0) {
				rzVo.setMessage(" 更新还款计划明细失败");
			} else if (rz == 1) {
				/** 更新还款计划 */
				rz = loanRepayPlanManager.updatePayResult(updateLoanRepayPlan);
			}
			if (rz == 0) {
				rzVo.setMessage("更新还款计划失败");
			}
			/** 有逾期的情况更新逾期还款表状态 */
			if (prepayDays < 0 && rz == 1) {
				overOverdue(rePayOP.getRepayPlanItemId(), rePayOP.getPaySuccAmt(), txDateString, txTime);
			}
			if (isOver && rz == 1) {
				/** 申请单完结情况时，更新申请状态，保存操作日志 */
				rz = updateStatusAndSaveLog(loanApply, rePayOP, statementVO, txDate);
				if (rz == 0) {
					rzVo.setMessage("更新申请单状态或保存操作日记失败");
					// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				} else if (rz == 1) {
					/** 申请单完结情况时，更新合同状态 */
					rz = OverContract(rePayOP.getApplyId());
					if (rz == 0) {
						rzVo.setMessage("更新合同信息失败");
						// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}
				}
			}

			/** 发送站内信通知 */
			if (rz == 1) {
				rz = sendMessage(rePayOP.getUserId(), rePayOP.getPaySuccAmt());
			}
			// 返回处理结果
			rzVo.setResult(rz);
			return rzVo;
		} else {
			rzVo.setMessage("还款计划不存在或重复还款");
		}
		rzVo.setResult(0);
		return rzVo;
	}

	/**
	 * 更新申请单状态，插入操作日志
	 * 
	 * @param loanApply
	 * @param rePayOP
	 * @param statementVO
	 * @return
	 */
	private int updateStatusAndSaveLog(LoanApply loanApply, RePayOP rePayOP, StatementVO statementVO, Date txDay) {
		loanApply.setId(rePayOP.getApplyId());
		loanApply.setUserId(rePayOP.getUserId());
		loanApply.setSource(rePayOP.getSource());
		loanApply.setIp(rePayOP.getIp());
		// 申请单张状态
		loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
		// 逾期情况判断
		// 提前天数
		int prepayDays = DateUtils.daysBetween(txDay, statementVO.getCurRepayDate());
		// if(statementVO!=null && statementVO.getCurPenalty()!=null &&
		// statementVO.getCurPenalty().doubleValue() > 0) {
		if (prepayDays < 0) {
			// 申请单阶段
			loanApply.setStage(XjdLifeCycle.LC_OVERDUE);
			// 申请单阶段状态
			loanApply.setStatus(XjdLifeCycle.LC_OVERDUE_1);
		}
		return loanApplyManager.updateStatusAndSaveLog(loanApply);
	}

	/**
	 * 完结合同
	 * 
	 * @param applyId
	 * @return
	 */
	private int OverContract(String applyId) {
		Contract contract = new Contract();
		contract.setApplyId(applyId);
		return contractManager.OverContract(contract);
	}

	/**
	 * 完结逾期还款列表记录
	 * 
	 * @param id
	 * @param paySuccAmt
	 * @return
	 */
	public int overOverdue(String id, String paySuccAmt, String paySuccDate, Date paySuccTime) {
		try {
			Overdue overdue = overdueManager.get(id);
			if (overdue != null) {
				overdue.setActualRepayAmt(new BigDecimal(paySuccAmt));
				overdue.setActualRepayDate(paySuccDate);
				overdue.setActualRepayTime(paySuccTime);
				overdue.setOverdueEndDate(paySuccTime);
				overdue.setStatus(ApplyStatusEnum.FINISHED.getValue());
				overdue.setUpdateTime(new Date());
				return overdueManager.update(overdue);
			}
		} catch (Exception e) {
			logger.error("更新逾期记录失败，id={}, msg={}", id, e.getMessage());
		}
		return 0;

		// Overdue overdue = new Overdue(id);
		// Overdue overdue = overdueManager.get(id);
		// //overdue.setId(id);
		// overdue.setActualRepayAmt(new BigDecimal(paySuccAmt));
		// overdue.setActualRepayDate(DateUtils.getDate());
		// overdue.setActualRepayTime(new Date());
		// overdue.setOverdueEndDate(new Date());
		// overdue.setStatus(ApplyStatusEnum.FINISHED.getValue());
		// overdue.preInsert();
		// return overdueManager.update(overdue);
	}

	/**
	 * 发送站内信通知
	 * 
	 * @param userId
	 * @param paySuccAmt
	 * @return
	 */
	private int sendMessage(String userId, String paySuccAmt) {
		// 初始化内部信参数对象
		Message message = new Message();
		message.setUserId(userId);
		message.setTitle(ShortMsgTemplate.REPAY_SUCCESS_TITLE);
		// 通知内容
		String content = String.format(ShortMsgTemplate.REPAY_SUCCESS, paySuccAmt);
		message.setContent(content);
		message.setType(Global.CUST_MESSAGE_TYPE_SYS);
		message.setNotifyTime(new Date());
		message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
		message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
		message.setStatus(MsgEnum.SEND_SUCCSS.getValue());
		message.setDel(0);
		message.preInsert();
		return messageManager.insert(message);
	}

	/**
	 * 逾期情况推送信息到理财端
	 * 
	 * @param statementVO
	 * @param paySuccAmt
	 * @param applyId
	 * @param userId
	 * @return
	 */
	private String overdueRepayPush(StatementVO statementVO, String paySuccAmt, String applyId, String userId,
			String payComOrderNo) {
		// 构造逾期推送对象
		OverdueRepayNoticeVO overdueRepayNoticeVO = new OverdueRepayNoticeVO();

		// 还款日期 yyyy-MM-dd HH:mm:ss
		overdueRepayNoticeVO.setRepayDate(DateUtils.getDate(DateUtils.FORMAT_LONG));
		// // 逾期-还款金额
		// overdueRepayNoticeVO.setRepayAmount(paySuccAmt);
		// 逾期-还款本金
		overdueRepayNoticeVO.setPrincipal(String.valueOf(statementVO.getCurPrincipal()));
		// 逾期-还款利息
		overdueRepayNoticeVO.setInterest(String.valueOf(statementVO.getCurInterest()));
		// 逾期管理费
		overdueRepayNoticeVO.setOverdueFee(String.valueOf(statementVO.getCurOverdueFee()));
		// 逾期罚息
		overdueRepayNoticeVO.setOverdueInterest(String.valueOf(statementVO.getCurPenalty()));
		// 减免费用
		overdueRepayNoticeVO.setReduceFee(String.valueOf(statementVO.getCurDeduction()));
		// 查询标的信息
		BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(applyId);
		if (null != borrowInfo) {
			// 资产id
			overdueRepayNoticeVO.setAssetId(borrowInfo.getOutsideSerialNo());
			// 电子账户
			overdueRepayNoticeVO.setAccountId(borrowInfo.getAccountId());
		}

		return pushAssetManager.OverdueRepayPush(overdueRepayNoticeVO, userId, applyId, payComOrderNo);
	}

	/**
	 * 正常按时还款推送信息到理财端
	 * 
	 * @param statementVO
	 * @param paySuccAmt
	 * @param applyId
	 * @param userId
	 * @return
	 */
	private String onTimeRepayPush(StatementVO statementVO, String paySuccAmt, String applyId, String userId,
			String payComOrderNo) {
		// 构造逾期推送对象
		RepayNoticeVO repayNoticeVO = new RepayNoticeVO();
		// 还款金额
		repayNoticeVO.setRepayAmount(paySuccAmt);
		// 计息开始日
		if (null != statementVO.getLoanStartDate()) {
			String interestStartDate = String.valueOf(DateUtils.getYYYYMMDD2Int(statementVO.getLoanStartDate()));
			repayNoticeVO.setInterestStartDate(interestStartDate);
			// 计息结束日（还款日期）
			repayNoticeVO.setInterestEndDate(String.valueOf(DateUtils.getYYYYMMDD2Int()));
		}
		// 查询标的信息
		BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(applyId);
		if (null != borrowInfo) {
			// 资产id
			repayNoticeVO.setAssetId(borrowInfo.getOutsideSerialNo());
			// 电子账户
			repayNoticeVO.setAccountId(borrowInfo.getAccountId());
		}
		return pushAssetManager.onTimeRepayPush(repayNoticeVO, userId, applyId, payComOrderNo);
	}

}
