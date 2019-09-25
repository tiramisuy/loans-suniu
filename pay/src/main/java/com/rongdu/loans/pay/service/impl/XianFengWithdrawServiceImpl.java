package com.rongdu.loans.pay.service.impl;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.XianFengServiceEnum;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.exception.XfNewInstanceException;
import com.rongdu.loans.pay.op.XfHandelWithdrawOP;
import com.rongdu.loans.pay.op.XfWithdrawOP;
import com.rongdu.loans.pay.service.PartnerApiService;
import com.rongdu.loans.pay.service.XianFengPayService;
import com.rongdu.loans.pay.service.XianFengWithdrawService;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.vo.XfWithdrawResultVO;
import com.rongdu.loans.pay.xianfeng.vo.XfWithdrawDataVO;

/**  
* @Title: XianFengWithdrawServiceImpl.java  
* @Package com.rongdu.loans.pay.service.impl  
* @Description: 先锋代付 
* @author: yuanxianchu  
* @date 2018年6月20日  
* @version V1.0  
*/
@Service(value="xianFengWithdrawService")
public class XianFengWithdrawServiceImpl extends PartnerApiService implements XianFengWithdrawService {
	
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private XianFengPayService xianFengPayService;
	@Autowired
	private PayLogService payLogService;

	@Override
	public XfWithdrawResultVO xfWithdraw(XfWithdrawOP op) {
		logger.debug("========先锋单笔代发类交易===========");
		XfWithdrawResultVO vo = null;
		Map<String, String> params = new HashMap<>();
		CustUserVO custUser = (CustUserVO) JedisUtils.getObject(Global.USER_CACHE_PREFIX + op.getUserId());
		if (null == custUser) {
			// 从数据库获取
			custUser = custUserService.getCustUserById(op.getUserId());
			if (null == custUser) {
				vo = new XfWithdrawResultVO();
				vo.setResMessage("用户不存在userId="+op.getUserId());
				return vo;
			}
		}
		//先锋单笔代发-业务数据
		XfWithdrawDataVO xfWithdrawDataVO = new XfWithdrawDataVO();
		xfWithdrawDataVO.setAmount(MoneyUtils.yuan2fen(op.getAmount()));
		xfWithdrawDataVO.setAccountName(custUser.getRealName());
		xfWithdrawDataVO.setBankNo(custUser.getBankCode());
		//先锋代发 部分银行代码转换
		if ("CITIC".equals(custUser.getBankCode())) {
			xfWithdrawDataVO.setBankNo("CNCB");//中信银行
		}
		if ("BCOM".equals(custUser.getBankCode())) {
			xfWithdrawDataVO.setBankNo("BOCOM");//中国交通银行
		}
		if ("SHB".equals(custUser.getBankCode())) {
			xfWithdrawDataVO.setBankNo("BOS");//上海银行
		}
		xfWithdrawDataVO.setAccountNo(custUser.getCardNo());
		xfWithdrawDataVO.setUserType("1");
//		xfWithdrawDataVO.setNoticeUrl("http://192.168.1.147:8080/admin/test/testNoticeUrl");
		params.put("data", JSONObject.toJSONString(xfWithdrawDataVO));
		try {
			vo = xianFengPayService.xfPayPostForObject(XianFengServiceEnum.REQ_WITHDRAW.getServiceName(), params,
					XfWithdrawResultVO.class);
		} catch (XfNewInstanceException e) {
			logger.error("返回结果类实例化异常：{}",e.getMsg(),e);
			vo = new XfWithdrawResultVO();
			vo.setResMessage("返回结果类实例化异常，请检查后重试");
		}
		vo.setSuccess(vo.isSuccess());
		vo.setAmountYuan(MoneyUtils.fen2yuan(vo.getAmount()));
		if (StringUtils.isBlank(vo.getTradeNo())) {
			vo.setMerchantNo(xfWithdrawDataVO.getMerchantNo());
			vo.setTradeTime(DateUtils.getDate("yyyyMMddHHmmss"));
		}
		if (StringUtils.isBlank(vo.getStatus())) {
			vo.setStatus("I");
		}
		/** 保存代扣记录 */
		saveXfWithdrawPayLog(vo, op, custUser);
		return vo;
	}

	
	
	
	/**
	 * 先锋手动代付
	 */
	@Override
	public XfWithdrawResultVO xfHandekWithDraw(XfHandelWithdrawOP op){
			logger.debug("========先锋单笔代发类交易===========");
			XfWithdrawResultVO vo = null;
			Map<String, String> params = new HashMap<>();
				//先锋单笔代发-业务数据
				XfWithdrawDataVO xfWithdrawDataVO = new XfWithdrawDataVO();
				xfWithdrawDataVO.setAmount(MoneyUtils.yuan2fen(op.getAmount()));
				xfWithdrawDataVO.setAccountName(op.getRealName());
				xfWithdrawDataVO.setBankNo(op.getBankCode());
				//先锋代发 部分银行代码转换
				if ("CITIC".equals(op.getBankCode())) {
					xfWithdrawDataVO.setBankNo("CNCB");//中信银行
				}
				if ("BCOM".equals(op.getBankCode())) {
					xfWithdrawDataVO.setBankNo("BOCOM");//中国交通银行
				}
				if ("SHB".equals(op.getBankCode())) {
					xfWithdrawDataVO.setBankNo("BOS");//上海银行
				}
				xfWithdrawDataVO.setAccountNo(op.getCardNo());
				xfWithdrawDataVO.setUserType("1");
//				xfWithdrawDataVO.setNoticeUrl("http://192.168.1.147:8080/admin/test/testNoticeUrl");
				params.put("data", JSONObject.toJSONString(xfWithdrawDataVO));
				try {
					vo = xianFengPayService.xfPayPostForObject(XianFengServiceEnum.REQ_WITHDRAW.getServiceName(), params,
							XfWithdrawResultVO.class);
				} catch (XfNewInstanceException e) {
					logger.error("返回结果类实例化异常：{}",e.getMsg(),e);
					vo = new XfWithdrawResultVO();
					vo.setResMessage("返回结果类实例化异常，请检查后重试");
				}
				vo.setSuccess(vo.isSuccess());
				vo.setAmountYuan(MoneyUtils.fen2yuan(vo.getAmount()));
				if (StringUtils.isBlank(vo.getTradeNo())) {
					vo.setMerchantNo(xfWithdrawDataVO.getMerchantNo());
					vo.setTradeTime(DateUtils.getDate("yyyyMMddHHmmss"));
				}
				if (StringUtils.isBlank(vo.getStatus())) {
					vo.setStatus("I");
				}
				
				/** 保存代扣记录 */
				saveXfPayLog(vo, op);
				return vo;
	}
	
