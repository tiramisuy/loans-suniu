package com.rongdu.loans.deposit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.http.ClientResult;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.bankDeposit.option.AgreeWithdrawOP;
import com.rongdu.loans.bankDeposit.option.OpenAccountOP;
import com.rongdu.loans.bankDeposit.option.TermsAuthOP;
import com.rongdu.loans.bankDeposit.service.BankDepositService;
import com.rongdu.loans.bankDeposit.vo.AccountDetailVO;
import com.rongdu.loans.bankDeposit.vo.AccountVO;
import com.rongdu.loans.bankDeposit.vo.AgreeWithdrawVO;
import com.rongdu.loans.bankDeposit.vo.AuthQueryResultVO;
import com.rongdu.loans.bankDeposit.vo.AuthQueryVO;
import com.rongdu.loans.bankDeposit.vo.OpenAccountResultVO;
import com.rongdu.loans.bankDeposit.vo.SMCodeVO;
import com.rongdu.loans.bankDeposit.vo.TermsAuthResultVO;
import com.rongdu.loans.bankDeposit.vo.TermsAuthVO;
import com.rongdu.loans.bankDeposit.vo.TransactionListVO;
import com.rongdu.loans.bankDeposit.vo.TransactionRecordVO;
import com.rongdu.loans.basic.entity.OpenAccountResult;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.AccountTxTypeEnum;
import com.rongdu.loans.enums.BankDepositRetCodeEnum;
import com.rongdu.loans.enums.LoanProductEnum;

/**
 * 银行存管服务类
 * 
 * @author likang
 * 
 */
@Service("bankDepositService")
public class BankDepositServiceImpl extends BaseService implements BankDepositService {

	/**
	 * 用户信息-实体管理接口
	 */
	@Autowired
	private CustUserManager custUserManager;

	@Override
	public SMCodeVO sendBankDepositSMCode(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			logger.error("the mobile is null");
			return null;
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.smcode.url");
		// 参数map设置
		Map<String, String> params = new HashMap<String, String>();
		params.put(Global.BANKDEPOSIT_SMCODE_SRVTXCODE_KEY, Global.BANKDEPOSIT_SMCODE_SRVTXCODE_VAL);
		params.put(Global.BANKDEPOSIT_SMCODE_SN_KEY, mobile);
		params.put(Global.BANKDEPOSIT_SMCODE_MOBILE_KEY, mobile);
		return doCunguanSms(url, params, mobile);
	}

