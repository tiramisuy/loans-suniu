package com.rongdu.loans.pay.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.common.CreditApiVo;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.baofoo.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.message.BaofooRequest;
import com.rongdu.loans.pay.message.BaseDataContent;
import com.rongdu.loans.pay.message.WithholdDataContent;
import com.rongdu.loans.pay.message.WithholdQueryDataContent;
import com.rongdu.loans.pay.message.WithholdQueryResponse;
import com.rongdu.loans.pay.message.WithholdResponse;
import com.rongdu.loans.pay.op.BfWithholdOP;
import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.op.WithholdQueryOP;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.service.BaofooWithholdService;
import com.rongdu.loans.pay.service.PartnerApiService;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.SecurityUtil;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;

@Service(value="baofooWithholdService")
public class BaofooWithholdServiceImpl extends PartnerApiService implements BaofooWithholdService {

	//配置参数
	private String partnerId = BaofooConfig.partner_id;
	private String partnerName = BaofooConfig.partner_name;
	private String bizCode = BaofooConfig.withhold_biz_code;
	private String bizName = BaofooConfig.withhold_biz_name;
	//商户号
	private String memberId = BaofooConfig.member_id;
	//终端号
	private String terminalId = BaofooConfig.withhold_terminal_id;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private BaofooWithdrawService baofooWithdrawService;


	/**
	 * 宝付代扣
	 *
	 * 1.调用宝付代扣接口
	 * 2.如果调用代扣接口结果未知，主动查询结果，
	 * 3.如果代扣成功，调用代付接口；如果结果未知，返回未知失败状态
	 * @param param
	 * @param payType code y0524
	 * @return
	 */
	@Override
	public WithholdResultVO withhold(WithholdOP param,Integer payType) {
		WithholdResultVO vo;
		try {
			vo = transaction(param);
		} catch (Exception e) {
			WithholdQueryResultVO queryResultVO = queryResult(param);
			vo = BeanMapper.map(queryResultVO, WithholdResultVO.class);
			vo.setTransId(queryResultVO.getOrigTransId());
			vo.setTradeDate(queryResultVO.getOrigTradeDate());
			e.printStackTrace();
		}
		/** 保存代扣记录 */
		RepayLogVO repayLogVO = saveRepayLog(vo, param, payType);
		//取消自动代付，由定时任务处理
//		if (vo.getSuccess()){
//			baofooWithdrawService.withraw(repayLogVO);
//		}
		return vo;
	}

	
	/**
	 * 保存还款记录
	 * @param vo
	 * @param param
	 * @param payType code y0524
	 * @return
	 */
	private RepayLogVO saveRepayLog(WithholdResultVO vo, WithholdOP param,Integer payType) {

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
		if (StringUtils.isNotBlank(vo.getTradeDate())){
			repayLog.setTxTime(DateUtils.parse(vo.getTradeDate(), DateUtils.FORMAT_INT_MINITE));
		}
		BigDecimal txAmt = new BigDecimal(param.getTxnAmt())
				.divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
		repayLog.setTxAmt(txAmt);
		repayLog.setTxFee(calWithholdFee(txAmt));
		repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
		repayLog.setChlOrderNo(vo.getTransNo());
		repayLog.setChlName("宝付支付");
		repayLog.setChlCode("BAOFOO");
		repayLog.setBindId(StringUtils.isNotBlank(user.getBindId())? user.getBindId() : user.getProtocolNo());
		repayLog.setBankCode(user.getBankCode());
		repayLog.setCardNo(user.getCardNo());
		repayLog.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
//		repayLog.setGoodsName("聚宝钱包还款");
		repayLog.setGoodsName("聚宝贷还款");
		repayLog.setGoodsNum(1);
		repayLog.setStatus(vo.getCode());
		repayLog.setRemark(vo.getMsg());
		repayLog.setPayType(payType);
		if (vo.getSuccess()) {
			BigDecimal succAmt = new BigDecimal(vo.getSuccAmt())
					.divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
			repayLog.setSuccAmt(succAmt);
			repayLog.setSuccTime(now);
			repayLog.setStatus(ErrInfo.SUCCESS.getCode());
		}
		repayLog.setCouponId(param.getCouponId());
		repayLogService.save(repayLog);
		return repayLog;
	}

	/**
	 * 查询代扣结果
	 * @param param
	 * @return
	 */
	public WithholdQueryResultVO queryResult(WithholdOP param){
		WithholdQueryOP op = new WithholdQueryOP();
		op.setOrigTransId(param.getTransId());
		op.setOrigTradeDate(param.getTradeDate());
		WithholdQueryResultVO queryResultVO = null;
		try {
			queryResultVO = query(op);
		}catch (Exception e){
			logger.error("宝付-代扣：结果未知，param = {}", JsonMapper.getInstance().toJson(param));
			return getUnknowStatusVO();
		}
		if (queryResultVO == null) {
			logger.error("宝付-代扣：结果未知，param = {}", JsonMapper.getInstance().toJson(param));
			return getUnknowStatusVO();
		}
		return queryResultVO;
	}
	/**
	 * 查询代扣结果
	 * @param param
	 * @return
	 */
	public WithholdQueryResultVO queryWithholdResult(WithholdQueryOP op){
		WithholdQueryResultVO queryResultVO = null;
		try {
			queryResultVO = query(op);
		}catch (Exception e){
			logger.error("宝付-代扣：结果未知，param = {}", JsonMapper.getInstance().toJson(op));
			return getUnknowStatusVO();
		}
		if (queryResultVO == null) {
			logger.error("宝付-代扣：结果未知，param = {}", JsonMapper.getInstance().toJson(op));
			return getUnknowStatusVO();
		}
		return queryResultVO;
	}

