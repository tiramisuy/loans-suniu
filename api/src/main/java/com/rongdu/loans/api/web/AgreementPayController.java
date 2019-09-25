package com.rongdu.loans.api.web;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.enums.RepayDeductionTypeEnum;
import com.rongdu.loans.loan.option.GoodsAddressOP;
import com.rongdu.loans.loan.option.GoodsOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.LoanGoodsResultVO;
import com.rongdu.loans.loan.vo.StatementVO;
import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.op.XfAgreementPayOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.BaofooWithholdService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.service.XianFengAgreementPayService;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;
import com.rongdu.loans.sys.annotation.LockAndSyncRequest;
import com.rongdu.loans.sys.web.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lee on 2018/4/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/agreement_pay")
public class AgreementPayController extends BaseController {
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private SettlementService settlementService;
	@Autowired
	private BaofooAgreementPayService baofooAgreementPayService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private LoanGoodsService loanGoodsService;
	@Autowired
	private GoodsAddressService goodsAddressService;
	@Autowired
	private XianFengAgreementPayService xianFengAgreementPayService;
	@Autowired
	private RongPointCutService rongPointCutService;
	@Autowired
	private WithholdService withholdService;
	@Autowired
	private BaofooWithholdService baofooWithholdService;
	@Autowired
	private ConfigService configService;

