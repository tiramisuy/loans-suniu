package com.rongdu.loans.kjtpay.service.impl;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.XianJinCardUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.enums.HaiErBankCodeEnum;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.service.RongPointCutService;
import com.rongdu.loans.loan.service.SettlementService;
import com.rongdu.loans.loan.service.ShopWithholdService;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.op.WithholdQueryOP;
import com.rongdu.loans.pay.op.XfAgreementAdminPayOP;
import com.rongdu.loans.pay.service.BaofooWithholdService;
import com.rongdu.loans.pay.service.KjtpayApiService;
import com.rongdu.loans.pay.service.KjtpayService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

@Service("kjtpayService")
public class KjtpayServiceImpl extends BaseService implements KjtpayService {

	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private SettlementService settlementService;
	@Autowired
	private WithholdService withholdService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ShopWithholdService shopWithholdService;

	@Autowired
	private RongPointCutService rongPointCutService;

	@Autowired
	private RepayPlanItemService repayPlanItemService;

	@Autowired
	private CustUserService custUserService;

	@Autowired
	private KjtpayApiService kjtpayApiService;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	
	@Autowired
	private PayLogService payLogService;

	@Override
	public WithholdResultVO tradeBankWitholding(WithholdOP op) {
		// TODO Auto-generated method stub

		// String tradeNo,String amount,CustUserVO custUserVO
		WithholdResultVO vo = null;
		try {
			CustUserVO custUserVO = new CustUserVO();
			custUserVO.setRealName(op.getRealName());
			custUserVO.setMobile(op.getMobile());
			custUserVO.setId(op.getUserId());
			custUserVO.setIdNo(op.getIdNo());
			custUserVO.setCardNo(op.getCardNo());
			//将平台银行编码转成海尔银行编码
			custUserVO.setBankCode(HaiErBankCodeEnum.getCode(BankCodeEnum.getName(op.getBankCode())));

			vo = kjtpayApiService.tradeBankWitholding(op.getTransId(), MoneyUtils.fen2yuan(op.getTxnAmt()), custUserVO);
		} catch (Exception e) {
			vo = new WithholdResultVO();
			vo.setTransId(op.getTransId());
			vo.setCode("SYS_ERR");
			vo.setMsg(e.getMessage());

		}

		/** 保存代扣记录 */
		RepayLogVO repayLogVO = saveRepayLog(vo, op, PayTypeEnum.SETTLEMENT.getId());

		return vo;

	}

