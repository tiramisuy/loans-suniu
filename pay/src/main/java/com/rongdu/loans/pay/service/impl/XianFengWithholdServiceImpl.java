package com.rongdu.loans.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.XianFengServiceEnum;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.exception.XfNewInstanceException;
import com.rongdu.loans.pay.message.XianFengWithholdDataContet;
import com.rongdu.loans.pay.op.XfWithholdOP;
import com.rongdu.loans.pay.service.PartnerApiService;
import com.rongdu.loans.pay.service.XianFengPayService;
import com.rongdu.loans.pay.service.XianFengWithholdService;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.vo.XfWithholdResultVO;

/**  
 * code y0602
* @Title: XianFengWithholdServiceImpl.java  
* @Description: 先锋代扣类 
* @author: yuanxianchu  
* @date 2018年6月2日  
* @version V1.0  
*/
@Service(value="xianFengWithholdService")
public class XianFengWithholdServiceImpl extends PartnerApiService implements XianFengWithholdService {
	
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private XianFengPayService xianFengPayService;
	
	@Override
	public XfWithholdResultVO xfWithhold(XfWithholdOP op, Integer payType) {
		logger.debug("========先锋单笔代扣类交易===========");
		XfWithholdResultVO vo = null;
		Map<String, String> params = new HashMap<>();
		
		// 先锋代扣-业务数据
		XianFengWithholdDataContet dataContet = BeanMapper.map(op, XianFengWithholdDataContet.class);
		dataContet.setTransCur("156");// 币种（由先锋支付定义）
		dataContet.setUserType("1");// 用户类型（由先锋支付定义）
		dataContet.setAccountType("1");// 账户类型（由先锋支付定义）
		dataContet.setCertificateType("0");// 证件类型（由先锋支付定义）
		params.put("data", JSONObject.toJSONString(dataContet));
		try {
			vo = xianFengPayService.xfPayPostForObject(XianFengServiceEnum.REQ_WITHOIDING.getServiceName(), params,
					XfWithholdResultVO.class);
		} catch (XfNewInstanceException e) {
			vo = new XfWithholdResultVO();
			vo.setResMessage("返回结果类实例化异常，请检查后重试");
		}
		vo.setSuccess(vo.isSuccess());
		vo.setAmountYuan(MoneyUtils.fen2yuan(vo.getAmount()));
		if (StringUtils.isBlank(vo.getTradeNo())) {
			vo.setMerchantNo(op.getMerchantNo());
			vo.setTradeTime(op.getTradeTime());
		}
		if (StringUtils.isBlank(vo.getStatus())) {
			vo.setStatus("I");
		}
		/** 保存代扣记录 */
		saveXfRepayLog(vo, op, payType);
		return vo;
	}

	@Override
	public XfWithholdResultVO xfWithholdQuery(String merchantNo) {
		logger.debug("========先锋单笔订单查询类交易===========");
		XfWithholdResultVO vo = null;
		Map<String, String> params = new HashMap<>();
		params.put("merchantNo", merchantNo);
		try {
			vo = xianFengPayService.xfPayPostForObject(XianFengServiceEnum.REQ_WITHOIDING_QUERY.getServiceName(), params,
					XfWithholdResultVO.class);
		} catch (XfNewInstanceException e) {
			vo = new XfWithholdResultVO();
			vo.setResMessage("返回结果类实例化异常，请检查后重试");
		}
		vo.setSuccess(vo.isSuccess());
		vo.setAmountYuan(MoneyUtils.fen2yuan(vo.getAmount()));
		if (StringUtils.isBlank(vo.getStatus())) {
			vo.setStatus("F");
		}
		return vo;
	}
	
