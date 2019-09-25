/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.compute.helper.ContractHelper;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.entity.ShopWithhold;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.manager.ContractManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanRepayPlanManager;
import com.rongdu.loans.loan.manager.OverdueManager;
import com.rongdu.loans.loan.manager.PromotionCaseManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.manager.ShopWithholdManager;
import com.rongdu.loans.loan.option.ShopWithholdOP;
import com.rongdu.loans.loan.service.BorrowInfoService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.service.ShopWithholdService;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.PrePayCostingVO;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.loan.vo.ShopWithholdVO;
import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.service.BaofooWithholdService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

/**
 * 代扣服务
 * 
 * @author zhangxiaolong
 * @version 2017-07-22
 */
@Service("shopWithholdService")
public class ShopWithholdServiceImpl extends BaseService implements ShopWithholdService {

	/**
	 * 购物款代扣-实体管理接口
	 */
	@Autowired
	private ShopWithholdManager shopWithholdManager;

	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private RepayLogService repayLogService;

	@Autowired
	private WithholdService withholdService;

	@Autowired
	private CustUserService userService;
	@Autowired
	private BorrowInfoService borrowInfoService;
	@Autowired
	private BaofooWithholdService baofooWithholdService;

	@Autowired
	private PromotionCaseManager promotionCaseManager;

	@Autowired
	private BorrowInfoManager borrowInfoManager;

	@Autowired
	private ContractManager contractManager;

	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;

	@Autowired
	private RepayPlanItemManager repayPlanItemManager;

	@Autowired
	private OverdueManager overdueManager;
	@Autowired
	private PayLogService payLogService;