	@Override
	public XfWithdrawResultVO xfWithdrawQuery(String merchantNo) {
		logger.debug("========先锋单笔订代发查询类交易===========");
		XfWithdrawResultVO vo = null;
		Map<String, String> params = new HashMap<>();
		params.put("merchantNo", merchantNo);
		try {
			vo = xianFengPayService.xfPayPostForObject(XianFengServiceEnum.REQ_WITHDRAW_QUERY_BY_ID.getServiceName(),
					params, XfWithdrawResultVO.class);
		} catch (XfNewInstanceException e) {
			logger.error("返回结果类实例化异常：{}",e.getMsg(),e);
			vo = new XfWithdrawResultVO();
			vo.setResMessage("返回结果类实例化异常，请检查后重试");
		}
		vo.setSuccess(vo.isSuccess());
		vo.setAmountYuan(MoneyUtils.fen2yuan(vo.getAmount()));
		if (StringUtils.isBlank(vo.getStatus())) {
			vo.setStatus("F");
		}
		return vo;
	}
	
	private int saveXfWithdrawPayLog(XfWithdrawResultVO result, XfWithdrawOP op, CustUserVO user) {
		
		String orderNo = result.getMerchantNo();
		Date now = new Date();
		PayLogVO payLog = new PayLogVO();
		payLog.setId(orderNo);
		payLog.setIsNewRecord(true);
		payLog.setApplyId(op.getApplyId());
		payLog.setContractNo(op.getContractId());
		payLog.setUserId(user.getId());
		payLog.setUserName(user.getRealName());
		
		payLog.setToAccName(user.getRealName());
		payLog.setToAccNo(user.getCardNo());
		payLog.setToIdno(user.getIdNo());
		payLog.setToMobile(user.getMobile());
		payLog.setToBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
		
		payLog.setIp(user.getLoginIp());
		payLog.setTerminal(user.getSource().toString());
		payLog.setChlCode(Global.XIANFENG_CHANNEL_CODE);
		payLog.setChlName(Global.XIANFENG_CHANNEL_NAME);
		payLog.setChlOrderNo(result.getTradeNo());
		payLog.setTxType("withdraw");//代付
		payLog.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
		payLog.setTxTime(now);
		payLog.setTxAmt(new BigDecimal(op.getAmount()));
		payLog.setTxFee(BigDecimal.ONE);
		
		payLog.setRemark(result.getResMessage());
		/*payLog.setReviewStatus(1);
		payLog.setReviewBy("");
		payLog.setReviewTime("");*/
		
		if (result.isSuccess()) {
			payLog.setSuccAmt(new BigDecimal(result.getAmountYuan()));
			payLog.setSuccTime(new Date());
			payLog.setStatus(WITHDRAWAL_SUCCESS.getValue().toString());
		}else if (result.isFail()) {
			payLog.setSuccAmt(BigDecimal.ZERO);
			payLog.setStatus(WITHDRAWAL_FAIL.getValue().toString());
		}else {
			payLog.setStatus(CASH_WITHDRAWAL.getValue().toString());
		}
		logger.debug("先锋代付-正在保存代付订单：{}，{}元，{}", user.getRealName(), result.getAmountYuan(), orderNo);
		return payLogService.save(payLog);
	}
	
	
	/**
	 * 保存先锋代付流水
	 * @param result
	 * @param op
	 * @param user
	 * @return
	 */
	private int saveXfPayLog(XfWithdrawResultVO result, XfHandelWithdrawOP op) {
		
		String orderNo = result.getMerchantNo();
		Date now = new Date();
		PayLogVO payLog = new PayLogVO();
		payLog.setId(orderNo);
		payLog.setIsNewRecord(true);
	//	payLog.setApplyId(op.getApplyId());
	//	payLog.setContractNo(op.getContractId());
	//	payLog.setUserId(user.getId());
		payLog.setUserName(op.getRealName());
		
		payLog.setToAccName(op.getRealName());
		payLog.setToAccNo(op.getCardNo());
		//payLog.setToIdno(user.getIdNo());
		//payLog.setToMobile(user.getMobile());
		payLog.setToBankName(BankLimitUtils.getNameByBankCode(op.getBankCode()));
		
		//payLog.setIp(user.getLoginIp());
		//payLog.setTerminal(user.getSource().toString());
		payLog.setChlCode(Global.XIANFENG_CHANNEL_CODE);
		payLog.setChlName(Global.XIANFENG_CHANNEL_NAME);
		payLog.setChlOrderNo(result.getTradeNo());
		payLog.setTxType("withdraw");//代付
		payLog.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
		payLog.setTxTime(now);
		payLog.setTxAmt(new BigDecimal(op.getAmount()));
		payLog.setTxFee(BigDecimal.ONE);
		
		payLog.setRemark(result.getResMessage());
		/*payLog.setReviewStatus(1);
		payLog.setReviewBy("");
		payLog.setReviewTime("");*/
		
		if (result.isSuccess()) {
			payLog.setSuccAmt(new BigDecimal(result.getAmountYuan()));
			payLog.setSuccTime(new Date());
			payLog.setStatus(WITHDRAWAL_SUCCESS.getValue().toString());
		}else if (result.isFail()) {
			payLog.setSuccAmt(BigDecimal.ZERO);
			payLog.setStatus(WITHDRAWAL_FAIL.getValue().toString());
		}else {
			payLog.setStatus(CASH_WITHDRAWAL.getValue().toString());
		}
		logger.debug("先锋代付-正在保存代付订单：{}，{}元，{}", op.getRealName(), result.getAmountYuan(), orderNo);
		return payLogService.save(payLog);
	}
	
}