	/**
	 * 保存还款记录
	 * 
	 * @param vo
	 * @param param
	 * @param payType
	 *            code y0524
	 * @return
	 */
	private RepayLogVO saveRepayLog(WithholdResultVO vo, WithholdOP param, Integer payType) {

		CustUserVO user = custUserService.getCustUserById(param.getUserId());

		Date now = new Date();
		RepayLogVO repayLog = new RepayLogVO();
		repayLog.setId(vo.getTransId());
		repayLog.setNewRecord(true);
		repayLog.setApplyId(param.getApplyId());
		repayLog.setContractId(param.getContNo());
		repayLog.setRepayPlanItemId(param.getRepayPlanItemId());
		repayLog.setUserId(user.getId());
		repayLog.setUserName(user.getRealName());
		repayLog.setIdNo(user.getIdNo());
		repayLog.setMobile(user.getMobile());
		repayLog.setTxType("WITHHOLD");
		repayLog.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
		if (StringUtils.isNotBlank(vo.getTradeDate())) {
			repayLog.setTxTime(DateUtils.parse(vo.getTradeDate(), DateUtils.FORMAT_INT_MINITE));
		}
		BigDecimal txAmt = new BigDecimal(param.getTxnAmt()).divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE,
				BigDecimal.ROUND_HALF_UP);
		repayLog.setTxAmt(txAmt);
		repayLog.setTxFee(calWithholdFee(txAmt));
		repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
		repayLog.setChlOrderNo(vo.getTransNo());
		repayLog.setChlName("海尔支付");
		repayLog.setChlCode("KJTPAY");
		repayLog.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());
		repayLog.setBankCode(user.getBankCode());
		repayLog.setCardNo(user.getCardNo());
		repayLog.setBankName(BankCodeEnum.getName(user.getBankCode()));
		// repayLog.setGoodsName("聚宝钱包还款");
		repayLog.setGoodsName("聚宝贷还款");
		repayLog.setGoodsNum(1);
		repayLog.setStatus(vo.getCode());
		repayLog.setRemark(vo.getMsg());
		repayLog.setPayType(payType);
		// if (vo.getSuccess()) {
		// BigDecimal succAmt = new BigDecimal(vo.getSuccAmt())
		// .divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE,
		// BigDecimal.ROUND_HALF_UP);
		// repayLog.setSuccAmt(succAmt);
		// repayLog.setSuccTime(now);
		// repayLog.setStatus(ErrInfo.SUCCESS.getCode());
		// }
		repayLogService.save(repayLog);
		return repayLog;
	}

	/**
	 * 计算每笔代扣交易费用 10万及以下，2.00元/笔 10万以上，5.00/笔
	 * 
	 * @param fee
	 *            单位元
	 * @return
	 */
	private BigDecimal calWithholdFee(BigDecimal fee) {
		if (fee.compareTo(new BigDecimal(100000)) > 0) {
			return BigDecimal.valueOf(5);
		}
		return BigDecimal.valueOf(2);
	}

	@Override
	public void updateProcessOrder(RepayLogVO repayLogVO) throws Exception {

		// 认证支付-主动还款
		if ("AUTH_PAY".equals(repayLogVO.getTxType())) {

		}
		// 系统代扣
		else if ("WITHHOLD".equals(repayLogVO.getTxType())) {
			/***** BAOFOO *****/
			WithholdQueryOP op = new WithholdQueryOP();
			op.setOrigTransId(repayLogVO.getId());
			op.setOrigTradeDate(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyyMMddHHmmss"));
			WithholdQueryResultVO retVo = kjtpayApiService.queryTradeBankWitholding(op.getOrigTransId());
			logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(retVo));
			if (retVo == null)
				throw new RuntimeException("系统代扣查询结果为空");
			if ("SYS_ERR".equals(retVo.getCode()))
				throw new RuntimeException("系统代扣查询异常，结果未知");
			// 查询结果为交易成功，更新还款记录
			if ("TRADE_SUCCESS".equals(retVo.getCode()) || "TRADE_FINISHED".equals(retVo.getCode())) {
				String succAmtYuan = MoneyUtils.fen2yuan(retVo.getSuccAmt());
				if (StringUtils.isBlank(retVo.getSuccAmt()))
					throw new RuntimeException("系统代扣查询成功金额为空");
				if (StringUtils.isBlank(retVo.getOrigTradeDate()))
					throw new RuntimeException("系统代扣查询原订单时间为空");

				// 还款
				if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
					withholdService.updateOrderInfo(repayLogVO.getRepayPlanItemId(), retVo);
					rongPointCutService.settlementPoint(repayLogVO.getRepayPlanItemId(), true);// 用作Rong360订单逾期，切面通知的切入点标记
					XianJinCardUtils.setRepayStatusFeedbackToRedis(repayLogVO.getApplyId(), Global.XJBK_SUCCESS);
				}
				// 延期费
				else if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
					contractService.delayDeal(repayLogVO.getRepayPlanItemId(), 2,
							DateUtils.formatDateTime(retVo.getOrigTradeDate()), null, null);
				}
				// 购物金
				else if (PayTypeEnum.SHOPPING.getId().equals(repayLogVO.getPayType())) {
					shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(), retVo.getMsg(), 0);
				}
				// 部分代扣
				else if (PayTypeEnum.PARTWITHHOLD.getId().equals(repayLogVO.getPayType())) {
					repayPlanItemService.updateForPartWithhold(repayLogVO.getRepayPlanItemId(), succAmtYuan,
							retVo.getOrigTradeDate());
				} else {
					logger.error("查询支付订单状态，错误的支付类型： payType=" + repayLogVO.getPayType());
				}

				repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
				repayLogVO.setRemark(retVo.getMsg());
				repayLogVO.setSuccTime(repayLogVO.getTxTime());
				repayLogVO.setSuccAmt(MoneyUtils.toDecimal(succAmtYuan));
				int updateRows = repayLogService.updateRepayResult(repayLogVO);
			} else if ("TRADE_CLOSED".equals(retVo.getCode())) {// 未付款交易超时关闭，或支付完成后全额退款
				if (PayTypeEnum.SHOPPING.getId().equals(repayLogVO.getPayType())) {
					shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(), retVo.getMsg(), 1);
				}
				repayLogVO.setStatus(retVo.getCode());
				repayLogVO.setRemark(retVo.getMsg());
				repayLogService.update(repayLogVO);

				// 还款失败
				if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
					repayPlanItemManager.unfinish(repayLogVO.getRepayPlanItemId());
					// 用作Rong360订单代扣失败，切面通知的切入点标记
					rongPointCutService.settlementPoint(repayLogVO.getRepayPlanItemId(), false);
				}
				// 延期失败
				if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
					rongPointCutService.repayProcessFailPoint(repayLogVO.getRepayPlanItemId());
				}
				XianJinCardUtils.setRepayStatusFeedbackToRedis(repayLogVO.getApplyId(), Global.XJBK_FAIL);
			}
		}
		// 协议支付-直接还款
		else if ("AM_PAY".equals(repayLogVO.getTxType())) {

		}

	}
	
	@Override
	public ApiResultVO transferToCard(String tradeNo, String cardNo, String realName, String bankCode, String amount) {
		// TODO Auto-generated method stub
		ApiResultVO resultVO = kjtpayApiService.transferToCard(tradeNo,cardNo,realName,bankCode,amount);
		saveTransferToCardPayLog(tradeNo,cardNo,realName,bankCode,amount,resultVO);
		return resultVO;
	}
	
	/**
	 * 保存后台手动代付流水
	 * 
	 * @param result
	 * @param op
	 * @return
	 */
	private int saveTransferToCardPayLog(String tradeNo, String cardNo, String realName, String bankCode, String amount,ApiResultVO resultVO) {
		
		Date now = new Date();
		PayLogVO payLog = new PayLogVO();
		payLog.setApplyId(tradeNo);
		payLog.setContractNo(tradeNo);
		payLog.setUserId("");
		payLog.setUserName(realName);
		payLog.setTxType("auto");
		payLog.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
		payLog.setTxTime(now);
		payLog.setTxAmt(new BigDecimal(amount));
		payLog.setTxFee(BigDecimal.ONE);
		payLog.setToAccName(realName);
		payLog.setToAccNo(cardNo);
		payLog.setToIdno("");
		payLog.setToMobile("");
		payLog.setToBankName(HaiErBankCodeEnum.getName(bankCode));
		if (StringUtils.isNotBlank(amount)) {
			payLog.setSuccAmt(BigDecimal.valueOf(Double.valueOf(amount)));
			payLog.setSuccTime(now);
			payLog.setStatus(WITHDRAWAL_SUCCESS.getValue().toString());
		} else {
			payLog.setSuccAmt(BigDecimal.ZERO);
			payLog.setStatus(WITHDRAWAL_FAIL.getValue().toString());
		}
		
		payLog.setRemark(resultVO.getMsg());
		
		payLog.setChlCode(Global.HAIER_CHANNEL_CODE);
		payLog.setChlName(Global.HAIER_CHANNEL_NAME);
		logger.debug("海尔支付-正在保存支付订单：{}，{}元，{}", realName, amount, tradeNo);
		
		return payLogService.save(payLog);
	}

	

}