	/**
	 * 定义未知状态返回值
	 * @return
	 */
	private WithholdQueryResultVO getUnknowStatusVO(){
		WithholdQueryResultVO queryResultVO = new WithholdQueryResultVO();
		queryResultVO.setCode("SYS_ERR");
		queryResultVO.setMsg("系统异常，结果未知");
		return queryResultVO;
	}

	/**
	 * 代扣交易
	 * @param param
	 * @return
	 */
	public WithholdResultVO transaction(WithholdOP param) {
		logger.debug("========13-代扣类交易===========");
		WithholdDataContent content = new WithholdDataContent();
		content.setTerminal_id(terminalId);
		content.setMember_id(memberId);
		content.setPay_code(param.getBankCode());
		content.setAcc_no(param.getCardNo());
		content.setId_card(param.getIdNo());
		content.setId_holder(param.getRealName());
		content.setMobile(param.getMobile());
		content.setValid_date(param.getValidDate());
		content.setValid_no(param.getValidNo());
		content.setTrans_id(param.getTransId());
		content.setTrade_date(param.getTradeDate());
		content.setTxn_amt(param.getTxnAmt());

		WithholdResponse response = (WithholdResponse)sendRequest(content,WithholdResponse.class);
		WithholdResultVO vo = BeanMapper.map(response, WithholdResultVO.class);
		vo.setSuccess(response.isSuccess());
		vo.setTransNo(response.getTrans_no());
		vo.setSuccAmt(response.getSucc_amt());
		vo.setTradeDate(response.getTrade_date());
		vo.setTransId(response.getTrans_id());
		vo.setTransSerialNo(response.getTrans_serial_no());
		return vo;
	}
	
	
	
	/**
	 * 宝付四要素代扣
	 * @param op
	 * @return
	 */
	public WithholdResultVO handerTransaction(BfWithholdOP op){
		logger.debug("========13-代扣类交易----宝付四要素代扣===========");
		WithholdDataContent content = new WithholdDataContent();
		content.setTerminal_id(terminalId);
		content.setMember_id(memberId);
		content.setPay_code(op.getBankCode());
		content.setAcc_no(op.getCardNo());
		content.setId_card(op.getIdNo());
		content.setId_holder(op.getRealName());
		content.setMobile(op.getMobile());
		content.setValid_date(op.getValidDate());
		content.setValid_no(op.getValidNo());
		content.setTrans_id(op.getTransId());
		content.setTrade_date(op.getTradeDate());
		content.setTxn_amt(new BigDecimal(op.getTxnAmt()).multiply(BigDecimal.valueOf(100)).toString());
		WithholdResponse response = (WithholdResponse)sendRequest(content,WithholdResponse.class);
		WithholdResultVO vo = BeanMapper.map(response, WithholdResultVO.class);
		vo.setSuccess(response.isSuccess());
		vo.setTransNo(response.getTrans_no());
		vo.setSuccAmt(response.getSucc_amt());
		vo.setTradeDate(response.getTrade_date());
		vo.setTransId(response.getTrans_id());
		vo.setTransSerialNo(response.getTrans_serial_no());
		
		
		/** 保存代扣记录 */
		RepayLogVO repayLogVO =bfSaveRepayLog(vo,op);
		return vo;
	}
	
	
	private RepayLogVO bfSaveRepayLog(WithholdResultVO vo, BfWithholdOP param) {
		logger.debug("========宝付四要素代扣=====保存流水记录======");
		Date now = new Date();
		RepayLogVO repayLog = new RepayLogVO();
		repayLog.setId(vo.getTransId());
		repayLog.setNewRecord(true);
		repayLog.setUserName(param.getRealName());
		repayLog.setIdNo(param.getIdNo());
		repayLog.setMobile(param.getMobile());
		repayLog.setTxType("WITHHOLD");
		repayLog.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
		if (StringUtils.isNotBlank(vo.getTradeDate())){
			repayLog.setTxTime(DateUtils.parse(vo.getTradeDate(), DateUtils.FORMAT_INT_MINITE));
		}
		BigDecimal txAmt = new BigDecimal(param.getTxnAmt());
		repayLog.setTxAmt(txAmt);
		repayLog.setTxFee(calWithholdFee(txAmt));
		repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
		repayLog.setChlOrderNo(vo.getTransNo());
		repayLog.setChlName("宝付支付");
		repayLog.setChlCode("BAOFOO");
	//	repayLog.setBindId(StringUtils.isNotBlank(user.getBindId())? user.getBindId() : user.getProtocolNo());
		repayLog.setBankCode(param.getBankCode());
		repayLog.setCardNo(param.getCardNo());
		repayLog.setBankName(param.getBankName());
//		repayLog.setGoodsName("聚宝钱包还款");
		repayLog.setGoodsName("宝付手动代扣");
		repayLog.setGoodsNum(1);
		repayLog.setStatus(vo.getCode());
		repayLog.setRemark(vo.getMsg());
		repayLog.setOrderInfo(param.getRemark());
		repayLog.setTxType(param.getTxType());
		if (vo.getSuccess()) {
			repayLog.setSuccAmt(new BigDecimal(param.getTxnAmt()));
			repayLog.setSuccTime(now);
			repayLog.setStatus(ErrInfo.SUCCESS.getCode());
		}
		repayLogService.save(repayLog);
		return repayLog;
	}

	
	
	
	