	@Override
	public SMCodeVO sendTermsAuthSMCode(String mobile, String userId) {
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(userId)) {
			logger.error("the param is null");
			return null;
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.termsAuthSmCode.url");
		// 参数map设置
		Map<String, String> params = new HashMap<String, String>();
		params.put(Global.BANKDEPOSIT_SMCODE_SRVTXCODE_KEY, Global.BANKDEPOSIT_SMCODE_SRVTXCODE_VAL_2);
		params.put(Global.BANKDEPOSIT_SMCODE_SN_KEY, mobile);
		params.put(Global.BANKDEPOSIT_SMCODE_MOBILE_KEY, mobile);
		params.put(Global.BANKDEPOSIT_SMCODE_REQTYPE_KEY, Global.BANKDEPOSIT_SMCODE_REQTYPE_TERMSAUTH);
		// 电子账户
		CustUser custUser = custUserManager.getById(userId);
		if (null != custUser && StringUtils.isNotBlank(custUser.getAccountId())) {
			params.put(Global.BANKDEPOSIT_CARDNO_KEY, custUser.getAccountId());
		} else {
			logger.error("the cust:[{}] is not openAccount");
		}
		return doCunguanSms(url, params, mobile);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param url
	 * @param params
	 * @param mobile
	 * @return
	 */
	private SMCodeVO doCunguanSms(String url, Map<String, String> params, String mobile) {

		if (logger.isDebugEnabled()) {
			logger.debug("the url is:[{}], param is:[{}]", url, JsonMapper.toJsonString(params));
		}
		// rest模式调用接口
		ClientResult clientResult = (ClientResult) RestTemplateUtils.getInstance().postForObject(url, params,
				ClientResult.class);
		// 结果处理
		if (null != clientResult && StringUtils.equals(Global.BANKDEPOSIT_SUCCSS_4, clientResult.getCode())) {
			Map<?, ?> resultMap = (Map<?, ?>) clientResult.getResult();
			// 结果判断 失败的情况打印日志
			if (null == resultMap
					|| !StringUtils.equals(Global.BANKDEPOSIT_SUCCSS_8,
							(String) resultMap.get(Global.BANKDEPOSIT_RETCODE_KEY))) {
				logger.error("[{}]存管业务,[{}]类型,发送短信验证码返回信息：[{}]", mobile,
						(String) resultMap.get(Global.BANKDEPOSIT_SRVAUTHCODE_KEY),
						(String) resultMap.get(Global.BANKDEPOSIT_RETMSG_KEY));
				return null;
			}
			// 业务授权码
			SMCodeVO resultVO = new SMCodeVO();
			resultVO.setSrvAuthCode((String) resultMap.get(Global.BANKDEPOSIT_SRVAUTHCODE_KEY));
			String smsSeq = (String) resultMap.get(Global.BANKDEPOSIT_SMSSEQ_KEY);
			if (logger.isDebugEnabled()) {
				logger.debug("smsSeq:[{}]; clientResult:[{}]", smsSeq, clientResult);
			}
			if (StringUtils.isBlank(smsSeq)) {
				resultVO.setSmsSeq("");
			} else {
				resultVO.setSmsSeq(smsSeq);
			}
			return resultVO;
		}
		return null;
	}

	@Override
	public OpenAccountResultVO openBankDepositAccount(OpenAccountOP openAccountOP, String productId) {
		// 构造返回参数
		OpenAccountResultVO resultVO = new OpenAccountResultVO();
		if (null == openAccountOP || StringUtils.isBlank(openAccountOP.getMobile())) {
			logger.error("开户错误:the mobile is null");
			resultVO.setCode("FAIL");
			resultVO.setMsg("参数为空");
			return resultVO;
		}

		// 设置资产合作单位码
		openAccountOP.setAssetCode(Global.ASSET_CODE_JUQIANBAO);

		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.opentflaccount.url");
		// 用户类型 ：大额分期7 小额分期8
		if(StringUtils.isBlank(productId) || LoanProductEnum.XJDFQ.getId().equals(productId)){
			openAccountOP.setV("7");
		} else {
			openAccountOP.setV("8"); 
		}
		// 参数map设置
		Map<String, String> params = null;
		try {
			params = BeanMapper.describe(openAccountOP);
		} catch (Exception e) {
			logger.error("开户错误：[{}]", e);
			resultVO.setCode("FAIL");
			resultVO.setMsg("参数序列化异常");
			return resultVO;
		}
		// rest模式调用接口
		logger.info("[{}]开户http请求：[{}]", openAccountOP.getIdCard(), JsonMapper.toJsonString(params));
		OpenAccountResult openAccountResult = (OpenAccountResult) RestTemplateUtils.getInstance().postForObject(url,
				params, OpenAccountResult.class);
		logger.info("[{}]开户http响应：[{}]", openAccountOP.getIdCard(), JsonMapper.toJsonString(openAccountResult));
		// 结果处理
		if (null != openAccountResult
				&& StringUtils.equals(Global.BANKDEPOSIT_SUCCSS_OK, openAccountResult.getStatus())) {
			// 如果返回结果不为空,并且状态为OK,解析结果.
			Map<?, ?> resultMap = (Map<?, ?>) openAccountResult.getData();
			// 判断开户前后是否为同一人开户
			if (openAccountOP.getIdCard().equals((String)resultMap.get("idcard"))) {
				// 开户成功
				resultVO.setSuccess(true);
				resultVO.setCode(Global.BANKDEPOSIT_RESULT_APP);
				resultVO.setMsg(openAccountResult.getMessage());
				// 电子账户,取值转型
				resultVO.setAccountId(resultMap.get("uid").toString());
				if(resultMap.get("url") != null){
					resultVO.setUrl(resultMap.get("url").toString());
				}
				if(resultMap.get("open_account") != null){
					resultVO.setOpen_account(Integer.parseInt(resultMap.get("open_account").toString()));
				}
				
				
			} else {
				logger.info("开户前后身份证对比：{},{}", openAccountOP.getIdCard(), resultMap.get("idcard"));
				resultVO.setCode("FAIL");
				resultVO.setMsg("身份证不一致");			
			}	
			return resultVO;
		}
		// 如果返回结果为空
		if (null == openAccountResult) {
			resultVO.setCode("FAIL");
			resultVO.setMsg("网络异常");
		} else {
			// 如果返回结果不为空,状态为NOK.
			resultVO.setCode("FAIL");
			if (StringUtils.isBlank(openAccountResult.getMessage())) {
				resultVO.setMsg("网络异常");
			} else {
				resultVO.setMsg(openAccountResult.getMessage());
			}
		}
		return resultVO;
	}

	@Override
	public List<TransactionRecordVO> getTransactionRecordS(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			logger.error("the mobile is null");
			return null;
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.accountdetailsquery.url");
		// 参数map设置
		Map<String, String> params = new HashMap<String, String>();
		params.put(Global.BANKDEPOSIT_QUERY_TYPE_KEY, Global.BANKDEPOSIT_QUERY_TYPE_VAL);
		params.put(Global.BANKDEPOSIT_QUERY_BEGINDATE_KEY, DateUtils.formatDate(
				DateUtils.addMonth(new Date(), Global.BANKDEPOSIT_QUERY_SUB_MONTH), DateUtils.FORMAT_INT_DATE));
		params.put(Global.BANKDEPOSIT_QUERY_ENDDATE_KEY, DateUtils.formatDate(new Date(), DateUtils.FORMAT_INT_DATE));
		// params.put(Global.BANKDEPOSIT_QUERY_BEGINDATE_KEY,
		// "20170417");
		// params.put(Global.BANKDEPOSIT_QUERY_ENDDATE_KEY,
		// "20170418");
		params.put(Global.BANKDEPOSIT_QUERY_PHONENUMBER_KEY, mobile);
		params.put(Global.BANKDEPOSIT_QUERY_PAGE_KEY, Global.BANKDEPOSIT_QUERY_PAGE_VAL);
		params.put(Global.BANKDEPOSIT_QUERY_ROWS_KEY, Global.BANKDEPOSIT_QUERY_ROWS_VAL);
		// rest模式调用接口
		String respString = RestTemplateUtils.getInstance().getForJson(url, params);
		TransactionListVO vo = (TransactionListVO) JsonMapper.fromJsonString(respString, TransactionListVO.class);
		// 构造返回对象
		List<TransactionRecordVO> rz = new ArrayList<TransactionRecordVO>();
		if (null != vo && vo.getTotal() > 0) {
			List<AccountDetailVO> rows = vo.getRows();
			if (null != rows && rows.size() > 0) {
				int size = rows.size();
				TransactionRecordVO tmpVo = null;
				for (int i = 0; i < size; i++) {
					AccountDetailVO temp = rows.get(i);
					if (null != temp) {
						// 交易类型适配
						String tranType = getTxTypeDesc(temp.getTranType());
						if (StringUtils.isBlank(tranType)) {
							// 跳出循环
							continue;
						}
						tmpVo = new TransactionRecordVO();
						// 交易类型
						tmpVo.setTxTpye(tranType);
						// 交易时间
						tmpVo.setTxTime(getTime(temp.getInpDate(), temp.getInpTime()));
						// 交易金额
						tmpVo.setTxAmount(temp.getTxFlag() + temp.getTxAmount());
						rz.add(tmpVo);
					}
				}
			}
		}
		return rz;
	}

	/**
	 * 拼接交易时间
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	private String getTime(String date, String time) {
		StringBuilder tiemBf = new StringBuilder();
		if (null != date && date.length() == 8) {
			tiemBf.append(date.substring(0, 4)).append("-").append(date.substring(4, 6)).append("-")
					.append(date.substring(6, 8));

		}
		if (null != time && time.length() > 4) {
			tiemBf.append(" ").append(time.substring(0, 2)).append(":").append(time.substring(2, 4));
		}
		return tiemBf.toString();
	}

	private String getTxTypeDesc(String tranTypeCode) {
		String rz = "";
		int tranType = tranTypeCode == null ? 0 : Integer.parseInt(tranTypeCode);
		// 适配交易类型
		switch (tranType) {
		// case Global.TX_TYPE_CASH:
		// rz = AccountTxTypeEnum.CASH.getDesc();
		// break;
		// case Global.TX_TYPE_CASH_2:
		// rz = AccountTxTypeEnum.CASH.getDesc();
		// break;
		case Global.TX_TYPE_LENDERS:
			rz = AccountTxTypeEnum.LENDERS.getDesc();
			break;
		case Global.TX_TYPE_REPAY:
			rz = AccountTxTypeEnum.REPAY.getDesc();
			break;
		case Global.TX_TYPE_SERV_FEE:
			rz = AccountTxTypeEnum.SERV_FEE.getDesc();
			break;
		case Global.TX_TYPE_SERV_FEE_2:
			rz = AccountTxTypeEnum.SERV_FEE.getDesc();
			break;
		// case Global.TX_TYPE_OVERDUE_FEE:
		// rz = AccountTxTypeEnum.OVERDUE_FEE.getDesc();
		// break;
		// case Global.TX_TYPE_OVERDUE_FEE_2:
		// rz = AccountTxTypeEnum.OVERDUE_FEE.getDesc();
		// break;
		default:
			rz = "";
		}
		return rz;
	}

	@Override
	public String getAccountId(String idNo, String bindBankCode) {
		if (StringUtils.isBlank(idNo)) {
			logger.error("the idNo is null");
			return null;
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.accountIdQuery.url");
		// 参数map设置
		Map<String, String> params = new HashMap<String, String>();
		params.put(Global.BANKDEPOSIT_QUERY_IDNO_KEY, idNo);
		params.put(Global.BANKDEPOSIT_QUERY_IDTYPE_KEY, Global.BANKDEPOSIT_QUERY_IDTYPE_VAL);
		// rest模式调用接口
		String respString = RestTemplateUtils.getInstance().getForJson(url, params);
		AccountVO accountVO = (AccountVO) JsonMapper.fromJsonString(respString, AccountVO.class);
		if (null != accountVO && null != accountVO.getAccountId()
				&& StringUtils.equals(bindBankCode, accountVO.getBankCode())) {
			return accountVO.getAccountId();
		} else if (null == accountVO) {
			logger.error("[{}]查询存管电子账户开户返回异常。", idNo);
		} else {
			logger.error("[{}]查询存管电子账户开户返回异常：[{}], [{}]", idNo, accountVO.getRetCode(), accountVO.getRetMsg());
		}
		return null;
	}

	@Override
	public TermsAuthVO termsAuth(TermsAuthOP termsAuthOP) {
		if (null == termsAuthOP) {
			logger.error("the termsAuthOP is null");
			return null;
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.termsAuth.url");
		// 参数对象转换成参数map
		Map<String, String> paramsMap = null;
		try {
			paramsMap = BeanUtils.describe(termsAuthOP);
		} catch (Exception e) {
			logger.error("object to map error:[{}]", e);
		}
		if (null == paramsMap) {
			logger.error("the param is error");
			return null;
		}
		// 参数补充设置
		// bitMap是否维护标志位
		paramsMap.put(Global.BANKDEPOSIT_BITMAP_KEY, Global.BANKDEPOSIT_BITMAP_VAL);
		// 开通自动投标功能标志 -取消
		paramsMap.put(Global.BANKDEPOSIT_AUTOBID_KEY, Global.BANKDEPOSIT_TERMSAUTH_CHANNEL);
		// 开通自动债转功能标志-取消
		paramsMap.put(Global.BANKDEPOSIT_AUTOTRANSFER_KEY, Global.BANKDEPOSIT_TERMSAUTH_CHANNEL);
		// 开通预约取现功能标志-开通
		paramsMap.put(Global.BANKDEPOSIT_AGREEWITHDRAW_KEY, Global.BANKDEPOSIT_TERMSAUTH_OPEN);
		// 开通预约取现功能标志-取消
		paramsMap.put(Global.BANKDEPOSIT_DIRECTCONSUME_KEY, Global.BANKDEPOSIT_TERMSAUTH_CHANNEL);
		// rest模式调用接口
		String respString = RestTemplateUtils.getInstance().getForJson(url, paramsMap);
		// 返回对象解析
		TermsAuthVO termsAuthVO = (TermsAuthVO) JsonMapper.fromJsonString(respString, TermsAuthVO.class);
		if (null != termsAuthVO) {
			if (StringUtils.equals(termsAuthVO.getCode(), Global.BANKDEPOSIT_SUCCSS_4)) {
				TermsAuthResultVO result = termsAuthVO.getResult();
				if (null != result) {
					termsAuthVO.setAccountId(result.getAccountId());
					termsAuthVO.setOrderId(result.getOrderId());
					termsAuthVO.setName(result.getName());
					// 更新用户信息-授权签约订单号
					CustUser entity = new CustUser();
					entity.setId(termsAuthOP.getUserId());
					entity.setTermsAuthId(result.getOrderId());
					entity.preUpdate();
					int rz = custUserManager.updateTermsAuthId(entity);
					logger.info("updateTermsAuthId result is:[{}]", rz);
				}
			} else {
				logger.error("四合一授权异常:[{}]", termsAuthVO.getMessage());
			}
			termsAuthVO.setResult(null);
		}
		return termsAuthVO;
	}

	@Override
	public String agreeWithdraw(AgreeWithdrawOP agreeWithdrawOP) {
		if (null == agreeWithdrawOP) {
			logger.error("the agreeWithdrawOP is null");
			return null;
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.agreeWithdraw.url");
		// 参数对象转换成参数map
		Map<String, String> paramsMap = null;
		try {
			paramsMap = BeanUtils.describe(agreeWithdrawOP);
		} catch (Exception e) {
			logger.error("object to map error:[{}]", e);
		}
		if (null == paramsMap) {
			logger.error("the param is error");
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("the param is:[{}]", JsonMapper.toJsonString(paramsMap));
		}
		// rest模式调用接口
		String respString = RestTemplateUtils.getInstance().getForJson(url, paramsMap);
		// 返回对象解析
		AgreeWithdrawVO agreeWithdrawVO = (AgreeWithdrawVO) JsonMapper
				.fromJsonString(respString, AgreeWithdrawVO.class);
		if (null != agreeWithdrawVO) {
			if (StringUtils.equals(agreeWithdrawVO.getCode(), Global.BANKDEPOSIT_SUCCSS_4)
					|| StringUtils.equals(agreeWithdrawVO.getCode(), BankDepositRetCodeEnum.FAULURE587.getCode())) {
				return agreeWithdrawVO.getResult().getTxAmount();
			} else {
				logger.error("免密提现异常:[{}]", agreeWithdrawVO.getMessage());
			}
		}
		return null;
	}

	@Override
	public AuthQueryVO authQuery(String accountId) {
		if (StringUtils.isBlank(accountId)) {
			logger.error("the accountId is null");
			return null;
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("deposit.authQuery.url");
		// 参数对象转换成参数map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Global.BANKDEPOSIT_ACCOUNTID_KEY, accountId);

		// rest模式调用接口
		String respString = RestTemplateUtils.getInstance().getForJson(url, paramsMap);
		// 返回对象解析
		AuthQueryVO authQueryVO = (AuthQueryVO) JsonMapper.fromJsonString(respString, AuthQueryVO.class);
		if (null != authQueryVO) {
			if (StringUtils.equals(authQueryVO.getCode(), Global.BANKDEPOSIT_SUCCSS_4)) {
				AuthQueryResultVO result = authQueryVO.getResult();
				if (null != result) {
					String attrFlags = result.getAttrFlags();
					// 自动投标功能
					if (StringUtils.contains(attrFlags, Global.BANKDEPOSIT_AUTOBID_KEY)) {
						authQueryVO.setAutoBidStatus(Global.BANKDEPOSIT_TERMSAUTH_OPEN);
					} else {
						authQueryVO.setAutoBidStatus(Global.BANKDEPOSIT_TERMSAUTH_CHANNEL);
					}
					// 自动债转功能
					if (StringUtils.contains(attrFlags, Global.BANKDEPOSIT_AUTOTRANSFER_KEY)) {
						authQueryVO.setAutoTransferStatus(Global.BANKDEPOSIT_TERMSAUTH_OPEN);
					} else {
						authQueryVO.setAutoTransferStatus(Global.BANKDEPOSIT_TERMSAUTH_CHANNEL);
					}
					// 预约取现功能
					if (StringUtils.contains(attrFlags, Global.BANKDEPOSIT_AGREEWITHDRAW_KEY)) {
						authQueryVO.setAgreeWithdrawStatus(Global.BANKDEPOSIT_TERMSAUTH_OPEN);
					} else {
						authQueryVO.setAgreeWithdrawStatus(Global.BANKDEPOSIT_TERMSAUTH_CHANNEL);
					}
					// 无密消费功能
					if (StringUtils.contains(attrFlags, Global.BANKDEPOSIT_DIRECTCONSUME_KEY)) {
						authQueryVO.setAgreeDeductStatus(Global.BANKDEPOSIT_TERMSAUTH_OPEN);
					} else {
						authQueryVO.setAgreeDeductStatus(Global.BANKDEPOSIT_TERMSAUTH_CHANNEL);
					}
				}
			} else {
				logger.error("四合一授权查询:[{}]", authQueryVO.getMessage());
			}
		}
		return authQueryVO;
	}
}