	@RequestMapping(value = "/confirmPay", method = RequestMethod.POST, name = "协议支付-直接支付")
	@ResponseBody
	@LockAndSyncRequest
	public ApiResult agreementPay(@Valid RePayOP param, Errors errors, HttpServletRequest request) throws Exception {

		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		String userId = request.getHeader("userId");
		String ip = Servlets.getIpAddress(request);
		String applyId = null;
		String contractId = null;

		param.setUserId(userId);

		String lockId = (PayTypeEnum.URGENT.getId().equals(param.getPayType())
				|| PayTypeEnum.TRIP.getId().equals(param.getPayType())) ? param.getApplyId()
						: param.getRepayPlanItemId();
		String userAgreementPayLockCacheKey = "user_agreementPay_lock_" + lockId;
		synchronized (AgreementPayController.class) {
			String userAgreementPayLock = JedisUtils.get(userAgreementPayLockCacheKey);
			if (userAgreementPayLock == null) {
				// 加锁，防止并发
				JedisUtils.set(userAgreementPayLockCacheKey, "locked", 60);
			} else {
				logger.warn("协议直接支付接口调用中，lockId= {}", lockId);
				// 处理中
				result.setCode("FAIL");
				result.setMsg("交易处理中,请稍后查询");
				return result;
			}
		}
		try {
			// 支付类型，1=还款，2=延期，3=加急,5=旅游券，兼容之前APP版本默认为1
			if (param.getPayType() == null) {
				param.setPayType(1);
			}
			// ytodo 0303 加急券 version
			if (PayTypeEnum.URGENT.getId().equals(param.getPayType())) {
				logger.error("协议支付，暂不支持购买加急大礼包，userId= {},payType={}", userId, param.getPayType());
				result.setCode("FAIL");
				result.setMsg("暂不支持购买加急大礼包，请更新APP");
				return result;
			}
			if (PayTypeEnum.DELAY.getId().equals(param.getPayType())) {
				logger.error("协议支付，暂不支持延期，userId= {},payType={}", userId, param.getPayType());
				result.setCode("FAIL");
				result.setMsg("暂不支持延期");
				return result;
			}
			if (!PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())
					&& !PayTypeEnum.DELAY.getId().equals(param.getPayType())
					&& !PayTypeEnum.URGENT.getId().equals(param.getPayType())
					&& !PayTypeEnum.TRIP.getId().equals(param.getPayType())) {
				logger.error("协议支付，支付类型错误，userId= {},payType={}", userId, param.getPayType());
				result.setCode("FAIL");
				result.setMsg("支付失败");
				return result;
			}
			if (!PayTypeEnum.URGENT.getId().equals(param.getPayType())
					&& !PayTypeEnum.TRIP.getId().equals(param.getPayType())) {
				// 验证还款计划明细id是否为空
				if (StringUtils.isBlank(param.getRepayPlanItemId())) {
					logger.error("协议支付，还款计划编号为空，userId= {}", userId);
					result.setCode("FAIL");
					result.setMsg("还款计划编号为空");
					return result;
				}
				// 口袋放款只能延期一次
				if (PayTypeEnum.DELAY.getId().equals(param.getPayType())) {
					String itemId = param.getRepayPlanItemId();
					Date applyDate = DateUtils.parse(itemId.substring(2, 10), "yyyyMMdd");
					Date startDate = DateUtils.parse("20180926", "yyyyMMdd");
					int term = Integer.parseInt(itemId.substring(itemId.length() - 2, itemId.length()));
					if (DateUtils.daysBetween(startDate, applyDate) >= 0 && term > 1) {
						logger.error("协议支付，延期次数超限，userId= {}，repayPlanItemId= {}", userId, itemId);
						result.setCode("FAIL");
						result.setMsg("延期次数超限");
						return result;
					}
				}
				// 一天内只能成功延期一次
				if (PayTypeEnum.DELAY.getId().equals(param.getPayType())
						&& JedisUtils.get(Global.DELAY_SUCCESS_FLAG_PREFIX + param.getUserId()) != null) {
					logger.error("协议支付，已延期成功，请勿重复提交，userId= {}", userId);
					result.setCode("FAIL");
					result.setMsg("已延期成功,请勿重复提交");
					return result;
				}
				// 验证还款计划是否结清
				StatementVO statementVO = settlementService.getStatementByItemId(param.getRepayPlanItemId());
				if (null == statementVO || statementVO.getCurRepayStatus() == 1) {
					logger.error("协议支付，还款计划为空或已结清，userId= {}", userId);
					result.setCode("FAIL");
					result.setMsg("支付成功,请刷新页面");
					return result;
				}
				// 还款期数错误
				if (statementVO.getCurTerm().intValue() != (statementVO.getLoanPayedTerm().intValue() + 1)) {
					logger.error("协议支付，还款期数错误，userId= {},itemId={}", userId, param.getRepayPlanItemId());
					result.setCode("FAIL");
					result.setMsg("还款期数错误,请刷新页面");
					return result;
				}
				// 分期产品不能延期
				if (PayTypeEnum.DELAY.getId().equals(param.getPayType())
						&& statementVO.getLoanTotalTerm().intValue() > 1) {
					logger.error("协议支付，分期产品不能延期，userId= {}", userId);
					result.setCode("FAIL");
					result.setMsg("暂不支持展期");
					return result;
				}
				// 是否允许提前还款/延期
				Integer pastDays = DateUtils.daysBetween(statementVO.getLoanStartDate(), new Date()) + 1;
				int minLoanDay = 1;
				if (pastDays <= minLoanDay) {
					logger.error("协议支付，最少持有一天,不可提前还款，userId= {},repayPlanItemId={}", userId,
							statementVO.getCurRepayItemId());
					result.setCode("FAIL");
					result.setMsg("最少持有一天,不可提前还款");
					return result;
				}
				// 验证是否有处理中交易
				Long payCount = repayLogService.countPayingByRepayPlanItemId(statementVO.getCurRepayItemId());
				if (payCount != 0) {
					logger.error("协议支付，重复支付，userId= {},repayPlanItemId={}", userId, statementVO.getCurRepayItemId());
					result.setCode("FAIL");
					result.setMsg("交易处理中,请稍后查询");
					return result;
				}
				applyId = statementVO.getApplyId();
				contractId = statementVO.getContNo();
			} else {
				// 验证申请单号是否为空
				if (StringUtils.isBlank(param.getApplyId())) {
					logger.error("协议支付，申请订单号为空，userId= {}", userId);
					result.setCode("FAIL");
					result.setMsg("申请订单号为空");
					return result;
				}
				// 验证申请单状态
				LoanApplySimpleVO applyVO = loanApplyService.getLoanApplyById(param.getApplyId());
				if (applyVO == null || XjdLifeCycle.LC_RAISE_0 != applyVO.getProcessStatus().intValue()) {
					logger.error("协议支付，申请订单号为空或状态错误,status={}", applyVO.getProcessStatus());
					result.setCode("FAIL");
					result.setMsg("支付成功,请刷新页面");
					return result;
				}
				// 重复购买加急券
				if (PayTypeEnum.URGENT.getId().equals(param.getPayType()) && LoanApplySimpleVO.APPLY_STATUS_URGENT
						.intValue() != applyVO.getLoanApplyStatus().intValue()) {
					logger.error("协议支付，已购买加急券，请刷新页面,status={}", applyVO.getProcessStatus());
					result.setCode("FAIL");
					result.setMsg("已购买加急券,请刷新页面");
					return result;
				}
				// 重复购买旅游券
				if (PayTypeEnum.TRIP.getId().equals(param.getPayType()) && LoanApplySimpleVO.APPLY_STATUS_SHOPPING
						.intValue() != applyVO.getLoanApplyStatus().intValue()) {
					logger.error("协议支付，已购买旅游券，请刷新页面,status={}", applyVO.getProcessStatus());
					result.setCode("FAIL");
					result.setMsg("已购买旅游券,请刷新页面");
					return result;
				}
				// 验证是否有处理中交易
				Long payCount = repayLogService.countPayingByApplyId(param.getApplyId());
				// ytodo 0303 加急券
				if (payCount != 0 || "I".equals(applyVO.getUrgentFee())) {
					logger.error("协议支付，重复支付，userId= {},applyId={}", userId, param.getApplyId());
					result.setCode("FAIL");
					result.setMsg("交易处理中,请稍后查询");
					return result;
				}
				applyId = param.getApplyId();
				contractId = "";
				param.setRepayPlanItemId("");
			}

			// 验证金额
			boolean txAmtError = false;
			LoanApplySimpleVO currApplyVO = loanApplyService.getUnFinishLoanApplyInfo(userId);
			if (PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())) {
				if (StringUtils.isBlank(param.getIsDeduction()) || "0".equals(param.getIsDeduction())) {
					if (currApplyVO.getCurToltalRepayAmt().compareTo(new BigDecimal(param.getTxAmt())) != 0) {
						txAmtError = true;
						logger.error("协议支付，还款金额错误,userId={},applyId={},currAmt={},txAmt={}", userId, applyId,
								currApplyVO.getCurToltalRepayAmt(), param.getTxAmt());
					}
				}
				if (StringUtils.isNotBlank(param.getIsDeduction()) && "1".equals(param.getIsDeduction())) {
					if (currApplyVO.getCurToltalRepayAmt().subtract(new BigDecimal(param.getDeductionAmt()))
							.compareTo(new BigDecimal(param.getTxAmt())) != 0) {
						txAmtError = true;
						logger.error("协议支付，还款金额错误,userId={},applyId={},currAmt={},txAmt={},deductionAmt={}", userId,
								applyId, currApplyVO.getCurToltalRepayAmt(), param.getTxAmt(), param.getDeductionAmt());
					}
				}
			} else if (PayTypeEnum.DELAY.getId().equals(param.getPayType())) {
				if (currApplyVO.getCurDelayRepayAmt().compareTo(new BigDecimal(param.getTxAmt())) != 0) {
					txAmtError = true;
					logger.error("协议支付，延期金额错误,userId={},applyId={},currAmt={},txAmt={}", userId, applyId,
							currApplyVO.getCurDelayRepayAmt(), param.getTxAmt());
				}
			} else if (PayTypeEnum.URGENT.getId().equals(param.getPayType())) {
				if (currApplyVO.getUrgentFee().compareTo(new BigDecimal(param.getTxAmt())) != 0) {
					txAmtError = true;
					logger.error("协议支付，加急金额错误,userId={},applyId={},currAmt={},txAmt={}", userId, applyId,
							currApplyVO.getUrgentFee(), param.getTxAmt());
				}
			} else if (PayTypeEnum.TRIP.getId().equals(param.getPayType())) {
				if (currApplyVO.getUrgentFee().compareTo(new BigDecimal(param.getTxAmt())) != 0) {
					txAmtError = true;
					logger.error("协议支付，旅游券金额错误,userId={},applyId={},currAmt={},txAmt={}", userId, applyId,
							currApplyVO.getUrgentFee(), param.getTxAmt());
				}
			}
			if (txAmtError || new BigDecimal(param.getTxAmt()).compareTo(BigDecimal.ZERO) <= 0) {
				Map<String, Object> data = new LinkedHashMap<>();
				result.setCode("FAIL");
				result.setMsg("支付金额错误");
				data.put("status", 1);
				result.setData(data);
				return result;
			}
			// 还款或延期时，验证repayPlanItemId和当前登录用户是否匹配
			if (PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())
					|| PayTypeEnum.DELAY.getId().equals(param.getPayType())) {
				if (!param.getRepayPlanItemId().contains(currApplyVO.getApplyId())) {
					logger.error("协议支付，还款计划明细ID错误，userId= {}，repayPlanItemId= {}", userId, param.getRepayPlanItemId());
					result.setCode("FAIL");
					result.setMsg("还款计划明细ID错误，请刷新页面");
					return result;
				}
			}
			// 加急或旅游券时,验证applyId和当前登录用户是否匹配
			if (PayTypeEnum.URGENT.getId().equals(param.getPayType())
					|| PayTypeEnum.TRIP.getId().equals(param.getPayType())) {
				if (!param.getApplyId().equals(currApplyVO.getApplyId())) {
					logger.error("协议支付，申请单号错误，userId= {}，applyId= {}", userId, param.getApplyId());
					result.setCode("FAIL");
					result.setMsg("申请单号错误，请刷新页面");
					return result;
				}
			}
			// 银行卡绑定验证
			CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
			if (null == custUserVO) {
				// 从数据库获取
				custUserVO = custUserService.getCustUserById(userId);
				if (null == custUserVO) {
					logger.error("协议支付，未查询到用户信息，userId= {}", userId);
					result.set(ErrInfo.UNFIND_CUST);
					return result;
				}
				// 更新缓存中的用户信息
				LoginUtils.cacheCustUserInfo(custUserVO);
			}
			// 绑卡验证
			if (StringUtils.isBlank(custUserVO.getCardNo())) {
				logger.error("协议支付，未绑定银行卡，请前往个人中心绑卡，userId= {}", userId);
				result.setCode("FAIL");
				result.setMsg("未绑定银行卡，请前往个人中心绑卡");
				return result;
			}
			// ytodo 0303 加急券
			/*if (PayTypeEnum.URGENT.getId().equals(param.getPayType()) && true){
				// 未选择购买加急券
				long count = riskWhitelistService.countByUserId(userId);
				if (count > 0 && DerateCounter.get().addByDay() <= DerateCounter.LIMIT){
					// 白名单客户每天20名放款500
				}
			}*/

			/**
			 * 宝付支付
			 */
			// PreAuthPayOP preAuthPayOP = new PreAuthPayOP();
			// preAuthPayOP.setContractId(contractId);
			// preAuthPayOP.setApplyId(applyId);
			// preAuthPayOP.setUserId(userId);
			// preAuthPayOP.setTxAmt(param.getTxAmt());
			// preAuthPayOP.setRepayPlanItemId(param.getRepayPlanItemId());
			// preAuthPayOP.setIpAddr(ip);
			// preAuthPayOP.setSource(param.getSource());
			// preAuthPayOP.setPayType(param.getPayType());
			// ConfirmAuthPayVO confirmAuthPayVO =
			// baofooAgreementPayService.agreementPay(preAuthPayOP);
			// // 保存支付结果
			// param.setPayStatus(confirmAuthPayVO.getCode());
			// param.setOrderInfo(confirmAuthPayVO.getMsg());
			// param.setPaySuccAmt(confirmAuthPayVO.getSuccAmt());
			// param.setPaySuccTime(confirmAuthPayVO.getSuccTime());
			// if (!confirmAuthPayVO.isSuccess()) {
			// logger.error("协议支付，{}，applyId= {}", confirmAuthPayVO.getMsg(),
			// param.getApplyId());
			// result.setCode("FAIL");
			// result.setMsg(confirmAuthPayVO.getMsg());
			// if ("BF00127".equals(confirmAuthPayVO.getCode()) ||
			// "BF00342".equals(confirmAuthPayVO.getCode())) {
			// result.setMsg(confirmAuthPayVO.getMsg() + ", 请更换绑卡");
			// } else {
			// result.setMsg(confirmAuthPayVO.getMsg() + ", 可在个人中心里更换绑卡");
			// }
			// return result;
			// }

			/**
			 * 先锋支付
			 */
			XfAgreementPayOP op = new XfAgreementPayOP();
			op.setContractId(contractId);
			op.setApplyId(applyId);
			op.setUserId(userId);
			op.setAmount(param.getTxAmt());
			op.setRepayPlanItemId(param.getRepayPlanItemId());
			op.setIp(ip);
			op.setSource(param.getSource());
			op.setPayType(param.getPayType());
			op.setCouponId(param.getCouponId());// 优惠劵ID
			if (PayTypeEnum.TRIP.getId().equals(param.getPayType())
					&& StringUtils.isNotBlank(param.getTripProductId())) {
				op.setGoodsId(param.getTripProductId());
			}

			String repay_method = configService.getValue("repay_method");// 1=宝付代扣,2=先锋支付
			XfAgreementPayResultVO xfAgreementPayResultVO = null;
			if ("1".equals(repay_method)) {
				xfAgreementPayResultVO = new XfAgreementPayResultVO();
				xfAgreementPayResultVO.setSuccess(false);
				xfAgreementPayResultVO.setResCode("00041");
				xfAgreementPayResultVO.setResMessage("支付失败");
				xfAgreementPayResultVO.setMerchantNo("AP" + System.nanoTime());
				xfAgreementPayResultVO.setAmountYuan(param.getTxAmt());
				xfAgreementPayResultVO.setTradeTime(DateUtils.getDate("yyyyMMddHHmmss"));
			} else {
				xfAgreementPayResultVO = xianFengAgreementPayService.agreementPay(op);
			}
			// 保存支付结果
			ConfirmAuthPayVO confirmAuthPayVO = new ConfirmAuthPayVO();
			confirmAuthPayVO.setSuccess(xfAgreementPayResultVO.isSuccess());
			confirmAuthPayVO.setCode(xfAgreementPayResultVO.getResCode());
			confirmAuthPayVO.setMsg(xfAgreementPayResultVO.getResMessage());
			confirmAuthPayVO.setOrderNo(xfAgreementPayResultVO.getMerchantNo());
			confirmAuthPayVO.setSuccAmt(xfAgreementPayResultVO.getAmountYuan());
			confirmAuthPayVO.setSuccTime(xfAgreementPayResultVO.getTradeTime());
			// 先锋支付失败，接着宝付代扣
			// 00041=暂不支持该银行
			boolean isWithholdSuccess = false;
			if (PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())
					&& "00041".equals(xfAgreementPayResultVO.getResCode())) {
				WithholdOP withholdOP = getParam(custUserVO, op);
				WithholdResultVO withholdResultVO = baofooWithholdService.withhold(withholdOP, param.getPayType());
				if (withholdResultVO.getSuccess()) {
					isWithholdSuccess = true;
					confirmAuthPayVO.setSuccess(true);
					confirmAuthPayVO.setCode("0000");
					confirmAuthPayVO.setMsg("支付成功");
					confirmAuthPayVO.setSuccTime(withholdResultVO.getTradeDate());
				} else {
					confirmAuthPayVO.setSuccess(false);
					confirmAuthPayVO.setCode("FAIL");
					confirmAuthPayVO.setMsg(withholdResultVO.getMsg());
					confirmAuthPayVO.setSuccTime(withholdResultVO.getTradeDate());
				}
			}
			param.setPayStatus(confirmAuthPayVO.getCode());
			param.setOrderInfo(confirmAuthPayVO.getMsg());
			param.setPaySuccAmt(confirmAuthPayVO.getSuccAmt());
			param.setPaySuccTime(confirmAuthPayVO.getSuccTime());
			param.setChlCode(Global.XIANFENG_CHANNEL_CODE);
			if (!confirmAuthPayVO.isSuccess()) {
				rongPointCutService.settlementPoint(param.getRepayPlanItemId(), false);// 用作Rong360订单还款时，切面通知的切入点标记
				logger.error("协议支付，{}，applyId= {}", confirmAuthPayVO.getMsg(), param.getApplyId());
				result.setCode("FAIL");
				result.setMsg(confirmAuthPayVO.getMsg());
				return result;
			}
			logger.info("协议支付，成功，applyId={}", param.getApplyId());

			ConfirmPayResultVO rz = null;
			if (PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())) {
				if (isWithholdSuccess) {
					param.setTxType(Global.REPAY_TYPE_AUTO);
				} else {
					param.setTxType(Global.REPAY_TYPE_MANUAL);
				}
				rz = settlementService.settlement(param);
			} else if (PayTypeEnum.DELAY.getId().equals(param.getPayType())) {
				rz = settlementService.delay(param);
			} else if (PayTypeEnum.URGENT.getId().equals(param.getPayType())) {
				// ytodo 0303 加急券 version
				/*if ("2".equals(param.getSource()) || "3".equals(param.getSource())){
					// Android用户
					rz = settlementService.urgentPushingLoan(param);
				}else {*/
					rz = settlementService.urgent(param);
				//}
			} else {
				rz = settlementService.trip(param);
			}
			if (null == rz || rz.getResult() == 0) {
				result.set(ErrInfo.PAY_SETTLEMENT_FAIL);
				logger.error("[{}], 还款结算结果:[{}]", param.getApplyId(), rz != null ? rz.getMessage() : "");
				return result;
			}
			result.setData(confirmAuthPayVO);
		} finally {
			// 移除锁
			// JedisUtils.del(userAgreementPayLockCacheKey);
		}
		return result;
	}

	@RequestMapping(value = "/getGoods", method = RequestMethod.POST, name = "加急券-礼包")
	@ResponseBody
	public ApiResult getGoods(@Valid GoodsOP goodsOP, Errors errors, HttpServletRequest request) {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		String userId = request.getHeader("userId");
		goodsOP.setUserId(userId);
		LoanGoodsResultVO resultData = loanGoodsService.getGoods(goodsOP);
		result.setData(resultData);
		return result;
	}

	@RequestMapping(value = "/saveAddress", method = RequestMethod.POST, name = "礼包地址-保存")
	@ResponseBody
	public ApiResult saveAddress(@Valid GoodsAddressOP goodsAddressOP, Errors errors, HttpServletRequest request) {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		ApiResult result = new ApiResult();
		String userId = request.getHeader("userId");

		String userSaveAddressLockCacheKey = "user_saveAddress_lock_" + userId;
		synchronized (AgreementPayController.class) {
			String userSaveAddressLock = JedisUtils.get(userSaveAddressLockCacheKey);
			if (userSaveAddressLock == null) {
				JedisUtils.set(userSaveAddressLockCacheKey, "locked", 120);
			} else {
				logger.warn("保存地址接口调用中，lockId= {}", userId);
				result.set(ErrInfo.WAITING);
				return result;
			}
		}
		try {
			goodsAddressOP.setUserId(userId);
			int saveBack = goodsAddressService.save(goodsAddressOP);
			if (saveBack == 0) {
				result.set(ErrInfo.ERROR);
				return result;
			}
		} finally {
			JedisUtils.del(userSaveAddressLockCacheKey);
		}
		return result;
	}

	@RequestMapping(value = "/getIsDeduction", method = RequestMethod.POST, name = "判断是可以否抵扣购物券（1-是，0-否）")
	@ResponseBody
	public ApiResult getIsDeduction(HttpServletRequest request) {
		ApiResult result = null;
		String repayPlanItemId = request.getParameter("repayPlanItemId");
		if (StringUtils.isBlank(repayPlanItemId)) {
			result = new ApiResult();
			result.setCode("FAIL");
			result.setMsg("还款详情id为空");
			return result;
		}
		String cacheKey = "getIsDeduction_" + repayPlanItemId;
		result = (ApiResult) JedisUtils.getObject(cacheKey);
		if (result == null) {
			result = new ApiResult();
			result.setData(
					settlementService.isEnableCouponDeduction(repayPlanItemId) ? RepayDeductionTypeEnum.YES.getValue()
							: RepayDeductionTypeEnum.NO.getValue());
			JedisUtils.setObject(cacheKey, result, Global.ONE_DAY_CACHESECONDS);
		}
		return result;
	}

	private WithholdOP getParam(CustUserVO custUserVO, XfAgreementPayOP xfAgreementPayOP) {
		WithholdOP op = new WithholdOP();
		op.setUserId(custUserVO.getId());
		op.setRealName(custUserVO.getRealName());
		op.setIdNo(custUserVO.getIdNo());
		if (StringUtils.isNotBlank(custUserVO.getBankMobile())) {
			op.setMobile(custUserVO.getBankMobile());
		} else {
			op.setMobile(custUserVO.getMobile());
		}
		op.setBankCode(custUserVO.getBankCode());
		op.setCardNo(custUserVO.getCardNo());
		op.setTxnAmt(new BigDecimal(xfAgreementPayOP.getAmount()).multiply(BigDecimal.valueOf(100)).toString());
		op.setApplyId(xfAgreementPayOP.getApplyId());
		op.setContNo(xfAgreementPayOP.getContractId());
		op.setRepayPlanItemId(xfAgreementPayOP.getRepayPlanItemId());
		op.setCouponId(xfAgreementPayOP.getCouponId());
		return op;
	}

}