	/**
	 * 用于更改购物款代扣状态
	 */
	@Override
	public boolean updateShopWithhold(String applyId, String remark, Integer status) {
		int flag = 0;
		ShopWithhold withHold = new ShopWithhold();
		withHold = shopWithholdManager.findByApplyId(applyId);
		if (withHold != null) { // 如果不为空则做update
			if (withHold.getWithholdStatus() != 0) { // 如果查询到代扣状态已经成功，则需要再做update
				withHold.setWithholdStatus(status);
				withHold.setRemark(remark);
				flag = shopWithholdManager.updateShopWithHold(withHold);
			}
		}
		if (flag > 0) {
			// 0=成功,1=失败
			if (status.intValue() == 0) {
				// 代扣服务费成功通知到p2p平台
				borrowInfoService.whithholdServFeeSuccessNotify(applyId);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 购物款代扣代扣 applyId 申请Id withHoldAmt 代扣金额 custUserId 客户ID
	 */
	@Override
	public XfAgreementPayResultVO doShopWithhold(String applyId, String amount) {
		// 代扣操作
		XfAgreementPayResultVO vo = new XfAgreementPayResultVO();
		Integer withHoldStatus = 0;
		// 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
		Long payCount = repayLogService.countPayingByApplyId(applyId);
		if (payCount != 0) {
			logger.error("代扣数据异常，订单正在处理中：{}", applyId);
			vo.setStatus("I");
			vo.setSuccess(false);
			return vo;
		}
		/** 先锋- 购物款代扣 */
		vo = withholdService.xfWithholdByApplyId(applyId, amount, PayTypeEnum.SHOPPING.getId());
		logger.info("代扣购物金:{},{}", applyId, JsonMapper.toJsonString(vo));
		if (vo.getSuccess()) { // 代扣成功
			withHoldStatus = 0;
		} else { // 代扣失败
			if ("I".equals(vo.getStatus())) { // 代扣处理中
				withHoldStatus = 2;
			} else { // 代扣失败
				withHoldStatus = 1;
			}
		}
		ShopWithhold withHold = shopWithholdManager.findByApplyId(applyId);
		int withholdNum = (withHold == null || withHold.getWithholdNumber() == null) ? 1
				: withHold.getWithholdNumber() + 1;
		// 先锋代扣失败，接着宝付代扣
		// 00041=暂不支持该银行
		if (withHoldStatus == 1 && "00041".equals(vo.getResCode())) {
			withHoldStatus = baofooWithholdShopping(applyId);
		}
		String msg = vo.getResMessage();
		if (withHoldStatus == 0) {
			msg = "成功";
		} else if (withHoldStatus == 2) {
			msg = "交易处理中";
		}
		if (withHold != null) {
			if (withHold.getWithholdStatus() != 0) { // 如果代扣状态已经成功，则不需要再做修改
				withHold.setWithholdStatus(withHoldStatus);
				withHold.setRemark(msg);
				withHold.setWithholdNumber(withholdNum);
				withHold.setWithholdTime(new Date());
				int flag = shopWithholdManager.updateShopWithHold(withHold);
				if (withHoldStatus == 0 && flag > 0) {
					// 代扣服务费成功通知到p2p平台
					borrowInfoService.whithholdServFeeSuccessNotify(applyId);
				}
			}
		} else {
			LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
			withHold = new ShopWithhold();
			withHold.setId(IdGen.uuid());
			withHold.setApplyId(applyId);
			withHold.setCustUserId(loanApply.getUserId());
			withHold.setWithholdNumber(withholdNum);
			withHold.setWithholdStatus(withHoldStatus);
			withHold.setWithholdTime(new Date());
			withHold.setWithholdFee(new BigDecimal(vo.getAmountYuan())); // 代扣成功金额
			withHold.setRemark(msg); // 代扣应答消息
			withHold.setCreateTime(new Date());
			int flag = shopWithholdManager.insert(withHold);
			if (withHoldStatus == 0 && flag > 0) {
				// 代扣服务费成功通知到p2p平台
				borrowInfoService.whithholdServFeeSuccessNotify(applyId);
			}
		}
		return vo;
	}

	/**
	 * 定时任务执行购物款代扣2、3次
	 */
	public TaskResult doShopWithholdTask() {
		List<ShopWithholdVO> list = shopWithholdManager.selectFaildShopWithHold();
		logger.info("代扣购物金任务开始");
		int success = 0;
		int fail = 0;
		long starTime = System.currentTimeMillis();
		for (ShopWithholdVO shopWithholdVO : list) {
			try {
				// Thread.sleep(2000);
				XfAgreementPayResultVO vo = doShopWithhold(shopWithholdVO.getApplyId(), shopWithholdVO.getWithholdFee()
						.toString());
				if (vo.getSuccess()) {
					success++;
				} else {
					fail++;
				}
			} catch (Exception e) {
				fail++;
				logger.error("代扣购物金失败，参数： " + JsonMapper.getInstance().toJson(shopWithholdVO), e);
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("代扣购物金任务结束，成功{}笔，失败{}笔。执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

	/**
	 * 客服手动代扣
	 */
	@Override
	public boolean handShopWithhold(String id) {
		String lockKey = "loan_shop_withhold_lock_" + id;
		String requestId = String.valueOf(System.nanoTime());// 请求标识
		boolean lock = JedisUtils.setLock(lockKey, requestId, 60);
		if (!lock) {
			return false;
		}
		ShopWithholdVO shopWithholdVO = BeanMapper.map(shopWithholdManager.getById(id), ShopWithholdVO.class);
		if (shopWithholdVO != null) {
			XfAgreementPayResultVO vo = doShopWithhold(shopWithholdVO.getApplyId(), shopWithholdVO.getWithholdFee()
					.toString());
			return vo.getSuccess();
		}
		return false;
	}

	/**
	 * 三次代扣失败代扣信息列表，用于展示页面客服提醒
	 */
	public Page<ShopWithholdVO> selectShopWithholdList(ShopWithholdOP op) {
		Page<ShopWithholdVO> voPage = new Page<ShopWithholdVO>(op.getPageNo(), op.getPageSize());
		List<ShopWithholdVO> voList = (List<ShopWithholdVO>) shopWithholdManager.selectShopWithHoldList(voPage, op);
		voPage.setList(voList);
		return voPage;

	}

	public List<RepayDetailListVO> getLoanApply(ShopWithholdOP op) {
		List<LoanApply> list = shopWithholdManager.getLoanApply(op);
		List<RepayDetailListVO> result = new ArrayList<RepayDetailListVO>();
		for (LoanApply apply : list) {
			List<RepayDetailListVO> repayItem = createRepayPlanItem(apply);
			result.addAll(repayItem);
		}
		return result;

	}

	@Override
	public int insertRepayLog(String applyId, String chlId, String chlName) {
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUserVO user = userService.getCustUserById(loanApply.getUserId());
		ShopWithhold withHold = new ShopWithhold();
		withHold = shopWithholdManager.findByApplyId(applyId);
		String orderId = IdGen.uuid();
		RepayLogVO order = new RepayLogVO();
		order.setId(orderId);
		order.setNewRecord(true);
		order.setUserId(loanApply.getUserId());
		order.setApplyId(applyId);
		order.setContractId(loanApply.getContNo()); // 合同编号
		// order.setRepayPlanItemId(op.getRepayPlanItemId()); //还款明细ID

		order.setUserName(user.getRealName());
		order.setIdNo(user.getIdNo());
		order.setMobile(user.getMobile());
		order.setBankCode(user.getBankCode());
		// order.setBankName(bankId); //银行名称
		order.setCardNo(user.getCardNo());
		order.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());

		order.setTxType("MANPAY");
		order.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
		order.setTxTime(new Date());
		order.setTxAmt(withHold.getWithholdFee());
		order.setTxFee(BigDecimal.ZERO);

		// order.setTerminal(op.getSource()); //终端类型
		// order.setIp(op.getIp());
		// order.setChlOrderNo(result.getTradeNo()); //支付公司订单号
		order.setChlCode(chlId);
		order.setChlName(chlName);
		order.setGoodsName("线下还款");
		order.setGoodsNum(1);

		order.setStatus("SUCCESS");
		order.setRemark("交易成功");
		order.setPayType(4);
		order.setSuccAmt(withHold.getWithholdFee());
		order.setSuccTime(new Date());
		order.setStatus(ErrInfo.SUCCESS.getCode());
		logger.debug("线下还款：{}，{}元，{}", user.getRealName(), withHold.getWithholdFee(), orderId);
		int rz = repayLogService.save(order);
		return rz;
	}

	/**
	 * 宝付代扣购物金
	 * 
	 * @param applyId
	 * @return 1=成功，2=失败，3=处理中
	 */
	public int baofooWithholdShopping(String applyId) {
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUserVO user = userService.getCustUserById(loanApply.getUserId());

		WithholdOP param = new WithholdOP();
		param.setUserId(user.getId());
		param.setRealName(user.getRealName());
		param.setIdNo(user.getIdNo());
		if (StringUtils.isNotBlank(user.getBankMobile())) {
			param.setMobile(user.getBankMobile());
		} else {
			param.setMobile(user.getMobile());
		}
		param.setBankCode(user.getBankCode());
		param.setCardNo(user.getCardNo());
		param.setTxnAmt(loanApply.getServFee().multiply(BigDecimal.valueOf(100)).toString());
		param.setApplyId(loanApply.getId());
		param.setContNo(loanApply.getContNo());
		param.setRepayPlanItemId("");

		/** 宝付代扣 购物金 */
		WithholdResultVO vo = baofooWithholdService.withhold(param, PayTypeEnum.SHOPPING.getId());
		if (RepayUnsolvedServiceImpl.isBaofooRespSuccess(vo.getCode())) {
			return 0;
		} else if (RepayUnsolvedServiceImpl.isBaofooRespUnsolved(vo.getCode())) {
			return 2;
		} else {
			return 1;
		}
	}

	/**
	 * 生产还款计划明细/组装导出list
	 * 
	 * @param loanApply
	 * @param loanRepayPlan
	 * @return
	 */

	public List<RepayDetailListVO> createRepayPlanItem(LoanApply loanApply) {

		List<RepayDetailListVO> result = new ArrayList<RepayDetailListVO>();

		// 1，生成合同
		Contract contract = createContract(loanApply, loanApply.getCreateTime());

		// 2,生成还款计划
		LoanRepayPlan loanRepayPlan = createRepayPlan(loanApply, contract, 3); // WithdrawalSource
																				// 1
																				// 线上
																				// ，2线下

		// 3，生成还款计划明细
		List<RepayPlanItem> list = new ArrayList<RepayPlanItem>();
		for (int i = 1; i <= loanRepayPlan.getTotalTerm(); i++) {
			String id = i < 10 ? loanRepayPlan.getApplyId() + "0" + i : loanRepayPlan.getApplyId() + i;

			// 组装导出还款计划明细数据
			RepayDetailListVO repay = new RepayDetailListVO();
			repay.setId(id);
			repay.setApplyId(loanRepayPlan.getApplyId());
			repay.setContNo(loanRepayPlan.getContNo());
			repay.setThisTerm(i);
			repay.setTotalTerm(loanRepayPlan.getTotalTerm());
			BigDecimal totalAmount = RepayPlanHelper.getTermPrincipal(loanApply, loanRepayPlan, i)
					.add(RepayPlanHelper.getTermInterest(loanApply, loanRepayPlan, i))
					.add(RepayPlanHelper.getTermServFee(loanApply));
			repay.setTotalAmount(totalAmount);
			repay.setServFee(RepayPlanHelper.getTermServFee(loanApply));
			repay.setInterest(RepayPlanHelper.getTermInterest(loanApply, loanRepayPlan, i));
			repay.setDeduction(BigDecimal.ZERO);
			// repay.setActualRepayTime(actualRepayTime); //实际还款实际：无
			repay.setRepayDate(RepayPlanHelper.getRepayDate(loanApply, contract, i));
			repay.setPenalty(BigDecimal.ZERO);
			repay.setPrepayFee(BigDecimal.ZERO);
			// repay.setActualRepayAmt(actualRepayAmt); //实际还款金额：无
			// repay.setRepayType(repayType); //还款方式：无
			repay.setPrincipal(RepayPlanHelper.getTermPrincipal(loanApply, loanRepayPlan, i));
			repay.setOverdueFee(BigDecimal.ZERO);
			repay.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
			repay.setServFeeRate(loanApply.getServFeeRate());
			repay.setUserId(loanApply.getUserId());
			repay.setUserName(loanApply.getUserName());
			repay.setIdNo(loanApply.getIdNo());
			repay.setMobile(loanApply.getMobile());
			repay.setProductName(loanApply.getProductName());
			repay.setApproveAmt(loanApply.getApproveAmt());
			repay.setApproveTerm(loanApply.getApproveTerm());
			repay.setBasicRate(loanApply.getBasicRate());
			repay.setDiscountRate(loanApply.getDiscountRate());
			repay.setRepayMethod(loanApply.getRepayMethod());
			repay.setLoanStartDate(loanRepayPlan.getLoanStartDate());
			repay.setApproverName(loanApply.getApproverName());
			repay.setServFee(RepayPlanHelper.getTermServFee(loanApply));
			result.add(repay);

		}
		return result;
	}

	/**
	 * 根据合同生成还款计划
	 * 
	 * @param contract
	 * @return
	 */
	public LoanRepayPlan createRepayPlan(LoanApply loanApply, Contract contract, int withdrawalSource) {
		LoanRepayPlan loanRepayPlan = BeanMapper.map(contract, LoanRepayPlan.class);
		loanRepayPlan.preInsert();
		loanRepayPlan.setContNo(contract.getId());
		loanRepayPlan.setServFee(RepayPlanHelper.getTotalServFee(loanApply));
		loanRepayPlan.setOverdueFee(contract.getOverdueFee());
		loanRepayPlan.setPayedTerm(0);
		loanRepayPlan.setUnpayTerm(contract.getTotalTerm());
		loanRepayPlan.setPayedPrincipal(BigDecimal.ZERO);
		loanRepayPlan.setUnpayPrincipal(contract.getPrincipal());
		loanRepayPlan.setPayedInterest(BigDecimal.ZERO);
		loanRepayPlan.setUnpayInterest(contract.getInterest());
		loanRepayPlan.setNextRepayDate(RepayPlanHelper.getRepayDateStr(loanApply, contract, 1));
		loanRepayPlan.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
		loanRepayPlan.setCurrentTerm(1);
		loanRepayPlan.setWithdrawalSource(withdrawalSource);
		return loanRepayPlan;
	}

	/**
	 * 根据贷款申请单生成合同
	 * 
	 * @param loanApply
	 * @param payTime
	 *            放款时间
	 * @return
	 */
	public Contract createContract(LoanApply loanApply, Date payTime) {
		Contract contract = BeanMapper.map(loanApply, Contract.class);
		contract.preInsert();
		contract.setId(loanApply.getContNo());
		contract.setApplyId(loanApply.getId());
		contract.setLoanStartDate(payTime);
		contract.setLoanEndDate(ContractHelper.getLoanEndDate(loanApply, contract.getLoanStartDate()));
		contract.setPrincipal(loanApply.getApproveAmt());
		contract.setTotalTerm(loanApply.getTerm());
		contract.setOverdueFee(loanApply.getOverdueFee());
		contract.setOverdueRate(loanApply.getOverdueRate());
		contract.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
		contract.setContTime(payTime);
		contract.setPayTime(payTime);
		contract.setLoanDays(loanApply.getApproveTerm());
		contract.setRepayMethod(Integer.valueOf(loanApply.getRepayMethod()));
		contract.setGraceType(Global.DEFAULT_GRACE_TYPE);
		contract.setGraceDay(0);
		contract.setFixPenaltyInt(Integer.valueOf(Global.YES));
		contract.setCompInt(Integer.valueOf(Global.NO));
		contract.setTotalAmount(contract.getPrincipal().add(contract.getInterest()));
		contract.setRemark(null);
		// 一次性还本付息的时候计算提前还款费用
		if (RepayMethodEnum.ONE_TIME.getValue().equals(contract.getRepayMethod())) {
			PrePayCostingVO prePayCostingVO = promotionCaseManager.calPrepayFee(loanApply.getId());
			if (prePayCostingVO != null && prePayCostingVO.getActualPrepayFee() != null) {
				contract.setPrepayFee(prePayCostingVO.getActualPrepayFee());
			}
		}
		// y:合同
		return contract;
	}

	@Override
	public Boolean isWithholdSuccess(String lid) {
		String applyId = borrowInfoManager.getApplyIdByOutSideNum(lid);
		if (null == applyId) {
			logger.info("重新放款,订单号不存在,标id:{}", lid);
			return false;
		}
		// 防止598重复扣款
		ShopWithhold withHold = shopWithholdManager.findByApplyId(applyId);
		if (withHold != null && withHold.getWithholdStatus().intValue() == 0) {
			return true;
		}
		logger.info("重新放款,598未扣款成功,标id:{}", lid);
		return false;
	}

	/**
	 * 用于修改还款日 更改合同表放款时间，然后生成计划总表时间和计划明细表时间 做修改
	 * 
	 * @param loanApply
	 * @return
	 */
	@Transactional
	public void updateRepayTime(String applyId, Date payTime) {
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		if (loanApply == null) {
			logger.error("修改账单日，贷款申请单不存在，applyId = {}", applyId);
			return;
		}
		// 1，修改合同
		Contract contract = createContract(loanApply, payTime);
		contractManager.updateforRepayTime(contract);
		// 2,修改还款计划
		LoanRepayPlan loanRepayPlan = createRepayPlan(loanApply, contract,
				WithdrawalSourceEnum.WITHDRAWAL_ONLINE.getValue());
		loanRepayPlanManager.updateRepayTime(loanRepayPlan);
		// 3，修改还款计划明细
		for (int i = 1; i <= loanRepayPlan.getTotalTerm(); i++) {
			String id = i < 10 ? loanRepayPlan.getApplyId() + "0" + i : loanRepayPlan.getApplyId() + i;
			RepayPlanItem repay = new RepayPlanItem();
			repay.setId(id);
			repay.setRepayDate(RepayPlanHelper.getRepayDate(loanApply, contract, i));
			repay.setStartDate(RepayPlanHelper.getStartDate(loanApply, contract, i));
			repayPlanItemManager.updateRepayTime(repay);
		}

		// 4,删除逾期记录
		overdueManager.deleteOverdueByApplyId(applyId);

	}
	
	/**
	 * 修改放款渠道
	 */
	@Transactional
	@Override
	public void changePaychannel(String id, String paychannel) {
		
		PayLogVO payLogVO =payLogService.get(id);
		
		LoanApply loanApply = loanApplyManager.getLoanApplyById(payLogVO.getApplyId());
		
		if (loanApply == null) {
			logger.error("修改放款渠道，贷款申请单不存在，applyId = {}", payLogVO.getApplyId());
			return;
		}

		String oldPayChannel = loanApply.getPayChannel();
		
		loanApply.setPayChannel(paychannel);
		loanApply.setStage(ApplyStatusLifeCycleEnum.WAITING_PUSH.getStage());
		loanApply.setStatus(ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue());
		
		loanApply.preUpdate();
		loanApplyManager.updateLoanApplyInfo(loanApply);
		
		BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(payLogVO.getApplyId());
		if(borrowInfo != null){
			borrowInfo.setPayChannel(Integer.parseInt(paychannel));
			borrowInfo.setPushStatus(loanApply.getStatus());
			
			borrowInfo.preUpdate();
			borrowInfoManager.update(borrowInfo);
		}
		

		payLogVO.setRemark(oldPayChannel+"_TO_"+paychannel);
		payLogService.update(payLogVO);
		
		
	}
}