	/**
	 * 代扣查询
	 * @param param
	 * @return
	 */
	public WithholdQueryResultVO query(WithholdQueryOP param) {
		logger.debug("========31-代扣交易状态查询类交易===========");
		WithholdQueryDataContent content = new WithholdQueryDataContent();
		content.setOrig_trade_date(param.getOrigTradeDate());
		content.setOrig_trans_id(param.getOrigTransId());
		content.setTerminal_id(terminalId);
		content.setMember_id(memberId);
		
		WithholdQueryResponse response = (WithholdQueryResponse)sendRequest(content,WithholdQueryResponse.class);
		WithholdQueryResultVO vo = BeanMapper.map(response, WithholdQueryResultVO.class);
		vo.setSuccess(response.isSuccess());
		vo.setOrderStat(response.getOrder_stat());
		vo.setTransSerialNo(response.getTrans_serial_no());
		vo.setSuccAmt(response.getSucc_amt());
		vo.setTransNo(response.getTrans_no());
		vo.setOrigTradeDate(response.getOrig_trade_date());
		vo.setOrigTransId(response.getOrig_trans_id());
		return vo;
	}

	/**
	 * 发送请求
	 * @param content
	 * @param clazz
	 * @return
	 */
	private Object sendRequest(BaseDataContent content, Class<?> clazz) {
		//测试地址
		String url = BaofooConfig.auth_pay_url;
		//商户私钥
		String  pfxpath = this.getClass().getResource(BaofooConfig.keystore_path).getPath();
		//宝付公钥
		String  cerpath = this.getClass().getResource(BaofooConfig.withhold_pubkey_path).getPath();
		BaofooRequest request = new BaofooRequest();
		request.setTxn_sub_type(content.getTxn_sub_type());
		String reqString = "";
		reqString = JsonMapper.toJsonString(content);
		long start = System.currentTimeMillis();
		logger.debug("{}-{}-请求报文：{}",partnerName,bizName,reqString);
		String base64str = null;
		Map<String, String> params = null;
		String respString = null;
		try {
			base64str = SecurityUtil.Base64Encode(reqString);
			String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,BaofooConfig.keystore_password);
			request.setTerminal_id(terminalId);
//			request.setMember_id(member_id);
			request.setData_content(dataContent);
			params = BeanUtils.describe(request);
			logger.debug("{}-{}-请求地址：{}",partnerName,bizName,url);
			logger.debug("{}-{}-请求报文：{}",partnerName,bizName,params);
			respString = httpUtils.getForJson(url, params);
			logger.debug("{}-{}-应答结果：{}",partnerName,bizName,respString);
			respString = RsaCodingUtil.decryptByPubCerFile(respString,cerpath);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//判断解密是否正确。如果为空则宝付公钥不正确
		if(respString.isEmpty()){
			logger.debug("认证支付-{}-检查解密公钥是否正确！",content.getTxn_sub_type());
		}
		try {
			respString = SecurityUtil.Base64Decode(respString);
			logger.debug("{}-{}-应答结果：{}",partnerName,bizName,respString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(respString, clazz);
		CreditApiVo vo= (CreditApiVo)response;
		boolean success = vo.isSuccess();
		String code = vo.getCode();
		String msg = vo.getMsg();
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		saveApiInvokeLog(partnerId,partnerName,bizCode,bizName,timeCost,success,code,msg);
		logger.debug("{}-{}-耗时：{}ms",partnerName,bizName,timeCost);

		return response;
	}

	/**
	 * 计算每笔代扣交易费用
	 * 10万及以下，2.00元/笔
	 * 10万以上，5.00/笔
	 *
	 * @param fee 单位元
	 * @return
	 */
	private BigDecimal calWithholdFee(BigDecimal fee) {
		if (fee.compareTo(new BigDecimal(100000)) > 0) {
			return BigDecimal.valueOf(5);
		}
		return BigDecimal.valueOf(2);
	}
}