	/**
	 * 
	* @Title: xfWithhold  
	* @Description: 请求先锋代扣接口  
	* @param @param serviceName 先锋接口名
	* @param @param params 接口请求参数
	* @return XfWithholdResultVO    返回类型  
	 */
	/*private XfWithholdResultVO xfWithhold(String serviceName, Map<String, String> params) {
		
		long start = System.currentTimeMillis();
		
		XianFengServiceEnum xianFengServiceEnum = XianFengServiceEnum.get(serviceName);
		String service = xianFengServiceEnum.getServiceName();//接口名称
		String version = xianFengServiceEnum.getVersion();//接口版本
		String secId = xianFengServiceEnum.getSecId();//签名算法
		String desc = xianFengServiceEnum.getDesc();
		
		String merchantId = XianFengConfig.merchantId;//商户号
		String key = XianFengConfig.merRSAKey;//密钥
		String actionUrl = XianFengConfig.gateway;//接入地址
		String sign = null;

		String responseStr = null;
		XianFengResponse response = null;
		XfWithholdResultVO vo = new XfWithholdResultVO();
		try {
			//序列号（本次请求的唯一标识，防止重复提交）
			String reqSn =UnRepeatCodeGenerator.createUnRepeatCode(merchantId, service, new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()));
			if (StringUtils.isNotBlank(params.get("data"))) {
				String dataStr = params.get("data");
//				logger.debug("{}-{}-业务数据：{}",service,desc,dataStr);
				
				dataStr = AESCoder.encrypt(dataStr, key);
				params.put("data",dataStr);
			}
			logger.debug("-------------------------------------------------");
			
			params.put("service",service);
			params.put("secId",secId);
			params.put("version",version);
			params.put("merchantId",merchantId);
			params.put("reqSn",reqSn);
			logger.debug("{}-{}-请求报文：{}",service,desc,params);
			sign = UcfForOnline.createSign(key, "sign", params, "RSA");
			params.put("sign", sign);
//			logger.debug("{}-{}-加密请求报文：{}",service,desc,params);
			logger.debug("-------------------------------------------------");
			
			responseStr = httpUtils.postForJson(actionUrl, params);
//			logger.debug("{}-{}-加密应答结果：{}",service,desc,responseStr);
			responseStr = AESCoder.decrypt(responseStr, key);
			//判断解密是否正确。如果为空则公钥不正确
			if(responseStr.isEmpty()){
				logger.debug("认证支付-{}-检查解密公钥是否正确！",service,desc);
			}
			logger.debug("{}-{}-应答结果：{}",service,desc,responseStr);
			logger.debug("-------------------------------------------------");
			response = JSONObject.parseObject(responseStr, XianFengResponse.class);
			vo = BeanMapper.map(response, XfWithholdResultVO.class);
			vo.setSuccess(response.isSuccess());
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			logger.debug("result="+vo);
		} catch (Exception e) {
			logger.error("{}-{}-先锋接口请求异常",service,desc,e);
		}
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		logger.debug("{}-{}-耗时：{}ms",service,desc,timeCost);
		return vo;
	}*/
	
	/**
	* @Title: saveXfRepayLog  
	* @Description: 保存先锋代扣日志 
	 */
	private RepayLogVO saveXfRepayLog(XfWithholdResultVO vo, XfWithholdOP op,Integer payType) {
		
		CustUserVO user = custUserService.getCustUserById(op.getUserId());

		Date now = new Date();
		RepayLogVO repayLog = new RepayLogVO();
		repayLog.setId(vo.getMerchantNo());
		repayLog.setNewRecord(true);
		repayLog.setApplyId(op.getApplyId());
		repayLog.setContractId(op.getContractId());
//		repayLog.setRepayPlanItemId(param.getRepayPlanItemId());
		repayLog.setUserId(user.getId());
		repayLog.setUserName(user.getRealName());
		repayLog.setIdNo(user.getIdNo());
		repayLog.setMobile(user.getMobile());
		repayLog.setTxType("WITHHOLD");
		repayLog.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
		if (StringUtils.isNotBlank(vo.getTradeTime())){
			repayLog.setTxTime(DateUtils.parse(vo.getTradeTime(), DateUtils.FORMAT_INT_MINITE));
		}
		BigDecimal amount = new BigDecimal(op.getAmount())
				.divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		repayLog.setTxAmt(amount);
//		repayLog.setTxFee(calWithholdFee(txAmt));
		repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
		repayLog.setChlOrderNo(vo.getTradeNo());
		repayLog.setChlName("先锋支付");
		repayLog.setChlCode("XIANFENG");
		repayLog.setBindId(StringUtils.isNotBlank(user.getBindId())? user.getBindId() : user.getProtocolNo());
		repayLog.setBankCode(user.getBankCode());
		repayLog.setCardNo(user.getCardNo());
		repayLog.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
		repayLog.setGoodsName("现金贷分期");
		repayLog.setGoodsNum(1);
		repayLog.setStatus(vo.getStatus());//先锋支付订单状态:I（支付处理中）S（支付成功）F（支付失败）
		repayLog.setRemark(vo.getResMessage());
		repayLog.setPayType(payType);
		if (vo.getSuccess()) {
			BigDecimal succAmt = new BigDecimal(vo.getAmount())
					.divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
			repayLog.setSuccAmt(succAmt);
			repayLog.setSuccTime(now);
			repayLog.setStatus(ErrInfo.SUCCESS.getCode());
		}
		repayLogService.save(repayLog);
		return repayLog;
	}

}
